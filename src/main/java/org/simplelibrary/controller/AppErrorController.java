package org.simplelibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.view.TemplateView;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller class for error pages.
 */
@Slf4j
@Controller
public class AppErrorController extends TemplateView implements ErrorController {

  @RequestMapping("/error")
  public String handleError(Model model, HttpServletRequest request) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    model.addAttribute("errorStatusCode", status.toString());
    return loadView(model, "errors/error");
  }

}
