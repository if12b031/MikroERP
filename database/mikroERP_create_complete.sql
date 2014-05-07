CREATE DATABASE  IF NOT EXISTS `mikroerp` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `mikroerp`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: mikroerp
-- ------------------------------------------------------
-- Server version	5.5.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `articles`
--

DROP TABLE IF EXISTS `articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles`
--

LOCK TABLES `articles` WRITE;
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` VALUES (1,'Hose',100),(2,'T-Shirt',27),(3,'Weiße Jacke',121),(4,'Pullover Rot',70),(5,'Schuhe',56);
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `suffix` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `employedAt` varchar(80) DEFAULT NULL,
  `address` varchar(100) NOT NULL,
  `plz` int(11) NOT NULL,
  `city` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,NULL,NULL,'Dr.',NULL,'Georg','Huszar',NULL,NULL,'Gewerbestraße 22',5066,'St. Pölten'),(2,NULL,NULL,'DDr.',NULL,'Stefan','Ziffer',NULL,NULL,'Haushochgasse 1',1220,'Wien'),(3,5698,'Meister Bau AG.',NULL,NULL,NULL,NULL,NULL,NULL,'Meisterstraße 2',3355,'Badenberg'),(4,424242,'Erste Bank',NULL,NULL,NULL,NULL,NULL,NULL,'Stephansplatz 42',1010,'Wien'),(5,NULL,NULL,NULL,'Msc.','Philipp','Müllner',NULL,NULL,'Am Lande 10',6666,'Wolkersdorf');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `invoiceNumber` int(11) NOT NULL,
  `isOutgoing` bit(1) NOT NULL,
  `creationDate` date NOT NULL,
  `expirationDate` date NOT NULL,
  `comment` varchar(45) DEFAULT NULL,
  `message` text,
  `customerName` varchar(45) NOT NULL,
  `shippingAddress` varchar(45) NOT NULL,
  `invoiceAddress` varchar(45) NOT NULL,
  `ust` int(11) NOT NULL,
  `gross` int(11) NOT NULL,
  `net` int(11) NOT NULL,
  `fkCustomerId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fkCustomerId` (`fkCustomerId`),
  CONSTRAINT `invoices_ibfk_1` FOREIGN KEY (`fkCustomerId`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
INSERT INTO `invoices` VALUES (1,10,'\0','2014-05-07','2021-05-07',NULL,'Das ist eine sehr lange Nachricht. Ich hoffe sie wird korrekt gespeichert','Georg Huszar','Gewerbestraße 22, 5066 St. Pölten','Gewerbestraße 22, 5066 St. Pölten',535,3211,2676,1),(2,11,'','2014-05-07','2021-05-07',NULL,'Das ist eine sehr lange Nachricht. Ich hoffe sie wird korrekt gespeichert','Stefan Ziffer','Haushochgasse 1, 1220 Wien','Gewerbestraße 22, 5066 St. Pölten',535,3211,2676,2),(3,12,'','2014-05-07','2021-05-07',NULL,'Das ist eine sehr lange Nachricht. Ich hoffe sie wird korrekt gespeichert','Meister Bau AG.','Meisterstraße 2, 3355 Badenberg','Meisterstraße 2, 3355 Badenberg',535,3211,2676,3),(4,13,'','2014-05-07','2021-05-07',NULL,'Das ist eine sehr lange Nachricht. Ich hoffe sie wird korrekt gespeichert','Erste Bank','Stephansplatz 42, 1010 Wien','Stephansplatz 42, 1010 Wien',535,3211,2676,4),(5,14,'','2014-05-07','2021-05-07',NULL,'Das ist eine sehr lange Nachricht. Ich hoffe sie wird korrekt gespeichert','Philipp Müllner','Am Lande 10, 6666 Wolkersdorf','Gewerbestraße 22, 5066 St. Pölten',535,3211,2676,5);
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices2articles`
--

DROP TABLE IF EXISTS `invoices2articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoices2articles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkInvoiceId` int(11) NOT NULL,
  `fkArticleId` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fkInvoiceId` (`fkInvoiceId`),
  KEY `fkArticleId` (`fkArticleId`),
  CONSTRAINT `invoices2articles_ibfk_1` FOREIGN KEY (`fkInvoiceId`) REFERENCES `invoices` (`id`),
  CONSTRAINT `invoices2articles_ibfk_2` FOREIGN KEY (`fkArticleId`) REFERENCES `articles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices2articles`
--

LOCK TABLES `invoices2articles` WRITE;
/*!40000 ALTER TABLE `invoices2articles` DISABLE KEYS */;
INSERT INTO `invoices2articles` VALUES (1,1,1,1),(2,2,1,1),(3,3,1,1),(4,4,1,1),(5,1,2,1),(6,2,2,1),(7,3,2,1),(8,4,2,1),(9,1,3,1),(10,2,3,1),(11,3,3,1),(12,4,3,1),(13,1,4,1),(14,2,4,1),(15,3,4,1),(16,4,4,1),(17,1,5,1),(18,2,5,1),(19,3,5,1),(20,4,5,1);
/*!40000 ALTER TABLE `invoices2articles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-07 12:02:42
