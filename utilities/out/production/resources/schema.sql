
CREATE DATABASE IF NOT EXISTS gamestart;

USE gamestart;

CREATE TABLE PRICELIST (
    PriceId 	INT(5) PRIMARY KEY,
    Price	    float(4,2));

CREATE TABLE GENDER (
    GenderId 	INT(5) PRIMARY KEY,
    Description	VARCHAR(15) );

CREATE TABLE CONSOLES (
    ConsoleId	INT(5) PRIMARY KEY,
    Name 	VARCHAR(20) );

CREATE TABLE PRODUCTS (
    ProductId 	INT(5) PRIMARY KEY,
    Name VARCHAR(35),
    StockQuantity INT(5),
    PriceId INT(5),
    FOREIGN KEY (PriceId) REFERENCES PRICELIST(PriceId));

CREATE TABLE MEMBERS (
    DNI CHAR (10) PRIMARY KEY,
    Name VARCHAR(20),
    Mail VARCHAR(30),
    PhoneNumber CHAR (9));

CREATE TABLE VIDEOGAMES (
    GameId INT(5) PRIMARY KEY,
    Title VARCHAR(30),
    GenderId INT(5),
    ConsoleId INT(5),
    StockQuantity INT(5),
    PriceId INT(5),
    FOREIGN KEY (GenderId) REFERENCES GENDER(GenderId),
    FOREIGN KEY (ConsoleId) REFERENCES CONSOLES(ConsoleId),
    FOREIGN KEY (PriceId) REFERENCES PRICELIST(PriceId));

