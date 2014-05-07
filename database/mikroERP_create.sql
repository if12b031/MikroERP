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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `invoices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `invoiceNumber` int(11) NOT NULL,
  `isOutgoing` bit(1) NOT NULL,
  `creationDate` date NOT NULL,
  `expirationDate` date NOT NULL,
  `comment` varchar(45) DEFAULT NULL,
  `message` Text,
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `articles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `invoices2articles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkInvoiceId` int(11) NOT NULL,
  `fkArticleId` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fkInvoiceId` (`fkInvoiceId`),
  CONSTRAINT `invoices2articles_ibfk_1` FOREIGN KEY (`fkInvoiceId`) REFERENCES `invoices` (`id`),
  KEY `fkArticleId` (`fkArticleId`),
  CONSTRAINT `invoices2articles_ibfk_2` FOREIGN KEY (`fkArticleId`) REFERENCES `articles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;




