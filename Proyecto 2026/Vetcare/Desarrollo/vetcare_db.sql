-- ============================================================
-- VetCare -- Base de datos MySQL
-- Ejecutar en MySQL Workbench o phpMyAdmin antes de correr la app
-- ============================================================

CREATE DATABASE IF NOT EXISTS vetcare_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_spanish_ci;

USE vetcare_db;

CREATE TABLE IF NOT EXISTS duenos (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    dni       VARCHAR(20)  NOT NULL UNIQUE,
    telefono  VARCHAR(20),
    correo    VARCHAR(100),
    direccion VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS veterinarios (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(100) NOT NULL,
    dni          VARCHAR(20)  NOT NULL,
    telefono     VARCHAR(20),
    correo       VARCHAR(100),
    especialidad VARCHAR(100),
    colegiatura  VARCHAR(50),
    activo       BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS mascotas (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    especie        VARCHAR(20)  NOT NULL,
    nombre         VARCHAR(100) NOT NULL,
    raza           VARCHAR(100),
    edad           INT,
    sexo           VARCHAR(10),
    peso           DOUBLE,
    dni_dueno      VARCHAR(20),
    observaciones  TEXT,
    atributo_extra VARCHAR(100),
    FOREIGN KEY (dni_dueno) REFERENCES duenos(dni)
);

CREATE TABLE IF NOT EXISTS citas (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    id_mascota          INT  NOT NULL,
    id_veterinario      INT  NOT NULL,
    fecha               VARCHAR(20) NOT NULL,
    hora                VARCHAR(10) NOT NULL,
    motivo              TEXT,
    estado              VARCHAR(20) DEFAULT 'PENDIENTE',
    motivo_cancelacion  TEXT,
    FOREIGN KEY (id_mascota)     REFERENCES mascotas(id),
    FOREIGN KEY (id_veterinario) REFERENCES veterinarios(id)
);

CREATE TABLE IF NOT EXISTS consultas (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    id_mascota    INT NOT NULL,
    fecha         VARCHAR(20) NOT NULL,
    diagnostico   TEXT NOT NULL,
    tratamiento   TEXT,
    observaciones TEXT,
    FOREIGN KEY (id_mascota) REFERENCES mascotas(id)
);
