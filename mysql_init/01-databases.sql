# create databases
CREATE DATABASE IF NOT EXISTS `db_inventory`;
CREATE DATABASE IF NOT EXISTS `db_users`;

# create root user and grant rights
-- CREATE USER 'root'@'localhost' IDENTIFIED BY 'neo.123';
GRANT ALL PRIVILEGES ON `db_inventory`.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON `db_users`.* TO 'root'@'%';