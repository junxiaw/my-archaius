CREATE TABLE IF NOT EXISTS `server_config` (
   `Id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
   `key` varchar(512) NOT NULL DEFAULT 'default' COMMENT '配置项Key',
   `value` varchar(255) NOT NULL DEFAULT 'default' COMMENT '配置项值',
   `micro_server` varchar(255) NOT NULL DEFAULT 'default' COMMENT '微服务名',
   `comment` varchar(255) DEFAULT '' COMMENT '属性说明',
   `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `last_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
   PRIMARY KEY (`Id`),
   UNIQUE KEY `micro_server_key_index` (`micro_server`,`key`),
   KEY `lastTime` (`last_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='熔断自定义配置';