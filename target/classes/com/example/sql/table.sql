DROP DATABASE IF EXISTS `demo-llm-db`;
CREATE DATABASE IF NOT EXISTS `demo-llm-db`;
USE `demo-llm-db`;

----------------------------------------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS `course`;
CREATE TABLE IF NOT EXISTS `course`(
    `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '学科名称',
    `edu` int NOT NULL DEFAULT '0' COMMENT '学历背景要求：0-无，1-初中，2-高中，3-大专，4-本科及以上',
    `type` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '课程类型：编程、设计、自媒体、其他',
    `price` bigint NOT NULL DEFAULT '0' COMMENT '课程价格',
    `duration` int unsigned NOT NULL DEFAULT '0' COMMENT '学习时长，单位：天',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci COMMENT='学科表';

DELETE FROM `course`;
INSERT INTO `course`(`id`, `name`, `edu`, `type`, `price`, `duration`) VALUES
    (1, 'JavaEE', 4, '编程', 21999, 108),
    (2, '鸿蒙应用开发', 3, '编程', 20999, 98),
    (3, 'AI人工智能', 4, '编程', 24999, 100),
    (4, 'python大数据开发', 4, '编程', 23999, 102),
    (5, '跨境电商', 0, '自媒体', 12999, 68),
    (6, '新媒体运营', 0, '自媒体', 10999, 61),
    (7, 'UI设计', 2, '设计', 11999, 66);

----------------------------------------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS `course_reservation`;
CREATE TABLE IF NOT EXISTS `course_reservation`(
    `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `course` varchar(50) NOT NULL DEFAULT '' COMMENT '预约课程',
    `student_name` varchar(50) NOT NULL DEFAULT '' COMMENT '学生姓名',
    `contact_info` varchar(255) NOT NULL DEFAULT '' COMMENT '联系方式',
    `school` varchar(50) NOT NULL DEFAULT '' COMMENT '预约校区',
    `remark` text NOT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci COMMENT='课程预约表';

DELETE FROM `course_reservation`;
INSERT INTO `course_reservation` (`id`, `course`, `student_name`, `contact_info`, `school`, `remark`) VALUES
    (1, '新媒体运营', '李华', '12899762348', '广东校区', '安排一个好一点的老师');

----------------------------------------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS `school`;
CREATE TABLE IF NOT EXISTS `school`(
   `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
   `name` varchar(50) DEFAULT NULL COMMENT '校区名称',
   `city` varchar(50) DEFAULT NULL COMMENT '校区所在城市',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci COMMENT='校区表';

DELETE FROM `school`;
INSERT INTO `school` (`id`, `name`, `city`) VALUES
    (1, '昌平校区', '北京'),
    (2, '顺义校区', '北京'),
    (3, '杭州校区', '杭州'),
    (4, '上海校区', '上海'),
    (5, '南京校区', '南京'),
    (6, '西安校区', '西安'),
    (7, '郑州校区', '郑州'),
    (8, '广东校区', '广东'),
    (9, '深圳校区', '深圳');

























