package org.simplelibrary.view;

import org.springframework.ui.Model;

public abstract class TemplateView {

  public String TemplateView(Model model, String view) {
    model.addAttribute("content", view);
    return "template";
  }

}
