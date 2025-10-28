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
-- Table structure for table `service_items`
--

DROP TABLE IF EXISTS `service_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `duration_minutes` int NOT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `name` varchar(150) NOT NULL,
  `price` decimal(14,2) NOT NULL,
  `type` varchar(80) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_items`
--

LOCK TABLES `service_items` WRITE;
/*!40000 ALTER TABLE `service_items` DISABLE KEYS */;
INSERT INTO `service_items` (`id`,`active`,`duration_minutes`,`image_path`,`name`,`price`,`type`,`created_at`) VALUES
                                                                                                                   (1,_binary '\x01',87,NULL,'Gội & Sấy',357507.33,'cắt tóc','2025-09-13 12:00:00'),
                                                                                                                   (2,_binary '\x00',70,NULL,'Gội & Sấy',291289.7,'chăm sóc da','2025-07-14 12:00:00'),
                                                                                                                   (3,_binary '\x01',21,NULL,'Nhuộm tóc',150641.19,'chăm sóc da','2025-07-15 12:00:00'),
                                                                                                                   (4,_binary '\x00',47,NULL,'Nhuộm tóc',209576.2,'nail','2025-07-10 12:00:00'),
                                                                                                                   (5,_binary '\x01',41,NULL,'Massage mặt',158019.36,'chăm sóc tóc','2025-10-21 12:00:00'),
                                                                                                                   (6,_binary '\x01',83,NULL,'Uốn tóc',81336.18,'chăm sóc da','2025-09-19 12:00:00'),
                                                                                                                   (7,_binary '\x01',84,NULL,'Uốn tóc',497983.41,'cắt tóc','2025-07-10 12:00:00'),
                                                                                                                   (8,_binary '\x01',48,NULL,'Uốn tóc',114406.53,'cắt tóc','2025-08-09 12:00:00'),
                                                                                                                   (9,_binary '\x01',64,NULL,'Massage toàn thân',52289.26,'nail','2025-10-18 12:00:00'),
                                                                                                                   (10,_binary '\x00',88,NULL,'Chăm sóc da cơ bản',215801.53,'massage','2025-07-24 12:00:00'),
                                                                                                                   (11,_binary '\x00',83,NULL,'Sấy tạo kiểu',98204.56,'nail','2025-07-24 12:00:00'),
                                                                                                                   (12,_binary '\x01',32,NULL,'Cắt tóc nam',469789.67,'chăm sóc tóc','2025-07-21 12:00:00'),
                                                                                                                   (13,_binary '\x01',71,NULL,'Tẩy tóc',386013.47,'nail','2025-09-14 12:00:00'),
                                                                                                                   (14,_binary '\x01',78,NULL,'Sấy tạo kiểu',160971.17,'chăm sóc da','2025-08-10 12:00:00'),
                                                                                                                   (15,_binary '\x00',73,NULL,'Sơn gel cơ bản',170420.3,'cắt tóc','2025-09-19 12:00:00'),
                                                                                                                   (16,_binary '\x01',23,NULL,'Sấy tạo kiểu',466155.03,'chăm sóc tóc','2025-09-15 12:00:00'),
                                                                                                                   (17,_binary '\x01',62,NULL,'Cắt tóc nữ',469165.03,'massage','2025-07-15 12:00:00'),
                                                                                                                   (18,_binary '\x01',81,NULL,'Nhuộm tóc',288673.24,'chăm sóc tóc','2025-10-20 12:00:00'),
                                                                                                                   (19,_binary '\x01',47,NULL,'Sơn gel cơ bản',353432.62,'massage','2025-09-06 12:00:00'),
                                                                                                                   (20,_binary '\x01',54,NULL,'Sơn gel cơ bản',273353.96,'chăm sóc tóc','2025-08-03 12:00:00'),
                                                                                                                   (21,_binary '\x00',42,NULL,'Duỗi tóc',240834.75,'nail','2025-10-03 12:00:00'),
                                                                                                                   (22,_binary '\x01',17,NULL,'Massage toàn thân',445753.54,'chăm sóc da','2025-07-14 12:00:00'),
                                                                                                                   (23,_binary '\x01',30,NULL,'Gội & Sấy',67372.04,'chăm sóc tóc','2025-07-29 12:00:00'),
                                                                                                                   (24,_binary '\x00',87,NULL,'Uốn tóc',390310.05,'cắt tóc','2025-10-19 12:00:00'),
                                                                                                                   (25,_binary '\x01',17,NULL,'Tẩy da chết',455799.6,'chăm sóc da','2025-09-11 12:00:00'),
                                                                                                                   (26,_binary '\x00',65,NULL,'Cắt tóc nam',352300.51,'massage','2025-07-27 12:00:00'),
                                                                                                                   (27,_binary '\x00',20,NULL,'Uốn tóc',409227.0,'massage','2025-07-27 12:00:00'),
                                                                                                                   (28,_binary '\x00',79,NULL,'Chăm sóc da cơ bản',320014.84,'chăm sóc da','2025-09-06 12:00:00'),
                                                                                                                   (29,_binary '\x01',59,NULL,'Gội đầu thư giãn',359005.07,'chăm sóc tóc','2025-09-29 12:00:00'),
                                                                                                                   (30,_binary '\x01',54,NULL,'Làm móng tay',65521.2,'chăm sóc da','2025-10-13 12:00:00'),
                                                                                                                   (31,_binary '\x01',56,NULL,'Sơn gel nghệ thuật',150832.02,'massage','2025-08-16 12:00:00'),
                                                                                                                   (32,_binary '\x01',57,NULL,'Sấy tạo kiểu',396723.84,'massage','2025-10-18 12:00:00'),
                                                                                                                   (33,_binary '\x01',16,NULL,'Tẩy da chết',189510.33,'chăm sóc da','2025-10-19 12:00:00'),
                                                                                                                   (34,_binary '\x01',45,NULL,'Sơn gel nghệ thuật',276579.03,'massage','2025-08-16 12:00:00'),
                                                                                                                   (35,_binary '\x01',57,NULL,'Chăm sóc da cơ bản',98736.11,'cắt tóc','2025-10-18 12:00:00'),
                                                                                                                   (36,_binary '\x01',73,NULL,'Sơn gel nghệ thuật',372415.13,'chăm sóc tóc','2025-07-05 12:00:00'),
                                                                                                                   (37,_binary '\x00',56,NULL,'Cắt tóc nữ',206164.14,'massage','2025-07-29 12:00:00'),
                                                                                                                   (38,_binary '\x01',72,NULL,'Sơn gel cơ bản',227434.37,'chăm sóc da','2025-09-30 12:00:00'),
                                                                                                                   (39,_binary '\x00',38,NULL,'Nhuộm tóc',359787.42,'chăm sóc da','2025-10-19 12:00:00'),
                                                                                                                   (40,_binary '\x01',26,NULL,'Làm móng tay',350525.54,'chăm sóc tóc','2025-07-11 12:00:00'),
                                                                                                                   (41,_binary '\x01',22,NULL,'Chăm sóc da chuyên sâu',105785.4,'cắt tóc','2025-07-21 12:00:00'),
                                                                                                                   (42,_binary '\x01',89,NULL,'Làm móng tay',188940.34,'nail','2025-10-01 12:00:00'),
                                                                                                                   (43,_binary '\x01',73,NULL,'Sơn gel nghệ thuật',143295.52,'cắt tóc','2025-10-17 12:00:00'),
                                                                                                                   (44,_binary '\x01',53,NULL,'Duỗi tóc',428853.49,'chăm sóc da','2025-09-07 12:00:00'),
                                                                                                                   (45,_binary '\x00',77,NULL,'Làm móng tay',196998.52,'nail','2025-07-19 12:00:00'),
                                                                                                                   (46,_binary '\x00',48,NULL,'Cắt tóc nữ',287619.19,'chăm sóc da','2025-10-27 12:00:00'),
                                                                                                                   (47,_binary '\x01',77,NULL,'Sơn gel nghệ thuật',264809.86,'nail','2025-07-09 12:00:00'),
                                                                                                                   (48,_binary '\x00',20,NULL,'Gội đầu dưỡng sinh',395570.02,'cắt tóc','2025-07-04 12:00:00'),
                                                                                                                   (49,_binary '\x01',15,NULL,'Massage toàn thân',319075.06,'nail','2025-09-26 12:00:00'),
                                                                                                                   (50,_binary '\x01',46,NULL,'Massage toàn thân',327188.39,'massage','2025-10-03 12:00:00'),
                                                                                                                   (51,_binary '\x01',81,NULL,'Sơn gel nghệ thuật',247521.62,'chăm sóc da','2025-07-15 12:00:00'),
                                                                                                                   (52,_binary '\x00',60,NULL,'Nhuộm tóc',178141.95,'cắt tóc','2025-09-01 12:00:00'),
                                                                                                                   (53,_binary '\x00',53,NULL,'Massage mặt',483749.56,'chăm sóc da','2025-10-23 12:00:00'),
                                                                                                                   (54,_binary '\x01',50,NULL,'Chăm sóc da cơ bản',293888.21,'chăm sóc da','2025-10-28 12:00:00'),
                                                                                                                   (55,_binary '\x01',27,NULL,'Làm móng tay',93089.65,'cắt tóc','2025-08-09 12:00:00'),
                                                                                                                   (56,_binary '\x01',25,NULL,'Tẩy da chết',133952.12,'massage','2025-09-08 12:00:00'),
                                                                                                                   (57,_binary '\x00',34,NULL,'Uốn tóc',94880.73,'cắt tóc','2025-08-27 12:00:00'),
                                                                                                                   (58,_binary '\x00',49,NULL,'Chăm sóc da chuyên sâu',178285.03,'nail','2025-09-08 12:00:00'),
                                                                                                                   (59,_binary '\x01',70,NULL,'Làm móng chân',173441.04,'nail','2025-10-07 12:00:00'),
                                                                                                                   (60,_binary '\x01',15,NULL,'Làm móng tay',128230.72,'chăm sóc tóc','2025-07-16 12:00:00'),
                                                                                                                   (61,_binary '\x01',66,NULL,'Tẩy da chết',474210.85,'nail','2025-07-11 12:00:00'),
                                                                                                                   (62,_binary '\x01',75,NULL,'Chăm sóc da chuyên sâu',178115.28,'massage','2025-08-16 12:00:00'),
                                                                                                                   (63,_binary '\x01',81,NULL,'Gội đầu thư giãn',399697.29,'cắt tóc','2025-08-31 12:00:00'),
                                                                                                                   (64,_binary '\x00',15,NULL,'Chăm sóc da chuyên sâu',153989.58,'nail','2025-07-11 12:00:00'),
                                                                                                                   (65,_binary '\x00',63,NULL,'Massage toàn thân',277794.18,'cắt tóc','2025-07-08 12:00:00'),
                                                                                                                   (66,_binary '\x01',27,NULL,'Cắt tóc nam',59901.31,'cắt tóc','2025-08-02 12:00:00'),
                                                                                                                   (67,_binary '\x00',48,NULL,'Chăm sóc da chuyên sâu',111094.87,'nail','2025-09-04 12:00:00'),
                                                                                                                   (68,_binary '\x00',54,NULL,'Sơn gel nghệ thuật',140072.61,'nail','2025-09-30 12:00:00'),
                                                                                                                   (69,_binary '\x00',64,NULL,'Sơn gel nghệ thuật',265615.44,'chăm sóc da','2025-09-17 12:00:00'),
                                                                                                                   (70,_binary '\x01',84,NULL,'Tẩy tóc',441095.56,'cắt tóc','2025-07-25 12:00:00'),
                                                                                                                   (71,_binary '\x01',87,NULL,'Massage mặt',224618.45,'cắt tóc','2025-08-25 12:00:00'),
                                                                                                                   (72,_binary '\x01',29,NULL,'Chăm sóc da chuyên sâu',299629.35,'massage','2025-10-01 12:00:00'),
                                                                                                                   (73,_binary '\x00',32,NULL,'Làm móng chân',57240.04,'chăm sóc da','2025-08-29 12:00:00'),
                                                                                                                   (74,_binary '\x00',19,NULL,'Gội & Sấy',240556.13,'chăm sóc da','2025-08-29 12:00:00'),
                                                                                                                   (75,_binary '\x01',62,NULL,'Gội đầu thư giãn',369215.85,'nail','2025-07-22 12:00:00'),
                                                                                                                   (76,_binary '\x01',23,NULL,'Sơn gel cơ bản',426174.31,'chăm sóc da','2025-08-21 12:00:00'),
                                                                                                                   (77,_binary '\x01',68,NULL,'Gội đầu thư giãn',209885.61,'nail','2025-09-30 12:00:00'),
                                                                                                                   (78,_binary '\x00',72,NULL,'Sơn gel nghệ thuật',80017.37,'massage','2025-10-06 12:00:00'),
                                                                                                                   (79,_binary '\x01',85,NULL,'Cắt tóc nữ',376137.77,'massage','2025-07-31 12:00:00'),
                                                                                                                   (80,_binary '\x01',52,NULL,'Gội & Sấy',104072.63,'cắt tóc','2025-08-01 12:00:00'),
                                                                                                                   (81,_binary '\x00',81,NULL,'Cắt tóc nữ',455319.36,'chăm sóc da','2025-07-23 12:00:00'),
                                                                                                                   (82,_binary '\x01',63,NULL,'Tẩy da chết',446911.1,'chăm sóc da','2025-09-14 12:00:00'),
                                                                                                                   (83,_binary '\x01',43,NULL,'Cắt tóc nữ',388255.78,'cắt tóc','2025-09-03 12:00:00'),
                                                                                                                   (84,_binary '\x00',36,NULL,'Chăm sóc da cơ bản',409328.56,'massage','2025-07-04 12:00:00'),
                                                                                                                   (85,_binary '\x01',20,NULL,'Sơn gel nghệ thuật',149119.36,'chăm sóc da','2025-08-16 12:00:00'),
                                                                                                                   (86,_binary '\x01',26,NULL,'Gội đầu thư giãn',133670.45,'cắt tóc','2025-10-01 12:00:00'),
                                                                                                                   (87,_binary '\x01',33,NULL,'Sơn gel nghệ thuật',383212.99,'massage','2025-09-05 12:00:00'),
                                                                                                                   (88,_binary '\x01',61,NULL,'Tẩy tóc',163567.72,'chăm sóc tóc','2025-09-21 12:00:00'),
                                                                                                                   (89,_binary '\x01',68,NULL,'Cắt tóc nữ',282955.12,'nail','2025-07-15 12:00:00'),
                                                                                                                   (90,_binary '\x01',40,NULL,'Làm móng tay',251658.05,'nail','2025-08-20 12:00:00'),
                                                                                                                   (91,_binary '\x01',26,NULL,'Massage mặt',224518.39,'nail','2025-09-11 12:00:00'),
                                                                                                                   (92,_binary '\x01',53,NULL,'Gội đầu dưỡng sinh',385568.21,'chăm sóc da','2025-07-08 12:00:00'),
                                                                                                                   (93,_binary '\x01',32,NULL,'Massage toàn thân',108734.4,'chăm sóc tóc','2025-09-02 12:00:00'),
                                                                                                                   (94,_binary '\x01',70,NULL,'Gội đầu dưỡng sinh',460754.74,'cắt tóc','2025-07-28 12:00:00'),
                                                                                                                   (95,_binary '\x01',68,NULL,'Gội đầu dưỡng sinh',439205.05,'chăm sóc tóc','2025-09-07 12:00:00'),
                                                                                                                   (96,_binary '\x00',34,NULL,'Sấy tạo kiểu',312984.83,'nail','2025-08-06 12:00:00'),
                                                                                                                   (97,_binary '\x00',60,NULL,'Cắt tóc nam',93121.25,'cắt tóc','2025-09-07 12:00:00'),
                                                                                                                   (98,_binary '\x01',81,NULL,'Cắt tóc nữ',92370.82,'massage','2025-07-27 12:00:00'),
                                                                                                                   (99,_binary '\x00',18,NULL,'Làm móng tay',394191.71,'chăm sóc da','2025-08-17 12:00:00'),
                                                                                                                   (100,_binary '\x01',39,NULL,'Sơn gel cơ bản',109362.36,'chăm sóc tóc','2025-10-08 12:00:00');

/*!40000 ALTER TABLE `service_items` ENABLE KEYS */;
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
