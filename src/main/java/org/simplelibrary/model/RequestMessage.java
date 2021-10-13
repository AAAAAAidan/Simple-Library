package org.simplelibrary.model;

import lombok.NoArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Temporary class for JSON request messages.
 */
@Component
@Getter
@NoArgsConstructor
public class RequestMessage {

  private Integer id;
  private String value;

}
