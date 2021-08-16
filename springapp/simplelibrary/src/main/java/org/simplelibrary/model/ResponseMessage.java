package org.simplelibrary.model;

// Credit to FrontBackend - https://frontbackend.com/spring-boot/spring-boot-upload-file-to-filesystem

public class ResponseMessage {

  private final String responseMessage;

  public ResponseMessage(String responseMessage) {
    this.responseMessage = responseMessage;
  }

  public String getResponseMessage() {
    return responseMessage;
  }

}
