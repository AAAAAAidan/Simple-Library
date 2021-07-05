INSERT INTO `book`
	(`bookId`,`bookTitle`,`bookIdentifiers`,`bookDescription`,`bookPublishDate`,`bookPageCount`,`bookAvailability`,`bookTotalBorrows`,`bookStatus`,`bookAddDate`)
VALUES
	('D0zFr3xc4bMC','A Christmas Carol','[{"type":"ISBN_10","identifier":"1603035052"},{"type":"ISBN_13","identifier":"9781603035057"}]','This Graphic Novel Series features classic tales retold with attractive color illustrations. Educatiors using the Dale-Chall vocabulary system adapted each title. Each 70 page, softcover book retains key phrases and quotations from the original classics. Introduce literature to reluctant readers and motivate struggling readers. Students build confidence through reading practice. Motivation makes all the difference. What''s more motivation then the expectation of success?','2010-09-01','64','Available','0','Active','2021-07-05  16:38:18'),
	('0NkTHRldUE4C','Notes of a Tour in America','[{"type":"ISBN_13","identifier":"9781429001519"},{"type":"ISBN_10","identifier":"1429001518"}]','An Irishman working for the Baptist church travels through New England, with most of his stops being opportunities to preach; many observations on local religious observance, with detailed statistics; also significant discussion of slavery.','2007-01-01','160','Available','0','Active','2021-07-05  16:38:19'),
	('Wads4igrTJAC','A New Social Contract for Peru: An Agenda for Improving Education, Health Care, and the Social Safety Net','[{"type":"ISBN_13","identifier":"9780821365687"},{"type":"ISBN_10","identifier":"0821365681"}]','Using the accountability framework developed by the World Development Report 2004: Making Services Work for Poor People, this book analyzes the low-level equilibrium and the numerous reforms attempted in recent decades in Peru, and, based on this analysis, proposes interventions that would facilitate the creation of a new social contract for Peru.','2006-01-01','303','Available','0','Active','2021-07-05  16:38:20'),
	('zsMevYbz2OMC','Treatment of Adolescents With Substance Use Disorders: A Treatment Improvement Protocol','[{"type":"ISBN_13","identifier":"9780788185854"},{"type":"ISBN_10","identifier":"0788185853"}]','Adolescents differ from adults both physiologically & emotionally as they make the transition from child to adult &, thus, require treatment adapted to their needs. This report details the scope & complexity of the problem. Presents factors to be considered when making treatment decisions. Discusses successful program components. Describes the treatment approaches used in 12-Step-based programs, therapeutic communities, & family therapy respectively. Discusses adolescents with distinctive treatment needs, such as those involved with the juvenile justice system. Explains legal issues concerning confidentiality laws.','1999-10-01','126','Available','0','Active','2021-07-05  16:38:21');

INSERT INTO `category`
	(`categoryId`,`categoryName`,`categoryType`,`categoryDescription`,`categoryStatus`,`categoryAddDate`)
VALUES
	('1','Charles Dickens','Author',NULL,'Active','2021-07-05  16:38:18'),
	('2','Juvenile Fiction','Subject',NULL,'Active','2021-07-05  16:38:18'),
	('3','Stephen Davis','Author',NULL,'Active','2021-07-05  16:38:19'),
	('4','Applewood Books','Publisher',NULL,'Active','2021-07-05  16:38:19'),
	('5','Travel','Subject',NULL,'Active','2021-07-05  16:38:19'),
	('6','Daniel Cotlear','Author',NULL,'Active','2021-07-05  16:38:20'),
	('7','World Bank Publications','Publisher',NULL,'Active','2021-07-05  16:38:20'),
	('8','Political Science','Subject',NULL,'Active','2021-07-05  16:38:20'),
	('9','Ken C. Winters, Ph.d.','Author',NULL,'Active','2021-07-05  16:38:21'),
	('10','DIANE Publishing','Publisher',NULL,'Active','2021-07-05  16:38:21'),
	('11','Social Science','Subject',NULL,'Active','2021-07-05  16:38:21');

INSERT INTO `bookcategorymap`
	(`bookId`,`categoryId`)
VALUES
	('D0zFr3xc4bMC','1'),
	('D0zFr3xc4bMC','2'),
	('0NkTHRldUE4C','3'),
	('0NkTHRldUE4C','4'),
	('0NkTHRldUE4C','5'),
	('Wads4igrTJAC','6'),
	('Wads4igrTJAC','7'),
	('Wads4igrTJAC','8'),
	('zsMevYbz2OMC','9'),
	('zsMevYbz2OMC','10'),
	('zsMevYbz2OMC','11');
