package org.simplelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Credit to FrontBackend - https://frontbackend.com/spring-boot/spring-boot-upload-file-to-filesystem

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileData {

  private String name;
  private String url;
  private Long size;

}
