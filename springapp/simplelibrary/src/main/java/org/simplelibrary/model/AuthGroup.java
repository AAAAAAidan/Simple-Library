package org.simplelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="auth_group")
public class AuthGroup implements Serializable {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="auth_group_id", nullable=false)
  Integer authId;

  @Column(name="auth_group_name", nullable=false)
  String name;

  //bi-directional many-to-many association to Account
  @ManyToMany(fetch = FetchType.EAGER, mappedBy="authGroups")
  private List<Account> accounts;

}
