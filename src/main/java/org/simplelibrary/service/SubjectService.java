package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Book;
import org.simplelibrary.model.Subject;
import org.simplelibrary.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class SubjectService {

  private SubjectRepository subjectRepository;

  @Autowired
  public SubjectService(SubjectRepository subjectRepository) {
    this.subjectRepository = subjectRepository;
  }

  public void save(Subject subject) {
    subjectRepository.save(subject);
  }

  public void saveAll(List<Subject> subjects) {
    subjectRepository.saveAll(subjects);
  }

  public boolean existsByName(String name) {
    return subjectRepository.existsByName(name);
  }

  public Subject getById(Integer id) {
    return subjectRepository.getById(id);
  }

  public Subject getByName(String name) {
    return subjectRepository.getByName(name);
  }

  public Subject getByRandom() {
    List<Subject> subjects = subjectRepository.findAll();
    Random random = new Random();
    return subjects.get(random.nextInt(subjects.size()));
  }

  public List<Subject> findAll() {
    return subjectRepository.findAll();
  }

  public void mapToBook(Subject subject, Book book) {
    List<Book> books = subject.getBooks();
    boolean alreadyPresent = false;

    if (books == null) {
      books = new ArrayList<>();
    }

    for (Book b : books) {
      if (b.getName().equals(book.getName())) {
        alreadyPresent = true;
        break;
      }
    }

    if (!alreadyPresent) {
      books.add(book);
    }

    subject.setBooks(books);
    subjectRepository.save(subject);
  }

}
