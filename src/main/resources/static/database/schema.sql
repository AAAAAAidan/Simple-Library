CREATE TABLE IF NOT EXISTS `account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_add_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `account_email` varchar(320) NOT NULL,
  `account_last_login_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `account_password` varchar(60) NOT NULL,
  `account_status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `UK_o72icrlyfe9jcc61hbgsnmkgr` (`account_email`)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS `account_auth_group_map` (
  `account_id` int(11) NOT NULL,
  `auth_group_id` int(11) NOT NULL,
  KEY `FK8ktu2j381scekrsb5h52y59kh` (`auth_group_id`),
  KEY `FKdkfoi683lsr2220uqwi0g125h` (`account_id`)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS `author` (
  `author_id` int(11) NOT NULL AUTO_INCREMENT,
  `author_add_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `author_name` varchar(320) NOT NULL,
  `author_status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`author_id`)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS `auth_group` (
  `auth_group_id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_group_add_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `auth_group_name` varchar(255) NOT NULL,
  `auth_group_status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`auth_group_id`),
  UNIQUE KEY `UK_owunwxuojta179c5muxctxmfe` (`auth_group_name`)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS `book` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_add_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `book_download_count` int(11) DEFAULT NULL,
  `book_name` varchar(640) NOT NULL,
  `book_status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  `book_view_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  UNIQUE KEY `UK_jy2eu6s9e28na3fwo7fxv0a0x` (`book_name`) USING HASH
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS `book_author_map` (
  `author_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  KEY `FK5h0dqfignshscdy6kkvjh8k5s` (`book_id`),
  KEY `FKk6ldk8xfgw4xsgbb46awwdtcv` (`author_id`)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS `book_catalog_map` (
  `catalog_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  KEY `FKl742w51d5091qreckg7rrc6ek` (`book_id`),
  KEY `FKemrxgt45a7riv51029a6jclc0` (`catalog_id`)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS `book_subject_map` (
  `subject_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  KEY `FKma0721xfc1uuinsu7ddm5miyu` (`book_id`),
  KEY `FKox1y90hw6ffrfao60r4vbi6i6` (`subject_id`)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS `catalog` (
  `catalog_id` int(11) NOT NULL AUTO_INCREMENT,
  `catalog_add_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `catalog_last_update` timestamp NOT NULL DEFAULT current_timestamp(),
  `catalog_name` varchar(320) NOT NULL,
  `catalog_privacy` enum('Private','Public') NOT NULL DEFAULT 'Private',
  `catalog_status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  `account_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`catalog_id`),
  KEY `FKa9hp9deasawo2hvpidalymv76` (`account_id`)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS `setting` (
  `setting_id` int(11) NOT NULL AUTO_INCREMENT,
  `setting_add_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `setting_last_update` timestamp NOT NULL DEFAULT current_timestamp(),
  `setting_search_display_type` enum('Comfortable','Normal','Compact') NOT NULL DEFAULT 'Normal',
  `setting_search_results_per_page` int(11) NOT NULL,
  `setting_status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  `account_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`setting_id`),
  KEY `FK8rstrl3usjabkaw9tgckgdna` (`account_id`)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS `subject` (
  `subject_id` int(11) NOT NULL AUTO_INCREMENT,
  `subject_add_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `subject_name` varchar(320) NOT NULL,
  `subject_status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`subject_id`)
) engine=InnoDB;
