package org.simplelibrary;

import lombok.extern.slf4j.Slf4j;

import org.simplelibrary.model.*;
import org.simplelibrary.service.*;
import org.simplelibrary.util.UrlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.json.JsonArray;
import javax.json.JsonObject;

@Slf4j
@Component
@Transactional
public class Runner implements CommandLineRunner {

  private final AccountService accountService;
  private final AuthGroupService authGroupService;
  private final AuthorService authorService;
  private final SubjectService subjectService;
  private final BookService bookService;
  private final FileService fileService;

  @Autowired
  public Runner(AccountService accountService,
                AuthGroupService authGroupService,
                AuthorService authorService,
                SubjectService subjectService,
                BookService bookService,
                FileService fileService) {
    this.accountService = accountService;
    this.authGroupService = authGroupService;
    this.authorService = authorService;
    this.subjectService = subjectService;
    this.bookService = bookService;
    this.fileService = fileService;
  }

  @Override
  public void run(String... args) throws Exception {

    String[] roles = new String[] {"ROLE_USER", "ROLE_ADMIN"};

    for (String role : roles) {
      if (authGroupService.getByName(role) == null) {
        log.info("Adding authentication group " + role);
        authGroupService.save(new AuthGroup(role));
      }
    }

    String[] emails = new String[] {"user@mail.com", "admin@mail.com"};

    for (String email : emails) {
      if (accountService.getByEmail(email) == null) {
        log.info("Adding account " + email);
        accountService.signUp(email, "password");
      }
    }

    String nextUrl = "https://gutendex.com/books/";

    while (nextUrl != null) {
      log.info("Fetching " + nextUrl);
      JsonObject jsonObject = UrlReader.getJsonObjectFromUrl(nextUrl);
      nextUrl = jsonObject.getString("next");
      JsonArray results = jsonObject.getJsonArray("results");

      for (int resultIndex = 0; resultIndex < results.size(); resultIndex++) {
        JsonObject result = results.getJsonObject(resultIndex);
        boolean copyright = result.getBoolean("copyright");
        String title = result.getString("title");
        Book book = new Book(title);

        // Ignore any books that aren't public domain or are already in the database
        if (copyright || bookService.existsByName(title)) {
          String reason;

          if (copyright) {
            reason = " (copyright)";
          }
          else {
            reason = " (duplicate)";
          }

          log.info("Skipping " + title + reason);
          continue;
        }

        log.info("Adding " + title);
        bookService.save(book);
        JsonArray authors = result.getJsonArray("authors");

        for (int authorIndex = 0; authorIndex < authors.size(); authorIndex++) {
          JsonObject authorObject = authors.getJsonObject(authorIndex);
          String name = authorObject.getString("name");
          Author author;

          if (authorService.existsByName(name)) {
            author = authorService.getByName(name);
          }
          else {
            author = new Author(name);
          }

          authorService.mapToBook(author, book);
        }

        JsonArray subjects = result.getJsonArray("subjects");

        for (int subjectIndex = 0; subjectIndex < subjects.size(); subjectIndex++) {
          String name = subjects.getString(subjectIndex);
          name = name.replaceAll("\\(.*", "").replaceAll("--.*", "").trim();
          Subject subject;

          if (subjectService.existsByName(name)) {
            subject = subjectService.getByName(name);
          }
          else {
            subject = new Subject(name);
          }

          subjectService.mapToBook(subject, book);
        }

        JsonObject formats = result.getJsonObject("formats");
        String imageName = "cover-" + book.getId() + ".png";

        if (!fileService.exists(imageName)) {
          log.info("Saving " + imageName);
          String imageUrl = formats.getString("image/jpeg").replace("small", "medium");
          String imageContentType = "image/png";
          byte[] imageContent = UrlReader.getByteArrayFromUrl(imageUrl);
          MultipartFile cover = new MockMultipartFile(imageName, imageName, imageContentType, imageContent);
          fileService.save(cover);
        }

        String readerName = "reader-" + book.getId() + ".epub";

        if (!fileService.exists(readerName)) {
          log.info("Saving " + readerName);
          String readerUrl = formats.getString("application/epub+zip").replace("small", "medium");
          String readerContentType = "application/epub+zip";
          byte[] readerContent = UrlReader.getByteArrayFromUrl(readerUrl);
          MultipartFile reader = new MockMultipartFile(readerName, readerName, readerContentType, readerContent);
          fileService.save(reader);
        }
      }
      break; // For now, 31 books is fine
    }
    log.info("Finished adding titles");
  }

}
