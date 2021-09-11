// Apply script to the search page
if (document.location.pathname.match("search")) {
  document.onclick = checkSelection;
}

// Check for changes to the results per page
async function checkSelection() {
  var oldSelection = document.getElementById("resultsPerPage").value;
  var newSelection = document.getElementById("resultsPerPageSelector").value;

  if (newSelection != oldSelection) {
    document.getElementById("resultsPerPage").value = newSelection;
    document.getElementById("searchForm").submit();
  }
}
