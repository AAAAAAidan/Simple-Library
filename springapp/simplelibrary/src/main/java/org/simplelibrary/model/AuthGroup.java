package org.simplelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthGroup implements Serializable {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name="auth_id", nullable=false)
  Integer authId;

  @Column(name="auth_group", nullable=false)
  String authGroup;

  @Column(name="account_id", nullable=false)
  Integer accountId;
}
