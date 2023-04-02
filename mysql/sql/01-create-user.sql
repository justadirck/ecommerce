create user 'ecommerceapp'@'%' identified by 'ecommerceapp';

grant all privileges on *.* to 'ecommerceapp'@'%';

alter user 'ecommerceapp'@'%' identified with mysql_native_password by 'ecommerceapp';