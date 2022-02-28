package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.AuthGroup;
import org.simplelibrary.repository.AuthGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for the authority group entity.
 */
@Slf4j
@Service
public class AuthGroupService {

  private final AuthGroupRepository authGroupRepository;

  @Autowired
  public AuthGroupService(AuthGroupRepository authGroupRepository) {
    this.authGroupRepository = authGroupRepository;
  }

  /**
   * Saves the provided authority group.
   *
   * @param authGroup the authority group to save
   */
  public void save(AuthGroup authGroup) {
    authGroupRepository.save(authGroup);
  }

  /**
   * Gets an authority based on the provided name.
   *
   * @param name the name of the desired authority group
   * @return the authority group found
   */
  public AuthGroup getByName(String name) {
    return authGroupRepository.getByName(name);
  }

}
