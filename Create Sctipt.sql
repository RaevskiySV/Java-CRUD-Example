DROP TABLE IF EXISTS users, employees, animals;

CREATE TABLE IF NOT EXISTS users (
	id serial PRIMARY KEY,
	login VARCHAR (50) UNIQUE NOT NULL,
	password VARCHAR (50) NOT NULL
);

CREATE TABLE IF NOT EXISTS employees (
	id serial PRIMARY KEY,
	name VARCHAR (50) NOT NULL,
	age INT NOT NULL,
	experience INT NOT NULL
);

CREATE TABLE IF NOT EXISTS animals (
	id serial PRIMARY KEY,
	name VARCHAR (50) NOT NULL,
	species VARCHAR (50) NOT NULL,
	id_employee INT NOT NULL,
	FOREIGN KEY (id_employee) REFERENCES employees(id) ON DELETE CASCADE
);