CREATE DATABASE  IF NOT EXISTS `unileverDB`;
USE `unileverDB`;
-- Table structure for table `DEPARTENTS`
--
DROP TABLE IF EXISTS `DEPARTENTS`;
CREATE TABLE DEPARTENTS (
    dep_id int(11) NOT NULL AUTO_INCREMENT,
    dep_name CHAR(30) NOT NULL,
  PRIMARY KEY (dep_id)
); /*ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;*/
--
-- Dumping data for table `DEPARTENTS`
--
INSERT INTO `DEPARTENTS` VALUES (1,'CS'),(2,'IS'),(3,'IT');

/*---------------------------------------------------------------------------------------------------*/
DROP TABLE IF EXISTS `EMPLOYEES`;
CREATE TABLE EMPLOYEES (
  emp_id  int(11) NOT NULL AUTO_INCREMENT,
  emp_name varchar(255) NOT NULL,
  emp_age int(11),
  salary decimal(15,2),
  department_id int(11),
  PRIMARY KEY (emp_id),
  FOREIGN KEY (department_id) REFERENCES DEPARTENTS(dep_id) ON UPDATE RESTRICT ON DELETE CASCADE
);
INSERT INTO `EMPLOYEES` VALUES (1,'Samar',29,1000,1),(2,'Mohamed',28,2000,2),(3,'Ahmed',26,3000,1),(4,'Basanty',24,4000,3),(5,'Mahmoud',20,5000,2),(6,'Zainab',14,6000,3);


