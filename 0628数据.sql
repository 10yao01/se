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
  `b_date` date NOT NULL COMMENT '日期',
  `origin_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产地',
  `g_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品编号',
  `g_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `is_dealed` int NOT NULL DEFAULT (0) COMMENT '1为已审核，0为未审核，-1为审核不通过',
  `amount` int DEFAULT NULL COMMENT '该批次的数量',
  PRIMARY KEY (`b_id`) USING BTREE,
  KEY `FK_batch_goods` (`g_id`) USING BTREE,
  CONSTRAINT `FK_batch_goods` FOREIGN KEY (`g_id`) REFERENCES `goods` (`g_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.batch 的数据：~5 rows (大约)
REPLACE INTO `batch` (`b_id`, `b_date`, `origin_id`, `g_id`, `g_name`, `is_dealed`, `amount`) VALUES
	('B01', '2024-05-25', 'P01', 'P01', '鸡', 1, 2),
	('B02', '2024-05-31', 'P02', 'P02', '鸭', 1, 20),
	('B03', '2024-06-03', 'P03', 'P01', '鸡', 0, 20),
	('B04', '2024-06-02', 'P02', 'P02', '鸭', 1, 20);

-- 导出  表 se.clock 结构
CREATE TABLE IF NOT EXISTS `clock` (
  `e_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `date` date DEFAULT NULL COMMENT '日期',
  `come` time DEFAULT NULL COMMENT '抵达时间',
  `leave` time DEFAULT NULL COMMENT '离开时间',
  KEY `e_id` (`e_id`),
  CONSTRAINT `FK_clock_employee` FOREIGN KEY (`e_id`) REFERENCES `employee` (`e_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='签到表';

-- 正在导出表  se.clock 的数据：~0 rows (大约)
REPLACE INTO `clock` (`e_id`, `date`, `come`, `leave`) VALUES
	('U2270119', '2024-06-28', '12:34:15', '20:34:16'),
	('U5430317', '2024-06-28', '15:25:12', '21:25:12'),
	('U2988270', '2024-04-28', '15:20:22', '19:25:24'),
	('U5278444', '2024-03-28', '07:25:35', '19:25:38'),
	('U2270119', '2024-06-19', '08:26:34', '19:26:36');

-- 导出  表 se.crop 结构
CREATE TABLE IF NOT EXISTS `crop` (
  `c_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作物编号',
  `c_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作物名',
  `c_info` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '作物信息',
  `w_time` int DEFAULT NULL COMMENT '灌溉间隔时间',
  `f_time` int DEFAULT NULL COMMENT '施肥间隔时间',
  PRIMARY KEY (`c_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.crop 的数据：~5 rows (大约)
REPLACE INTO `crop` (`c_id`, `c_name`, `c_info`, `w_time`, `f_time`) VALUES
	('C01', '大葱', '非常好吃', 24, 48),
	('C02', '白菜', '白白嫩嫩', 6, 12),
	('C03', '土豆', '美国大土豆品种', 30, 36),
	('C04', '茄子', '非常紫', 22, 12),
	('C05', '豆角', '好吃', 2, 4);

-- 导出  表 se.employee 结构
CREATE TABLE IF NOT EXISTS `employee` (
  `e_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '2开头是农场管理员，5开头是牧场管理员',
  `e_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `performance` json DEFAULT NULL COMMENT 'json格式存储近几个月的绩效。触发规则：order表里新增一条数据时，修改对应员工该月绩效+=amount*score*price*0.2',
  `tel` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `salary` double DEFAULT NULL COMMENT '待发放工资，每次perform变、clock新增、leave信息被审核时，修改值',
  `is_got` int(1) unsigned zerofill DEFAULT NULL COMMENT '是否发放，审核后将per和salary变为0，发放后新添加工资时，要触发器再变为0',
  PRIMARY KEY (`e_id`),
  CONSTRAINT `FK_employee_user` FOREIGN KEY (`e_id`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.employee 的数据：~8 rows (大约)
REPLACE INTO `employee` (`e_id`, `e_name`, `performance`, `tel`, `salary`, `is_got`) VALUES
	('U2270119', '邰元', '{"2024-1": 1000, "2024-2": 2000, "2024-3": 5000, "2024-4": 500, "2024-5": 500, "2024-6": 1400, "2024-7": 1281}', '12315', 4227, 0),
	('U2379633', '夏侯贞', '{"2024-1": 2000, "2024-2": 3000, "2024-3": 1200, "2024-4": 4200, "2024-5": 3500, "2024-6": 3299, "2024-7": 1281}', '110', 200, 0),
	('U2787669', '孟剑', '{"2024-1": 3000, "2024-2": 2000, "2024-3": 1200, "2024-4": 5200, "2024-5": 2500, "2024-6": 5400, "2024-7": 1043}', '120', 200, 0),
	('U2988270', '赫嘉', '{"2024-1": 1200, "2024-2": 1500, "2024-3": 1200, "2024-4": 1600, "2024-5": 1500, "2024-6": 1600, "2024-7": 340}', '119', 200, 0),
	('U5278444', '卓君轩', '{"2024-1": 1000, "2024-2": 1300, "2024-3": 1200, "2024-4": 1200, "2024-5": 3500, "2024-6": 3100, "2024-7": 900}', '177', 400, 0),
	('U5430317', '狄薇泓', '{"2024-1": 1000, "2024-2": 2000, "2024-3": 650, "2024-4": 5200, "2024-5": 1700, "2024-6": 1200, "2024-7": 439}', '456', 500, 0),
	('U5504479', '谌玄', '{"2024-1": 3000, "2024-2": 2000, "2024-3": 4300, "2024-4": 4200, "2024-5": 1500, "2024-6": 900, "2024-7": 200}', '125', 2000, 0),
	('U5574819', '端木秋会', '{"2024-1": 900, "2024-2": 1200, "2024-3": 1200, "2024-4": 2200, "2024-5": 4300, "2024-6": 1900, "2024-7": 128}', '223', 1600, 0);

-- 导出  表 se.farm 结构
CREATE TABLE IF NOT EXISTS `farm` (
  `f_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '农场编号',
  `f_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '农场名称',
  `c_id` char(50) NOT NULL COMMENT '作物编号',
  `c_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作物名称',
  `c_amount` int NOT NULL DEFAULT (0) COMMENT '作物数量',
  `p_date` date NOT NULL COMMENT '种植时间',
  `w_id` char(50) NOT NULL COMMENT '负责员工编号',
  `harvest_date` date DEFAULT NULL COMMENT '预计收成时间',
  `stage` int DEFAULT NULL COMMENT '成长阶段',
  `pos_info` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '农场位置备注',
  `longitude` double DEFAULT NULL COMMENT '经度',
  `latitude` double unsigned DEFAULT NULL COMMENT '纬度',
  `soil_type` int DEFAULT NULL COMMENT '1为黄土地，2为黑土地',
  `nutrient` json DEFAULT NULL COMMENT '养分含量，2024-1代表一月',
  `moisture` json DEFAULT NULL COMMENT '土壤墒情，1表示最干旱，5表示最潮湿，按月份统计',
  PRIMARY KEY (`f_id`) USING BTREE,
  KEY `FK_farms_crop` (`c_id`),
  KEY `FK_farm_user` (`w_id`),
  CONSTRAINT `FK_farm_user` FOREIGN KEY (`w_id`) REFERENCES `user` (`u_id`),
  CONSTRAINT `FK_farms_crop` FOREIGN KEY (`c_id`) REFERENCES `crop` (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.farm 的数据：~6 rows (大约)
REPLACE INTO `farm` (`f_id`, `f_name`, `c_id`, `c_name`, `c_amount`, `p_date`, `w_id`, `harvest_date`, `stage`, `pos_info`, `longitude`, `latitude`, `soil_type`, `nutrient`, `moisture`) VALUES
	('F01', '高高兴兴农场', 'C02', '白菜', 100, '2024-05-25', 'U2988270', '2024-06-07', 2, '北京', 116, 39, 1, '{"2024-1": 9, "2024-2": 10, "2024-3": 50, "2024-4": 30, "2024-5": 25, "2024-6": 40, "2024-7": 25}', '{"2024-1": 1, "2024-2": 2, "2024-3": 1, "2024-4": 1, "2024-5": 3, "2024-6": 4, "2024-7": 3}'),
	('F02', '悲伤农场', 'C01', '大葱', 50, '2024-05-17', 'U2270119', '2024-06-10', 2, '浙江', 120, 30, 1, '{"2024-1": 7, "2024-2": 20, "2024-3": 20, "2024-4": 10, "2024-5": 25, "2024-6": 20, "2024-7": 20}', '{"2024-1": 3, "2024-2": 1, "2024-3": 2, "2024-4": 1, "2024-5": 3, "2024-6": 2, "2024-7": 5}'),
	('F03', 'c++农场', 'C03', '土豆', 23, '2024-05-22', 'U2379633', '2024-06-12', 1, '河南', 114, 38, 2, '{"2024-1": 4, "2024-2": 6, "2024-3": 10, "2024-4": 20, "2024-5": 30, "2024-6": 40, "2024-7": 40}', '{"2024-1": 1, "2024-2": 3, "2024-3": 2, "2024-4": 2, "2024-5": 2, "2024-6": 5, "2024-7": 3}'),
	('F04', 'py农场', 'C03', '土豆', 122, '2024-05-13', 'U2787669', '2025-05-23', 1, '河南', 120, 37, 1, '{"2024-1": 15, "2024-2": 8, "2024-3": 12, "2024-4": 30, "2024-5": 25, "2024-6": 33, "2024-7": 35}', '{"2024-1": 1, "2024-2": 3, "2024-3": 1, "2024-4": 2, "2024-5": 2, "2024-6": 1, "2024-7": 4}'),
	('F05', '好农场', 'C02', '大白菜', 123, '2024-06-03', 'U2988270', '2024-06-15', 2, '河南', 108, 30, 2, '{"2024-1": 3, "2024-2": 4, "2024-3": 9, "2024-4": 19, "2024-5": 25, "2024-6": 22, "2024-7": 30}', '{"2024-1": 3, "2024-2": 1, "2024-3": 2, "2024-4": 1, "2024-5": 3, "2024-6": 5, "2024-7": 4}'),
	('F06', '很好', 'C02', '白菜', 200, '2024-06-18', 'U3559363', '2024-06-18', 2, '鲁A济南车', 125, 40, 2, '{"2024-1": 9, "2024-2": 20, "2024-3": 19, "2024-4": 20, "2024-5": 23, "2024-6": 9, "2024-7": 30}', '{"2024-1": 2, "2024-2": 3, "2024-3": 1, "2024-4": 2, "2024-5": 5, "2024-6": 5, "2024-7": 4}');

-- 导出  表 se.feed 结构
CREATE TABLE IF NOT EXISTS `feed` (
  `f_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作编号',
  `p_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '养殖场编号',
  `f_time` date NOT NULL COMMENT '喂食时间',
  PRIMARY KEY (`f_id`) USING BTREE,
  KEY `FK_feed_pasture` (`p_id`),
  CONSTRAINT `FK_feed_pasture` FOREIGN KEY (`p_id`) REFERENCES `pasture` (`p_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.feed 的数据：~4 rows (大约)
REPLACE INTO `feed` (`f_id`, `p_id`, `f_time`) VALUES
	('F01', 'P01', '2024-05-25'),
	('F02', 'P02', '2024-06-02'),
	('F03', 'P05', '2024-06-02'),
	('F04', 'P01', '2024-06-07'),
	('F05', 'P03', '2024-06-04');

-- 导出  表 se.fertile 结构
CREATE TABLE IF NOT EXISTS `fertile` (
  `f_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作编号',
  `farm_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '农场编号',
  `op_type` int NOT NULL COMMENT '0为灌溉操作，1为施肥操作',
  `op_time` date NOT NULL COMMENT '灌溉时间或施肥时间',
  PRIMARY KEY (`f_id`) USING BTREE,
  KEY `FK_fertile_farm` (`farm_id`),
  CONSTRAINT `FK_fertile_farm` FOREIGN KEY (`farm_id`) REFERENCES `farm` (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.fertile 的数据：~5 rows (大约)
REPLACE INTO `fertile` (`f_id`, `farm_id`, `op_type`, `op_time`) VALUES
	('F01', 'F02', 0, '2024-05-25'),
	('F02', 'F04', 0, '2024-06-07'),
	('F03', 'F03', 1, '2024-06-03'),
	('F04', 'F05', 1, '2024-06-03'),
	('F05', 'F04', 1, '2024-06-02');

-- 导出  表 se.goods 结构
CREATE TABLE IF NOT EXISTS `goods` (
  `g_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品号',
  `g_name` char(50) NOT NULL COMMENT '商品名',
  `stock` int NOT NULL DEFAULT (0) COMMENT '库存余量，初始默认为0，不删该商品',
  `sale` int NOT NULL DEFAULT (0) COMMENT '销量=余量+批次表里的该商品的数量',
  `price` double NOT NULL DEFAULT '0' COMMENT '价格',
  `score` float DEFAULT '0' COMMENT '平均评分，使用float类型便于保留一位小数',
  `rater_num` int DEFAULT (0) COMMENT '评分人数，触发器修改',
  PRIMARY KEY (`g_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.goods 的数据：~9 rows (大约)
REPLACE INTO `goods` (`g_id`, `g_name`, `stock`, `sale`, `price`, `score`, `rater_num`) VALUES
	('C01', '大葱', 19, 119, 20, 3.6, 6),
	('C02', '白菜', -180, 230, 10, 4.68654, 104),
	('C03', '土豆', 247, 827, 43, 4.8791, 134),
	('C04', '茄子', 101, 2434, 32, 4.6, 211),
	('P01', '白鸡', -6, 132, 10, 3.5625, 8),
	('P02', '鸭子', 23, 129, 6, 3, 4),
	('P03', '牛肉', 4, 26, 40, 6.57143, 7),
	('P04', '羊', 10, 60, 40, 1.83333, 6),
	('P05', '鱼', 50, 0, 10, 0, 0);

-- 导出  表 se.leave 结构
CREATE TABLE IF NOT EXISTS `leave` (
  `e_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `start_date` date DEFAULT NULL,
  `back_date` date DEFAULT NULL,
  `is_dealed` int DEFAULT '0',
  KEY `e_id` (`e_id`),
  CONSTRAINT `FK_leave_employee` FOREIGN KEY (`e_id`) REFERENCES `employee` (`e_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='请假表';

-- 正在导出表  se.leave 的数据：~0 rows (大约)
REPLACE INTO `leave` (`e_id`, `start_date`, `back_date`, `is_dealed`) VALUES
	('U2270119', '2024-06-28', '2024-06-30', 1),
	('U5430317', '2024-06-23', '2024-07-02', 0),
	('U2379633', '2024-06-19', '2024-06-20', 0),
	('U2270119', '2024-06-19', '2024-06-21', 0);

-- 导出  表 se.order 结构
CREATE TABLE IF NOT EXISTS `order` (
  `o_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单号',
  `customer_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '顾客账号',
  `g_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品号',
  `g_name` char(50) NOT NULL COMMENT '商品名称',
  `amount` int NOT NULL COMMENT '购买数量',
  `score` int DEFAULT NULL COMMENT '评分',
  `e_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT (0) COMMENT '负责员工',
  `price` double DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`o_id`) USING BTREE,
  KEY `FK_order_users` (`customer_id`),
  KEY `FK_order_goods` (`g_id`),
  CONSTRAINT `FK_order_goods` FOREIGN KEY (`g_id`) REFERENCES `goods` (`g_id`),
  CONSTRAINT `FK_order_users` FOREIGN KEY (`customer_id`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.order 的数据：~9 rows (大约)
REPLACE INTO `order` (`o_id`, `customer_id`, `g_id`, `g_name`, `amount`, `score`, `e_id`, `price`) VALUES
	('O01', 'U3365824', 'C03', '土豆', 22, 5, 'U2270119', 43),
	('O02', 'U3875211', 'C01', '大葱', 23, 4, 'U2270119', 20),
	('O03', 'U3875211', 'C03', '土豆', 212, 4, 'U2270119', 43),
	('O04', 'U3559363', 'P03', '牛肉', 2, 4, 'U5430317', 40),
	('O05', 'U3949267', 'C03', '土豆', 34, 5, 'U2787669', 43),
	('O06', 'U3365824', 'P02', '鸭子', 43, 3, 'U5278444', 6),
	('O07', 'U3295433', 'P01', '白鸡', 44, 5, 'U5504479', 10),
	('O08', 'U3493396', 'P04', '羊肉', 20, 4, 'U2270119', 40),
	('O10', 'U3946853', 'P03', '鱼', 20, 10, 'U5430317', 10);

-- 导出  表 se.pasture 结构
CREATE TABLE IF NOT EXISTS `pasture` (
  `p_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '养殖场编号',
  `p_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '养殖场名称',
  `poultry_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '家禽编号',
  `poultry_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '家禽名称',
  `poultry_num` int NOT NULL DEFAULT '0' COMMENT '家禽数量',
  `start_time` date NOT NULL COMMENT '入栏时间',
  `w_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '负责职工号',
  `mature_time` date DEFAULT NULL COMMENT '成熟时间',
  `stage` int DEFAULT NULL COMMENT '成长阶段',
  `pos_info` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '位置备注',
  PRIMARY KEY (`p_id`) USING BTREE,
  KEY `FK_pasture_poultry` (`poultry_id`),
  KEY `FK_pasture_user` (`w_id`),
  CONSTRAINT `FK_pasture_poultry` FOREIGN KEY (`poultry_id`) REFERENCES `poultry` (`p_id`),
  CONSTRAINT `FK_pasture_user` FOREIGN KEY (`w_id`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.pasture 的数据：~5 rows (大约)
REPLACE INTO `pasture` (`p_id`, `p_name`, `poultry_id`, `poultry_name`, `poultry_num`, `start_time`, `w_id`, `mature_time`, `stage`, `pos_info`) VALUES
	('P01', '养鸡场', 'P01', '鸡', 16, '2024-05-25', 'U5504479', '2024-06-02', 1, '山西省朔州市应县'),
	('P02', '养鸭场', 'P02', '鸭', 32, '2024-05-25', 'U5278444', '2024-05-30', 1, '四川省黎川县'),
	('P03', '渔场', 'P05', '鱼', 25, '2024-05-25', 'U5430317', '2024-05-27', 1, '四川省泸州市叙永县'),
	('P04', '养牛场', 'P03', '牛', 12, '2024-05-25', 'U5278444', '2024-05-31', 1, '湖南省株洲市芦淞区里'),
	('P05', '羊场', 'P04', '羊', 14, '2024-05-25', 'U5574819', '2024-05-26', 1, '河南省郑州市惠济区园河东路');

-- 导出  表 se.poultry 结构
CREATE TABLE IF NOT EXISTS `poultry` (
  `p_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '家禽编号',
  `p_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '家禽名称',
  `p_info` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '家禽信息',
  `feed_interval` int DEFAULT NULL COMMENT '喂食间隔时间',
  PRIMARY KEY (`p_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.poultry 的数据：~6 rows (大约)
REPLACE INTO `poultry` (`p_id`, `p_name`, `p_info`, `feed_interval`) VALUES
	('P01', '鸡', '一种鸡', 5),
	('P02', '鸭', '鸭子', 10),
	('P03', '牛', '好吃', 5),
	('P04', '羊', '咩', 3),
	('P05', '鱼', '很好吃', 3),
	('P06', '奶牛', '哞', 3);

-- 导出  表 se.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `u_id` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `pwd` blob NOT NULL COMMENT '密码',
  `id_type` int NOT NULL COMMENT '身份类型',
  `name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '姓名',
  `gender` int DEFAULT NULL COMMENT '性别',
  `age` int DEFAULT NULL COMMENT '年龄',
  `tel` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  se.user 的数据：~22 rows (大约)
REPLACE INTO `user` (`u_id`, `pwd`, `id_type`, `name`, `gender`, `age`, `tel`) VALUES
	('U0128311', _binary 0xfcf5ad14dd5b640464f12864c298eed7, 0, '危素珊', 1, 28, '16800471240'),
	('U0435012', _binary 0x71cf03c88eceb8b7ed025b3d3f3458f8, 0, '史众旭', 1, 30, '18742660408'),
	('U1089205', _binary 0xe0e0a5caab539143f94cafef9b52369a, 1, '刀枝', 0, 27, '19764282389'),
	('U1256977', _binary 0xdbbafe3d7e535a36a0659584eec254d1, 1, '童洪彬', 0, 40, '18007012712'),
	('U2270119', _binary 0x99c22485546a7badd4d3fd732ebacfca, 2, '邰元', 1, 38, '14712268387'),
	('U2379633', _binary 0xdb638003e97c6b56c38ac9560b61518b, 2, '夏侯贞', 0, 19, '16133166846'),
	('U2787669', _binary 0x9cda9650cf7139393e8a7623004f39ec, 2, '孟剑', 1, 30, '17311655583'),
	('U2988270', _binary 0x63206154924264dbd52fd8c07af2110b, 2, '赫嘉', 0, 38, '14728770657'),
	('U3219216', _binary 0x5ca4ffaabcc296d1d7e2e73a9e430ce6, 3, '池佳一', 1, 64, '14799966768'),
	('U3295433', _binary 0x77dde9cd90283affb0616f9dc8456ffe, 2, '那洪东', 0, 28, '15428784294'),
	('U3365824', _binary 0x3263804051a017a4e76dba58b39e9efe, 3, '庄雪子', 1, 54, '18014284486'),
	('U3493396', _binary 0xac6871d67aba67427cee3f149fe21970, 3, '平睿宙', 0, 55, '16841732033'),
	('U3559363', _binary 0xc79b5a431db6d62428fd3264796120c5, 3, '封荔锦', 1, 29, '13200463406'),
	('U3871667', _binary 0xd56a280a06d3241aeb143a296d19ce2a, 3, '尹丞安', 1, 30, '18474659228'),
	('U3875211', _binary 0xc8838e4dc9db8b66e0759e2d7773e7d4, 3, '崔家昊', 0, 30, '15560245831'),
	('U3946853', _binary 0xb2d575de8228707acf49cec28d347369, 3, '仝和', 1, 38, '17233763386'),
	('U3949267', _binary 0xfc24078882be433e7ee24f90c24ee7b7, 3, '赫厚旻', 1, 54, '13633146035'),
	('U4691729', _binary 0xad326bca54597a2a8e1bd7896de7e18c, 1, '桂燕', 0, 44, '19840825329'),
	('U5278444', _binary 0xc4424a836ae0f686faae246652643957, 2, '卓君轩', 1, 38, '19565043473'),
	('U5430317', _binary 0xe3b58e8e0adddd4e9c68a7236830cb84, 2, '狄薇泓', 0, 37, '18525156302'),
	('U5504479', _binary 0x18e4ded10b89f0bd3170ec3639064ff9, 2, '谌玄', 1, 50, '19614360721'),
	('U5574819', _binary 0xbf681f3f8d691c1b840df70f571400b8, 2, '端木秋会', 1, 25, '14754953376');

-- 导出  触发器 se.batch_after_update 结构
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `batch_after_update` AFTER UPDATE ON `batch` FOR EACH ROW BEGIN
	IF NEW.is_dealed = 1 AND OLD.is_dealed = 0 THEN
		UPDATE goods 
		SET stock = stock + NEW.amount
		WHERE g_id = NEW.g_id;
	END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 导出  触发器 se.clock_update_salary 结构
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `clock_update_salary` AFTER UPDATE ON `clock` FOR EACH ROW BEGIN
    DECLARE hour_diff DECIMAL(10, 2);
    DECLARE current_salary DECIMAL(10, 2) DEFAULT 0;
    DECLARE new_salary DECIMAL(10, 2);

    -- 检查 leave 字段是否被更新
    IF OLD.leave <> NEW.leave THEN
        -- 计算 come 和 leave 之间的小时差
        SET hour_diff = TIMESTAMPDIFF(HOUR, NEW.come, NEW.leave);

        -- 从 employee 表中读取当前的 salary 值
        SELECT salary INTO current_salary
        FROM employee
        WHERE e_id = NEW.e_id;

        -- 计算新的 salary 值，增加小时数乘以 30
        SET new_salary = IFNULL(current_salary, 0) + hour_diff * 30;

        -- 更新 employee 表中的 salary 字段
        UPDATE employee
        SET salary = new_salary
        WHERE e_id = NEW.e_id;
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 导出  触发器 se.employee_update_is_got 结构
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `employee_update_is_got` BEFORE UPDATE ON `employee` FOR EACH ROW BEGIN
    -- 检查旧的 salary 是否为 0，并且新的 salary 是否不等于 0
    IF OLD.salary = 0 AND NEW.salary <> 0 THEN
        -- 更新 is_got 字段为 1
        SET NEW.is_got = 0;
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 导出  触发器 se.employee_update_salary 结构
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `employee_update_salary` BEFORE UPDATE ON `employee` FOR EACH ROW BEGIN
    -- 检查旧的 salary 是否为 0，并且新的 salary 是否不等于 0
    IF NEW.is_got = 1 AND OLD.is_got = 0 THEN
        -- 更新 is_got 字段为 1
        SET NEW.salary = 0;
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 导出  触发器 se.farm_after_insert 结构
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `farm_after_insert` AFTER INSERT ON `farm` FOR EACH ROW BEGIN
    -- 检查 goods 表中是否存在 g_id = NEW.c_id 的记录
    IF NOT EXISTS (SELECT 1 FROM goods WHERE g_id = NEW.c_id) THEN
        -- 在 goods 表中插入新的记录
        INSERT INTO goods (g_id, g_name)
        VALUES (NEW.c_id, NEW.c_name);
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 导出  触发器 se.leave_update_salary 结构
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `leave_update_salary` AFTER UPDATE ON `leave` FOR EACH ROW BEGIN
    DECLARE current_salary DECIMAL(10, 2);
    DECLARE new_salary DECIMAL(10, 2);
    DECLARE day_diff INT;

    -- 检查 is_dealed 字段是否从 0 变为 1
    IF OLD.is_dealed = 0 AND NEW.is_dealed = 1 THEN
    
    	  SET day_diff = DATEDIFF(NEW.back_date, NEW.start_date);
        -- 从 employee 表中读取当前的 salary 值
        SELECT salary INTO current_salary
        FROM employee
        WHERE e_id = NEW.e_id;

        -- 计算新的 salary 值，减少 200
        SET new_salary = IFNULL(current_salary, 0) - day_diff*200;

        -- 更新 employee 表中的 salary 字段
        UPDATE employee
        SET salary = new_salary
        WHERE e_id = NEW.e_id;
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 导出  触发器 se.order_after_insert 结构
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `order_after_insert` AFTER INSERT ON `order` FOR EACH ROW BEGIN
  UPDATE goods 
  SET stock = stock - NEW.amount, sale = sale + NEW.amount
  WHERE g_id = NEW.g_id;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 导出  触发器 se.order_after_update 结构
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `order_after_update` AFTER UPDATE ON `order` FOR EACH ROW BEGIN
  DECLARE new_score DOUBLE;
  DECLARE current_rater_num INT;
  DECLARE current_score DOUBLE;

  SELECT score, rater_num INTO current_score, current_rater_num 
  FROM goods WHERE g_id = NEW.g_id;

  SET new_score = (current_score * current_rater_num + NEW.score) / (current_rater_num + 1);

  UPDATE goods 
  SET score = new_score, rater_num = rater_num + 1
  WHERE g_id = NEW.g_id;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 导出  触发器 se.order_update_performance 结构
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `order_update_performance` AFTER UPDATE ON `order` FOR EACH ROW BEGIN
    DECLARE current_value DECIMAL(10, 2) DEFAULT 0;
    DECLARE new_value DECIMAL(10, 2);
    
    DECLARE current_salary DECIMAL(10, 2) DEFAULT 0;
    DECLARE new_salary DECIMAL(10, 2);

    -- 从employee表中读取当前的performance值
    SELECT JSON_UNQUOTE(JSON_EXTRACT(performance, '$."2024-6"'))
    INTO current_value
    FROM employee
    WHERE e_id = NEW.e_id;

    -- 计算新的performance值
    SET new_value = current_value + NEW.amount * NEW.score * NEW.price * 0.2;

    -- 更新employee表中的performance字段
    UPDATE employee
    SET performance = JSON_SET(performance, '$."2024-6"', new_value)
    WHERE e_id = NEW.e_id;

    
    -- 从 employee 表中读取当前的 salary 值
    SELECT salary INTO current_salary
    FROM employee
    WHERE e_id = NEW.e_id;

    -- 计算新的 salary 值
    SET new_salary = IFNULL(current_salary, 0) + new_value;

    -- 更新 employee 表中的 salary 字段
    UPDATE employee
    SET salary = new_salary
    WHERE e_id = NEW.e_id;
    
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 导出  触发器 se.pasture_after_insert 结构
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `pasture_after_insert` AFTER INSERT ON `pasture` FOR EACH ROW BEGIN
    -- 检查 goods 表中是否存在 g_id = NEW.c_id 的记录
    IF NOT EXISTS (SELECT 1 FROM goods WHERE g_id = NEW.p_id) THEN
        -- 在 goods 表中插入新的记录
        INSERT INTO goods (g_id, g_name)
        VALUES (NEW.poultry_id, NEW.poultry_name);
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
