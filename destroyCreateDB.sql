/* Guarcas and Rodriguez
 * Date: 2022-03-26
 * Description: use to destroy/create database, in progress
 * Environment: 10.3.31-MariaDB-0+deb10u1 on school Linux server "artemis"
 * To run: log into mysql, select db, then "source filename.sql"
 */

-- drop tables starting with leaves
DROP TABLE IF EXISTS medications;
DROP TABLE IF EXISTS dailyHealth;
DROP TABLE IF EXISTS pet;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS species;

-- referenced by 'pet' table
CREATE TABLE `species` (
  `speciesID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `speciesName` varchar(15) NOT NULL,
  PRIMARY KEY (`speciesID`)
);

INSERT INTO species (speciesName) VALUES ('Dog'),('Cat'),('Bird'),('Fish'),('Reptile'),('Amphibian'),('Small-Mammall'),('Large-Mammal'),('Other');

-- all pets must be tied to a user
CREATE TABLE `user` (
  `userID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(60) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`userID`)
);

ALTER TABLE user AUTO_INCREMENT= 1000;

-- test users
INSERT INTO user (userName, email, password) VALUES ('nick', 'nick@nite', 'password');
INSERT INTO user (userName, email, password) VALUES ('Señor TestUser', 'señor@email.com', 'password123');
INSERT INTO user (userName, email, password) VALUES ('Glenda', 'gdog@email.com', '2Bencrypted');
INSERT INTO user (userName, email, password) VALUES ('Joey', 'jman@email.com', '2Bencrypted2');

CREATE TABLE `pet` (
  `userID` int(10) unsigned NOT NULL,
  `petID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `petName` varchar(30) NOT NULL,
  `speciesID` int(10) unsigned NOT NULL,
  `birthDate` date DEFAULT NULL,
  `sex` enum('Female','Male','Other') DEFAULT NULL,
  `microchipNum` varchar(30) DEFAULT NULL,
  `petPic` mediumblob DEFAULT NULL,
  PRIMARY KEY (`petID`),
  KEY `userID` (`userID`),
  KEY `speciesID` (`speciesID`),
  CONSTRAINT `pet_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`),
  CONSTRAINT `pet_ibfk_2` FOREIGN KEY (`speciesID`) REFERENCES `species` (`speciesID`)
);

ALTER TABLE pet AUTO_INCREMENT= 1000;

-- test pets
INSERT INTO pet (userID, petName, speciesID, birthDate, sex) VALUES (1001, 'Banjo', 2, '2016-03-17', 'Male');
INSERT INTO pet (userID, petName, speciesID, sex) VALUES (1001, 'Daisy', 1, 1);
INSERT INTO pet (userID, petName, speciesID, birthDate, sex) VALUES (1002, 'Buttercup', 1, '2020-04-20', 'Female');
INSERT INTO pet (userID, petName, speciesID, birthDate, sex) VALUES (1002, 'Rocco', 2, '2020-06-09', 'Male');
INSERT INTO pet (userID, petName, speciesID, birthDate, sex) VALUES (1002, 'Scarlete', 2, '2020-06-09', 'Female');
INSERT INTO pet (userID, petName, speciesID, sex) VALUES (1003, 'Isaiah', 2, 'Male');

-- keeps track of daily health of pet
CREATE TABLE `dailyHealth` (
  `petID` int(10) unsigned NOT NULL,
  `logDay` date DEFAULT current_timestamp(),
  `eatingHabits` enum('Not Eating','Eating Less','Regular','Excessive Eating','Other') DEFAULT 'Regular',
  `treat` enum('None','One','Multiple') DEFAULT 'None',
  `vomit` enum('No Vomit','Vomit','Excessive Vomiting') DEFAULT 'No Vomit',
  `urineHabits` enum('Not Peeing','Peeing Less','Regular','Excessive Peeing','Other') DEFAULT 'Regular',
  `poopHabits` enum('No Poop','Less Poop','Regular','Diarrhea','Other') DEFAULT 'Regular',
  `exercise` enum('None','One Play or Walk Session','Multiple Play or Walk Sessions','Other') DEFAULT 'None',
  `sleepHabits` enum('Not Sleeping','Regular','Excessive Sleeping','Other') DEFAULT 'Regular',
  `dayNote` varchar(500) DEFAULT NULL,
  KEY `petID` (`petID`),
  CONSTRAINT `dailyHealth_ibfk_1` FOREIGN KEY (`petID`) REFERENCES `pet` (`petID`)
);

ALTER TABLE dailyHealth AUTO_INCREMENT= 1000;

-- holds table for medications pets take
CREATE TABLE `medications` (
  `petID` int(10) unsigned NOT NULL,
  `medID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `medName` varchar(60) DEFAULT NULL,
  `medNotes` varchar(300) DEFAULT NULL,
  `prescriber` varchar(60) DEFAULT NULL,
  `frequency` varchar(60) DEFAULT NULL,
  `alarm` time DEFAULT NULL,
  PRIMARY KEY (`medID`),
  KEY `petID` (`petID`),
  CONSTRAINT `medications_ibfk_1` FOREIGN KEY (`petID`) REFERENCES `pet` (`petID`)
);

ALTER TABLE medications AUTO_INCREMENT= 1000;
