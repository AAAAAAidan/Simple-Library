package org.simplelibrary.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;

@Slf4j
public abstract class TemplateView {

  public String loadView(Model model, String view) {
    log.info("Directing to " + view);
    model.addAttribute("content", view);
    return "template";
  }

}
