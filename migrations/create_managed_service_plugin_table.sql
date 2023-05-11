USE morpheus;
CREATE TABLE `custom_managed_services_plugin` (
    `id` INT NOT NULL AUTO_INCREMENT COMMENT 'a record id',
    `instance_name` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'contains a reference for the instance name',
    `service_name` VARCHAR(255) NOT NULL COMMENT 'contains a managed service name',
    `service_description` VARCHAR(255) NOT NULL COMMENT 'contains a managed service description',
    `service_cost` DECIMAL(10,2) NOT NULL COMMENT 'contains the cost of the managed service',
    PRIMARY KEY (`id`)
);
