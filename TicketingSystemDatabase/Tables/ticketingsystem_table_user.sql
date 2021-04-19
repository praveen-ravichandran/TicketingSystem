
DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS `user` (
  UserId int(11) NOT NULL AUTO_INCREMENT,
  FirstName varchar(50) NOT NULL,
  LastName varchar(50) NOT NULL,
  EmailAddress varchar(50) NOT NULL,
  IsAgent bit(1) NOT NULL,
  PRIMARY KEY (UserId),
  UNIQUE KEY EmailAddress (EmailAddress)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
