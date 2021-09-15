package org.simplelibrary.model;

import lombok.NoArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
public class RequestMessage {

  private Integer id;
  private String value;

}
