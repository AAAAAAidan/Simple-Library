package org.simplelibrary.model;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the setting database table.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Setting implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="setting_id", nullable=false)
  private Integer settingId;

  @Column(name="setting_profile_image", length=12)
  private String profileImage = null;

  @Column(name="setting_search_results_per_page", nullable=false)
  private Integer searchResultsPerPage = 10;

  @Column(name="setting_search_display_type", nullable=false,
          columnDefinition="ENUM('Comfortable', 'Normal', 'Compact') DEFAULT 'Normal'")
  private String searchDisplayType = "Normal";

  @Column(name="setting_last_update", nullable=false, insertable=false,
          columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp lastUpdate;

  @Column(name="setting_add_date", nullable=false, updatable=false, insertable=false,
          columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp addDate;

  @Column(name="setting_status", nullable=false,
          columnDefinition="ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'")
  private String status = "ACTIVE";

  // Bi-directional many-to-one association to Account
  @ManyToOne
  @JoinColumn(name="account_id")
  private Account account;

}
