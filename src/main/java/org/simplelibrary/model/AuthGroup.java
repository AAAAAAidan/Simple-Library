package org.simplelibrary.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Persistent class for the authority group database table.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name="auth_group")
public class AuthGroup implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="auth_group_id", nullable=false)
  Integer authId;

  @NonNull
  @Column(name="auth_group_name", unique=true, nullable=false)
  String name;

  @Column(name="auth_group_add_date", nullable=false, updatable=false, insertable=false,
          columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp addDate;

  @Column(name="auth_group_status", length=12, nullable=false)
  private String status = "ACTIVE";

  // Bi-directional many-to-many association to Account
  @ManyToMany(fetch=FetchType.EAGER, mappedBy="authGroups")
  private List<Account> accounts;

}
