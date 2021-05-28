# Create DB 
CREATE DATABASE mail_cube default CHARACTER SET UTF8;
use mail_cube;

# Create Employee table
CREATE TABLE employee(
    id int AUTO_INCREMENT,
    email varchar(50) NOT NULL UNIQUE,
    department varchar(50) NOT NULL,
    name varchar(50) NOT NULL,

    PRIMARY KEY (id)
);

# Create Template table
CREATE TABLE template(
    id int AUTO_INCREMENT,
	title varchar(50) NOT NULL,
    content varchar(3000) NOT NULL,

    PRIMARY KEY (id)
);

# Create Recruitment table
CREATE TABLE recruitment(
    id int AUTO_INCREMENT,
    title varchar(30) NOT NULL,
    department varchar(20) NOT NULL,
    recruite_section varchar(20)  NOT NULL,

    PRIMARY KEY (id)
);

# Create Applicant table
CREATE TABLE applicant(
    id int AUTO_INCREMENT,
    name varchar(20) NOT NULL,
    email varchar(30) NOT NULL,
    phone varchar(20) NOT NULL,
    status varchar(20) NOT NULL DEFAULT 'APPLIED',
    recruitment_id int NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (recruitment_id) REFERENCES recruitment(id) ON UPDATE CASCADE
);

# Create Sending table
CREATE TABLE sending(
    id int AUTO_INCREMENT,
    employee_id int NOT NULL,
    template_id int NOT NULL,
    applicant_id int NOT NULL,
    sender varchar(50)  NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (employee_id) REFERENCES employee(id) ON UPDATE CASCADE,
    FOREIGN KEY (template_id) REFERENCES template(id) ON UPDATE CASCADE,
    FOREIGN KEY (applicant_id) REFERENCES applicant(id) ON UPDATE CASCADE
);

# Create excelfile table
CREATE TABLE excelfile(
    id int AUTO_INCREMENT,
    s3_url VARCHAR(100) NOT NULL,
    datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

show tables;