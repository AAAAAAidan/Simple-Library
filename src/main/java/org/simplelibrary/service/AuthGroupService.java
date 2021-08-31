package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.AuthGroup;
import org.simplelibrary.repository.AuthGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthGroupService {

  private final AuthGroupRepository authGroupRepository;

  @Autowired
  public AuthGroupService(AuthGroupRepository authGroupRepository) {
    this.authGroupRepository = authGroupRepository;
  }

  public void save(AuthGroup authGroup) {
    authGroupRepository.save(authGroup);
  }

  public AuthGroup getByName(String name) {
    return authGroupRepository.getByName(name);
  }

}
