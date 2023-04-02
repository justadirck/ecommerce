
use `ecommerceapp`;

set foreign_key_checks=0;
drop table if exists `order_item`;
drop table if exists `orders`;
drop table if exists `customer`;
drop table if exists `address`;
set foreign_key_checks=1;

create table `address` (
  `id` bigint not null auto_increment,
  `city` varchar(255) default null,
  `country` varchar(255) default null,
  `state` varchar(255) default null,
  `street` varchar(255) default null,
  `zip_code` varchar(255) default null,
  primary key (`id`)
) engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

create table `customer` (
  `id` bigint not null auto_increment,
  `first_name` varchar(255) default null,
  `last_name` varchar(255) default null,
  `email` varchar(255) default null,
  primary key (`id`)
) engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

create table `orders` (
  `id` bigint not null auto_increment,
  `order_tracking_number` varchar(255) default null,
  `total_price` decimal(19,2) default null,
  `total_quantity` int default null,
  `billing_address_id` bigint default null,
  `customer_id` bigint default null,
  `shipping_address_id` bigint default null,
  `status` varchar(128) default null,
  `date_created` datetime(6) default null,
  `last_updated` datetime(6) default null,
  primary key (`id`),
  unique key `uk_billing_address_id` (`billing_address_id`),
  unique key `uk_shipping_address_id` (`shipping_address_id`),
  key `k_customer_id` (`customer_id`),
  constraint `fk_customer_id` foreign key (`customer_id`) references `customer` (`id`),
  constraint `fk_billing_address_id` foreign key (`billing_address_id`) references `address` (`id`),
  constraint `fk_shipping_address_id` foreign key (`shipping_address_id`) references `address` (`id`)
) engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

create table `order_item` (
  `id` bigint not null auto_increment,
  `image_url` varchar(255) default null,
  `quantity` int default null,
  `unit_price` decimal(19,2) default null,
  `order_id` bigint default null,
  `product_id` bigint default null,
  primary key (`id`),
  key `k_order_id` (`order_id`),
  constraint `fk_order_id` foreign key (`order_id`) references `orders` (`id`),
  constraint `fk_product_id` foreign key (`product_id`) references `product` (`id`)
) engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;
