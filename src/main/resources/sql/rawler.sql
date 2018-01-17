/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.6.38 : Database - cctread
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cctread` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `cctread`;

/*Table structure for table `cct_rawler_task` */

DROP TABLE IF EXISTS `cct_rawler_task`;

CREATE TABLE `cct_rawler_task` (
  `id` INT(8) NOT NULL AUTO_INCREMENT COMMENT '爬虫任务id',
  `book_name` VARCHAR(50) DEFAULT NULL COMMENT '书名',
  `author` VARCHAR(50) DEFAULT NULL COMMENT '作者',
  `start_chapter` VARCHAR(10) DEFAULT NULL COMMENT '开始章节',
  `end_chapter` VARCHAR(10) DEFAULT NULL COMMENT '结束章节',
  `status` INT(1) NOT NULL DEFAULT '0' COMMENT '任务状态(0=未执行1=已执行)',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  `del_flag` INT(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0正常,1删除）',
  `version` INT(10) DEFAULT '1' COMMENT '版本号',
  `create_date` DATETIME DEFAULT NULL COMMENT '记录新增时间',
  `update_date` DATETIME DEFAULT NULL COMMENT '记录修改时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
