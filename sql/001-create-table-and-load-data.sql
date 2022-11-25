DROP TABLE IF EXISTS movies;

CREATE TABLE movies (
  id int unsigned AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  director VARCHAR(100) NOT NULL,
  published_year int(4) NOT NULL,

  PRIMARY KEY(id)
);

INSERT INTO movies (name, director, published_year) VALUES ("ショーシャンクの空に", "フランク・ダラボン","1994");
INSERT INTO movies (name, director, published_year) VALUES ("この世界の片隅に", "片渕須直","2020");
INSERT INTO movies (name, director, published_year) VALUES ("アルマゲドン", "ジェリー・ブラッカイマー","1998");
