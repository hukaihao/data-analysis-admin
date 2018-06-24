SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS  `da_product`;
CREATE TABLE `da_product` (
  `id_da_product` char(32) NOT NULL COMMENT '主键',
  `product_code` char(10) NOT NULL COMMENT '产品代码',
  `product_name` varchar(255) NOT NULL COMMENT '产品名称',
  `product_abbr` varchar(90) DEFAULT NULL COMMENT '产品简称',
  `product_des` text COMMENT '产品描述',
  `product_type` char(2) NOT NULL COMMENT '产品类型',
  `product_channel` varchar(255) NOT NULL COMMENT '产品来源',
  `status` char(1) NOT NULL DEFAULT 'Y' COMMENT '状态',
  PRIMARY KEY (`id_da_product`),
  UNIQUE KEY `inx_pm_ida` (`id_da_product`) USING BTREE,
  UNIQUE KEY `inx_pro_code` (`product_code`) USING BTREE,
  KEY `fk_dpt_pro_type` (`product_type`),
  CONSTRAINT `fk_dpt_pro_type` FOREIGN KEY (`product_type`) REFERENCES `da_product_type` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';

DROP TABLE IF EXISTS  `da_every_color_data`;
CREATE TABLE `da_every_color_data` (
  `id_every_color_data` char(32) NOT NULL COMMENT '主键',
  `product_code` char(10) NOT NULL COMMENT '产品代码',
  `lottery_period` varchar(32) DEFAULT NULL COMMENT '开奖期数',
  `lottery_date` datetime DEFAULT NULL COMMENT '开奖日期',
  `wan_digit` tinyint(4) DEFAULT NULL COMMENT '万位',
  `qian_digit` tinyint(4) DEFAULT NULL COMMENT '千位',
  `bai_digit` tinyint(4) DEFAULT NULL COMMENT '百位',
  `shi_digit` tinyint(4) DEFAULT NULL COMMENT '十位',
  `ge_digit` tinyint(4) DEFAULT NULL COMMENT '个位',
  `big_deal` char(1) DEFAULT NULL COMMENT '大小结果',
  `vingt_etun` char(1) DEFAULT NULL COMMENT '龙虎和结果',
  `parity` char(1) DEFAULT NULL COMMENT '奇偶结果',
  PRIMARY KEY (`id_every_color_data`),
  UNIQUE KEY `inx_decd_pm_iecd` (`id_every_color_data`),
  KEY `inx_decd_pro_code` (`product_code`) USING BTREE,
  KEY `inx_decd_lot_num` (`lottery_period`),
  KEY `inx_decd_lot_date` (`lottery_date`),
  CONSTRAINT `fk_dp_pro_code` FOREIGN KEY (`product_code`) REFERENCES `da_product` (`product_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='时时彩每期数据和结果';

DROP TABLE IF EXISTS  `da_admin`;
CREATE TABLE `da_admin` (
  `id_da_admin` varchar(32) NOT NULL COMMENT '主键',
  `user_name` varchar(60) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(60) NOT NULL DEFAULT '' COMMENT '密码',
  `last_login_ip` varchar(60) DEFAULT NULL COMMENT '上次登录IP',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `update_time` datetime DEFAULT NULL COMMENT '用户信息更新时间',
  `avatar` varchar(255) DEFAULT '' COMMENT '管理员头像',
  `registration_time` datetime DEFAULT NULL COMMENT '注册时间',
  `invalid_time` datetime DEFAULT NULL COMMENT '无效时间',
  `status` varchar(1) NOT NULL DEFAULT 'Y' COMMENT '状态',
  PRIMARY KEY (`id_da_admin`),
  UNIQUE KEY `inx_user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='da系统管理员用户表';

DROP TABLE IF EXISTS  `da_product_type`;
CREATE TABLE `da_product_type` (
  `code` char(2) NOT NULL COMMENT '代码',
  `full_name` varchar(255) NOT NULL COMMENT '全称',
  `abbr_name` varchar(90) NOT NULL COMMENT '简称',
  `status` char(1) NOT NULL DEFAULT 'Y' COMMENT '状态',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品类型表';

DROP TABLE IF EXISTS  `learn_user`;
CREATE TABLE `learn_user` (
  `id_learn_user` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `id_type` varchar(2) DEFAULT NULL COMMENT '证件类型',
  `id_no` varchar(32) DEFAULT NULL COMMENT '证件号码',
  `birthday` date DEFAULT NULL COMMENT '出生年月',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `phone_no` varchar(20) DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`id_learn_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;

