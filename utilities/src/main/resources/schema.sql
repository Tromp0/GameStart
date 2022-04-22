CREATE DATABASE IF NOT EXISTS gamestart;

USE gamestart;

CREATE TABLE LISTAPRECIO (
    IdPrecio 	INT(5) PRIMARY KEY,
    Precio	    float(7));

CREATE TABLE GENERO (
    IdGenero 	INT(5) PRIMARY KEY,
    descripcion	VARCHAR(15) );

CREATE TABLE CONSOLAS (
    IDConsola	INT(5) PRIMARY KEY,
                          Nombre 	VARCHAR(20) );

CREATE TABLE VIDEOJUEGOS (
    IDJuego INT(5) PRIMARY KEY,
    Titulo VARCHAR(15),
    IdGenero INT(5),
    IdConsola INT(5),
    CantEnStock INT(5),
    IdPrecio INT(5),
    FOREIGN KEY (IdGenero) REFERENCES GENERO(IdGenero),
    FOREIGN KEY (IdConsola) REFERENCES CONSOLAS(IdConsola),
    FOREIGN KEY (IdPrecio) REFERENCES LISTAPRECIO(IdPrecio));

CREATE TABLE PRODUCTOS (
    IdProducto 	INT(5) PRIMARY KEY,
    Nombre VARCHAR(20),
    CantEnStock INT(5),
    IdPrecio INT(5),
    FOREIGN KEY (IdPrecio) REFERENCES LISTAPRECIO(IdPrecio));

CREATE TABLE SOCIOS (
    DNI CHAR (10) PRIMARY KEY,
    Nombre VARCHAR(20),
    Correo VARCHAR(30),
    Telefono CHAR (9));

