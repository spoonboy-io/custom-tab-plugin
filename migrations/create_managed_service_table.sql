USE morpheus;
CREATE TABLE `custom_managed_services` (
    `id` INT NOT NULL AUTO_INCREMENT COMMENT 'a record id',
    `instance_name` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'contains a reference for the instance name',
    `service_name` VARCHAR(255) NOT NULL COMMENT 'contains a managed service name or description',
    `service_cost` DECIMAL NOT NULL COMMENT 'contains the costs of the managed service',
    PRIMARY KEY (`id`)
);
