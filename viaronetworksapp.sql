/*
  Author: Kevin Lorenzo ( Software Developer )
*/

USE mysql;

DROP DATABASE viaronetworksapp;

CREATE DATABASE viaronetworksapp;

USE viaronetworksapp;

CREATE TABLE Alumno (
  id INTEGER AUTO_INCREMENT,
  nombre VARCHAR(50),
  apellidos VARCHAR(50),
  genero CHAR(1),
  fechaNacimiento DATETIME,
  PRIMARY KEY (id)
);

CREATE TABLE Profesor (
  id INTEGER AUTO_INCREMENT,
  nombre VARCHAR(50),
  apellidos VARCHAR(50),
  genero CHAR(1),
  PRIMARY KEY (id)
);

CREATE TABLE Grado (
  id INTEGER AUTO_INCREMENT,
  nombre VARCHAR(50),
  profesorId INTEGER,
  PRIMARY KEY (id),
  FOREIGN KEY(profesorId) REFERENCES Profesor(id)
);

CREATE TABLE AlumnoGrado (
  id INTEGER AUTO_INCREMENT,
  alumnoId INTEGER,
  gradoId INTEGER,
  seccion VARCHAR(10),
  PRIMARY KEY (id),
  FOREIGN KEY(alumnoId) REFERENCES Alumno(id),
  FOREIGN KEY(gradoId) REFERENCES Grado(id)
);
