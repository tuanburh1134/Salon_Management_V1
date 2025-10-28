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
-- Table structure for table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotion` (
  `id_promotion` int NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_at` date NOT NULL,
  `name` varchar(100) NOT NULL,
  `percent` decimal(5,2) NOT NULL,
  `start_at` date NOT NULL,
  `status` enum('ACTIVE','EXPIRED','INACTIVE','UPCOMING') NOT NULL,
  `type` enum('ALL_CUSTOMERS','LOYAL_CUSTOMER','NEW_CUSTOMER','SPECIAL_CUSTOMER') NOT NULL,
  PRIMARY KEY (`id_promotion`),
  UNIQUE KEY `UKlomfalb6gsh66ox4gy0t2g7qw` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion`
--

LOCK TABLES `promotion` WRITE;
/*!40000 ALTER TABLE `promotion` DISABLE KEYS */;
INSERT INTO `promotion` (`id_promotion`,`code`,`description`,`end_at`,`name`,`percent`,`start_at`,`status`,`type`) VALUES
                                                                                                                       (1,'KM0001','Chương trình khuyến mãi số 1 dành cho khách hàng.','2025-10-06','Khuyến mãi 1',9.31,'2025-09-29','INACTIVE','NEW_CUSTOMER'),
                                                                                                                       (2,'KM0002','Chương trình khuyến mãi số 2 dành cho khách hàng.','2025-11-25','Khuyến mãi 2',45.27,'2025-10-08','ACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (3,'KM0003','Chương trình khuyến mãi số 3 dành cho khách hàng.','2025-10-25','Khuyến mãi 3',31.04,'2025-09-28','EXPIRED','NEW_CUSTOMER'),
                                                                                                                       (4,'KM0004','Chương trình khuyến mãi số 4 dành cho khách hàng.','2025-11-03','Khuyến mãi 4',38.61,'2025-10-09','ACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (5,'KM0005','Chương trình khuyến mãi số 5 dành cho khách hàng.','2025-12-13','Khuyến mãi 5',10.88,'2025-10-25','EXPIRED','LOYAL_CUSTOMER'),
                                                                                                                       (6,'KM0006','Chương trình khuyến mãi số 6 dành cho khách hàng.','2025-11-07','Khuyến mãi 6',46.94,'2025-10-24','ACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (7,'KM0007','Chương trình khuyến mãi số 7 dành cho khách hàng.','2025-11-08','Khuyến mãi 7',34.8,'2025-10-01','INACTIVE','LOYAL_CUSTOMER'),
                                                                                                                       (8,'KM0008','Chương trình khuyến mãi số 8 dành cho khách hàng.','2025-12-06','Khuyến mãi 8',9.93,'2025-10-26','INACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (9,'KM0009','Chương trình khuyến mãi số 9 dành cho khách hàng.','2025-10-29','Khuyến mãi 9',46.2,'2025-10-01','UPCOMING','NEW_CUSTOMER'),
                                                                                                                       (10,'KM0010','Chương trình khuyến mãi số 10 dành cho khách hàng.','2025-12-11','Khuyến mãi 10',11.27,'2025-10-13','INACTIVE','NEW_CUSTOMER'),
                                                                                                                       (11,'KM0011','Chương trình khuyến mãi số 11 dành cho khách hàng.','2025-10-31','Khuyến mãi 11',27.53,'2025-10-13','ACTIVE','LOYAL_CUSTOMER'),
                                                                                                                       (12,'KM0012','Chương trình khuyến mãi số 12 dành cho khách hàng.','2025-11-17','Khuyến mãi 12',13.64,'2025-10-01','EXPIRED','ALL_CUSTOMERS'),
                                                                                                                       (13,'KM0013','Chương trình khuyến mãi số 13 dành cho khách hàng.','2025-11-26','Khuyến mãi 13',46.12,'2025-10-25','UPCOMING','LOYAL_CUSTOMER'),
                                                                                                                       (14,'KM0014','Chương trình khuyến mãi số 14 dành cho khách hàng.','2025-11-14','Khuyến mãi 14',32.86,'2025-10-14','EXPIRED','LOYAL_CUSTOMER'),
                                                                                                                       (15,'KM0015','Chương trình khuyến mãi số 15 dành cho khách hàng.','2025-11-13','Khuyến mãi 15',49.92,'2025-10-04','EXPIRED','ALL_CUSTOMERS'),
                                                                                                                       (16,'KM0016','Chương trình khuyến mãi số 16 dành cho khách hàng.','2025-11-01','Khuyến mãi 16',8.18,'2025-10-14','INACTIVE','LOYAL_CUSTOMER'),
                                                                                                                       (17,'KM0017','Chương trình khuyến mãi số 17 dành cho khách hàng.','2025-11-27','Khuyến mãi 17',7.49,'2025-10-06','UPCOMING','LOYAL_CUSTOMER'),
                                                                                                                       (18,'KM0018','Chương trình khuyến mãi số 18 dành cho khách hàng.','2025-10-27','Khuyến mãi 18',23.83,'2025-09-28','INACTIVE','NEW_CUSTOMER'),
                                                                                                                       (19,'KM0019','Chương trình khuyến mãi số 19 dành cho khách hàng.','2025-11-06','Khuyến mãi 19',42.26,'2025-10-08','EXPIRED','LOYAL_CUSTOMER'),
                                                                                                                       (20,'KM0020','Chương trình khuyến mãi số 20 dành cho khách hàng.','2025-10-28','Khuyến mãi 20',14.61,'2025-10-03','INACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (21,'KM0021','Chương trình khuyến mãi số 21 dành cho khách hàng.','2025-11-04','Khuyến mãi 21',12.81,'2025-10-08','ACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (22,'KM0022','Chương trình khuyến mãi số 22 dành cho khách hàng.','2025-11-13','Khuyến mãi 22',9.33,'2025-10-13','ACTIVE','LOYAL_CUSTOMER'),
                                                                                                                       (23,'KM0023','Chương trình khuyến mãi số 23 dành cho khách hàng.','2025-12-11','Khuyến mãi 23',11.75,'2025-10-28','INACTIVE','NEW_CUSTOMER'),
                                                                                                                       (24,'KM0024','Chương trình khuyến mãi số 24 dành cho khách hàng.','2025-11-28','Khuyến mãi 24',46.72,'2025-10-24','INACTIVE','LOYAL_CUSTOMER'),
                                                                                                                       (25,'KM0025','Chương trình khuyến mãi số 25 dành cho khách hàng.','2025-11-09','Khuyến mãi 25',28.98,'2025-09-28','ACTIVE','LOYAL_CUSTOMER'),
                                                                                                                       (26,'KM0026','Chương trình khuyến mãi số 26 dành cho khách hàng.','2025-11-29','Khuyến mãi 26',16.52,'2025-10-18','EXPIRED','ALL_CUSTOMERS'),
                                                                                                                       (27,'KM0027','Chương trình khuyến mãi số 27 dành cho khách hàng.','2025-11-04','Khuyến mãi 27',40.41,'2025-10-20','UPCOMING','NEW_CUSTOMER'),
                                                                                                                       (28,'KM0028','Chương trình khuyến mãi số 28 dành cho khách hàng.','2025-12-08','Khuyến mãi 28',8.02,'2025-10-17','ACTIVE','ALL_CUSTOMERS'),
                                                                                                                       (29,'KM0029','Chương trình khuyến mãi số 29 dành cho khách hàng.','2025-12-16','Khuyến mãi 29',6.8,'2025-10-20','ACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (30,'KM0030','Chương trình khuyến mãi số 30 dành cho khách hàng.','2025-10-21','Khuyến mãi 30',21.66,'2025-09-28','UPCOMING','ALL_CUSTOMERS'),
                                                                                                                       (31,'KM0031','Chương trình khuyến mãi số 31 dành cho khách hàng.','2025-11-20','Khuyến mãi 31',19.2,'2025-10-11','ACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (32,'KM0032','Chương trình khuyến mãi số 32 dành cho khách hàng.','2025-12-15','Khuyến mãi 32',36.73,'2025-10-23','UPCOMING','ALL_CUSTOMERS'),
                                                                                                                       (33,'KM0033','Chương trình khuyến mãi số 33 dành cho khách hàng.','2025-11-24','Khuyến mãi 33',37.84,'2025-10-11','UPCOMING','NEW_CUSTOMER'),
                                                                                                                       (34,'KM0034','Chương trình khuyến mãi số 34 dành cho khách hàng.','2025-12-06','Khuyến mãi 34',7.34,'2025-10-19','INACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (35,'KM0035','Chương trình khuyến mãi số 35 dành cho khách hàng.','2025-11-22','Khuyến mãi 35',27.5,'2025-09-30','EXPIRED','SPECIAL_CUSTOMER'),
                                                                                                                       (36,'KM0036','Chương trình khuyến mãi số 36 dành cho khách hàng.','2025-12-07','Khuyến mãi 36',24.67,'2025-10-08','EXPIRED','LOYAL_CUSTOMER'),
                                                                                                                       (37,'KM0037','Chương trình khuyến mãi số 37 dành cho khách hàng.','2025-12-01','Khuyến mãi 37',49.85,'2025-10-08','INACTIVE','ALL_CUSTOMERS'),
                                                                                                                       (38,'KM0038','Chương trình khuyến mãi số 38 dành cho khách hàng.','2025-11-06','Khuyến mãi 38',28.74,'2025-10-18','ACTIVE','LOYAL_CUSTOMER'),
                                                                                                                       (39,'KM0039','Chương trình khuyến mãi số 39 dành cho khách hàng.','2025-12-17','Khuyến mãi 39',48.32,'2025-10-20','ACTIVE','NEW_CUSTOMER'),
                                                                                                                       (40,'KM0040','Chương trình khuyến mãi số 40 dành cho khách hàng.','2025-12-01','Khuyến mãi 40',8.17,'2025-10-10','UPCOMING','ALL_CUSTOMERS'),
                                                                                                                       (41,'KM0041','Chương trình khuyến mãi số 41 dành cho khách hàng.','2025-11-15','Khuyến mãi 41',42.12,'2025-10-02','INACTIVE','ALL_CUSTOMERS'),
                                                                                                                       (42,'KM0042','Chương trình khuyến mãi số 42 dành cho khách hàng.','2025-12-12','Khuyến mãi 42',45.11,'2025-10-20','EXPIRED','NEW_CUSTOMER'),
                                                                                                                       (43,'KM0043','Chương trình khuyến mãi số 43 dành cho khách hàng.','2025-12-07','Khuyến mãi 43',45.34,'2025-10-19','EXPIRED','LOYAL_CUSTOMER'),
                                                                                                                       (44,'KM0044','Chương trình khuyến mãi số 44 dành cho khách hàng.','2025-10-10','Khuyến mãi 44',49.1,'2025-10-01','ACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (45,'KM0045','Chương trình khuyến mãi số 45 dành cho khách hàng.','2025-11-09','Khuyến mãi 45',31.77,'2025-10-28','EXPIRED','NEW_CUSTOMER'),
                                                                                                                       (46,'KM0046','Chương trình khuyến mãi số 46 dành cho khách hàng.','2025-11-13','Khuyến mãi 46',5.51,'2025-10-25','ACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (47,'KM0047','Chương trình khuyến mãi số 47 dành cho khách hàng.','2025-10-31','Khuyến mãi 47',26.06,'2025-09-30','ACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (48,'KM0048','Chương trình khuyến mãi số 48 dành cho khách hàng.','2025-11-23','Khuyến mãi 48',45.72,'2025-10-28','INACTIVE','ALL_CUSTOMERS'),
                                                                                                                       (49,'KM0049','Chương trình khuyến mãi số 49 dành cho khách hàng.','2025-11-14','Khuyến mãi 49',32.29,'2025-10-07','ACTIVE','NEW_CUSTOMER'),
                                                                                                                       (50,'KM0050','Chương trình khuyến mãi số 50 dành cho khách hàng.','2025-11-22','Khuyến mãi 50',45.67,'2025-10-03','UPCOMING','ALL_CUSTOMERS'),
                                                                                                                       (51,'KM0051','Chương trình khuyến mãi số 51 dành cho khách hàng.','2025-10-19','Khuyến mãi 51',5.96,'2025-10-07','INACTIVE','NEW_CUSTOMER'),
                                                                                                                       (52,'KM0052','Chương trình khuyến mãi số 52 dành cho khách hàng.','2025-11-13','Khuyến mãi 52',23.31,'2025-10-02','INACTIVE','LOYAL_CUSTOMER'),
                                                                                                                       (53,'KM0053','Chương trình khuyến mãi số 53 dành cho khách hàng.','2025-12-18','Khuyến mãi 53',46.39,'2025-10-23','EXPIRED','SPECIAL_CUSTOMER'),
                                                                                                                       (54,'KM0054','Chương trình khuyến mãi số 54 dành cho khách hàng.','2025-11-01','Khuyến mãi 54',27.99,'2025-10-23','INACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (55,'KM0055','Chương trình khuyến mãi số 55 dành cho khách hàng.','2025-12-05','Khuyến mãi 55',18.54,'2025-10-24','INACTIVE','ALL_CUSTOMERS'),
                                                                                                                       (56,'KM0056','Chương trình khuyến mãi số 56 dành cho khách hàng.','2025-11-24','Khuyến mãi 56',27.63,'2025-10-23','INACTIVE','NEW_CUSTOMER'),
                                                                                                                       (57,'KM0057','Chương trình khuyến mãi số 57 dành cho khách hàng.','2025-11-07','Khuyến mãi 57',8.38,'2025-10-22','INACTIVE','LOYAL_CUSTOMER'),
                                                                                                                       (58,'KM0058','Chương trình khuyến mãi số 58 dành cho khách hàng.','2025-10-14','Khuyến mãi 58',47.2,'2025-09-29','UPCOMING','LOYAL_CUSTOMER'),
                                                                                                                       (59,'KM0059','Chương trình khuyến mãi số 59 dành cho khách hàng.','2025-11-14','Khuyến mãi 59',44.83,'2025-10-08','INACTIVE','NEW_CUSTOMER'),
                                                                                                                       (60,'KM0060','Chương trình khuyến mãi số 60 dành cho khách hàng.','2025-12-15','Khuyến mãi 60',17.13,'2025-10-24','UPCOMING','NEW_CUSTOMER'),
                                                                                                                       (61,'KM0061','Chương trình khuyến mãi số 61 dành cho khách hàng.','2025-12-26','Khuyến mãi 61',29.01,'2025-10-28','UPCOMING','NEW_CUSTOMER'),
                                                                                                                       (62,'KM0062','Chương trình khuyến mãi số 62 dành cho khách hàng.','2025-10-24','Khuyến mãi 62',46.69,'2025-10-07','EXPIRED','LOYAL_CUSTOMER'),
                                                                                                                       (63,'KM0063','Chương trình khuyến mãi số 63 dành cho khách hàng.','2025-11-22','Khuyến mãi 63',21.1,'2025-10-10','UPCOMING','ALL_CUSTOMERS'),
                                                                                                                       (64,'KM0064','Chương trình khuyến mãi số 64 dành cho khách hàng.','2025-12-03','Khuyến mãi 64',27.73,'2025-10-16','EXPIRED','ALL_CUSTOMERS'),
                                                                                                                       (65,'KM0065','Chương trình khuyến mãi số 65 dành cho khách hàng.','2025-10-30','Khuyến mãi 65',25.77,'2025-10-13','EXPIRED','SPECIAL_CUSTOMER'),
                                                                                                                       (66,'KM0066','Chương trình khuyến mãi số 66 dành cho khách hàng.','2025-11-30','Khuyến mãi 66',36.4,'2025-10-21','EXPIRED','NEW_CUSTOMER'),
                                                                                                                       (67,'KM0067','Chương trình khuyến mãi số 67 dành cho khách hàng.','2025-10-27','Khuyến mãi 67',7.59,'2025-10-06','ACTIVE','LOYAL_CUSTOMER'),
                                                                                                                       (68,'KM0068','Chương trình khuyến mãi số 68 dành cho khách hàng.','2025-11-02','Khuyến mãi 68',16.09,'2025-09-30','INACTIVE','LOYAL_CUSTOMER'),
                                                                                                                       (69,'KM0069','Chương trình khuyến mãi số 69 dành cho khách hàng.','2025-12-04','Khuyến mãi 69',35.62,'2025-10-14','ACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (70,'KM0070','Chương trình khuyến mãi số 70 dành cho khách hàng.','2025-11-07','Khuyến mãi 70',5.33,'2025-10-07','ACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (71,'KM0071','Chương trình khuyến mãi số 71 dành cho khách hàng.','2025-11-13','Khuyến mãi 71',9.97,'2025-10-24','UPCOMING','NEW_CUSTOMER'),
                                                                                                                       (72,'KM0072','Chương trình khuyến mãi số 72 dành cho khách hàng.','2025-10-31','Khuyến mãi 72',7.44,'2025-10-22','ACTIVE','NEW_CUSTOMER'),
                                                                                                                       (73,'KM0073','Chương trình khuyến mãi số 73 dành cho khách hàng.','2025-11-13','Khuyến mãi 73',16.55,'2025-10-28','UPCOMING','NEW_CUSTOMER'),
                                                                                                                       (74,'KM0074','Chương trình khuyến mãi số 74 dành cho khách hàng.','2025-11-26','Khuyến mãi 74',34.03,'2025-10-14','INACTIVE','NEW_CUSTOMER'),
                                                                                                                       (75,'KM0075','Chương trình khuyến mãi số 75 dành cho khách hàng.','2025-11-05','Khuyến mãi 75',42.58,'2025-10-22','UPCOMING','ALL_CUSTOMERS'),
                                                                                                                       (76,'KM0076','Chương trình khuyến mãi số 76 dành cho khách hàng.','2025-12-10','Khuyến mãi 76',28.62,'2025-10-11','ACTIVE','ALL_CUSTOMERS'),
                                                                                                                       (77,'KM0077','Chương trình khuyến mãi số 77 dành cho khách hàng.','2025-12-03','Khuyến mãi 77',14.72,'2025-10-20','ACTIVE','NEW_CUSTOMER'),
                                                                                                                       (78,'KM0078','Chương trình khuyến mãi số 78 dành cho khách hàng.','2025-11-24','Khuyến mãi 78',39.31,'2025-10-14','UPCOMING','LOYAL_CUSTOMER'),
                                                                                                                       (79,'KM0079','Chương trình khuyến mãi số 79 dành cho khách hàng.','2025-12-01','Khuyến mãi 79',43.61,'2025-10-14','ACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (80,'KM0080','Chương trình khuyến mãi số 80 dành cho khách hàng.','2025-12-11','Khuyến mãi 80',29.0,'2025-10-19','UPCOMING','NEW_CUSTOMER'),
                                                                                                                       (81,'KM0081','Chương trình khuyến mãi số 81 dành cho khách hàng.','2025-12-17','Khuyến mãi 81',21.13,'2025-10-18','INACTIVE','LOYAL_CUSTOMER'),
                                                                                                                       (82,'KM0082','Chương trình khuyến mãi số 82 dành cho khách hàng.','2025-11-27','Khuyến mãi 82',33.08,'2025-10-27','INACTIVE','NEW_CUSTOMER'),
                                                                                                                       (83,'KM0083','Chương trình khuyến mãi số 83 dành cho khách hàng.','2025-11-18','Khuyến mãi 83',24.16,'2025-10-27','ACTIVE','LOYAL_CUSTOMER'),
                                                                                                                       (84,'KM0084','Chương trình khuyến mãi số 84 dành cho khách hàng.','2025-11-20','Khuyến mãi 84',35.57,'2025-10-19','UPCOMING','SPECIAL_CUSTOMER'),
                                                                                                                       (85,'KM0085','Chương trình khuyến mãi số 85 dành cho khách hàng.','2025-11-24','Khuyến mãi 85',49.69,'2025-10-22','ACTIVE','ALL_CUSTOMERS'),
                                                                                                                       (86,'KM0086','Chương trình khuyến mãi số 86 dành cho khách hàng.','2025-11-30','Khuyến mãi 86',39.6,'2025-10-04','EXPIRED','ALL_CUSTOMERS'),
                                                                                                                       (87,'KM0087','Chương trình khuyến mãi số 87 dành cho khách hàng.','2025-11-17','Khuyến mãi 87',40.18,'2025-09-30','ACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (88,'KM0088','Chương trình khuyến mãi số 88 dành cho khách hàng.','2025-11-01','Khuyến mãi 88',32.82,'2025-10-16','ACTIVE','NEW_CUSTOMER'),
                                                                                                                       (89,'KM0089','Chương trình khuyến mãi số 89 dành cho khách hàng.','2025-11-02','Khuyến mãi 89',36.13,'2025-10-04','EXPIRED','NEW_CUSTOMER'),
                                                                                                                       (90,'KM0090','Chương trình khuyến mãi số 90 dành cho khách hàng.','2025-11-14','Khuyến mãi 90',18.12,'2025-10-02','ACTIVE','LOYAL_CUSTOMER'),
                                                                                                                       (91,'KM0091','Chương trình khuyến mãi số 91 dành cho khách hàng.','2025-11-23','Khuyến mãi 91',31.17,'2025-10-02','ACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (92,'KM0092','Chương trình khuyến mãi số 92 dành cho khách hàng.','2025-11-26','Khuyến mãi 92',26.24,'2025-10-15','EXPIRED','ALL_CUSTOMERS'),
                                                                                                                       (93,'KM0093','Chương trình khuyến mãi số 93 dành cho khách hàng.','2025-10-25','Khuyến mãi 93',38.29,'2025-10-03','UPCOMING','LOYAL_CUSTOMER'),
                                                                                                                       (94,'KM0094','Chương trình khuyến mãi số 94 dành cho khách hàng.','2025-11-09','Khuyến mãi 94',34.31,'2025-10-12','ACTIVE','ALL_CUSTOMERS'),
                                                                                                                       (95,'KM0095','Chương trình khuyến mãi số 95 dành cho khách hàng.','2025-10-26','Khuyến mãi 95',17.63,'2025-10-02','INACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (96,'KM0096','Chương trình khuyến mãi số 96 dành cho khách hàng.','2025-11-06','Khuyến mãi 96',27.98,'2025-10-02','EXPIRED','LOYAL_CUSTOMER'),
                                                                                                                       (97,'KM0097','Chương trình khuyến mãi số 97 dành cho khách hàng.','2025-11-24','Khuyến mãi 97',49.91,'2025-10-20','INACTIVE','ALL_CUSTOMERS'),
                                                                                                                       (98,'KM0098','Chương trình khuyến mãi số 98 dành cho khách hàng.','2025-12-19','Khuyến mãi 98',15.03,'2025-10-20','INACTIVE','SPECIAL_CUSTOMER'),
                                                                                                                       (99,'KM0099','Chương trình khuyến mãi số 99 dành cho khách hàng.','2025-11-21','Khuyến mãi 99',41.4,'2025-10-11','ACTIVE','LOYAL_CUSTOMER'),
                                                                                                                       (100,'KM0100','Chương trình khuyến mãi số 100 dành cho khách hàng.','2025-12-07','Khuyến mãi 100',27.32,'2025-10-15','ACTIVE','LOYAL_CUSTOMER');

/*!40000 ALTER TABLE `promotion` ENABLE KEYS */;
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
