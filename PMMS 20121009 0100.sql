-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.15


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema expenses
--

CREATE DATABASE IF NOT EXISTS expenses;
USE expenses;

--
-- Definition of table `account_bank_account`
--

DROP TABLE IF EXISTS `account_bank_account`;
CREATE TABLE `account_bank_account` (
  `ACCOUNT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `BANK_ACCOUNT_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ACCOUNT_ID`,`BANK_ACCOUNT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;


--
-- Definition of table `account_borrower`
--

DROP TABLE IF EXISTS `account_borrower`;
CREATE TABLE `account_borrower` (
  `ACCOUNT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `BORROWER_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ACCOUNT_ID`,`BORROWER_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;


--
-- Definition of table `account_credit_card`
--

DROP TABLE IF EXISTS `account_credit_card`;
CREATE TABLE `account_credit_card` (
  `ACCOUNT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CREDIT_CARD_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ACCOUNT_ID`,`CREDIT_CARD_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;



--
-- Definition of table `account_custom_expense_category`
--

DROP TABLE IF EXISTS `account_custom_expense_category`;
CREATE TABLE `account_custom_expense_category` (
  `ACCOUNT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CATEGORY_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ACCOUNT_ID`,`CATEGORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account_custom_expense_category`
--

/*!40000 ALTER TABLE `account_custom_expense_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_custom_expense_category` ENABLE KEYS */;


--
-- Definition of table `account_expense`
--

DROP TABLE IF EXISTS `account_expense`;
CREATE TABLE `account_expense` (
  `ACCOUNT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `EXPENSE_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ACCOUNT_ID`,`EXPENSE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account_expense`
--

/*!40000 ALTER TABLE `account_expense` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_expense` ENABLE KEYS */;


--
-- Definition of table `account_expense_group`
--

DROP TABLE IF EXISTS `account_expense_group`;
CREATE TABLE `account_expense_group` (
  `ACCOUNT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `EXPENSE_GROUP_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ACCOUNT_ID`,`EXPENSE_GROUP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;


--
-- Definition of table `account_expense_type`
--

DROP TABLE IF EXISTS `account_expense_type`;
CREATE TABLE `account_expense_type` (
  `ACCOUNT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `EXPENSE_TYPE_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ACCOUNT_ID`,`EXPENSE_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account_expense_type`
--

/*!40000 ALTER TABLE `account_expense_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_expense_type` ENABLE KEYS */;


--
-- Definition of table `account_income_source`
--

DROP TABLE IF EXISTS `account_income_source`;
CREATE TABLE `account_income_source` (
  `ACCOUNT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `INCOME_SOURCE_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ACCOUNT_ID`,`INCOME_SOURCE_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;


--
-- Definition of table `account_payee`
--

DROP TABLE IF EXISTS `account_payee`;
CREATE TABLE `account_payee` (
  `ACCOUNT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `PAYEE_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ACCOUNT_ID`,`PAYEE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;



--
-- Definition of table `account_user`
--

DROP TABLE IF EXISTS `account_user`;
CREATE TABLE `account_user` (
  `ACCOUNT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `USER_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ACCOUNT_ID`,`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;


--
-- Definition of table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts` (
  `ACCOUNT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ACCOUNTDESCRIPTION` varchar(45) NOT NULL,
  `PRIMARYUSERID` int(10) unsigned NOT NULL,
  `CASH_BALANCE` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`ACCOUNT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;


--
-- Definition of table `bank_account`
--

DROP TABLE IF EXISTS `bank_account`;
CREATE TABLE `bank_account` (
  `BANK_ACCOUNT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) NOT NULL,
  `CURRENT_CHECK_NUMBER` int(10) unsigned NOT NULL,
  `ACCOUNT_TYPE_ID` int(10) unsigned NOT NULL,
  `BALANCE` decimal(10,2) NOT NULL,
  `CREATEDBY` int(10) unsigned DEFAULT NULL,
  `CREATEDDATE` datetime DEFAULT NULL,
  `MODIFIEDBY` int(10) unsigned DEFAULT NULL,
  `MODIFIEDDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`BANK_ACCOUNT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;


--
-- Definition of table `bank_account_bank_account_transaction`
--

DROP TABLE IF EXISTS `bank_account_bank_account_transaction`;
CREATE TABLE `bank_account_bank_account_transaction` (
  `BANK_ACCOUNT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `BANK_ACCOUNT_TRANSACTION_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`BANK_ACCOUNT_ID`,`BANK_ACCOUNT_TRANSACTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bank_account_bank_account_transaction`
--

/*!40000 ALTER TABLE `bank_account_bank_account_transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `bank_account_bank_account_transaction` ENABLE KEYS */;


--
-- Definition of table `bank_account_transaction`
--

DROP TABLE IF EXISTS `bank_account_transaction`;
CREATE TABLE `bank_account_transaction` (
  `BANK_ACCOUNT_TRANSACTION_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `AMOUNT` decimal(10,2) NOT NULL,
  `DATE` datetime NOT NULL,
  `BANK_ACCOUNT_TRANSACTION_TYPE_ID` int(10) unsigned NOT NULL,
  `BANK_ACCOUNT_ID` int(10) unsigned NOT NULL,
  `TRANSACTION_INITIATOR_ID` int(10) unsigned DEFAULT NULL,
  `DESCRIPTION` varchar(150) DEFAULT NULL,
  `USER_ID` int(10) unsigned NOT NULL,
  `CHECK_NUMBER` varchar(45) DEFAULT NULL,
  `REASON_ID` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`BANK_ACCOUNT_TRANSACTION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=latin1;


--
-- Definition of table `bank_account_transaction_type`
--

DROP TABLE IF EXISTS `bank_account_transaction_type`;
CREATE TABLE `bank_account_transaction_type` (
  `BANK_ACCOUNT_TRANSACTION_TYPE_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) NOT NULL,
  `SIGN` int(11) NOT NULL,
  PRIMARY KEY (`BANK_ACCOUNT_TRANSACTION_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bank_account_transaction_type`
--

/*!40000 ALTER TABLE `bank_account_transaction_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `bank_account_transaction_type` ENABLE KEYS */;


--
-- Definition of table `borrower`
--

DROP TABLE IF EXISTS `borrower`;
CREATE TABLE `borrower` (
  `BORROWER_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) NOT NULL,
  PRIMARY KEY (`BORROWER_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;


--
-- Definition of table `borrowers`
--

DROP TABLE IF EXISTS `borrowers`;
CREATE TABLE `borrowers` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `borrowers`
--

/*!40000 ALTER TABLE `borrowers` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrowers` ENABLE KEYS */;


--
-- Definition of table `cash_transaction`
--

DROP TABLE IF EXISTS `cash_transaction`;
CREATE TABLE `cash_transaction` (
  `CASH_TRANSACTION_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `AMOUNT` decimal(10,2) NOT NULL,
  `DATE` datetime NOT NULL,
  `TRANSACTION_INITIATOR_ID` int(10) unsigned DEFAULT NULL,
  `CASH_TRANSACTION_TYPE_ID` int(10) unsigned NOT NULL,
  `DESCRIPTION` varchar(150) DEFAULT NULL,
  `USER_ID` int(10) unsigned NOT NULL,
  `REASON_ID` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`CASH_TRANSACTION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;


--
-- Definition of table `cash_transfer`
--

DROP TABLE IF EXISTS `cash_transfer`;
CREATE TABLE `cash_transfer` (
  `CASH_TRANSFER_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `SOURCEID` int(10) unsigned DEFAULT NULL,
  `DESTINATIONID` int(10) unsigned DEFAULT NULL,
  `AMOUNT` decimal(10,2) NOT NULL,
  `TRANSFERDATE` datetime NOT NULL,
  `NOTES` varchar(200) NOT NULL,
  PRIMARY KEY (`CASH_TRANSFER_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;


--
-- Definition of table `credit_card`
--

DROP TABLE IF EXISTS `credit_card`;
CREATE TABLE `credit_card` (
  `CREDIT_CARD_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) NOT NULL,
  `BALANCE` decimal(10,2) NOT NULL,
  `CREATEDBY` int(10) unsigned DEFAULT NULL,
  `CREATEDDATE` datetime DEFAULT NULL,
  `MODIFIEDBY` int(10) unsigned DEFAULT NULL,
  `MODIFIEDDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`CREDIT_CARD_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;


--
-- Definition of table `credit_card_credit_card_transaction`
--

DROP TABLE IF EXISTS `credit_card_credit_card_transaction`;
CREATE TABLE `credit_card_credit_card_transaction` (
  `CREDIT_CARD_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CREDIT_CARD_TRANSACTION_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`CREDIT_CARD_ID`,`CREDIT_CARD_TRANSACTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `credit_card_credit_card_transaction`
--

/*!40000 ALTER TABLE `credit_card_credit_card_transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `credit_card_credit_card_transaction` ENABLE KEYS */;


--
-- Definition of table `credit_card_payment`
--

DROP TABLE IF EXISTS `credit_card_payment`;
CREATE TABLE `credit_card_payment` (
  `CREDIT_CARD_ID` int(10) unsigned NOT NULL,
  `CREDIT_CARD_TRANSACTION_ID` int(10) unsigned NOT NULL,
  `PAYMENT_METHOD_ID` int(10) unsigned NOT NULL,
  `TRANSACTION_ID` int(10) unsigned NOT NULL,
  `CREDIT_CARD_PAYMENT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`CREDIT_CARD_PAYMENT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;


--
-- Definition of table `credit_card_transaction`
--

DROP TABLE IF EXISTS `credit_card_transaction`;
CREATE TABLE `credit_card_transaction` (
  `CREDIT_CARD_TRANSACTION_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `AMOUNT` decimal(10,2) NOT NULL,
  `DATE` datetime NOT NULL,
  `CREDIT_CARD_TRANSACTION_TYPE_ID` int(10) unsigned NOT NULL,
  `CREDIT_CARD_ID` int(10) unsigned NOT NULL,
  `TRANSACTION_INITIATOR_ID` int(10) unsigned DEFAULT NULL,
  `DESCRIPTION` varchar(150) DEFAULT NULL,
  `USER_ID` int(10) unsigned NOT NULL,
  `REASON_ID` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`CREDIT_CARD_TRANSACTION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;


--
-- Definition of table `credit_card_transaction_type`
--

DROP TABLE IF EXISTS `credit_card_transaction_type`;
CREATE TABLE `credit_card_transaction_type` (
  `CREDIT_CARD_TRANSACTION_TYPE_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) NOT NULL,
  `SIGN` int(11) NOT NULL,
  PRIMARY KEY (`CREDIT_CARD_TRANSACTION_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `credit_card_transaction_type`
--

/*!40000 ALTER TABLE `credit_card_transaction_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `credit_card_transaction_type` ENABLE KEYS */;


--
-- Definition of table `currencyonhand`
--

DROP TABLE IF EXISTS `currencyonhand`;
CREATE TABLE `currencyonhand` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `AMOUNT` decimal(10,2) NOT NULL,
  `DATE` datetime NOT NULL,
  `SOURCEID` int(10) unsigned DEFAULT NULL,
  `GROUPID` int(10) unsigned DEFAULT NULL,
  `TYPEID` int(10) unsigned NOT NULL,
  `USERID` int(10) unsigned NOT NULL,
  `SOURCE_TYPE_ID` int(10) unsigned NOT NULL,
  `CHECKNUMBER` varchar(250) DEFAULT NULL,
  `TRANSACTION_TYPE_ID` int(10) unsigned DEFAULT NULL,
  `TRANSACTION_ID` int(10) unsigned DEFAULT NULL,
  `USED` tinyint(1) NOT NULL,
  `REFERENCE_ID` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=latin1;


--
-- Definition of table `deposit`
--

DROP TABLE IF EXISTS `deposit`;
CREATE TABLE `deposit` (
  `DEPOSIT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `BANK_ACCOUNT_ID` int(10) unsigned NOT NULL,
  `DATE` datetime NOT NULL,
  `TOTAL_CASH` decimal(10,2) NOT NULL,
  `USER_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`DEPOSIT_ID`,`BANK_ACCOUNT_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=latin1;


--
-- Definition of table `deposit_currency_on_hand`
--

DROP TABLE IF EXISTS `deposit_currency_on_hand`;
CREATE TABLE `deposit_currency_on_hand` (
  `DEPOSIT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CURRENCY_ON_HAND_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`DEPOSIT_ID`,`CURRENCY_ON_HAND_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=latin1;


--
-- Definition of table `expense`
--

DROP TABLE IF EXISTS `expense`;
CREATE TABLE `expense` (
  `EXPENSE_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DATE` datetime NOT NULL,
  `AMOUNT` decimal(10,2) NOT NULL,
  `PAYMENT_METHOD_ID` int(10) unsigned NOT NULL,
  `BANK_ACCOUNT_ID` int(10) unsigned DEFAULT NULL,
  `CHECK_NUMBER` varchar(250) DEFAULT NULL,
  `CREDIT_CARD_ID` int(10) unsigned DEFAULT NULL,
  `EXPENSE_GROUP_ID` int(10) DEFAULT NULL,
  `EXPENSE_TYPE_ID` int(10) unsigned DEFAULT NULL,
  `PAYEE_ID` int(10) unsigned NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `USER_ID` int(10) unsigned NOT NULL,
  `MONEY_ORDER_ID` int(10) unsigned DEFAULT NULL,
  `EXPENSE_PURPOSE_ID` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`EXPENSE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=931 DEFAULT CHARSET=latin1;


--
-- Definition of table `expense_category`
--

DROP TABLE IF EXISTS `expense_category`;
CREATE TABLE `expense_category` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(100) NOT NULL,
  `ACCOUNT_ID` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;


--
-- Definition of table `expense_expense_item`
--

DROP TABLE IF EXISTS `expense_expense_item`;
CREATE TABLE `expense_expense_item` (
  `EXPENSE_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `EXPENSE_ITEM_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`EXPENSE_ID`,`EXPENSE_ITEM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=900 DEFAULT CHARSET=latin1;


--
-- Definition of table `expense_group`
--

DROP TABLE IF EXISTS `expense_group`;
CREATE TABLE `expense_group` (
  `EXPENSE_GROUP_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) NOT NULL,
  PRIMARY KEY (`EXPENSE_GROUP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;


--
-- Definition of table `expense_item`
--

DROP TABLE IF EXISTS `expense_item`;
CREATE TABLE `expense_item` (
  `EXPENSE_ITEM_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `AMOUNT` decimal(10,2) NOT NULL,
  `EXPENSE_TYPE_ID` int(10) unsigned NOT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `EXPENSE_GROUP_ID` int(10) unsigned DEFAULT NULL,
  `TAX` decimal(10,2) unsigned DEFAULT NULL,
  `EXPENSE_PURPOSE_ID` int(10) unsigned NOT NULL,
  `BORROWER_ID` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`EXPENSE_ITEM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=520 DEFAULT CHARSET=latin1;


--
-- Definition of table `expense_sub_type`
--

DROP TABLE IF EXISTS `expense_sub_type`;
CREATE TABLE `expense_sub_type` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(100) NOT NULL,
  `CATEGORY_ID` int(10) unsigned NOT NULL,
  `ACCOUNT_ID` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=latin1;


--
-- Definition of table `expense_type`
--

DROP TABLE IF EXISTS `expense_type`;
CREATE TABLE `expense_type` (
  `EXPENSE_TYPE_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) NOT NULL,
  PRIMARY KEY (`EXPENSE_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `expense_type`
--

/*!40000 ALTER TABLE `expense_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `expense_type` ENABLE KEYS */;


--
-- Definition of table `income`
--

DROP TABLE IF EXISTS `income`;
CREATE TABLE `income` (
  `INCOME_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `INCOME_SOURCE_ID` int(10) unsigned NOT NULL,
  `DATE` datetime NOT NULL,
  `AMOUNT` decimal(10,0) NOT NULL,
  `CHECK_NUMBER` varchar(45) DEFAULT NULL,
  `DEPOSIT_ID` int(10) unsigned DEFAULT NULL,
  `EXPENSE_GROUP_ID` int(10) unsigned DEFAULT NULL,
  `USER_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`INCOME_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `income`
--

/*!40000 ALTER TABLE `income` DISABLE KEYS */;
/*!40000 ALTER TABLE `income` ENABLE KEYS */;


--
-- Definition of table `income_source`
--

DROP TABLE IF EXISTS `income_source`;
CREATE TABLE `income_source` (
  `INCOME_SOURCE_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) NOT NULL,
  PRIMARY KEY (`INCOME_SOURCE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;



--
-- Definition of table `loan`
--

DROP TABLE IF EXISTS `loan`;
CREATE TABLE `loan` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DATE` datetime NOT NULL,
  `AMOUNT` decimal(10,2) NOT NULL,
  `PAYMENT_METHOD_ID` int(10) DEFAULT NULL,
  `BANK_ACCOUNT_ID` int(10) unsigned DEFAULT NULL,
  `CHECK_NUMBER` varchar(250) DEFAULT NULL,
  `CREDIT_CARD_ID` int(10) unsigned DEFAULT NULL,
  `PAYEE_ID` int(10) unsigned DEFAULT NULL,
  `BORROWER_ID` int(10) unsigned NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `USER_ID` int(10) unsigned NOT NULL,
  `MONEY_ORDER_ID` int(10) unsigned DEFAULT NULL,
  `TRANSACTION_ID` int(10) unsigned DEFAULT NULL,
  `TRANSACTION_TYPE_ID` int(10) unsigned DEFAULT NULL,
  `GROUP_ID` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;


--
-- Definition of table `loan_currency_on_hand`
--

DROP TABLE IF EXISTS `loan_currency_on_hand`;
CREATE TABLE `loan_currency_on_hand` (
  `LOAN_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CURRENCY_ON_HAND_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`LOAN_ID`,`CURRENCY_ON_HAND_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;


--
-- Definition of table `money_order_fee`
--

DROP TABLE IF EXISTS `money_order_fee`;
CREATE TABLE `money_order_fee` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `MONEY_ORDER_ID` int(10) unsigned NOT NULL,
  `FEE` decimal(10,2) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `money_order_fee`
--

/*!40000 ALTER TABLE `money_order_fee` DISABLE KEYS */;
/*!40000 ALTER TABLE `money_order_fee` ENABLE KEYS */;


--
-- Definition of table `payee`
--

DROP TABLE IF EXISTS `payee`;
CREATE TABLE `payee` (
  `PAYEE_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) NOT NULL,
  PRIMARY KEY (`PAYEE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=latin1;



--
-- Definition of table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `USER_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(45) NOT NULL,
  `FIRSTNAME` varchar(45) NOT NULL,
  `LASTNAME` varchar(45) NOT NULL,
  `PASSWORD` varchar(45) NOT NULL,
  `ACCOUNTID` int(10) unsigned NOT NULL,
  `PERMISSIONS` text NOT NULL,
  `CASH_BALANCE` decimal(10,2) NOT NULL,
  `MAIN_USER` tinyint(1) NOT NULL,
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
