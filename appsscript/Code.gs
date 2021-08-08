// Build a spreadsheet with data from Google Books
function fetchBooks() {

  var startTime = new Date();
  var spreadsheet = SpreadsheetApp.getActiveSpreadsheet();
  var bookSheet = spreadsheet.getSheetByName("book");
  var isbnSheet = spreadsheet.getSheetByName("isbn");
  var mapSheet = spreadsheet.getSheetByName("book_category_map");
  var categorySheet = spreadsheet.getSheetByName("category");

  var categoryId = 0;
  var startIndex = 0;
  var additionCount = 0;

  if (bookSheet.getRange("A2").getValue() != "") {
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

      var bookRow = bookSheet.getLastRow() + 1;
      var isbnRow = isbnSheet.getLastRow() + 1;
      var mapRow = mapSheet.getLastRow() + 1;
      var categoryRow = categoryId + 1;

      Logger.log("Row " + bookRow + ": " + bookTitle);

      // Book - 9 columns

      var bookId = bookId;
      var bookTitle = bookTitle;
      var bookDescription = item.volumeInfo.description;
      var bookPublishDate = item.volumeInfo.publishedDate;
      var bookPageCount = item.volumeInfo.pageCount;
      var bookAvailability = "Available";
      var bookTotalBorrows = 0;
      var bookStatus = "Active";
      var bookAddDate = new Date();

      if (!bookDescription) { bookDescription = "NULL"; }
      if (!bookPublishDate) { bookPublishDate = "NULL"; }
      if (!bookPageCount) { bookPageCount = "NULL"; }

      if (bookPublishDate != "NULL") {
        while (bookPublishDate.split("-").length != 3) {
          bookPublishDate += "-01";
        }
      }

      bookSheet.getRange(bookRow, 1, 1, 9).setValues([[
        bookId,
        bookTitle,
        bookDescription,
        bookPublishDate,
        bookPageCount,
        bookAvailability,
        bookTotalBorrows,
        bookStatus,
        bookAddDate
      ]]);

      // Isbn - 5 columns

      var isbnId = "NULL";
      var isbnType = "NULL";
      var isbnStatus = "Active";
      var isbnAddDate = new Date();
      var bookId = bookId;

      var industryIdentifiers = item.volumeInfo.industryIdentifiers;

      for (var i in industryIdentifiers) {
        isbnId = industryIdentifiers[i].identifier;
        isbnType = industryIdentifiers[i].type;

        isbnSheet.getRange(isbnRow++, 1, 1, 5).setValues([[
          isbnId,
          isbnType,
          isbnStatus,
          isbnAddDate,
          bookId
        ]]);
      }

      // BookCategoryMap - 2 columns

      var bookId = bookId;
      var mapCategoryId = categoryId;

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

          mapCategoryId = categoryId;

        } else {
          Logger.log("Found an existing category for " + categoryName[i]);
          mapCategoryId = categorySheet.getRange(index + 2, 1).getValue();
        }

        mapSheet.getRange(mapRow++, 1, 1, 2).setValues([[
          bookId,
          mapCategoryId
        ]]);

      }

      additionCount++;
    }

    startIndex += 10;
    var currentTime = new Date();

  } while (responseJson.items && currentTime.getTime() - startTime.getTime() < 320000);

  Logger.log("Added " + additionCount + " books");
}



// Build an insert query from the spreadsheet values
function buildInsert() {

  var inserts = [];
  var names = ["book", "isbn", "category", "book_category_map"];
  var spreadsheet = SpreadsheetApp.getActiveSpreadsheet();

  for (var i in names) {

    var sheet = spreadsheet.getSheetByName(names[i]);
    var values = sheet.getDataRange().getDisplayValues();
    var columns = "`" + values[0].join("`,`") + "`";

    values.shift(); // Remove the column names

    for (var j in values) {

      for (var k in values[j]) {
        values[j][k] = "'" + values[j][k].toString().replace(/'/g, "''") + "'";
      }

      values[j] = "(" + values[j] + ")";
    }

    values = values.join(",\n  ");
    inserts.push("INSERT INTO `" + names[i] + "`\n  (" + columns + ")\nVALUES\n  " + values + ";");
  }

  var insert = inserts.join("\n\n").replace(/'NULL'/g, "NULL");
  return insert;
}



// Return an insert query for the database
function doGet(e) {
  return ContentService.createTextOutput(buildInsert());
}
