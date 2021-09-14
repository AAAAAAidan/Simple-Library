package org.simplelibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Catalog;
import org.simplelibrary.model.CatalogItem;
import org.simplelibrary.model.RequestMessage;
import org.simplelibrary.model.ResponseMessage;
import org.simplelibrary.service.CatalogService;
import org.simplelibrary.service.CatalogItemService;
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

@Slf4j
@Controller
public class CatalogController extends TemplateView {

  private final CatalogService catalogService;
  private final CatalogItemService catalogItemService;

  @Autowired
  public CatalogController(CatalogService catalogService,
                           CatalogItemService catalogItemService) {
    this.catalogService = catalogService;
    this.catalogItemService = catalogItemService;
  }

  @GetMapping("/lists")
  public String getLists(RedirectAttributes redirectAttributes) {
    redirectAttributes.addAttribute("filter", "lists");
    return "redirect:search";
  }

  @PostMapping(value="/lists/save")
  public ResponseEntity<Catalog> getList(@RequestBody RequestMessage requestMessage) {
    String listName = requestMessage.getValue();

    try {
      Catalog newList = catalogService.saveAndFlush(new Catalog(listName));
      newList.setAccount(null); // Plain JavaScript can't parse this
      log.info("Created list: " + listName);
      return ResponseEntity.status(HttpStatus.OK).body(newList);
    }
    catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
    }
  }

  @PostMapping("/lists/{id}")
  public ResponseEntity<CatalogItem> postListItem(@PathVariable Integer id,
                                                  @RequestBody RequestMessage requestMessage) {
    try {
      Catalog list = catalogService.getById(id);
      CatalogItem listItem = new CatalogItem(requestMessage.getId(), requestMessage.getValue());
      listItem = catalogItemService.saveAndFlush(listItem);
      list.addCatalogItem(listItem);
      catalogService.saveAndFlush(list);
      String successEntry = "Added item to list " + id;
      log.info(successEntry);
      return ResponseEntity.status(HttpStatus.OK).body(listItem);
    }
    catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
    }
  }

  @GetMapping("/lists/{id}")
  public String getList(Model model, @PathVariable Integer id) {
    Catalog list = catalogService.getById(id);
    model.addAttribute("list", list);
    return loadView(model, "lists/list");
  }

  @DeleteMapping("/lists/{id}")
  public ResponseEntity<ResponseMessage> deleteList(@PathVariable Integer id) {
    try {
      catalogService.deleteById(id);
      String successEntry = "Deleted list " + id;
      log.info(successEntry);
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(successEntry));
    }
    catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Failed to create list"));
    }
  }

  @DeleteMapping("/lists/{id}/{itemid}")
  public ResponseEntity<ResponseMessage> deleteListItem(@PathVariable Integer id,
                                                        @PathVariable Integer itemid) {
    try {
      Catalog list = catalogService.getById(id);
      CatalogItem listItem = catalogItemService.saveAndFlush(listItem);
      list.addCatalogItem(listItem);
      catalogService.saveAndFlush(list);
      String successEntry = "Added item to list " + id;
      log.info(successEntry);
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(successEntry));
    }
    catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Failed to add item to list"));
    }
  }

}
