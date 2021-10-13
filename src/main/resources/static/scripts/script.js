// Table of Contents
// 1. Classes
// 2. Utilities
// 3. Lists
// 4. Search

/////////////
// Classes //
/////////////

/**
 * JSON object for use with POST requests.
 */
class RequestMessage {
  constructor(id, value) {
    this.id = id;
    this.value = value;
  }
}

///////////////
// Utilities //
///////////////

/**
 * Fetches a URL and parses the JSON response.
 *
 * @param {String} url The URL path
 * @param {String} method The URL method
 * @param {Object} data The JSON request object
 * @returns {Object} Returns the JSON response
 */
async function fetchResponseData(url, method, data) {
  if (!url.includes("http")) {
    var currentUrl = window.location.toString();

    if (currentUrl.includes("simplelibrary.org")) {
      url = "https://simplelibrary.org/" + url;
    }
    else if (currentUrl.includes("localhost:8080")) {
      url = "http://localhost:8080/" + url;
    }
  }

  try {
    const response = await fetch(url, {
      method: method,
      mode: 'cors',
      cache: 'no-cache',
      credentials: 'same-origin',
      headers: { 'Content-Type': 'application/json' },
      redirect: 'follow',
      referrerPolicy: 'no-referrer',
      body: JSON.stringify(data)
    });

    if (response.status == 200) {
      return response.json();
    }
  }
  catch (e) {
    console.log(e);
  }

  return null;
}

///////////
// Lists //
///////////

var itemSourceId;
var itemSourceFilter;

/**
 * Opens the pop-up menu.
 *
 * @param {String} id The list item ID
 * @param {String} filter The list item class filter
 */
function openListPopup(id, filter) {
  document.getElementById("listOverlay").style.display = "block";
  document.getElementById("listOverlay").addEventListener('click', function(e){checkForOverlayClick(e)});
  itemSourceId = id;
  itemSourceFilter = filter;
  var listElements = document.getElementsByClassName("list-row");

  for (var i = 0; i < listElements.length; i++) {
    var listElement = listElements[i];
    var listItemsElement = listElement.getElementsByTagName("td")[0];
    var listItemElementId = itemSourceFilter + itemSourceId;
    var boxElementId = "box" + listElement.id.replace("list", "");

    if (listItemsElement && listItemsElement.getElementsByClassName(listItemElementId).length > 0) {
      document.getElementById(boxElementId).innerHTML = "☑";
    }
    else {
      document.getElementById(boxElementId).innerHTML = "☐";
    }
  }
}

/**
 * Checks if the area outside of the pop-up is clicked.
 */
function checkForOverlayClick(click) {
  if (!document.getElementById('listPopup').contains(click.target)) {
    itemSourceId = null;
    itemSourceFilter = null;
    closeListPopup();
  }
}

/**
 * Closes the pop-up menu.
 *
 * @param {String} method The URL method
 */
function closeListPopup() {
  document.getElementById("listOverlay").style.display = "none";
}

/**
 * Updates a list, either adding or deleting an item.
 *
 * @param {String} listId The list ID
 */
function updateList(listId) {
  var boxElementId = "box" + listId;

  if (document.getElementById(boxElementId).innerHTML.includes("☐")) {
    saveToList(listId);
  }
  else {
    deleteFromList(listId);
  }
}

/**
 * Saves a new list.
 */
function saveList() {
  var newListName = document.getElementById("newListName").value;
  var url = "lists/save";
  var method = "POST";
  var data = new RequestMessage(null, newListName);
  fetchResponseData(url, method, data)
  .then(data => {
    if (data) {
      document.getElementById("newListName").value = "";
      var listId = data.id;
      var newListElement = document.getElementById("listTemplate").cloneNode(true);
      newListElement.id = "list" + listId;
      newListElement.className = "list-row";
      newListElement.style.display = "table-row";
      newListElement.getElementsByTagName("span")[0].id = "box" + listId;
      newListElement.getElementsByTagName("span")[1].innerHTML = newListName;
      newListElement.getElementsByTagName("button")[0].onclick = function() {updateList(listId);};
      newListElement.getElementsByTagName("button")[1].onclick = function() {deleteList(listId);};
      document.getElementById("listTemplate").insertAdjacentElement("beforebegin", newListElement);
      console.log("Created list " + newListName);
      saveToList(listId);
    }
    else {
      console.log("Failed to create list " + newListName);
    }
  });
}

/**
 * Saves a new list item.
 *
 * @param {String} listId The list ID
 */
function saveToList(listId) {
  var url = "lists/" + listId + "/save";
  var method = "POST";
  var data = new RequestMessage(itemSourceId, itemSourceFilter);
  fetchResponseData(url, method, data)
  .then(data => {
    if (data) {
      var boxElementId = "box" + listId;
      var listElementId = "list" + listId;
      var listItemElement = document.createElement("span");
      listItemElement.id = "item" + data.id;
      listItemElement.className = itemSourceFilter + itemSourceId;
      document.getElementById(listElementId).getElementsByTagName("td")[0].append(listItemElement);
      document.getElementById(boxElementId).innerHTML = "☑";
      console.log("Added to list " + listId);
    }
    else {
      console.log("Failed to add to list " + listId);
    }
  });
}

/**
 * Deletes a list.
 *
 * @param {String} listId The list ID
 */
function deleteList(listId) {
  var url = "lists/" + listId;
  var method = "DELETE";
  fetchResponseData(url, method)
  .then(data => {
    if (data) {
      var listElementId = "list" + listId;
      document.getElementById(listElementId).remove();
      console.log("Deleted list " + listId);
    }
    else {
      console.log("Failed to delete list " + listId);
    }
  });
}

/**
 * Deletes a list item.
 *
 * @param {Integer} listId The list ID
 */
function deleteFromList(listId) {
  listElementId = "list" + listId;
  var itemElementClassName = itemSourceFilter + itemSourceId;
  var itemId = document.getElementById(listElementId).getElementsByClassName(itemElementClassName)[0].id.replace("item", "");
  var url = "lists/" + listId + "/" + itemId;
  var method = "DELETE";
  fetchResponseData(url, method)
  .then(data => {
    if (data) {
      var boxElementId = "box" + listId;
      var itemElementId = "item" + itemId;
      document.getElementById(boxElementId).innerHTML = "☐";
      document.getElementById(itemElementId).remove();
      console.log("Removed item from list " + listId);
    }
    else {
      console.log("Failed to remove item from list " + listId);
    }
  });
}

////////////
// Search //
////////////

// For the search page only
if (document.location.pathname.match("search")) {
  document.addEventListener('click', checkResultsPerPageSelection);
}

/**
 * Checks for changes to the results per page selection element.
 */
function checkResultsPerPageSelection() {
  var oldSelection = document.getElementById("resultsPerPage").value;
  var newSelection = document.getElementById("resultsPerPageSelector").value;

  if (newSelection != oldSelection) {
    document.getElementById("resultsPerPage").value = newSelection;
    document.getElementById("searchForm").submit();
  }
}
