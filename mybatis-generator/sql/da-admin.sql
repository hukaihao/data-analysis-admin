
-- Table structure for table `da_admin`
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
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id_da_admin`),
  UNIQUE KEY `inx_user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='da系统管理员用户表'