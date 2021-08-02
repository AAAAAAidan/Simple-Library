# Row insert statements

USE `library`;

INSERT INTO `book`
  (`bookId`,`bookTitle`,`bookDescription`,`bookPublishDate`,`bookPageCount`,`bookAvailability`,`bookTotalBorrows`,`bookStatus`,`bookAddDate`)
VALUES
  ('BgUueiR4Lj0C','Prohibited Personnel Practices: A Study Retrospective','The U.S. Merit Systems Protection Board (MSPB) launched a re-examination of the prevalence of prohibited personnel practices (PPP) within the Federal Government. This report highlights what the MSPB has learned from past studies in which they examined PPP. It focuses on the PPP because occurrences of these particular behaviors can have an exceptionally negative impact on the morale and productivity of any Federal office. This report identifies 12 PPP, such as discrimination, retaliation, nepotism, deceit or obstruction, coercing political activity, violating veterans¿ preference requirements, or taking or failing to take any personnel action that violates any law, rule, or regulation directly concerning the merit system principles. Illustrations.','2010-10-01','32','Available','0','Active','2021-08-01  23:58:39'),
  ('B_QC-hKqFhEC','Summary of Activities One Hundred Ninth Congress: A Report of the Committee on Standards of Official Conduct, House of Representatives',NULL,'2007-01-01','54','Available','0','Active','2021-08-01  23:58:40'),
  ('0NkTHRldUE4C','Notes of a Tour in America','An Irishman working for the Baptist church travels through New England, with most of his stops being opportunities to preach; many observations on local religious observance, with detailed statistics; also significant discussion of slavery.','2007-01-01','160','Available','0','Active','2021-08-01  23:58:41'),
  ('Wads4igrTJAC','A New Social Contract for Peru: An Agenda for Improving Education, Health Care, and the Social Safety Net','Using the accountability framework developed by the World Development Report 2004: Making Services Work for Poor People, this book analyzes the low-level equilibrium and the numerous reforms attempted in recent decades in Peru, and, based on this analysis, proposes interventions that would facilitate the creation of a new social contract for Peru.','2006-01-01','303','Available','0','Active','2021-08-01  23:58:43'),
  ('EfTY9SCAlMwC','Wildlife habitat basics: a series of 52 short articles on wildlife conservation',NULL,'2004-01-01','56','Available','0','Active','2021-08-01  23:58:44'),
  ('J_C-Rp3lCnwC','Protecting our most vulnerable residents: a review of reform efforts at the District of Columbia Child and Family Services Agency : hearing before the Committee on Government Reform, House of Representatives, One Hundred Eighth Congress, first session, May 16, 2003',NULL,'2003-01-01','144','Available','0','Active','2021-08-01  23:58:45'),
  ('q25wZf0qnwoC','Reducing Tobacco Use: A Report of the Surgeon General : Executive Summary',NULL,'2000-01-01','22','Available','0','Active','2021-08-01  23:58:46');

INSERT INTO `isbn`
  (`IsbnId`,`IsbnType`,`bookId`)
VALUES
  ('9781437935264','ISBN_13','BgUueiR4Lj0C'),
  ('1437935265','ISBN_10','BgUueiR4Lj0C'),
  ('PURD:32754082299433','OTHER','B_QC-hKqFhEC'),
  ('9781429001519','ISBN_13','0NkTHRldUE4C'),
  ('1429001518','ISBN_10','0NkTHRldUE4C'),
  ('9780821365687','ISBN_13','Wads4igrTJAC'),
  ('821365681','ISBN_10','Wads4igrTJAC'),
  ('MINN:31951D02461531B','OTHER','EfTY9SCAlMwC'),
  ('PURD:32754077067480','OTHER','J_C-Rp3lCnwC'),
  ('PURD:32754071790541','OTHER','q25wZf0qnwoC');

INSERT INTO `category`
  (`categoryId`,`categoryName`,`categoryType`,`categoryDescription`,`categoryStatus`,`categoryAddDate`)
VALUES
  ('1','Susan Tsui Grunmann','Author',NULL,'Active','2021-08-01  23:58:39'),
  ('2','DIANE Publishing','Publisher',NULL,'Active','2021-08-01  23:58:39'),
  ('3','United States. Congress. House. Committee on Standards of Official Conduct','Author',NULL,'Active','2021-08-01  23:58:40'),
  ('4','Stephen Davis','Author',NULL,'Active','2021-08-01  23:58:41'),
  ('5','Applewood Books','Publisher',NULL,'Active','2021-08-01  23:58:41'),
  ('6','Travel','Subject',NULL,'Active','2021-08-01  23:58:41'),
  ('7','Daniel Cotlear','Author',NULL,'Active','2021-08-01  23:58:43'),
  ('8','World Bank Publications','Publisher',NULL,'Active','2021-08-01  23:58:43'),
  ('9','Political Science','Subject',NULL,'Active','2021-08-01  23:58:43'),
  ('10','Government publications','Subject',NULL,'Active','2021-08-01  23:58:44'),
  ('11','United States','Author',NULL,'Active','2021-08-01  23:58:45'),
  ('12','United States. Congress. House. Committee on Government Reform','Author',NULL,'Active','2021-08-01  23:58:45'),
  ('13','Children','Subject',NULL,'Active','2021-08-01  23:58:45'),
  ('14','Health education','Subject',NULL,'Active','2021-08-01  23:58:46');

INSERT INTO `bookcategorymap`
  (`bookId`,`categoryId`)
VALUES
  ('BgUueiR4Lj0C','1'),
  ('BgUueiR4Lj0C','2'),
  ('B_QC-hKqFhEC','3'),
  ('0NkTHRldUE4C','4'),
  ('0NkTHRldUE4C','5'),
  ('0NkTHRldUE4C','6'),
  ('Wads4igrTJAC','7'),
  ('Wads4igrTJAC','8'),
  ('Wads4igrTJAC','9'),
  ('EfTY9SCAlMwC','10'),
  ('J_C-Rp3lCnwC','11'),
  ('J_C-Rp3lCnwC','12'),
  ('J_C-Rp3lCnwC','13'),
  ('q25wZf0qnwoC','14');
