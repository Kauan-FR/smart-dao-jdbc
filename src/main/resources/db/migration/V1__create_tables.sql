CREATE TABLE department (
    id SERIAL PRIMARY KEY,
    name VARCHAR(60) NOT NULL
);

CREATE TABLE seller (
    id SERIAL PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    birthdate DATE NOT NULL,
    basesalary DOUBLE PRECISION NOT NULL,
    departmentid INTEGER NOT NULL,
    FOREIGN KEY (departmentid) REFERENCES department (id)
);