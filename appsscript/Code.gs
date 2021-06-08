function fetchBooks()
{
  var startTime = new Date();
  var spreadsheet = SpreadsheetApp.getActiveSpreadsheet();
  var bookSheet = spreadsheet.getSheetByName("Book");
  var keySheet = spreadsheet.getSheetByName("Key");
  var categorySheet = spreadsheet.getSheetByName("Category");

  var keyId = 0;
  var categoryId = 0;
  var startIndex = 0;
  var additionCount = 0;

  if (bookSheet.getRange("A2").getValue() != "") {
    keyId = keySheet.getLastRow() - 1;
    categoryId = categorySheet.getLastRow() - 1;
  }

  do {

    var parameters = "?q=a&country=US&download=epub&filter=full&maxResults=40&orderBy=newest&printType=books&projection=full&startIndex=" + startIndex;
    var url = "https://www.googleapis.com/books/v1/volumes" + parameters;
    var responseText = UrlFetchApp.fetch(url).getContentText();
    var responseJson = JSON.parse(responseText);

    Logger.log(url);
    Logger.log("Found " + responseJson.totalItems + " items");

    for (i in responseJson.items) {

      var item = responseJson.items[i];
      var bookId = item.id;
      var bookTitle = item.volumeInfo.title;
      var subtitle = item.volumeInfo.subtitle;

      if (subtitle) {
        bookTitle += ": " + subtitle;
      }

      var ids = bookSheet.getRange("A2:A").getValues();
      var index = ids.findIndex(ids => {return ids[0] == bookId});

      if (index != -1) {
        Logger.log("Skipping " + bookTitle);
        continue;
      }

      var row = bookSheet.getLastRow() + 1;
      var keyRow = keyId + 1;
      var categoryRow = categoryId + 1;

      Logger.log("Row " + row + ": " + bookTitle);

      // Book - 10 columns

      var bookId = bookId;
      var bookTitle = bookTitle;
      var bookIdentifiers = JSON.stringify(item.volumeInfo.industryIdentifiers);
      var bookDescription = item.volumeInfo.description;
      var bookPublishDate = item.volumeInfo.publishedDate;
      var bookPageCount = item.volumeInfo.pageCount;
      var bookAvailability = "Available";
      var bookTotalBorrows = 0;
      var bookStatus = "Active";
      var bookAddDate = new Date();

      if (!bookIdentifiers) {
        bookIdentifiers = "NULL";
      }

      if (!bookDescription) {
        bookDescription = "NULL";
      }

      if (!bookPublishDate) {
        bookPublishDate = "NULL";
      }

      if (!bookPageCount) {
        bookPageCount = "NULL";
      }

      bookSheet.getRange(row, 1, 1, 10).setValues([[
        bookId,
        bookTitle,
        bookIdentifiers,
        bookDescription,
        bookPublishDate,
        bookPageCount,
        bookAvailability,
        bookTotalBorrows,
        bookStatus,
        bookAddDate
      ]]);

      // Key - 6 columns

      var keyId = keyId;
      var keyStatus = "Active";
      var keyAddDate = new Date();
      var bookId = bookId;
      var keyCategoryId = categoryId;
      var listId = "NULL";

      // Category - 6 columns

      var categoryId = categoryId;
      var categoryName = [];
      var categoryType = [];

      for (var i in item.volumeInfo.authors) {
        categoryName.push(item.volumeInfo.authors[i]);
        categoryType.push("Author");
      }

      if (item.volumeInfo.publisher) {
        categoryName.push(item.volumeInfo.publisher);
        categoryType.push("Publisher");
      }

      for (var i in item.volumeInfo.categories) {
        categoryName.push(item.volumeInfo.categories[i]);
        categoryType.push("Subject");
      }

      var categoryDescription = "NULL";
      var categoryStatus = "Active";
      var categoryAddDate = new Date();

      for (var i in categoryName) {

        var names = categorySheet.getRange("B2:B").getValues();
        var index = names.findIndex(names => {return names[0] == categoryName[i]});

        if (index == -1) {

          categorySheet.getRange(++categoryRow, 1, 1, 6).setValues([[
            ++categoryId,
            categoryName[i],
            categoryType[i],
            categoryDescription,
            categoryStatus,
            categoryAddDate
          ]]);

          keyCategoryId = categoryId;

        } else {
          Logger.log("Found an existing category for " + categoryName[i]);
          keyCategoryId = categorySheet.getRange(index + 2, 1).getValue();
        }

        keySheet.getRange(++keyRow, 1, 1, 6).setValues([[
          ++keyId,
          keyStatus,
          keyAddDate,
          bookId,
          keyCategoryId,
          listId
        ]]);

      }

      additionCount++;
    }

    startIndex += 10;
    var currentTime = new Date();

  } while (responseJson.items && currentTime.getTime() - startTime.getTime() < 300000);

  Logger.log("Added " + additionCount + " books");
}
