package org.simplelibrary.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.simplelibrary.model.FileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import org.simplelibrary.model.ResponseMessage;
import org.simplelibrary.service.FileService;

// Credit to FrontBackend - https://frontbackend.com/spring-boot/spring-boot-upload-file-to-filesystem

@RestController
@RequestMapping("files")
public class FileController {

  private final FileService fileService;

  @Autowired
  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @PostMapping
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
      fileService.save(file);
      return ResponseEntity.status(HttpStatus.OK)
          .body(new ResponseMessage("Uploaded the file successfully: " + file.getOriginalFilename()));
    }
    catch (Exception e) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseMessage("Could not upload the file: " + file.getOriginalFilename() + "!"));
    }
  }

  @GetMapping
  public ResponseEntity<List<FileData>> getListFiles() {
    List<FileData> fileData = fileService.loadAll()
        .stream()
        .map(this::pathToFile)
        .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(fileData);
  }

  @DeleteMapping
  public void delete() {
    fileService.deleteAll();
  }

  private FileData pathToFile(Path path) {
    FileData fileData = new FileData();
    String filename = path.getFileName().toString();
    fileData.setFilename(filename);
    fileData.setUrl(MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile", filename)
        .build().toString());

    try {
      fileData.setSize(Files.size(path));
    }
    catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Error: " + e.getMessage());
    }

    return fileData;
  }

  @GetMapping("{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = fileService.load(filename);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_PNG);
    return ResponseEntity.ok().headers(headers).body(file);
  }

}
