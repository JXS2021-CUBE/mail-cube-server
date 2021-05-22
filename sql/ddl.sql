# Create DB 
CREATE DATABASE mail_cube default CHARACTER SET UTF8;


# Create Employee table
CREATE TABLE Employee(
    id int AUTO_INCREMENT,
    email varchar(50) NOT NULL UNIQUE,
    department varchar(50) NOT NULL,
    name varchar(50) NOT NULL,

    PRIMARY KEY (id)
);

# Create Template table
CREATE TABLE Template(
    id int AUTO_INCREMENT,
	title varchar(50) NOT NULL,
    content varchar(3000) NOT NULL,

    PRIMARY KEY (id)
);

# Create Applicant table
CREATE TABLE Applicant(
    id int AUTO_INCREMENT,
    name varchar(20) NOT NULL,
    email varchar(30) NOT NULL,
    phone varchar(20) NOT NULL,
    status varchar(20) NOT NULL DEFAULT 'APPLIED',
    recruitment_id int NOT NULL,
    
    PRIMARY KEY (id),
    FOREIGN KEY (recruitment_id) REFERENCES Recruitment(id) ON UPDATE CASCADE
);


# Create Recruitment table
CREATE TABLE Recruitment(
    id int AUTO_INCREMENT,
    title varchar(30) NOT NULL,
    department varchar(20) NOT NULL,
    recruite_section varchar(20)  NOT NULL,

    PRIMARY KEY (id)
);

# Create Sending table
CREATE TABLE Sending(
    id int AUTO_INCREMENT,
    employee_id int NOT NULL,
    template_id int NOT NULL,
    applicant_id int NOT NULL,
    sender varchar(50)  NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (employee_id) REFERENCES Employee(id) ON UPDATE CASCADE,
    FOREIGN KEY (template_id) REFERENCES Template(id) ON UPDATE CASCADE,
    FOREIGN KEY (applicant_id) REFERENCES Applicant(id) ON UPDATE CASCADE
);