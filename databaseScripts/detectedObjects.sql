CREATE TABLE `detected_object` (
    `id` serial,
    `name` varchar(100) NOT NULL,
    `image_id` bigint unsigned NOT NULL,
     KEY `label_FK` (`image_id`),
     KEY `detected_object_name_IDX` (`name`),
     CONSTRAINT `label_FK` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);