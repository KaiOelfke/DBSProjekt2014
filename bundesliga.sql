SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema bundesliga
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `bundesliga` ;
CREATE SCHEMA IF NOT EXISTS `bundesliga` DEFAULT CHARACTER SET latin1 ;
USE `bundesliga` ;

-- -----------------------------------------------------
-- Table `bundesliga`.`liga`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bundesliga`.`liga` ;

CREATE TABLE IF NOT EXISTS `bundesliga`.`liga` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bundesliga`.`verein`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bundesliga`.`verein` ;

CREATE TABLE IF NOT EXISTS `bundesliga`.`verein` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `liga` INT(11) NOT NULL,
  `stadion` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`id` ASC),
  INDEX `fk_verein_liga1_idx` (`liga` ASC),
  CONSTRAINT `fk_verein_liga1`
    FOREIGN KEY (`liga`)
    REFERENCES `bundesliga`.`liga` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bundesliga`.`spieler`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bundesliga`.`spieler` ;

CREATE TABLE IF NOT EXISTS `bundesliga`.`spieler` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `verein` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `trikotnummer` INT NOT NULL,
  `heimatland` VARCHAR(45) NOT NULL,
  `tore` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_spieler_verein1_idx` (`verein` ASC),
  CONSTRAINT `fk_spieler_verein1`
    FOREIGN KEY (`verein`)
    REFERENCES `bundesliga`.`verein` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bundesliga`.`spiel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bundesliga`.`spiel` ;

CREATE TABLE IF NOT EXISTS `bundesliga`.`spiel` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `datum` DATE NOT NULL,
  `uhrzeit` TIME NOT NULL,
  `gastgeber` INT NOT NULL,
  `gast` INT NOT NULL,
  `gastgeber_tore` INT NOT NULL,
  `gast_tore` INT NOT NULL,
  `spieltag` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_spiel_verein1_idx` (`gastgeber` ASC),
  INDEX `fk_spiel_verein2_idx` (`gast` ASC),
  CONSTRAINT `fk_spiel_verein1`
    FOREIGN KEY (`gastgeber`)
    REFERENCES `bundesliga`.`verein` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_spiel_verein2`
    FOREIGN KEY (`gast`)
    REFERENCES `bundesliga`.`verein` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
