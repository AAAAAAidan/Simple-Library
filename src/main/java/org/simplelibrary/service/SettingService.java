package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SettingService {

  private final SettingRepository settingRepository;

  @Autowired
  public SettingService(SettingRepository settingRepository) {
    this.settingRepository = settingRepository;
  }

}
