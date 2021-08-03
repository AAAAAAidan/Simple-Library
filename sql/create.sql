# Database and table create statements
# Note that this file is deprecated

DROP DATABASE IF EXISTS `library`;

CREATE DATABASE `library`;

USE `library`;

CREATE TABLE IF NOT EXISTS `account` (
  `account_id` INT NOT NULL AUTO_INCREMENT,
  `account_first_name` VARCHAR(320) DEFAULT NULL,
  `account_last_name` VARCHAR(320) DEFAULT NULL,
  `account_password` VARCHAR(32) NOT NULL,
  `account_email` VARCHAR(320) DEFAULT NULL,
  `account_active_borrows` INT DEFAULT 0,
  `account_total_borrows` INT DEFAULT 0,
  `account_last_login_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `account_status` ENUM('Active', 'Inactive') DEFAULT 'Active',
  `account_add_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`account_id`)
);

CREATE TABLE IF NOT EXISTS `setting` (
  `setting_id` INT NOT NULL AUTO_INCREMENT,
  `account_id` INT UNIQUE NOT NULL,
  `setting_profile_image` VARCHAR(12) DEFAULT NULL,
  `setting_search_results_per_page` INT DEFAULT 30,
  `setting_search_display_type` ENUM('Compact', 'Normal', 'Comfortable') DEFAULT 'Normal',
  `setting_last_update` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `setting_status` ENUM('Active', 'Inactive') DEFAULT 'Active',
  `setting_add_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`setting_id`),
  FOREIGN KEY (`account_id`) REFERENCES account(`account_id`)
);

CREATE TABLE IF NOT EXISTS `card` (
  `card_id` INT NOT NULL AUTO_INCREMENT,
  `card_expiration_date` DATE NOT NULL,
  `card_status` ENUM('Active', 'Inactive') DEFAULT 'Active',
  `card_add_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`card_id`),
  FOREIGN KEY (`account_id`) REFERENCES account(`account_id`)
);

CREATE TABLE IF NOT EXISTS `book` (
  `book_id` VARCHAR(12) NOT NULL,
  `book_title` VARCHAR(320) NOT NULL,
  `book_description` VARCHAR(3200) DEFAULT NULL,
  `book_publish_date` DATE DEFAULT NULL,
  `book_page_count` INT DEFAULT NULL,
  `book_availability` ENUM('Available', 'Unavailable') DEFAULT 'Available',
  `book_total_borrows` INT DEFAULT 0,
  `book_status` ENUM('Active', 'Inactive') DEFAULT 'Active',
  `book_add_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`book_id`)
);

CREATE TABLE IF NOT EXISTS `isbn` (
  `isbn_id` VARCHAR(32) NOT NULL,
  `isbn_type` VARCHAR(32) DEFAULT 'OTHER',
  `book_id` VARCHAR(12) NOT NULL,
  `isbn_status` ENUM('Active', 'Inactive') DEFAULT 'Active',
  `isbn_add_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`isbn_id`),
  FOREIGN KEY (`book_id`) REFERENCES book(`book_id`)
);

CREATE TABLE IF NOT EXISTS `borrow` (
  `borrow_id` INT NOT NULL AUTO_INCREMENT,
  `borrow_queue_number` INT DEFAULT 1,
  `borrow_queue_status` ENUM('Waiting', 'Started', 'Ended') DEFAULT 'Waiting',
  `borrow_due_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, # + 24 hr
  `borrow_add_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `borrow_status` ENUM('Active', 'Inactive') DEFAULT 'Active',
  `book_id` VARCHAR(12) NOT NULL,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`borrow_id`),
  FOREIGN KEY (`book_id`) REFERENCES book(`book_id`),
  FOREIGN KEY (`account_id`) REFERENCES account(`account_id`)
);

CREATE TABLE IF NOT EXISTS `catalog` (
  `catalog_id` INT NOT NULL AUTO_INCREMENT,
  `catalog_name` VARCHAR(320) NOT NULL,
  `catalog_description` VARCHAR(3200) DEFAULT NULL,
  `catalog_privacy` ENUM('Public', 'Private') DEFAULT 'Private',
  `catalog_last_update` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `catalog_status` ENUM('Active', 'Inactive') DEFAULT 'Active',
  `catalog_add_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`catalog_id`),
  FOREIGN KEY (`account_id`) REFERENCES account(`account_id`)
);

CREATE TABLE IF NOT EXISTS `category` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(320) NOT NULL,
  `category_type` ENUM('Author', 'Publisher', 'Subject') NOT NULL,
  `category_description` VARCHAR(3200) DEFAULT NULL,
  `category_status` ENUM('Active', 'Inactive') DEFAULT 'Active',
  `category_add_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`category_id`)
);

CREATE TABLE IF NOT EXISTS `book_category_map` (
  `book_id` VARCHAR(12) NOT NULL,
  `category_id` INT NOT NULL,
  FOREIGN KEY (`book_id`) REFERENCES book(`book_id`),
  FOREIGN KEY (`category_id`) REFERENCES category(`category_id`)
);

CREATE TABLE IF NOT EXISTS `book_catalog_map` (
  `book_id` VARCHAR(12) NOT NULL,
  `catalog_id` INT NOT NULL,
  FOREIGN KEY (`book_id`) REFERENCES book(`book_id`),
  FOREIGN KEY (`catalog_id`) REFERENCES catalog(`catalog_id`)
);
