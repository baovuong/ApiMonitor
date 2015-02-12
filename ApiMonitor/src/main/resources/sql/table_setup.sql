CREATE TABLE IF NOT EXISTS calls
(
id INT NOT NULL AUTO_INCREMENT,
date CHAR(30),
name CHAR(30),
ip_address CHAR(45),
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS locations
(
ip_address CHAR(45) NOT NULL,
city VARCHAR(30),
region VARCHAR(30),
country VARCHAR(30),
PRIMARY KEY(ip_address)
);