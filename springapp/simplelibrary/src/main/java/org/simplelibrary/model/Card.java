package org.simplelibrary.model;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the card database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name="card_id", nullable=false)
  private int id;

  @Column(name="card_add_date", nullable=false, updatable=false, insertable=false,
      columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp addDate;

  @Temporal(TemporalType.DATE)
  @Column(name="card_expiration_date", nullable=false)
  private Date expirationDate = null;

  @Column(name="card_status", nullable=false,
      columnDefinition="ENUM('Active', 'Inactive') DEFAULT 'Active'")
  private String status = "Active";

  //bi-directional many-to-one association to Account
  @ManyToOne
  @JoinColumn(name="account_id")
  private Account account;

}