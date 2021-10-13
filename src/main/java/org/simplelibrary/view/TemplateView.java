package org.simplelibrary.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;

/**
 * View class for loading views inside the template.
 */
@Slf4j
public abstract class TemplateView {

  /**
   * View class for loading views inside the template.
   *
   * @param model the view model
   * @param view the view name
   * @return the template view: "template"
   */
  public String loadView(Model model, String view) {
    log.info("Directing to " + view);
    model.addAttribute("content", view);
    return "template";
  }

}
