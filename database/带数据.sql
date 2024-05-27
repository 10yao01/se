-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.32 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.7.0.6850
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 se 的数据库结构
CREATE DATABASE IF NOT EXISTS `se` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `se`;

-- 导出  表 se.batch 结构
CREATE TABLE IF NOT EXISTS `batch` (
  `b_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '批次号',
  `b_date` date NOT NULL COMMENT '联合主键，不想让同样的商品按照批次去卖',
  `p_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '农田（养殖场）编号，联合外键问题？',
  `g_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品编号',
  `amount` int NOT NULL DEFAULT '0' COMMENT '该批次的数量',
  PRIMARY KEY (`b_id`) USING BTREE,
  KEY `FK_batch_goods` (`g_id`) USING BTREE,
  CONSTRAINT `FK_batch_goods` FOREIGN KEY (`g_id`) REFERENCES `goods` (`g_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.batch 的数据：~1 rows (大约)
REPLACE INTO `batch` (`b_id`, `b_date`, `p_id`, `g_id`, `amount`) VALUES
	('B01', '2024-05-25', 'P01', 'G01', 2);

-- 导出  表 se.crop 结构
CREATE TABLE IF NOT EXISTS `crop` (
  `c_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作物编号',
  `c_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作物名',
  `c_info` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作物信息',
  `w_time` int DEFAULT NULL COMMENT '灌溉间隔时间',
  `f_time` int DEFAULT NULL COMMENT '施肥间隔时间',
  PRIMARY KEY (`c_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.crop 的数据：~4 rows (大约)
REPLACE INTO `crop` (`c_id`, `c_name`, `c_info`, `w_time`, `f_time`) VALUES
	('C01', '大葱', '非常好吃', 24, 48),
	('C02', '白菜', '白白嫩嫩', 6, 12),
	('C03', '土豆', '美国大土豆品种', 30, 36),
	('C04', '茄子', '非常紫', 22, 12);

-- 导出  表 se.farm 结构
CREATE TABLE IF NOT EXISTS `farm` (
  `f_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '农场编号',
  `f_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '农场名称',
  `c_id` char(50) NOT NULL COMMENT '作物编号',
  `c_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作物名称',
  `c_amount` int NOT NULL COMMENT '作物数量',
  `p_date` date DEFAULT NULL COMMENT '种植时间',
  `w_id` char(50) NOT NULL COMMENT '负责员工编号',
  `harvest_date` date DEFAULT NULL COMMENT '预计收成时间',
  `stage` int DEFAULT NULL COMMENT '成长阶段',
  `pos_info` char(50) DEFAULT NULL COMMENT '农场位置备注',
  PRIMARY KEY (`f_id`) USING BTREE,
  KEY `FK_farms_crop` (`c_id`),
  KEY `FK_farm_user` (`w_id`),
  CONSTRAINT `FK_farm_user` FOREIGN KEY (`w_id`) REFERENCES `user` (`u_id`),
  CONSTRAINT `FK_farms_crop` FOREIGN KEY (`c_id`) REFERENCES `crop` (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.farm 的数据：~4 rows (大约)
REPLACE INTO `farm` (`f_id`, `f_name`, `c_id`, `c_name`, `c_amount`, `p_date`, `w_id`, `harvest_date`, `stage`, `pos_info`) VALUES
	('F01', '高高兴兴农场', 'C02', '白菜', 100, '2024-05-25', 'U7295433', '2024-05-25', 2, '北京'),
	('F02', '悲伤农场', 'C01', '大葱', 50, '2024-05-17', 'U6824355', '2024-05-28', 5, '浙江'),
	('F03', 'c++农场', 'C03', '土豆', 23, '2024-05-22', 'U7278444', '2024-06-03', 6, '河南'),
	('F04', 'py农场', 'C03', '土豆', 122, '2024-05-13', 'U5270119', '2024-05-23', 8, '河南');

-- 导出  表 se.feed 结构
CREATE TABLE IF NOT EXISTS `feed` (
  `f_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作编号',
  `p_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '养殖场编号',
  `f_time` date DEFAULT NULL COMMENT '喂食时间',
  PRIMARY KEY (`f_id`) USING BTREE,
  KEY `FK_feed_pasture` (`p_id`),
  CONSTRAINT `FK_feed_pasture` FOREIGN KEY (`p_id`) REFERENCES `pasture` (`p_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.feed 的数据：~1 rows (大约)
REPLACE INTO `feed` (`f_id`, `p_id`, `f_time`) VALUES
	('F01', 'P01', '2024-05-25');

-- 导出  表 se.fertile 结构
CREATE TABLE IF NOT EXISTS `fertile` (
  `f_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作编号',
  `farm_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '农场编号',
  `irrigate_time` date DEFAULT NULL COMMENT '灌溉时间',
  `fertile_time` date DEFAULT NULL COMMENT '施肥时间',
  PRIMARY KEY (`f_id`) USING BTREE,
  KEY `FK_fertile_farm` (`farm_id`),
  CONSTRAINT `FK_fertile_farm` FOREIGN KEY (`farm_id`) REFERENCES `farm` (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.fertile 的数据：~1 rows (大约)
REPLACE INTO `fertile` (`f_id`, `farm_id`, `irrigate_time`, `fertile_time`) VALUES
	('F01', 'F02', '2024-05-25', '2024-05-25');

-- 导出  表 se.goods 结构
CREATE TABLE IF NOT EXISTS `goods` (
  `g_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品号',
  `stock` int NOT NULL DEFAULT '0' COMMENT '库存余量，初始默认为0，不删该商品',
  `sale` int NOT NULL COMMENT '销量=余量+批次表里的该商品的数量',
  `price` int NOT NULL COMMENT '价格',
  `score` double NOT NULL COMMENT '平均评分',
  `raters_num` int DEFAULT NULL COMMENT '评分人数',
  PRIMARY KEY (`g_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.goods 的数据：~4 rows (大约)
REPLACE INTO `goods` (`g_id`, `stock`, `sale`, `price`, `score`, `raters_num`) VALUES
	('G01', 100, 50, 20, 4.3, 30),
	('G02', 20, 30, 10, 4.7, 102),
	('G03', 21, 23, 43, 4.9, 122),
	('G04', 12, 2434, 32, 4.6, 1211);

-- 导出  表 se.order 结构
CREATE TABLE IF NOT EXISTS `order` (
  `o_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单号',
  `customer_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '顾客账号',
  `g_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品号',
  `amount` int DEFAULT NULL COMMENT '购买数量',
  `score` int DEFAULT NULL COMMENT '评分',
  PRIMARY KEY (`o_id`) USING BTREE,
  KEY `FK_order_users` (`customer_id`),
  CONSTRAINT `FK_order_users` FOREIGN KEY (`customer_id`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.order 的数据：~7 rows (大约)
REPLACE INTO `order` (`o_id`, `customer_id`, `g_id`, `amount`, `score`) VALUES
	('O01', 'N2717569', 'G01', 22, 5),
	('O02', 'N8493396', 'G03', 23, 4),
	('O03', 'N4946853', 'G04', 212, 4),
	('O04', 'N5871667', 'G02', 2, 4),
	('O05', 'N3365824', 'G01', 34, 5),
	('O06', 'N2717569', 'G03', 43, 3),
	('O07', 'N8493396', 'G04', 44, 3);

-- 导出  表 se.pasture 结构
CREATE TABLE IF NOT EXISTS `pasture` (
  `p_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '养殖场编号',
  `p_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '养殖场名称',
  `poultry_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '家禽编号',
  `poulty_num` int NOT NULL DEFAULT '0' COMMENT '家禽数量',
  `start_time` date DEFAULT NULL COMMENT '入栏时间',
  `w_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '负责职工号',
  `mature_time` date DEFAULT NULL COMMENT '成熟时间',
  `stage` int NOT NULL COMMENT '成长阶段',
  `pos_info` char(50) NOT NULL DEFAULT '' COMMENT '位置备注',
  PRIMARY KEY (`p_id`) USING BTREE,
  KEY `FK_pasture_poultry` (`poultry_id`),
  KEY `FK_pasture_user` (`w_id`),
  CONSTRAINT `FK_pasture_poultry` FOREIGN KEY (`poultry_id`) REFERENCES `poultry` (`p_id`),
  CONSTRAINT `FK_pasture_user` FOREIGN KEY (`w_id`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.pasture 的数据：~5 rows (大约)
REPLACE INTO `pasture` (`p_id`, `p_name`, `poultry_id`, `poulty_num`, `start_time`, `w_id`, `mature_time`, `stage`, `pos_info`) VALUES
	('P01', '养鸡场', 'P01', 16, '2024-05-25', 'U5504479', '2024-06-02', 1, '山西省朔州市应县'),
	('P02', '养鸭场', 'P02', 32, '2024-05-25', 'U8430317', '2024-05-30', 1, '四川省黎川县'),
	('P03', '渔场', 'P05', 25, '2024-05-25', 'U6574819', '2024-05-27', 1, '四川省泸州市叙永县'),
	('P04', '养牛场', 'P03', 12, '2024-05-25', 'U5270119', '2024-05-31', 1, '湖南省株洲市芦淞区里'),
	('P05', '羊场', 'P04', 14, '2024-05-25', 'U5379633', '2024-05-26', 1, '河南省郑州市惠济区园河东路');

-- 导出  表 se.poultry 结构
CREATE TABLE IF NOT EXISTS `poultry` (
  `p_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '家禽编号',
  `p_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '家禽名称',
  `p_info` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '家禽信息',
  `feed_interval` int DEFAULT NULL COMMENT '喂食间隔时间',
  PRIMARY KEY (`p_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.poultry 的数据：~5 rows (大约)
REPLACE INTO `poultry` (`p_id`, `p_name`, `p_info`, `feed_interval`) VALUES
	('P01', '鸡', '一种鸡', 5),
	('P02', '鸭', '鸭子', 10),
	('P03', '牛', '让', 5),
	('P04', '羊', '咩', 3),
	('P05', '鱼', 'a', 3);

-- 导出  表 se.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `u_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `pwd` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `id_type` int DEFAULT NULL COMMENT '身份类型',
  `name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '姓名',
  `gender` int DEFAULT NULL COMMENT '性别',
  `age` int DEFAULT NULL COMMENT '年龄',
  `tel` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.user 的数据：~30 rows (大约)
REPLACE INTO `user` (`u_id`, `pwd`, `id_type`, `name`, `gender`, `age`, `tel`) VALUES
	('U0219216', 'pwd549', 3, '池佳一', 1, 64, '14799966768'),
	('U1128311', 'pwd768', 0, '危素珊', 1, 28, '16800471240'),
	('U2089205', 'pwd829', 1, '刀枝', 0, 27, '19764282389'),
	('U2655222', 'pwd749', 1, '葛懿', 0, 37, '19484283457'),
	('U2717569', 'pwd076', 3, '陈琰', 1, 54, '19871221799'),
	('U2787669', 'pwd144', 2, '孟剑', 1, 30, '17311655583'),
	('U2949267', 'pwd778', 3, '赫厚旻', 1, 54, '13633146035'),
	('U3256977', 'pwd675', 1, '童洪彬', 0, 40, '18007012712'),
	('U3365824', 'pwd651', 3, '庄雪子', 1, 54, '18014284486'),
	('U3924694', 'pwd599', 1, '徐廷新', 0, 30, '17583120162'),
	('U3988270', 'pwd437', 2, '赫嘉', 0, 38, '14728770657'),
	('U4559363', 'pwd089', 3, '封荔锦', 1, 29, '13200463406'),
	('U4688739', 'pwd088', 0, '甄淳', 1, 20, '15216166514'),
	('U4946853', 'pwd493', 3, '仝和', 1, 38, '17233763386'),
	('U5270119', 'pwd994', 2, '邰元', 1, 38, '14712268387'),
	('U5379633', 'pwd167', 2, '夏侯贞', 0, 19, '16133166846'),
	('U5504479', 'pwd593', 2, '谌玄', 1, 50, '19614360721'),
	('U5871667', 'pwd482', 3, '尹丞安', 1, 30, '18474659228'),
	('U5875211', 'pwd988', 3, '崔家昊', 0, 30, '15560245831'),
	('U6435012', 'pwd017', 0, '史众旭', 1, 30, '18742660408'),
	('U6574819', 'pwd721', 2, '端木秋会', 1, 25, '14754953376'),
	('U6577235', 'pwd565', 1, '佘蕊晓', 1, 25, '13493346347'),
	('U6691729', 'pwd358', 1, '桂燕', 0, 44, '19840825329'),
	('U6824355', 'pwd768', 2, '唐颖', 1, 39, '17916723573'),
	('U7278444', 'pwd408', 2, '卓君轩', 1, 38, '19565043473'),
	('U7295433', 'pwd757', 2, '那洪东', 0, 28, '15428784294'),
	('U7659050', 'pwd608', 0, '端木顺福', 0, 56, '13781814420'),
	('U8430317', 'pwd415', 2, '狄薇泓', 0, 37, '18525156302'),
	('U8493396', 'pwd784', 3, '平睿宙', 0, 55, '16841732033'),
	('U8545579', 'pwd218', 3, '区璧', 0, 48, '14743240290');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
