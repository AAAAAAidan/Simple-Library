package org.simplelibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.service.SettingService;
import org.simplelibrary.view.TemplateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class SettingController extends TemplateView {

  private final SettingService settingService;

  @Autowired
  public SettingController(SettingService settingService) {
    this.settingService = settingService;
  }

}
