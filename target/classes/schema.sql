-- Eliminar tablas existentes en orden inverso a las dependencias
DROP TABLE IF EXISTS reserva_extras;
DROP TABLE IF EXISTS hotel_servicios;
DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS habitaciones;
DROP TABLE IF EXISTS extras;
DROP TABLE IF EXISTS servicios;
DROP TABLE IF EXISTS hoteles;
DROP TABLE IF EXISTS usuarios;

-- Crear tablas en orden según dependencias
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(200) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    activo BOOLEAN DEFAULT true
);

CREATE TABLE hoteles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    pais VARCHAR(100) NOT NULL,
    estrellas INT NOT NULL,
    descripcion TEXT,
    activo BOOLEAN DEFAULT true
);

CREATE TABLE habitaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hotel_id BIGINT NOT NULL,
    numero VARCHAR(10) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    capacidad INT NOT NULL,
    precio_por_noche DECIMAL(10,2) NOT NULL,
    descripcion TEXT,
    activa BOOLEAN DEFAULT true,
    FOREIGN KEY (hotel_id) REFERENCES hoteles(id),
    UNIQUE KEY uk_hotel_numero (hotel_id, numero)
);

CREATE TABLE servicios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    activo BOOLEAN DEFAULT true
);

CREATE TABLE hotel_servicios (
    hotel_id BIGINT NOT NULL,
    servicio_id BIGINT NOT NULL,
    PRIMARY KEY (hotel_id, servicio_id),
    FOREIGN KEY (hotel_id) REFERENCES hoteles(id),
    FOREIGN KEY (servicio_id) REFERENCES servicios(id)
);

CREATE TABLE extras (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    disponible BOOLEAN DEFAULT true
);

CREATE TABLE reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_entrada DATE NOT NULL,
    fecha_salida DATE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    precio_total DECIMAL(10,2) NOT NULL,
    habitacion_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    FOREIGN KEY (habitacion_id) REFERENCES habitaciones(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE reserva_extras (
    reserva_id BIGINT NOT NULL,
    extra_id BIGINT NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    precio_unitario DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (reserva_id, extra_id),
    FOREIGN KEY (reserva_id) REFERENCES reservas(id),
    FOREIGN KEY (extra_id) REFERENCES extras(id)
); 