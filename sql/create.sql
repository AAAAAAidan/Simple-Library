# Aidan Zamboni
# Closed Library table building queries

DROP DATABASE IF EXISTS `closedlibrary`;

CREATE DATABASE `closedlibrary`;

USE `closedlibrary`;

CREATE TABLE IF NOT EXISTS `account` (
	`accountId`							INT NOT NULL AUTO_INCREMENT,
	`accountFirstName`				VARCHAR(300) NOT NULL,
	`accountLastName`					VARCHAR(300) NOT NULL,
	`accountPassword`					VARCHAR(32) NOT NULL,
	`accountEmail`						VARCHAR(320) DEFAULT NULL,
	`accountActiveBorrows`			INT DEFAULT 0,
	`accountTotalBorrows`			INT DEFAULT 0,
	`accountLastLoginDate`			TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`accountStatus`					ENUM('Active', 'Inactive') DEFAULT 'Active',
	`accountAddDate`					TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`accountId`)
);

CREATE TABLE IF NOT EXISTS `setting` (
	`settingId`							INT NOT NULL AUTO_INCREMENT,
	`settingProfileImage`			VARCHAR(12) DEFAULT NULL,
	`settingSearchResultsPerPage`	INT DEFAULT 30,
	`settingSearchDisplayType` 	ENUM('Compact', 'Normal', 'Comfortable') DEFAULT 'Normal',
	`settingLastUpdate`				TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`settingStatus`					ENUM('Active', 'Inactive') DEFAULT 'Active',
	`settingAddDate`					TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`accountId`							INT NOT NULL,
	PRIMARY KEY (`settingId`),
	FOREIGN KEY (`accountId`) REFERENCES account(`accountId`)
);

CREATE TABLE IF NOT EXISTS `card` (
	`cardId`								INT NOT NULL AUTO_INCREMENT,
	`cardNumber`						INT NOT NULL,
	`cardExpirationDate`				DATE NOT NULL,
	`cardStatus`						ENUM('Active', 'Inactive') DEFAULT 'Active',
	`cardAddDate`						TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`accountId`							INT NOT NULL,
	PRIMARY KEY (`cardId`),
	FOREIGN KEY (`accountId`) REFERENCES account(`accountId`)
);

CREATE TABLE IF NOT EXISTS `book` (
	`bookId`								VARCHAR(12) NOT NULL,
	`bookTitle`							VARCHAR(300) NOT NULL,
	`bookIdentifiers`					VARCHAR(300) DEFAULT NULL,
	`bookDescription`					VARCHAR(900) DEFAULT NULL,
	`bookPublishDate` 				DATE DEFAULT NULL,
	`bookPageCount`					INT DEFAULT NULL,
	`bookAvailability`				ENUM('Available', 'Unavailable') DEFAULT 'Available',
	`bookTotalBorrows`				INT DEFAULT 0,
	`bookStatus`						ENUM('Active', 'Inactive') DEFAULT 'Active',
	`bookAddDate`						TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`bookId`)
);

CREATE TABLE IF NOT EXISTS `borrow` (
	`borrowId`							INT NOT NULL AUTO_INCREMENT,
	`borrowQueueNumber` 				INT DEFAULT 1,
	`borrowQueueStatus` 				ENUM('Waiting', 'Started', 'Ended') DEFAULT 'Waiting',
	`borrowDueDate`					TIMESTAMP DEFAULT CURRENT_TIMESTAMP, # + 24 hr
	`borrowAddDate`					TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`borrowStatus`						ENUM('Active', 'Inactive') DEFAULT 'Active',
	`bookId`								VARCHAR(12) NOT NULL,
	`accountId`							INT NOT NULL,
	PRIMARY KEY (`borrowId`),
	FOREIGN KEY (`bookId`) REFERENCES book(`bookId`),
	FOREIGN KEY (`accountId`) REFERENCES account(`accountId`)
);


CREATE TABLE IF NOT EXISTS `list` (
	`listId`								INT NOT NULL AUTO_INCREMENT,
	`listName`							VARCHAR(300) NOT NULL,
	`listDescription`					VARCHAR(900) DEFAULT NULL,
	`listPrivacy`						ENUM('Public', 'Private') DEFAULT 'Private',
	`listLastUpdate`					TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`listStatus`						ENUM('Active', 'Inactive') DEFAULT 'Active',
	`listAddDate`						TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`bookId`								VARCHAR(12) NOT NULL,
	`accountId`							INT NOT NULL,
	PRIMARY KEY (`listId`),
	FOREIGN KEY (`bookId`) REFERENCES book(`bookId`),
	FOREIGN KEY (`accountId`) REFERENCES account(`accountId`)
);

CREATE TABLE IF NOT EXISTS `category` (
	`categoryId`						INT NOT NULL AUTO_INCREMENT,
	`categoryName`						VARCHAR(300) NOT NULL,
	`categoryType`						ENUM('Author', 'Publisher', 'Subject') NOT NULL,
	`categoryDescription`			VARCHAR(900) DEFAULT NULL,
	`categoryStatus`					ENUM('Active', 'Inactive') DEFAULT 'Active',
	`categoryAddDate`					TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`categoryId`)
);

CREATE TABLE IF NOT EXISTS `key` (
	`keyId`								INT NOT NULL AUTO_INCREMENT,
	`keyStatus`							ENUM('Active', 'Inactive') DEFAULT 'Active',
	`keyAddDate` 						TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`bookId`								VARCHAR(12) NOT NULL,
	`categoryId`						INT DEFAULT NULL,
	`listId`								INT DEFAULT NULL,
	PRIMARY KEY (`keyId`),
	FOREIGN KEY (`bookId`) REFERENCES book(`bookId`),
	FOREIGN KEY (`categoryId`) REFERENCES category(`categoryId`),
	FOREIGN KEY (`listId`) REFERENCES list(`listId`)
);
