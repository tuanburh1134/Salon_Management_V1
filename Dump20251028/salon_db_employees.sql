-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: salon_db
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `name` varchar(100) NOT NULL,
  `position` varchar(255) NOT NULL,
  `salary` bigint NOT NULL,
  `shift` varchar(255) NOT NULL,
  `specialty` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `date_of_birth` date NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `hometown` varchar(100) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `photo_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKj9xgmd0ya5jmus09o0b8pqrpb` (`email`),
  UNIQUE KEY `UKgnponadwwxr5nm2tqe5b905hs` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'2025-10-27 23:56:35.081132',_binary '\0','Nguyễn Văn A','Quản lý',15000000,'Ca sáng','Chăm sóc tóc','2025-10-27 23:56:35.081132','1990-05-15','nguyenvana@example.com','Hà Nội','0912345670',NULL),(2,'2025-10-27 23:56:35.119132',_binary '\0','Trần Thị B','Nhân viên',8000000,'Ca chiều','Chăm sóc da','2025-10-27 23:56:35.119132','1995-08-22','tranthib@example.com','Đà Nẵng','0912345671',NULL),(3,'2025-10-27 23:56:35.121133',_binary '\0','Lê Văn C','Nhân viên',9000000,'Ca tối','Trang điểm','2025-10-27 23:56:35.121133','1998-01-30','levanc@example.com','TP. Hồ Chí Minh','0912345672',NULL),(4,'2025-10-27 23:56:35.123132',_binary '\0','Phạm Thị D','Quản lý',12000000,'Ca sáng','Mát xa','2025-10-27 23:56:35.123132','1992-11-11','phamthid@example.com','Hải Phòng','0912345673',NULL),(5,'2025-10-27 23:56:35.125132',_binary '\0','Hoàng Văn E','Nhân viên',7000000,'Ca chiều','Chăm sóc tay','2025-10-27 23:56:35.125132','2000-02-29','hoangvane@example.com','Cần Thơ','0912345674',NULL),(6,'2025-10-27 23:56:35.127132',_binary '\0','Vũ Thị F','Nhân viên',8500000,'Ca tối','Chăm sóc tóc','2025-10-27 23:56:35.127132','1999-07-07','vuthif@example.com','Bình Dương','0912345675',NULL),(7,'2025-10-27 23:56:35.128133',_binary '\0','Đặng Văn G','Quản lý',14000000,'Ca sáng','Chăm sóc da','2025-10-27 23:56:35.128133','1988-10-20','dangvang@example.com','Nghệ An','0912345676',NULL),(8,'2025-10-27 23:56:35.130131',_binary '\0','Bùi Thị H','Nhân viên',7500000,'Ca chiều','Trang điểm','2025-10-27 23:56:35.130131','2001-03-08','buithih@example.com','Thanh Hóa','0912345677',NULL),(9,'2025-10-27 23:56:35.131132',_binary '\0','Ngô Văn I','Nhân viên',9500000,'Ca tối','Mát xa','2025-10-27 23:56:35.131132','1997-06-01','ngovani@example.com','Quảng Ninh','0912345678',NULL),(10,'2025-10-27 23:56:35.133132',_binary '\0','Đỗ Thị K','Nhân viên',8000000,'Ca sáng','Chăm sóc tay','2025-10-27 23:56:35.133132','1996-04-19','dothik@example.com','Lâm Đồng','0912345679',NULL),(11,'2025-10-28 03:11:06.552045',_binary '\0','anh tuấn','Quản lý',55555555,'Ca sáng','Mát xa','2025-10-28 03:11:06.552045','2007-11-28','Tuantaitu123z@gmail.com','hà nội','0123654789',NULL);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-28 10:21:37
