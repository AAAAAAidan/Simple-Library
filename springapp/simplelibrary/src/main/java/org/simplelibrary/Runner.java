package org.simplelibrary;

import lombok.extern.slf4j.Slf4j;

import org.simplelibrary.model.AuthGroup;
import org.simplelibrary.model.Book;
import org.simplelibrary.model.Category;
import org.simplelibrary.service.AuthGroupService;
import org.simplelibrary.service.BookService;
import org.simplelibrary.service.CategoryService;
import org.simplelibrary.util.UrlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.simplelibrary.service.FileService;

import javax.json.JsonArray;
import javax.json.JsonObject;

@Slf4j
@Component
@Transactional
public class Runner implements CommandLineRunner {

  private final AuthGroupService authGroupService;
  private final CategoryService categoryService;
  private final BookService bookService;
  private final FileService fileService;

  @Autowired
  public Runner(AuthGroupService authGroupService,
                CategoryService categoryService,
                BookService bookService,
                FileService fileService) {
    this.authGroupService = authGroupService;
    this.categoryService = categoryService;
    this.bookService = bookService;
    this.fileService = fileService;
  }

  @Override
  public void run(String... args) throws Exception {

    String[] authGroups = new String[] {"ROLE_USER", "ROLE_ADMIN"};

    for (String authGroup : authGroups) {
      if (authGroupService.getByName(authGroup) == null) {
        authGroupService.save(new AuthGroup((authGroup)));
      }
    }

    String nextUrl = "https://gutendex.com/books/";

    while (nextUrl != null) {
      log.info("Fetching from " + nextUrl);
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
          JsonObject author = authors.getJsonObject(authorIndex);
          String name = author.getString("name");
          Category category;

          if (categoryService.existsByName(name)) {
            category = categoryService.getByName(name);
          }
          else {
            category = new Category(name, "Author");
          }

          categoryService.mapToBook(category, book);
        }

        JsonArray subjects = result.getJsonArray("subjects");

        for (int subjectIndex = 0; subjectIndex < subjects.size(); subjectIndex++) {
          String name = subjects.getString(subjectIndex);
          name = name.replaceAll("\\(.*", "").replaceAll("--.*", "").trim();
          Category category;

          if (categoryService.existsByName(name)) {
            category = categoryService.getByName(name);
          }
          else {
            category = new Category(name, "Subject");
          }

          categoryService.mapToBook(category, book);
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
