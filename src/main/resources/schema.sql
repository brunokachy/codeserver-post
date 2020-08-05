CREATE TABLE `sdlc_system` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `base_url` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_date` datetime NOT NULL,
  PRIMARY KEY (`ID`)
);

CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `external_id` varchar(255) NOT NULL,
  `sdlc_system_id` int(11) NOT NULL,
  `name` varchar(255),
  `created_date` datetime NOT NULL,
  `last_modified_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `system_project` (`sdlc_system_id`,`external_id`),
  CONSTRAINT `project_sdlc_system_fk` FOREIGN KEY (`sdlc_system_id`) REFERENCES `sdlc_system` (`id`)
);
