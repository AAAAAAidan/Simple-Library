# Row insert statements

USE `simplelibrary`;

INSERT INTO `book`
  (`book_id`,`book_title`,`book_description`,`book_publish_date`,`book_page_count`,`book_availability`,`book_total_borrows`,`book_status`,`book_add_date`)
VALUES
  ('BgUueiR4Lj0C','Prohibited Personnel Practices: A Study Retrospective','The U.S. Merit Systems Protection Board (MSPB) launched a re-examination of the prevalence of prohibited personnel practices (PPP) within the Federal Government. This report highlights what the MSPB has learned from past studies in which they examined PPP. It focuses on the PPP because occurrences of these particular behaviors can have an exceptionally negative impact on the morale and productivity of any Federal office. This report identifies 12 PPP, such as discrimination, retaliation, nepotism, deceit or obstruction, coercing political activity, violating veterans¿ preference requirements, or taking or failing to take any personnel action that violates any law, rule, or regulation directly concerning the merit system principles. Illustrations.','2010-10-01','32','Available','0','Active','2021-08-03  02:44:48'),
  ('bl49OgJCvCYC','Transforming Scholarly Publishing Through Open Access: A Bibliography','Can scholarly journal articles and other scholarly works be made freely available on the Internet? The open access movement says "yes," and it is having a significant impact on scholarly publishing. There are two major open access strategies: (1) open access journals publish articles (typically peer-reviewed articles) that are free of charge and may be able to be reused under an open license (e.g., a Creative Commons license), and (2) self-archiving of digital e-prints (typically prepublication versions of articles) by authors in digital repositories, where they can be accessed free of charge and sometimes reused. Transforming Scholarly Publishing through Open Access: A Bibliography, which has over 1,100 references, provides in-depth coverage of published journal articles, books, and other works about the open access movement. Many references have links to freely available copies of included works.','2010-01-01','176','Available','0','Active','2021-08-03  02:44:49'),
  ('B_QC-hKqFhEC','Summary of Activities One Hundred Ninth Congress: A Report of the Committee on Standards of Official Conduct, House of Representatives',NULL,'2007-01-01','54','Available','0','Active','2021-08-03  02:44:50'),
  ('0NkTHRldUE4C','Notes of a Tour in America','An Irishman working for the Baptist church travels through New England, with most of his stops being opportunities to preach; many observations on local religious observance, with detailed statistics; also significant discussion of slavery.','2007-01-01','160','Available','0','Active','2021-08-03  02:44:51'),
  ('Wads4igrTJAC','A New Social Contract for Peru: An Agenda for Improving Education, Health Care, and the Social Safety Net','Using the accountability framework developed by the World Development Report 2004: Making Services Work for Poor People, this book analyzes the low-level equilibrium and the numerous reforms attempted in recent decades in Peru, and, based on this analysis, proposes interventions that would facilitate the creation of a new social contract for Peru.','2006-01-01','303','Available','0','Active','2021-08-03  02:44:52'),
  ('EfTY9SCAlMwC','Wildlife habitat basics: a series of 52 short articles on wildlife conservation',NULL,'2004-01-01','56','Available','0','Active','2021-08-03  02:44:53'),
  ('J_C-Rp3lCnwC','Protecting our most vulnerable residents: a review of reform efforts at the District of Columbia Child and Family Services Agency : hearing before the Committee on Government Reform, House of Representatives, One Hundred Eighth Congress, first session, May 16, 2003',NULL,'2003-01-01','144','Available','0','Active','2021-08-03  02:44:54'),
  ('q25wZf0qnwoC','Reducing Tobacco Use: A Report of the Surgeon General : Executive Summary',NULL,'2000-01-01','22','Available','0','Active','2021-08-03  02:44:55'),
  ('zsMevYbz2OMC','Treatment of Adolescents With Substance Use Disorders: A Treatment Improvement Protocol','Adolescents differ from adults both physiologically & emotionally as they make the transition from child to adult &, thus, require treatment adapted to their needs. This report details the scope & complexity of the problem. Presents factors to be considered when making treatment decisions. Discusses successful program components. Describes the treatment approaches used in 12-Step-based programs, therapeutic communities, & family therapy respectively. Discusses adolescents with distinctive treatment needs, such as those involved with the juvenile justice system. Explains legal issues concerning confidentiality laws.','1999-10-01','126','Available','0','Active','2021-08-03  02:44:55');

INSERT INTO `isbn`
  (`isbn_id`,`isbn_type`,`isbn_status`,`isbn_add_date`,`book_id`)
VALUES
  ('9781437935264','ISBN_13','Active','2021-08-03  02:44:48','BgUueiR4Lj0C'),
  ('1437935265','ISBN_10','Active','2021-08-03  02:44:48','BgUueiR4Lj0C'),
  ('9781453780817','ISBN_13','Active','2021-08-03  02:44:49','bl49OgJCvCYC'),
  ('1453780815','ISBN_10','Active','2021-08-03  02:44:49','bl49OgJCvCYC'),
  ('PURD:32754082299433','OTHER','Active','2021-08-03  02:44:50','B_QC-hKqFhEC'),
  ('9781429001519','ISBN_13','Active','2021-08-03  02:44:51','0NkTHRldUE4C'),
  ('1429001518','ISBN_10','Active','2021-08-03  02:44:51','0NkTHRldUE4C'),
  ('9780821365687','ISBN_13','Active','2021-08-03  02:44:52','Wads4igrTJAC'),
  ('821365681','ISBN_10','Active','2021-08-03  02:44:52','Wads4igrTJAC'),
  ('MINN:31951D02461531B','OTHER','Active','2021-08-03  02:44:53','EfTY9SCAlMwC'),
  ('PURD:32754077067480','OTHER','Active','2021-08-03  02:44:54','J_C-Rp3lCnwC'),
  ('PURD:32754071790541','OTHER','Active','2021-08-03  02:44:55','q25wZf0qnwoC'),
  ('9780788185854','ISBN_13','Active','2021-08-03  02:44:55','zsMevYbz2OMC'),
  ('788185853','ISBN_10','Active','2021-08-03  02:44:55','zsMevYbz2OMC');

INSERT INTO `category`
  (`category_id`,`category_name`,`category_type`,`category_description`,`category_status`,`category_add_date`)
VALUES
  ('1','Susan Tsui Grunmann','Author',NULL,'Active','2021-08-03  02:44:48'),
  ('2','DIANE Publishing','Publisher',NULL,'Active','2021-08-03  02:44:48'),
  ('3','Charles Wesley Bailey','Author',NULL,'Active','2021-08-03  02:44:49'),
  ('4','Charles W  Bailey Jr','Publisher',NULL,'Active','2021-08-03  02:44:49'),
  ('5','Reference','Subject',NULL,'Active','2021-08-03  02:44:49'),
  ('6','United States. Congress. House. Committee on Standards of Official Conduct','Author',NULL,'Active','2021-08-03  02:44:50'),
  ('7','Stephen Davis','Author',NULL,'Active','2021-08-03  02:44:51'),
  ('8','Applewood Books','Publisher',NULL,'Active','2021-08-03  02:44:51'),
  ('9','Travel','Subject',NULL,'Active','2021-08-03  02:44:51'),
  ('10','Daniel Cotlear','Author',NULL,'Active','2021-08-03  02:44:52'),
  ('11','World Bank Publications','Publisher',NULL,'Active','2021-08-03  02:44:52'),
  ('12','Political Science','Subject',NULL,'Active','2021-08-03  02:44:52'),
  ('13','Government publications','Subject',NULL,'Active','2021-08-03  02:44:53'),
  ('14','United States','Author',NULL,'Active','2021-08-03  02:44:54'),
  ('15','United States. Congress. House. Committee on Government Reform','Author',NULL,'Active','2021-08-03  02:44:54'),
  ('16','Children','Subject',NULL,'Active','2021-08-03  02:44:54'),
  ('17','Health education','Subject',NULL,'Active','2021-08-03  02:44:55'),
  ('18','Ken C. Winters, Ph.d.','Author',NULL,'Active','2021-08-03  02:44:56'),
  ('19','Social Science','Subject',NULL,'Active','2021-08-03  02:44:56');

INSERT INTO `book_category_map`
  (`book_id`,`category_id`)
VALUES
  ('BgUueiR4Lj0C','1'),
  ('BgUueiR4Lj0C','2'),
  ('bl49OgJCvCYC','3'),
  ('bl49OgJCvCYC','4'),
  ('bl49OgJCvCYC','5'),
  ('B_QC-hKqFhEC','6'),
  ('0NkTHRldUE4C','7'),
  ('0NkTHRldUE4C','8'),
  ('0NkTHRldUE4C','9'),
  ('Wads4igrTJAC','10'),
  ('Wads4igrTJAC','11'),
  ('Wads4igrTJAC','12'),
  ('EfTY9SCAlMwC','13'),
  ('J_C-Rp3lCnwC','14'),
  ('J_C-Rp3lCnwC','15'),
  ('J_C-Rp3lCnwC','16'),
  ('q25wZf0qnwoC','17'),
  ('zsMevYbz2OMC','18'),
  ('zsMevYbz2OMC','2'),
  ('zsMevYbz2OMC','19');
