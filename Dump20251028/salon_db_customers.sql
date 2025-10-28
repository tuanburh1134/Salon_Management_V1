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
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `deleted` bit(1) NOT NULL,
  `email` varchar(120) DEFAULT NULL,
  `member_type` enum('DAC_BIET','MOI','THAN_QUEN') NOT NULL,
  `name` varchar(120) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `point` int DEFAULT NULL,
  `stt` varchar(10) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'2025-10-28 10:40:01',_binary '\0','user1@example.com','MOI','Khách hàng 1','0959473333',291,'KH001','2025-10-28 10:40:01','Khách thân thiết - thường đặt cuối tuần','TP. Hồ Chí Minh',NULL,NULL),
                                (2,'2025-10-28 10:40:02',_binary '\0','user2@example.com','MOI','Khách hàng 2','0966086331',184,'KH002','2025-10-28 10:40:02','Khách quen lâu năm','Huế',NULL,NULL),
                                (3,'2025-10-28 10:40:03',_binary '\0','user3@example.com','MOI','Khách hàng 3','0916361122',130,'KH003','2025-10-28 10:40:03','Khách VIP - gói Platinum','Đà Nẵng',NULL,NULL),
                                (4,'2025-10-28 10:40:04',_binary '\0','user4@example.com','MOI','Khách hàng 4','0999129196',114,'KH004','2025-10-28 10:40:04','Khách thân thiết - thường đặt cuối tuần','Cần Thơ',NULL,NULL),
                                (5,'2025-10-28 10:40:05',_binary '\0','user5@example.com','THAN_QUEN','Khách hàng 5','0969528111',99,'KH005','2025-10-28 10:40:05','Khách quen lâu năm','Nghệ An',NULL,NULL),
                                (6,'2025-10-28 10:40:06',_binary '\0','user6@example.com','MOI','Khách hàng 6','0944770975',237,'KH006','2025-10-28 10:40:06','Khách quen cắt tóc định kỳ','Nghệ An',NULL,NULL),
                                (7,'2025-10-28 10:40:07',_binary '\0','user7@example.com','MOI','Khách hàng 7','0997674249',400,'KH007','2025-10-28 10:40:07','Khách quen cắt tóc định kỳ','Nghệ An',NULL,NULL),
                                (8,'2025-10-28 10:40:08',_binary '\0','user8@example.com','THAN_QUEN','Khách hàng 8','0990058043',116,'KH008','2025-10-28 10:40:08','Khách mới được bạn giới thiệu','TP. Hồ Chí Minh',NULL,NULL),
                                (9,'2025-10-28 10:40:09',_binary '\0','user9@example.com','THAN_QUEN','Khách hàng 9','0951334466',220,'KH009','2025-10-28 10:40:09','Khách quen lâu năm','Bắc Ninh',NULL,NULL),
                                (10,'2025-10-28 10:40:10',_binary '\0','user10@example.com','MOI','Khách hàng 10','0946070259',228,'KH010','2025-10-28 10:40:10','Khách mới đặt lần đầu','TP. Hồ Chí Minh',NULL,NULL),
                                (11,'2025-10-28 10:40:11',_binary '\0','user11@example.com','MOI','Khách hàng 11','0982837395',287,'KH011','2025-10-28 10:40:11','Khách quen thường đi cùng bạn','Đà Nẵng',NULL,NULL),
                                (12,'2025-10-28 10:40:12',_binary '\0','user12@example.com','THAN_QUEN','Khách hàng 12','0977749107',267,'KH012','2025-10-28 10:40:12','Khách mới đặt lần đầu','TP. Hồ Chí Minh',NULL,NULL),
                                (13,'2025-10-28 10:40:13',_binary '\0','user13@example.com','THAN_QUEN','Khách hàng 13','0912790909',29,'KH013','2025-10-28 10:40:13','Khách mới đặt qua website','Thái Bình',NULL,NULL),
                                (14,'2025-10-28 10:40:14',_binary '\0','user14@example.com','THAN_QUEN','Khách hàng 14','0945119829',363,'KH014','2025-10-28 10:40:14','Khách mới từ quảng cáo Facebook','Nam Định',NULL,NULL),
                                (15,'2025-10-28 10:40:15',_binary '\0','user15@example.com','MOI','Khách hàng 15','0926026343',382,'KH015','2025-10-28 10:40:15','Khách thân thiết - thường đặt cuối tuần','Thái Bình',NULL,NULL),
                                (16,'2025-10-28 10:40:16',_binary '\0','user16@example.com','THAN_QUEN','Khách hàng 16','0977762775',385,'KH016','2025-10-28 10:40:16','Khách VIP - ưu tiên đặt lịch','Hải Phòng',NULL,NULL),
                                (17,'2025-10-28 10:40:17',_binary '\0','user17@example.com','MOI','Khách hàng 17','0944229258',336,'KH017','2025-10-28 10:40:17','Khách mới đặt lần đầu','Huế',NULL,NULL),
                                (18,'2025-10-28 10:40:18',_binary '\0','user18@example.com','MOI','Khách hàng 18','0964683993',177,'KH018','2025-10-28 10:40:18','Khách quen cắt tóc định kỳ','TP. Hồ Chí Minh',NULL,NULL),
                                (19,'2025-10-28 10:40:19',_binary '\0','user19@example.com','THAN_QUEN','Khách hàng 19','0962888829',488,'KH019','2025-10-28 10:40:19','Khách mới được bạn giới thiệu','TP. Hồ Chí Minh',NULL,NULL),
                                (20,'2025-10-28 10:40:20',_binary '\0','user20@example.com','MOI','Khách hàng 20','0978739867',175,'KH020','2025-10-28 10:40:20','Khách mới đặt lần đầu','Hải Phòng',NULL,NULL),
                                (21,'2025-10-28 10:40:21',_binary '\0','user21@example.com','THAN_QUEN','Khách hàng 21','0985493695',134,'KH021','2025-10-28 10:40:21','Khách quen thường đi cùng bạn','Hà Nội',NULL,NULL),
                                (22,'2025-10-28 10:40:22',_binary '\0','user22@example.com','THAN_QUEN','Khách hàng 22','0940643134',207,'KH022','2025-10-28 10:40:22','Khách quen lâu năm','Hải Phòng',NULL,NULL),
                                (23,'2025-10-28 10:40:23',_binary '\0','user23@example.com','DAC_BIET','Khách hàng 23','0986347119',181,'KH023','2025-10-28 10:40:23','Khách VIP - ưu tiên đặt lịch','Hải Phòng',NULL,NULL),
                                (24,'2025-10-28 10:40:24',_binary '\0','user24@example.com','THAN_QUEN','Khách hàng 24','0957159846',270,'KH024','2025-10-28 10:40:24','Khách mới đặt qua website','Huế',NULL,NULL),
                                (25,'2025-10-28 10:40:25',_binary '\0','user25@example.com','DAC_BIET','Khách hàng 25','0952080592',264,'KH025','2025-10-28 10:40:25','Khách thân thiết - thường đặt cuối tuần','Hà Nội',NULL,NULL),
                                (26,'2025-10-28 10:40:26',_binary '\0','user26@example.com','THAN_QUEN','Khách hàng 26','0911821398',26,'KH026','2025-10-28 10:40:26','Khách mới được bạn giới thiệu','Đà Nẵng',NULL,NULL),
                                (27,'2025-10-28 10:40:27',_binary '\0','user27@example.com','MOI','Khách hàng 27','0932425512',121,'KH027','2025-10-28 10:40:27','Khách quen thường đi cùng bạn','Hà Nội',NULL,NULL),
                                (28,'2025-10-28 10:40:28',_binary '\0','user28@example.com','THAN_QUEN','Khách hàng 28','0963196262',284,'KH028','2025-10-28 10:40:28','Khách mới đặt lần đầu','Bắc Ninh',NULL,NULL),
                                (29,'2025-10-28 10:40:29',_binary '\0','user29@example.com','MOI','Khách hàng 29','0910729032',339,'KH029','2025-10-28 10:40:29','Khách quen thường đi cùng bạn','Nghệ An',NULL,NULL),
                                (30,'2025-10-28 10:40:30',_binary '\0','user30@example.com','THAN_QUEN','Khách hàng 30','0946243147',472,'KH030','2025-10-28 10:40:30','Khách mới đặt lần đầu','Thái Bình',NULL,NULL),
                                (31,'2025-10-28 10:40:31',_binary '\0','user31@example.com','THAN_QUEN','Khách hàng 31','0987201875',301,'KH031','2025-10-28 10:40:31','Khách thân thiết - thường đặt cuối tuần','Hải Phòng',NULL,NULL),
                                (32,'2025-10-28 10:40:32',_binary '\0','user32@example.com','THAN_QUEN','Khách hàng 32','0958540169',284,'KH032','2025-10-28 10:40:32','Khách mới từ quảng cáo Facebook','Huế',NULL,NULL),
                                (33,'2025-10-28 10:40:33',_binary '\0','user33@example.com','THAN_QUEN','Khách hàng 33','0994060289',58,'KH033','2025-10-28 10:40:33','Khách quen cắt tóc định kỳ','Hà Nội',NULL,NULL),
                                (34,'2025-10-28 10:40:34',_binary '\0','user34@example.com','DAC_BIET','Khách hàng 34','0938635840',69,'KH034','2025-10-28 10:40:34','Khách VIP - gói Platinum','Hải Phòng',NULL,NULL),
                                (35,'2025-10-28 10:40:35',_binary '\0','user35@example.com','THAN_QUEN','Khách hàng 35','0911889582',452,'KH035','2025-10-28 10:40:35','Khách mới từ quảng cáo Facebook','Hải Phòng',NULL,NULL),
                                (36,'2025-10-28 10:40:36',_binary '\0','user36@example.com','DAC_BIET','Khách hàng 36','0997739198',31,'KH036','2025-10-28 10:40:36','Khách quen cắt tóc định kỳ','Hà Nội',NULL,NULL),
                                (37,'2025-10-28 10:40:37',_binary '\0','user37@example.com','MOI','Khách hàng 37','0993848634',164,'KH037','2025-10-28 10:40:37','Khách quen thường đi cùng bạn','Huế',NULL,NULL),
                                (38,'2025-10-28 10:40:38',_binary '\0','user38@example.com','THAN_QUEN','Khách hàng 38','0958100258',64,'KH038','2025-10-28 10:40:38','Khách mới đặt lần đầu','Hải Phòng',NULL,NULL),
                                (39,'2025-10-28 10:40:39',_binary '\0','user39@example.com','THAN_QUEN','Khách hàng 39','0936064025',488,'KH039','2025-10-28 10:40:39','Khách thân thiết - thường đặt cuối tuần','Huế',NULL,NULL),
                                (40,'2025-10-28 10:40:40',_binary '\0','user40@example.com','DAC_BIET','Khách hàng 40','0927405769',61,'KH040','2025-10-28 10:40:40','Khách mới đặt lần đầu','Hà Nội',NULL,NULL),
                                (41,'2025-10-28 10:40:41',_binary '\0','user41@example.com','THAN_QUEN','Khách hàng 41','0922640942',358,'KH041','2025-10-28 10:40:41','Khách VIP - gói Platinum','Nam Định',NULL,NULL),
                                (42,'2025-10-28 10:40:42',_binary '\0','user42@example.com','MOI','Khách hàng 42','0998559052',460,'KH042','2025-10-28 10:40:42','Khách VIP - ưu tiên đặt lịch','Nghệ An',NULL,NULL),
                                (43,'2025-10-28 10:40:43',_binary '\0','user43@example.com','MOI','Khách hàng 43','0915961933',348,'KH043','2025-10-28 10:40:43','Khách quen thường đi cùng bạn','Nam Định',NULL,NULL),
                                (44,'2025-10-28 10:40:44',_binary '\0','user44@example.com','MOI','Khách hàng 44','0957855954',110,'KH044','2025-10-28 10:40:44','Khách thân thiết - thường đặt cuối tuần','Bắc Ninh',NULL,NULL),
                                (45,'2025-10-28 10:40:45',_binary '\0','user45@example.com','MOI','Khách hàng 45','0946242850',197,'KH045','2025-10-28 10:40:45','Khách thân thiết - thường đặt cuối tuần','Huế',NULL,NULL),
                                (46,'2025-10-28 10:40:46',_binary '\0','user46@example.com','DAC_BIET','Khách hàng 46','0989419916',162,'KH046','2025-10-28 10:40:46','Khách quen thường đi cùng bạn','Hà Nội',NULL,NULL),
                                (47,'2025-10-28 10:40:47',_binary '\0','user47@example.com','DAC_BIET','Khách hàng 47','0913802971',238,'KH047','2025-10-28 10:40:47','Khách mới được bạn giới thiệu','Nghệ An',NULL,NULL),
                                (48,'2025-10-28 10:40:48',_binary '\0','user48@example.com','MOI','Khách hàng 48','0990524190',105,'KH048','2025-10-28 10:40:48','Khách VIP - gói Platinum','Cần Thơ',NULL,NULL),
                                (49,'2025-10-28 10:40:49',_binary '\0','user49@example.com','DAC_BIET','Khách hàng 49','0998889205',1,'KH049','2025-10-28 10:40:49','Khách VIP - gói Platinum','Thái Bình',NULL,NULL),
                                (50,'2025-10-28 10:40:50',_binary '\0','user50@example.com','MOI','Khách hàng 50','0951695953',344,'KH050','2025-10-28 10:40:50','Khách mới đặt qua website','Đà Nẵng',NULL,NULL),
                                (51,'2025-10-28 10:40:51',_binary '\0','user51@example.com','THAN_QUEN','Khách hàng 51','0957400636',89,'KH051','2025-10-28 10:40:51','Khách mới từ quảng cáo Facebook','Hải Phòng',NULL,NULL),
                                (52,'2025-10-28 10:40:52',_binary '\0','user52@example.com','THAN_QUEN','Khách hàng 52','0994716307',426,'KH052','2025-10-28 10:40:52','Khách quen lâu năm','Nam Định',NULL,NULL),
                                (53,'2025-10-28 10:40:53',_binary '\0','user53@example.com','DAC_BIET','Khách hàng 53','0980634719',209,'KH053','2025-10-28 10:40:53','Khách mới được bạn giới thiệu','Cần Thơ',NULL,NULL),
                                (54,'2025-10-28 10:40:54',_binary '\0','user54@example.com','THAN_QUEN','Khách hàng 54','0999239501',310,'KH054','2025-10-28 10:40:54','Khách quen lâu năm','Bắc Ninh',NULL,NULL),
                                (55,'2025-10-28 10:40:55',_binary '\0','user55@example.com','MOI','Khách hàng 55','0943430448',470,'KH055','2025-10-28 10:40:55','Khách thân thiết - thường đặt cuối tuần','Đà Nẵng',NULL,NULL),
                                (56,'2025-10-28 10:40:56',_binary '\0','user56@example.com','MOI','Khách hàng 56','0941282754',162,'KH056','2025-10-28 10:40:56','Khách mới đặt qua website','Nghệ An',NULL,NULL),
                                (57,'2025-10-28 10:40:57',_binary '\0','user57@example.com','DAC_BIET','Khách hàng 57','0923115142',431,'KH057','2025-10-28 10:40:57','Khách VIP - ưu tiên đặt lịch','Thái Bình',NULL,NULL),
                                (58,'2025-10-28 10:40:58',_binary '\0','user58@example.com','MOI','Khách hàng 58','0968711038',165,'KH058','2025-10-28 10:40:58','Khách thân thiết - thường đặt cuối tuần','Đà Nẵng',NULL,NULL),
                                (59,'2025-10-28 10:40:59',_binary '\0','user59@example.com','MOI','Khách hàng 59','0979136270',101,'KH059','2025-10-28 10:40:59','Khách mới từ quảng cáo Facebook','Cần Thơ',NULL,NULL),
                                (60,'2025-10-28 10:41:00',_binary '\0','user60@example.com','DAC_BIET','Khách hàng 60','0959503553',453,'KH060','2025-10-28 10:41:00','Khách mới đặt qua website','Đà Nẵng',NULL,NULL),
                                (61,'2025-10-28 10:41:01',_binary '\0','user61@example.com','MOI','Khách hàng 61','0999024919',123,'KH061','2025-10-28 10:41:01','Khách thân thiết - thường đặt cuối tuần','Nghệ An',NULL,NULL),
                                (62,'2025-10-28 10:41:02',_binary '\0','user62@example.com','MOI','Khách hàng 62','0932017972',316,'KH062','2025-10-28 10:41:02','Khách VIP - gói Platinum','Cần Thơ',NULL,NULL),
                                (63,'2025-10-28 10:41:03',_binary '\0','user63@example.com','DAC_BIET','Khách hàng 63','0947063416',270,'KH063','2025-10-28 10:41:03','Khách mới đặt qua website','Hải Phòng',NULL,NULL),
                                (64,'2025-10-28 10:41:04',_binary '\0','user64@example.com','MOI','Khách hàng 64','0969320967',313,'KH064','2025-10-28 10:41:04','Khách quen cắt tóc định kỳ','Bắc Ninh',NULL,NULL),
                                (65,'2025-10-28 10:41:05',_binary '\0','user65@example.com','THAN_QUEN','Khách hàng 65','0982400330',250,'KH065','2025-10-28 10:41:05','Khách thân thiết - thường đặt cuối tuần','Đà Nẵng',NULL,NULL),
                                (66,'2025-10-28 10:41:06',_binary '\0','user66@example.com','DAC_BIET','Khách hàng 66','0944369876',40,'KH066','2025-10-28 10:41:06','Khách mới đặt lần đầu','Đà Nẵng',NULL,NULL),
                                (67,'2025-10-28 10:41:07',_binary '\0','user67@example.com','THAN_QUEN','Khách hàng 67','0955390694',243,'KH067','2025-10-28 10:41:07','Khách VIP - ưu tiên đặt lịch','Hà Nội',NULL,NULL),
                                (68,'2025-10-28 10:41:08',_binary '\0','user68@example.com','DAC_BIET','Khách hàng 68','0943261488',327,'KH068','2025-10-28 10:41:08','Khách quen lâu năm','Nam Định',NULL,NULL),
                                (69,'2025-10-28 10:41:09',_binary '\0','user69@example.com','MOI','Khách hàng 69','0952825721',367,'KH069','2025-10-28 10:41:09','Khách thân thiết - thường đặt cuối tuần','Thái Bình',NULL,NULL),
                                (70,'2025-10-28 10:41:10',_binary '\0','user70@example.com','DAC_BIET','Khách hàng 70','0919231302',141,'KH070','2025-10-28 10:41:10','Khách quen lâu năm','TP. Hồ Chí Minh',NULL,NULL),
                                (71,'2025-10-28 10:41:11',_binary '\0','user71@example.com','THAN_QUEN','Khách hàng 71','0938329226',287,'KH071','2025-10-28 10:41:11','Khách mới từ quảng cáo Facebook','Bắc Ninh',NULL,NULL),
                                (72,'2025-10-28 10:41:12',_binary '\0','user72@example.com','MOI','Khách hàng 72','0997344833',428,'KH072','2025-10-28 10:41:12','Khách thân thiết - thường đặt cuối tuần','Hải Phòng',NULL,NULL),
                                (73,'2025-10-28 10:41:13',_binary '\0','user73@example.com','DAC_BIET','Khách hàng 73','0936487497',67,'KH073','2025-10-28 10:41:13','Khách mới được bạn giới thiệu','Bắc Ninh',NULL,NULL),
                                (74,'2025-10-28 10:41:14',_binary '\0','user74@example.com','MOI','Khách hàng 74','0944011529',236,'KH074','2025-10-28 10:41:14','Khách VIP - gói Platinum','TP. Hồ Chí Minh',NULL,NULL),
                                (75,'2025-10-28 10:41:15',_binary '\0','user75@example.com','DAC_BIET','Khách hàng 75','0910477894',270,'KH075','2025-10-28 10:41:15','Khách thân thiết - thường đặt cuối tuần','Huế',NULL,NULL),
                                (76,'2025-10-28 10:41:16',_binary '\0','user76@example.com','MOI','Khách hàng 76','0930077224',392,'KH076','2025-10-28 10:41:16','Khách mới được bạn giới thiệu','Nam Định',NULL,NULL),
                                (77,'2025-10-28 10:41:17',_binary '\0','user77@example.com','DAC_BIET','Khách hàng 77','0953128187',75,'KH077','2025-10-28 10:41:17','Khách mới đặt lần đầu','Thái Bình',NULL,NULL),
                                (78,'2025-10-28 10:41:18',_binary '\0','user78@example.com','THAN_QUEN','Khách hàng 78','0947376835',340,'KH078','2025-10-28 10:41:18','Khách quen lâu năm','Hà Nội',NULL,NULL),
                                (79,'2025-10-28 10:41:19',_binary '\0','user79@example.com','MOI','Khách hàng 79','0933371067',94,'KH079','2025-10-28 10:41:19','Khách thân thiết - thường đặt cuối tuần','Hà Nội',NULL,NULL),
                                (80,'2025-10-28 10:41:20',_binary '\0','user80@example.com','DAC_BIET','Khách hàng 80','0963878714',301,'KH080','2025-10-28 10:41:20','Khách quen thường đi cùng bạn','Cần Thơ',NULL,NULL),
                                (81,'2025-10-28 10:41:21',_binary '\0','user81@example.com','MOI','Khách hàng 81','0933382668',64,'KH081','2025-10-28 10:41:21','Khách VIP - gói Platinum','Đà Nẵng',NULL,NULL),
                                (82,'2025-10-28 10:41:22',_binary '\0','user82@example.com','MOI','Khách hàng 82','0947892973',493,'KH082','2025-10-28 10:41:22','Khách mới đặt lần đầu','Nam Định',NULL,NULL),
                                (83,'2025-10-28 10:41:23',_binary '\0','user83@example.com','DAC_BIET','Khách hàng 83','0979326613',70,'KH083','2025-10-28 10:41:23','Khách mới đặt qua website','Đà Nẵng',NULL,NULL),
                                (84,'2025-10-28 10:41:24',_binary '\0','user84@example.com','DAC_BIET','Khách hàng 84','0915562533',353,'KH084','2025-10-28 10:41:24','Khách mới đặt lần đầu','Bắc Ninh',NULL,NULL),
                                (85,'2025-10-28 10:41:25',_binary '\0','user85@example.com','THAN_QUEN','Khách hàng 85','0943953998',248,'KH085','2025-10-28 10:41:25','Khách quen thường đi cùng bạn','Nghệ An',NULL,NULL),
                                (86,'2025-10-28 10:41:26',_binary '\0','user86@example.com','MOI','Khách hàng 86','0940138036',500,'KH086','2025-10-28 10:41:26','Khách mới từ quảng cáo Facebook','Hà Nội',NULL,NULL),
                                (87,'2025-10-28 10:41:27',_binary '\0','user87@example.com','THAN_QUEN','Khách hàng 87','0963518671',50,'KH087','2025-10-28 10:41:27','Khách mới từ quảng cáo Facebook','TP. Hồ Chí Minh',NULL,NULL),
                                (88,'2025-10-28 10:41:28',_binary '\0','user88@example.com','DAC_BIET','Khách hàng 88','0926525794',46,'KH088','2025-10-28 10:41:28','Khách quen thường đi cùng bạn','Hải Phòng',NULL,NULL),
                                (89,'2025-10-28 10:41:29',_binary '\0','user89@example.com','THAN_QUEN','Khách hàng 89','0989544527',213,'KH089','2025-10-28 10:41:29','Khách VIP - ưu tiên đặt lịch','Nghệ An',NULL,NULL),
                                (90,'2025-10-28 10:41:30',_binary '\0','user90@example.com','MOI','Khách hàng 90','0976467919',140,'KH090','2025-10-28 10:41:30','Khách quen lâu năm','Nam Định',NULL,NULL),
                                (91,'2025-10-28 10:41:31',_binary '\0','user91@example.com','MOI','Khách hàng 91','0961833608',149,'KH091','2025-10-28 10:41:31','Khách quen thường đi cùng bạn','Hải Phòng',NULL,NULL),
                                (92,'2025-10-28 10:41:32',_binary '\0','user92@example.com','DAC_BIET','Khách hàng 92','0920898880',58,'KH092','2025-10-28 10:41:32','Khách VIP - gói Platinum','Cần Thơ',NULL,NULL),
                                (93,'2025-10-28 10:41:33',_binary '\0','user93@example.com','DAC_BIET','Khách hàng 93','0989912284',300,'KH093','2025-10-28 10:41:33','Khách VIP - gói Platinum','TP. Hồ Chí Minh',NULL,NULL),
                                (94,'2025-10-28 10:41:34',_binary '\0','user94@example.com','THAN_QUEN','Khách hàng 94','0937444781',166,'KH094','2025-10-28 10:41:34','Khách quen lâu năm','Cần Thơ',NULL,NULL),
                                (95,'2025-10-28 10:41:35',_binary '\0','user95@example.com','DAC_BIET','Khách hàng 95','0933804643',222,'KH095','2025-10-28 10:41:35','Khách quen cắt tóc định kỳ','Nam Định',NULL,NULL),
                                (96,'2025-10-28 10:41:36',_binary '\0','user96@example.com','DAC_BIET','Khách hàng 96','0914092835',56,'KH096','2025-10-28 10:41:36','Khách VIP - ưu tiên đặt lịch','Hải Phòng',NULL,NULL),
                                (97,'2025-10-28 10:41:37',_binary '\0','user97@example.com','THAN_QUEN','Khách hàng 97','0941127991',128,'KH097','2025-10-28 10:41:37','Khách quen thường đi cùng bạn','TP. Hồ Chí Minh',NULL,NULL),
                                (98,'2025-10-28 10:41:38',_binary '\0','user98@example.com','THAN_QUEN','Khách hàng 98','0981216577',390,'KH098','2025-10-28 10:41:38','Khách quen cắt tóc định kỳ','TP. Hồ Chí Minh',NULL,NULL),
                                (99,'2025-10-28 10:41:39',_binary '\0','user99@example.com','DAC_BIET','Khách hàng 99','0923077867',154,'KH099','2025-10-28 10:41:39','Khách mới đặt lần đầu','TP. Hồ Chí Minh',NULL,NULL),
                                (100,'2025-10-28 10:41:40',_binary '\0','user100@example.com','THAN_QUEN','Khách hàng 100','0932229433',39,'KH100','2025-10-28 10:41:40','Khách quen cắt tóc định kỳ','Đà Nẵng',NULL,NULL);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
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
