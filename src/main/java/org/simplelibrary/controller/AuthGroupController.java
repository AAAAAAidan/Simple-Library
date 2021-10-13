package org.simplelibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.service.AuthGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Controller class for authority group related services.
 */
@Slf4j
@Controller
public class AuthGroupController {

  private final AuthGroupService authGroupService;

  @Autowired
  public AuthGroupController(AuthGroupService authGroupService) {
    this.authGroupService = authGroupService;
  }

}
