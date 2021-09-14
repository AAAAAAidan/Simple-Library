// Table of Contents
// 1. Classes
// 2. Utilities
// 3. Lists
// 4. Search

/////////////
// Classes //
/////////////

class RequestMessage {
  constructor(id, value) {
    this.id = id;
    this.value = value;
  }
}

///////////////
// Utilities //
///////////////

async function fetchResponseData(url, method, data) {
  const response = await fetch(url, {
    method: method,
    mode: 'cors',
    cache: 'no-cache',
    credentials: 'same-origin',
    headers: { 'Content-Type': 'application/json' },
    redirect: 'follow',
    referrerPolicy: 'no-referrer',
    body: JSON.stringify(data)
  })
  .then(response => {
    if (response.status == 200) {
      return response.json();
    }
  })
  .catch(error => {
    console.error('Error:', error);
  });

  return null;
}

///////////
// Lists //
///////////

var itemSourceId;
var itemSourceType;
var elementId;
var catalogMap = {};

function openListPopup(id, type) {
  document.getElementById("listOverlay").style.display = "block";
  document.getElementById("listOverlay").addEventListener('click', function(e){checkForOverlayClick(e)});
  itemSourceId = id;
  itemSourceType = type;
}

function checkForOverlayClick(click) {
  if (!document.getElementById('listPopup').contains(click.target)) {
    closeListPopup();
  }
}

function closeListPopup() {
  document.getElementById("listOverlay").style.display = "none";
}

function updateList(listId) {
  elementId = "box" + listId;

  if (document.getElementById(elementId).innerHTML.includes("☐")) {
    saveToList(listId, elementId);
  }
  else {
    deleteFromList(listId);
  }
}

function saveList() {
  var newListName = document.getElementById("newListName").value;
  var url = "lists/save";
  var method = "POST";
  var data = new RequestMessage(null, newListName);
  var responseData = fetchResponseData(url, method, data);

  if (responseData) {
    var listId = responseData.
    console.log("Created list " + newListName);
  }
  else {
    console.log("Failed to create list " + newListName);
  }
}

function saveToList(listId) {
  var url = "lists/" + listId;
  var method = "POST";
  var data = new RequestMessage(itemSourceId, itemSourceType);
  var responseData = fetchResponseData(url, method, data);

  if (responseData) {
    document.getElementById(elementId).innerHTML = "☑";
    console.log("Added to list " + listId);
  }
  else {
    console.log("Failed to add to list " + listId);
  }
}

function deleteList(listId) {
  var url = "lists/" + listId;
  var method = "DELETE";
  var responseData = fetchResponseData(url, method)

  if (responseData) {
    console.log("Deleted list " + listId);
  }
  else {
    console.log("Failed to delete list " + listId);
  }
}

// TODO - Fix this
function deleteFromList(listId) {
  var itemId = document.getElementById(elementId);
  var url = "lists/" + listId + "/" + itemId;
  var method = "DELETE";
  var responseData = fetchResponseData(url, method);

  if (responseData) {
    document.getElementById(elementId).innerHTML = "☐";
    console.log("Removed item from list " + listId);
  }
  else {
    console.log("Failed to remove item from list " + listId);
  }
}

////////////
// Search //
////////////

// For the search page only
if (document.location.pathname.match("search")) {
  document.addEventListener('click', checkResultsPerPageSelection);
}

// Check for changes to the results per page selection
function checkResultsPerPageSelection() {
  var oldSelection = document.getElementById("resultsPerPage").value;
  var newSelection = document.getElementById("resultsPerPageSelector").value;

  if (newSelection != oldSelection) {
    document.getElementById("resultsPerPage").value = newSelection;
    document.getElementById("searchForm").submit();
  }
}
