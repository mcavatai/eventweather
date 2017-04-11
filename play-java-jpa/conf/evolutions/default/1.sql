# --- !Ups
CREATE TABLE PlaySearches (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  searchcount INT NOT NULL,
  PRIMARY KEY (id)
);

# --- !Downs
DROP TABLE PlaySearches;