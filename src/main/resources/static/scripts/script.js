var overlayId = "listOverlay";
var uncheckedBox = "☐";
var checkedBox = "☑";
var itemId = "";
var itemType = "";

function openListPopup(id, type) {
  document.getElementById(overlayId).style.display = "block";
  document.getElementById(overlayId).addEventListener('click', function(e){checkForOverlayClick(e)});
  itemId = id;
  itemType = type;
}

function checkForOverlayClick(click) {
  if (!document.getElementById('listPopup').contains(click.target)) {
    closeListPopup();
  }
}

function closeListPopup() {
  document.getElementById(overlayId).style.display = "none";
}

function updateList(listId) {
  var element = document.getElementById(listId);

  if (element.innerHTML.indexOf(uncheckedBox) != -1) {
    saveToList(listId);
  }
  else {
    deleteFromList(listId);
  }
}

function saveList() {
  var newListId = document.getElementById("newListId").value;
  console.log("Save lists/" + newListId)
}

function saveToList(listId) {
  console.log("Save " + itemType + "/" + itemId + " to lists/" + listId)
  var oldValue = document.getElementById(listId).innerHTML;
  document.getElementById(listId).innerHTML = oldValue.replace(uncheckedBox, checkedBox);
}

function deleteList(listId) {
  console.log("Delete lists/" + listId)
}

function deleteFromList(listId) {
  console.log("Delete " + itemType + "/" + itemId + " from lists/" + listId)
  var oldValue = document.getElementById(listId).innerHTML;
  document.getElementById(listId).innerHTML = oldValue.replace(checkedBox, uncheckedBox);
}

// For the search page only
if (document.location.pathname.match("search")) {
  document.addEventListener('click', checkResultsPerPageSelection);
}

// Check for changes to the results per page
function checkResultsPerPageSelection() {
  var oldSelection = document.getElementById("resultsPerPage").value;
  var newSelection = document.getElementById("resultsPerPageSelector").value;

  if (newSelection != oldSelection) {
    document.getElementById("resultsPerPage").value = newSelection;
    document.getElementById("searchForm").submit();
  }
}
