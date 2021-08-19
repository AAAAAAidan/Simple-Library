package org.simplelibrary.model;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the borrow database table.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Borrow implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="borrow_id", length=32, nullable=false)
  private Integer id;

  @NonNull
  @Column(name="borrow_queue_number", nullable=false)
  private Integer queueNumber;

  @NonNull
  @Column(name="borrow_queue_status", nullable=false,
          columnDefinition="ENUM('Waiting', 'Started', 'Ended') DEFAULT 'Waiting'")
  private String queueStatus;

  @NonNull
  @Column(name="borrow_due_date", nullable=false,
          columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp dueDate;

  @Column(name="borrow_add_date", nullable=false, updatable=false, insertable=false,
          columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp addDate;

  @Column(name="borrow_status", nullable=false,
          columnDefinition="ENUM('Active', 'Inactive') DEFAULT 'Active'")
  private String status = "Active";

  // Bi-directional many-to-one association to Account
  @ManyToOne
  @JoinColumn(name="account_id")
  private Account account;

  // Bi-directional many-to-one association to Book
  @ManyToOne
  @JoinColumn(name="book_id")
  private Book book;

}
