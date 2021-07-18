package org.simplelibrary.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the setting database table.
 * 
 */
@Entity
@NamedQuery(name="Setting.findAll", query="SELECT s FROM Setting s")
public class Setting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int settingId;

	private Timestamp settingAddDate;

	private Timestamp settingLastUpdate;

	private String settingProfileImage;

	private String settingSearchDisplayType;

	private int settingSearchResultsPerPage;

	private String settingStatus;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="accountId")
	private Account account;

	public Setting() {
	}

	public int getSettingId() {
		return this.settingId;
	}

	public void setSettingId(int settingId) {
		this.settingId = settingId;
	}

	public Timestamp getSettingAddDate() {
		return this.settingAddDate;
	}

	public void setSettingAddDate(Timestamp settingAddDate) {
		this.settingAddDate = settingAddDate;
	}

	public Timestamp getSettingLastUpdate() {
		return this.settingLastUpdate;
	}

	public void setSettingLastUpdate(Timestamp settingLastUpdate) {
		this.settingLastUpdate = settingLastUpdate;
	}

	public String getSettingProfileImage() {
		return this.settingProfileImage;
	}

	public void setSettingProfileImage(String settingProfileImage) {
		this.settingProfileImage = settingProfileImage;
	}

	public String getSettingSearchDisplayType() {
		return this.settingSearchDisplayType;
	}

	public void setSettingSearchDisplayType(String settingSearchDisplayType) {
		this.settingSearchDisplayType = settingSearchDisplayType;
	}

	public int getSettingSearchResultsPerPage() {
		return this.settingSearchResultsPerPage;
	}

	public void setSettingSearchResultsPerPage(int settingSearchResultsPerPage) {
		this.settingSearchResultsPerPage = settingSearchResultsPerPage;
	}

	public String getSettingStatus() {
		return this.settingStatus;
	}

	public void setSettingStatus(String settingStatus) {
		this.settingStatus = settingStatus;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}