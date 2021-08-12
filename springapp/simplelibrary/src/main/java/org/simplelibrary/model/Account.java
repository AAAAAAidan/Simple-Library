package org.simplelibrary.model;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the account database table.
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="account_id", nullable=false)
  private Integer id;

  @Column(name="account_active_borrows")
  private Integer activeBorrows = 0;

  @Column(name="account_add_date", nullable=false, updatable=false, insertable=false,
      columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp addDate;

  @Column(name="account_email", unique=true, length=320)
  private String email = null;

  @Column(name="account_first_name", length=320)
  private String firstName = null;

  @Column(name="account_last_login_date", nullable=false, insertable=false,
      columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp lastLoginDate;

  @Column(name="account_last_name", length=320)
  private String lastName = null;

  @Column(name="account_password", length=60, nullable=false)
  private String password;

  @Column(name="account_status", nullable=false,
      columnDefinition="ENUM('Active', 'Inactive') DEFAULT 'Active'")
  private String status = "Active";

  @Column(name="account_total_borrows")
  private Integer totalBorrows = 0;

  //bi-directional many-to-one association to Borrow
  @OneToMany(mappedBy="account")
  private List<Borrow> borrows;

  //bi-directional many-to-one association to Card
  @OneToMany(mappedBy="account")
  private List<Card> cards;

  //bi-directional many-to-one association to Catalog
  @OneToMany(mappedBy="account")
  private List<Catalog> catalogs;

  //bi-directional many-to-one association to Setting
  @OneToMany(mappedBy="account")
  private List<Setting> settings;

  //bi-directional many-to-many association to AuthGroup
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name="account_auth_group_map",
      joinColumns={ @JoinColumn(name="account_id") },
      inverseJoinColumns={ @JoinColumn(name="auth_group_id") }
  )
  private List<AuthGroup> authGroups;

  public Borrow addBorrow(Borrow borrow) {
    getBorrows().add(borrow);
    borrow.setAccount(this);

    return borrow;
  }

  public Borrow removeBorrow(Borrow borrow) {
    getBorrows().remove(borrow);
    borrow.setAccount(null);

    return borrow;
  }

  public Card addCard(Card card) {
    getCards().add(card);
    card.setAccount(this);

    return card;
  }

  public Card removeCard(Card card) {
    getCards().remove(card);
    card.setAccount(null);

    return card;
  }

  public Catalog addCatalog(Catalog catalog) {
    getCatalogs().add(catalog);
    catalog.setAccount(this);

    return catalog;
  }

  public Catalog removeCatalog(Catalog catalog) {
    getCatalogs().remove(catalog);
    catalog.setAccount(null);

    return catalog;
  }

  public Setting addSetting(Setting setting) {
    getSettings().add(setting);
    setting.setAccount(this);

    return setting;
  }

  public Setting removeSetting(Setting setting) {
    getSettings().remove(setting);
    setting.setAccount(null);

    return setting;
  }

}
