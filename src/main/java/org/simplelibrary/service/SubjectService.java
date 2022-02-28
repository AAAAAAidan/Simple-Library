package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Book;
import org.simplelibrary.model.Subject;
import org.simplelibrary.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service class for the subject entity.
 */
@Slf4j
@Service
public class SubjectService {

  private final SubjectRepository subjectRepository;

  @Autowired
  public SubjectService(SubjectRepository subjectRepository) {
    this.subjectRepository = subjectRepository;
  }

  /**
   * Saves a subject.
   *
   * @param subject the subject to save
   */
  public void save(Subject subject) {
    subjectRepository.save(subject);
  }

  /**
   * Saves all subjects provided.
   *
   * @param subjects the subjects to save
   */
  public void saveAll(List<Subject> subjects) {
    subjectRepository.saveAll(subjects);
  }

  /**
   * Checks if an subject with the provided name exists.
   *
   * @param name the name of the subject
   * @return true if the subject exists, else false
   */
  public boolean existsByName(String name) {
    return subjectRepository.existsByName(name);
  }

  /**
   * Gets a subject based on the provided ID.
   *
   * @param id the ID of the subject
   * @return the subject found
   */
  public Subject getById(Integer id) {
    return subjectRepository.getById(id);
  }

  /**
   * Gets a subject based on the provided name.
   *
   * @param name the name of the subject
   * @return the subject found
   */
  public Subject getByName(String name) {
    return subjectRepository.getByName(name);
  }

  /**
   * Gets a list of subjects based on the provided name.
   *
   * @param name the name to use as a search term
   * @param sort the sort order for the results
   * @return the subjects found
   */
  public List<Subject> getSubjectsByNameIsContainingIgnoreCase(String name, Sort sort) {
    return subjectRepository.getSubjectsByNameIsContainingIgnoreCase(name, sort);
  }

  /**
   * Gets a random subject.
   *
   * @return the subject found
   */
  public Subject getByRandom() {
    List<Subject> subjects = subjectRepository.findAll();
    Random random = new Random();
    return subjects.get(random.nextInt(subjects.size()));
  }

  /**
   * Finds all subjects.
   *
   * @return the subjects found
   */
  public List<Subject> findAll() {
    return subjectRepository.findAll();
  }

  /**
   * Adds a book to a subject's list of books if not already present.
   *
   * @param subject the subject to save
   * @param book the book to save to the subject
   */
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
