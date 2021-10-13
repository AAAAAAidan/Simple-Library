package org.simplelibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Account;
import org.simplelibrary.model.Catalog;
import org.simplelibrary.model.CatalogItem;
import org.simplelibrary.model.RequestMessage;
import org.simplelibrary.model.ResponseMessage;
import org.simplelibrary.service.AccountService;
import org.simplelibrary.service.AuthorService;
import org.simplelibrary.service.BookService;
import org.simplelibrary.service.CatalogService;
import org.simplelibrary.service.CatalogItemService;
import org.simplelibrary.service.SubjectService;
import org.simplelibrary.view.TemplateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller class for catalog related services.
 */
@Slf4j
@Controller
public class CatalogController extends TemplateView {

  private final AccountService accountService;
  private final AuthorService authorService;
  private final BookService bookService;
  private final CatalogService catalogService;
  private final CatalogItemService catalogItemService;
  private final SubjectService subjectService;

  @Autowired
  public CatalogController(AccountService accountService,
                           AuthorService authorService,
                           BookService bookService,
                           CatalogService catalogService,
                           CatalogItemService catalogItemService,
                           SubjectService subjectService) {
    this.accountService = accountService;
    this.authorService = authorService;
    this.bookService = bookService;
    this.catalogService = catalogService;
    this.catalogItemService = catalogItemService;
    this.subjectService = subjectService;
  }

  @GetMapping("/lists")
  public String getCatalogs(RedirectAttributes redirectAttributes) {
    redirectAttributes.addAttribute("filter", "lists");
    return "redirect:/search";
  }

  @PostMapping("/lists/save")
  public ResponseEntity<Catalog> getCatalog(@RequestBody RequestMessage requestMessage) {
    if (!accountService.isLoggedIn()) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
    }

    String catalogName = requestMessage.getValue();

    try {
      Catalog catalog = new Catalog(catalogName);
      catalog.setAccount(accountService.getLoggedInAccount());
      catalogService.saveAndFlush(catalog);
      log.info("Created catalog: " + catalogName);
      catalog.setAccount(null); // Plain JavaScript can't parse this
      return ResponseEntity.status(HttpStatus.OK).body(catalog);
    }
    catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
    }
  }

  @PostMapping("/lists/{id}/save")
  public ResponseEntity<CatalogItem> postCatalogItem(@PathVariable Integer id,
                                                     @RequestBody RequestMessage requestMessage) {
    if (!accountService.isLoggedIn()) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
    }

    try {
      Catalog catalog = catalogService.getById(id);

      if (!catalog.getAccount().getId().equals(accountService.getLoggedInId())) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
      }

      CatalogItem catalogItem = new CatalogItem(requestMessage.getId(), requestMessage.getValue());
      catalog.addCatalogItem(catalogItemService.saveAndFlush(catalogItem));
      catalogService.saveAndFlush(catalog);
      log.info("Added item to catalog " + id);
      catalogItem.setCatalog(null); // Plain JavaScript can't parse this
      return ResponseEntity.status(HttpStatus.OK).body(catalogItem);
    }
    catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
    }
  }

  @GetMapping("/lists/{id}")
  public String getCatalog(Model model, @PathVariable Integer id) {
    if (!accountService.isLoggedIn()) {
      return "redirect:/lists";
    }

    Account loggedInAccount = accountService.getLoggedInAccount();
    Catalog catalog = catalogService.getById(id);

    if (catalog.getAccount() == null ||
       !catalog.getAccount().getId().equals(accountService.getLoggedInId())) {
      return "redirect:/lists";
    }

    List<Catalog> catalogs = loggedInAccount.getCatalogs();
    List<CatalogItem> catalogItems = catalog.getCatalogItems();

    for (CatalogItem item : catalogItems) {
      Integer sourceId = item.getSourceId();
      String sourceName = sourceId.toString();

      switch (item.getSourceFilter()) {
        case "books":
          sourceName = bookService.getById(sourceId).getName();
          break;
        case "authors":
          sourceName = authorService.getById(sourceId).getName();
          break;
        case "subjects":
          sourceName = subjectService.getById(sourceId).getName();
          break;
        case "lists":
        case "catalogs":
          sourceName = catalogService.getById(sourceId).getName();
      }

      item.setSourceName(sourceName);
    }

    catalog.setCatalogItems(catalogItems);
    model.addAttribute("list", catalog);
    model.addAttribute("lists", catalogs);

    if (accountService.isLoggedIn() ) {
      return loadView(model, "lists/list");
    }
    else {
      return "redirect:/lists";
    }
  }

  @DeleteMapping("/lists/{id}")
  public ResponseEntity<Catalog> deleteCatalog(@PathVariable Integer id) {
    if (!accountService.isLoggedIn()) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
    }

    try {
      Catalog catalog = catalogService.getById(id);

      if (!catalog.getAccount().getId().equals(accountService.getLoggedInId())) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
      }

      catalog.setAccount(null);
      catalogService.saveAndFlush(catalog);
      log.info("Deleted catalog " + id);
      return ResponseEntity.status(HttpStatus.OK).body(new Catalog()); // Temporary fix
    }
    catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
    }
  }

  @DeleteMapping("/lists/{id}/{itemId}")
  public ResponseEntity<CatalogItem> deleteCatalogItem(@PathVariable Integer id,
                                                       @PathVariable Integer itemId) {
    if (!accountService.isLoggedIn()) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
    }

    try {
      Catalog catalog = catalogService.getById(id);

      if (!catalog.getAccount().getId().equals(accountService.getLoggedInId())) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
      }

      CatalogItem catalogItem = catalogItemService.getById(itemId);
      catalogItem.setCatalog(null);
      catalogItemService.saveAndFlush(catalogItem);
      log.info("Removed item from catalog " + id);
      return ResponseEntity.status(HttpStatus.OK).body(new CatalogItem()); // Temporary fix
    }
    catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
    }
  }

}
