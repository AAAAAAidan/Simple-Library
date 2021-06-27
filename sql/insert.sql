# Row insert statements

INSERT INTO `book`
	(`bookId`,`bookTitle`,`bookIdentifiers`,`bookDescription`,`bookPublishDate`,`bookPageCount`,`bookAvailability`,`bookTotalBorrows`,`bookStatus`,`bookAddDate`)
VALUES
	('FEgFEAAAQBAJ','Making up Numbers: A History of Invention in Mathematics','[{"type":"ISBN_13","identifier":"9781800640979"},{"type":"ISBN_10","identifier":"1800640978"}]','Making up Numbers: A History of Invention in Mathematics offers a detailed but accessible account of a wide range of mathematical ideas. Starting with elementary concepts, it leads the reader towards aspects of current mathematical research. The book explains how conceptual hurdles in the development of numbers and number systems were overcome in the course of history, from Babylon to Classical Greece, from the Middle Ages to the Renaissance, and so to the nineteenth and twentieth centuries. The narrative moves from the Pythagorean insistence on positive multiples to the gradual acceptance of negative numbers, irrationals and complex numbers as essential tools in quantitative analysis. Within this chronological framework, chapters are organised thematically, covering a variety of topics and contexts: writing and solving equations, geometric construction, coordinates and complex numbers, perceptions of ‘infinity’ and its permissible uses in mathematics, number systems, and evolving views of the role of axioms. Through this approach, the author demonstrates that changes in our understanding of numbers have often relied on the breaking of long-held conventions to make way for new inventions at once providing greater clarity and widening mathematical horizons. Viewed from this historical perspective, mathematical abstraction emerges as neither mysterious nor immutable, but as a contingent, developing human activity. Making up Numbers will be of great interest to undergraduate and A-level students of mathematics, as well as secondary school teachers of the subject. In virtue of its detailed treatment of mathematical ideas, it will be of value to anyone seeking to learn more about the development of the subject.','2020-10-23','280','Available','0','Active','2021-06-09  16:22:52'),
	('JwZDTaHsx5cC','Federal Energy and Fleet Management: Plug-in Vehicles Offer Potential Benefits, But High Costs and Limited Information Could Hinder Integration Into the Federal Fleet','[{"type":"ISBN_13","identifier":"9781437917987"},{"type":"ISBN_10","identifier":"1437917984"}]','The U.S. transportation sector relies almost exclusively on oil; as a result, it causes about a third of the nation''s greenhouse gas emissions. Advanced technology vehicles powered by alternative fuels, such as electricity and ethanol, are one way to reduce oil consumption. The fed. gov¿t. set a goal for fed. agencies to use plug-in hybrid electric vehicles -- vehicles that run on both gasoline and batteries charged by connecting a plug into an electric power source -- as they become available at a reasonable cost. This report examined the: (1) potential benefits of plug-ins; (2) factors affecting the availability of plug-ins; and (3) challenges to incorporating plug-ins into the fed. fleet. Illustrations.','2009-11-01','53','Available','0','Active','2021-06-09  16:22:54'),
	('ez0VRbQi1i4C','Mariology: A Guide for Priests, Deacons, Seminarians, and Consecrated Persons','[{"type":"ISBN_13","identifier":"9781579183554"},{"type":"ISBN_10","identifier":"1579183557"}]',NULL,'1905-06-30','887','Available','0','Active','2021-06-09  16:22:54'),
	('aby2XI0np5UC','Frenchman in America','[{"type":"ISBN_13","identifier":"9781429004954"},{"type":"ISBN_10","identifier":"1429004959"}]','Mr. O''Rell''s good-humored and clever observations offer today''s reader a refreshing image of America, based on the author''s experiences while on an extensive book tour. Mr. O''Rell even offers a spry and entertaining essay on Texas, and why he chose not to go there.','2007-01-01','388','Available','0','Active','2021-06-09  16:22:55');

INSERT INTO `category`
	(`categoryId`,`categoryName`,`categoryType`,`categoryDescription`,`categoryStatus`,`categoryAddDate`)
VALUES
	('1','Ekkehard Kopp','Author',NULL,'Active','2021-06-09  16:22:52'),
	('2','Open Book Publishers','Publisher',NULL,'Active','2021-06-09  16:22:52'),
	('3','Mathematics','Subject',NULL,'Active','2021-06-09  16:22:52'),
	('4','Susan A. Fleming','Author',NULL,'Active','2021-06-09  16:22:54'),
	('5','DIANE Publishing','Publisher',NULL,'Active','2021-06-09  16:22:54'),
	('6','Various Authors','Author',NULL,'Active','2021-06-09  16:22:54'),
	('7','Mark I. Miravalle, S.T.D.','Publisher',NULL,'Active','2021-06-09  16:22:54'),
	('8','Religion','Subject',NULL,'Active','2021-06-09  16:22:54'),
	('9','Max O''Rell','Author',NULL,'Active','2021-06-09  16:22:55'),
	('10','Applewood Books','Publisher',NULL,'Active','2021-06-09  16:22:55'),
	('11','Travel','Subject',NULL,'Active','2021-06-09  16:22:55');

INSERT INTO `key`
	(`keyId`,`keyStatus`,`keyAddDate`,`bookId`,`categoryId`,`listId`)
VALUES
	('1','Active','2021-06-09  16:22:52','FEgFEAAAQBAJ','1',NULL),
	('2','Active','2021-06-09  16:22:52','FEgFEAAAQBAJ','2',NULL),
	('3','Active','2021-06-09  16:22:52','FEgFEAAAQBAJ','3',NULL),
	('4','Active','2021-06-09  16:22:54','JwZDTaHsx5cC','4',NULL),
	('5','Active','2021-06-09  16:22:54','JwZDTaHsx5cC','5',NULL),
	('6','Active','2021-06-09  16:22:54','ez0VRbQi1i4C','6',NULL),
	('7','Active','2021-06-09  16:22:54','ez0VRbQi1i4C','7',NULL),
	('8','Active','2021-06-09  16:22:54','ez0VRbQi1i4C','8',NULL),
	('9','Active','2021-06-09  16:22:55','aby2XI0np5UC','9',NULL),
	('10','Active','2021-06-09  16:22:55','aby2XI0np5UC','10',NULL),
	('11','Active','2021-06-09  16:22:55','aby2XI0np5UC','11',NULL);
	