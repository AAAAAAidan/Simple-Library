<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
	<meta charset="ISO-8859-1">
    <title>Simple Library</title>
    <!-- Icon by Good Ware - https://www.flaticon.com/free-icon/book_864685 -->
    <link rel="shortcut icon" type="image/png" href="<c:url value="/resources/images/favicon.ico" />"/>
    <link href="<c:url value="/resources/style.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/script.js" />"></script>
  </head>
  <body>
    <header class="wrapper">
      <div>
        <h1><a href="index">Simple Library</a></h1>
      </div>
      <ul>
        <li><a href="signup">Signup</a></li>
        <li><a href="login">Login</a></li>
      </ul>
    </header>
    <main>
      <nav>
        <ul>
          <li><a href="search">Search</a></li>
          <li><a href="about">About</a></li>
          <li><a href="help">Help</a></li>
        </ul>
      </nav>
      <div id="content" class="wrapper">
      	<jsp:include page="${content}"/>
      </div>
    </main>
    <footer>
      <a target="_blank" href="https://github.com/AAAAAAidan/">&copy;Aidan Zamboni</a>
    </footer>
  </body>
</html>
