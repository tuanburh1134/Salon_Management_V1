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
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `booking_date_time` datetime(6) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `notes` varchar(500) DEFAULT NULL,
  `status` varchar(50) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `customer_id` bigint NOT NULL,
  `employee_id` bigint NOT NULL,
  `service_id` bigint NOT NULL,
  `booking_code` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKq97166k18hklq6ls46osbrftx` (`booking_code`),
  KEY `FKbvfibgflhsb0g2hnjauiv5khs` (`customer_id`),
  KEY `FKeskil2o07k6m8psw18xu2xnco` (`employee_id`),
  KEY `FK1bcfbcbojrkm10yuyyh0g3fgh` (`service_id`),
  CONSTRAINT `FK1bcfbcbojrkm10yuyyh0g3fgh` FOREIGN KEY (`service_id`) REFERENCES `service_items` (`id`),
  CONSTRAINT `FKbvfibgflhsb0g2hnjauiv5khs` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FKeskil2o07k6m8psw18xu2xnco` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (4,'2025-10-30 04:00:00.000000','2025-10-28 04:58:43.207073','','Xác nhận',NULL,6,8,2,'DL0003'),(5,'2025-10-29 00:58:00.000000','2025-10-28 08:54:29.515548','','Xác nhận',NULL,1,5,1,'DL0004'),(8,'2025-10-31 10:18:00.000000','2025-10-28 10:15:26.189751','','Chờ xử lý',NULL,1,1,2,'DL0005');
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
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
