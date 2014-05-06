use mikroerp;

CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `suffix` varchar(10) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `employedAt` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `invoiceAddress` varchar(45) DEFAULT NULL,
  `shippingAddress` varchar(45) DEFAULT NULL,
  `plz` int(11) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2

use mikroerp;

CREATE TABLE `invoices` (
  `invoiceNumber` int(11) NOT NULL AUTO_INCREMENT,
  `isOutgoing` bit(1) DEFAULT NULL,
  `creationDate` date DEFAULT NULL,
  `expirationDate` date DEFAULT NULL,
  `comment` varchar(45) DEFAULT NULL,
  `message` varchar(100) DEFAULT NULL,
  `customerName` varchar(45) DEFAULT NULL,
  `shippingAddress` varchar(45) DEFAULT NULL,
  `invoiceAddress` varchar(45) DEFAULT NULL,
  `ust` int(11) DEFAULT NULL,
  `gross` int(11) DEFAULT NULL,
  `net` int(11) DEFAULT NULL,
  `fkCustomerId` int(11) DEFAULT NULL,
	FOREIGN KEY (`fkCustomerId`) 
        REFERENCES customer(`id`),
  PRIMARY KEY (`invoiceNumber`)
) ENGINE=InnoDB

