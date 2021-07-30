<div>
  <h2>Search the library!</h2>
  <form action="javascript:search()" method="post">
    <input type="text" name="searchTerms" placeholder="Enter search terms"/>
    <input type="submit" value="Search"/>
    <select name="filter">
      <option value="unfiltered">Books</option>
      <option value="authors">Authors</option>
      <option value="publishers">Publishers</option>
      <option value="genres">Genres</option>
      <option value="lists">Lists</option>
    </select>
      <select name="orderby" selectedIndex=2>
      <option value="date">By date</option>
      <option value="name">By name</option>
    </select>
      <select name="sort">
      <option value="descending">Descending</option>
      <option value="ascending">Ascending</option>
    </select>
  </form>
  <p id="result"></p>
</div>