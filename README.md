# Simple Library

[simplelibrary.org](https://simplelibrary.org/) is a digital book library web application for my full stack Java development case study. The application contains search functionality through a database of books, authors, and subjects, all of which can be individually viewed on their own pages. The book selection is restricted to those in the public domain, all of which can be downloaded and read through the application. For additional details, see the software requirements specification document in external links, which includes the database schema and user stories, among other details.

### Local Development

Use the following command to run locally. Requires Maven and Java 11.

    mvn spring-boot:run

Alternatively, you can use the following to more closely simulate the web deployment process.

    rm -r target
    mvn package -DskipTests
    java -jar target/*.jar

### Dependencies and Services

Front-end languages: HTML, CSS, JavaScript.

Back-end languages: Java, SQL, XML, Dockerfile.

Back-end dependencies: Spring Boot, Spring Security, Spring Data JPA, JUnit, JSON, PostgreSQL, Thymeleaf, Lombok, Google Cloud Secret Manager, Google Cloud PostgreSQL, Google Cloud NIO.

Cloud services: Google Cloud Run, Google Cloud Build, Google Cloud SQL, Google Cloud Storage.

Third-party resources: Project Gutenberg, Gutendex, Good Ware, Bibi.

### Technical Overview

* Contains 7 JPA entity models (Book, Author, Subject, Catalog, CatalogItem, Account, AuthGroup), 3 many-to-many join tables, and 4 generic models (AccountDetails, FileData, RequestMessage, ResponseMessage). Getters, setters, and constructors are generated using Lombok. Validation is done through the associated controllers.

* All entity models have associated repositories, services, and controllers. FileData has services and a REST controller.

* All Junit test classes can be found in [src/test/java/org/simplelibrary](https://github.com/AAAAAAidan/Simple-Library/tree/main/src/test/java/org/simplelibrary). Note that many of these test classes do not have any related test cases yet.

* No CSS frameworks were used. All CSS is custom and can be found in [src/main/resources/static/styles](https://github.com/AAAAAAidan/Simple-Library/tree/main/src/main/resources/static/styles).

* Spring Security has been implemented and the application has functioning signup and login pages. Only registered users can access the /account URL directory. Only users with "ROLE_ADMIN" authentication can access /files and /admin URL directories. See [SecurityConfiguration.java](https://github.com/AAAAAAidan/Simple-Library/blob/main/src/main/java/org/simplelibrary/config/SecurityConfiguration.java).

* Any resource accessed that results in an error—whether page not found, no permission granted, or anything else—will be mapped to the associated error page by [AppErrorController.java](https://github.com/AAAAAAidan/Simple-Library/blob/main/src/main/java/org/simplelibrary/controller/AppErrorController.java). Error pages can be found in [src/main/resources/templates/errors](https://github.com/AAAAAAidan/Simple-Library/tree/main/src/main/resources/templates/errors).

* The biggest challenges came with Spring Security and file uploading due to the relative complexity of their configuration. Additionally, problems arose when trying to download files from the [Google Books API](https://developers.google.com/books/docs/v1/reference/volumes/list) due to its CAPTCHA protection, so the switch to the [Gutenberg API](https://gutendex.com/) was made. Problems continue to arise in making the application look decent on mobile.

* JSON data is accessed through the Gutenberg API and used to initialize the database content in [Runner.java](https://github.com/AAAAAAidan/Simple-Library/blob/main/src/main/java/org/simplelibrary/Runner.java). A REST controller is used in [FileController.java](https://github.com/AAAAAAidan/Simple-Library/blob/main/src/main/java/org/simplelibrary/controller/FileController.java) to manage and view uploaded files. Note that I am not the creator of this controller, instead having only modified it to better suit my needs.

* [Bibi](https://github.com/satorumurmur/bibi) was utilized to read epub files inside the application.

### External Links

* [Software Requirements Specification](https://docs.google.com/document/d/1_wm5c655yeb50E9_qUmYXXQPPT1rFpTIRPdNIKOd3Qg/edit)
