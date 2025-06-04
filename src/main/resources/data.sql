-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Servidor: db
-- Tiempo de generación: 29-05-2025 a las 08:59:29
-- Versión del servidor: 10.11.11-MariaDB-ubu2204
-- Versión de PHP: 8.2.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `hotel_db`
--
CREATE DATABASE IF NOT EXISTS `hotel_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `hotel_db`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `datos_bancarios`
--

CREATE TABLE `datos_bancarios` (
  `id` bigint(20) NOT NULL,
  `activa` bit(1) NOT NULL,
  `cvv` varchar(255) NOT NULL,
  `fecha_expiracion` varchar(255) NOT NULL,
  `fecha_registro` datetime(6) NOT NULL,
  `numero_tarjeta` varchar(255) NOT NULL,
  `predeterminada` bit(1) NOT NULL,
  `tipo_tarjeta` enum('VISA','MASTERCARD','AMERICAN_EXPRESS','DINERS_CLUB') NOT NULL,
  `titular` varchar(255) NOT NULL,
  `ultima_modificacion` datetime(6) DEFAULT NULL,
  `usuario_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `datos_bancarios`
--

INSERT INTO `datos_bancarios` (`id`, `activa`, `cvv`, `fecha_expiracion`, `fecha_registro`, `numero_tarjeta`, `predeterminada`, `tipo_tarjeta`, `titular`, `ultima_modificacion`, `usuario_id`) VALUES
(1, b'1', '123', '04/30', '2025-05-18 11:20:01.131238', '1234123412341234', b'1', 'VISA', 'Marta Valero', NULL, 1),
(2, b'1', '321', '02/09', '2025-05-24 20:39:42.777876', '7894789478947894', b'1', 'MASTERCARD', 'Pablo García', '2025-05-24 20:45:33.114130', 3),
(3, b'0', '321', '02/29', '2025-05-24 20:39:42.968022', '7894789478947894', b'1', 'MASTERCARD', 'Pablo García', '2025-05-24 20:45:00.254993', 3),
(4, b'0', '321', '02/29', '2025-05-24 20:39:43.194975', '7894789478947894', b'0', 'MASTERCARD', 'Pablo García', '2025-05-24 20:44:49.807813', 3),
(5, b'0', '321', '02/29', '2025-05-24 20:39:43.626634', '7894789478947894', b'0', 'MASTERCARD', 'Pablo García', '2025-05-24 20:44:55.159354', 3),
(6, b'0', '321', '02/29', '2025-05-24 20:39:44.713879', '7894789478947894', b'0', 'MASTERCARD', 'Pablo García', '2025-05-24 20:44:48.230515', 3),
(7, b'0', '321', '02/29', '2025-05-24 20:39:53.222657', '7894789478947894', b'0', 'MASTERCARD', 'Pablo García', '2025-05-24 20:44:56.761205', 3),
(8, b'0', '321', '02/29', '2025-05-24 20:39:54.442469', '7894789478947894', b'0', 'MASTERCARD', 'Pablo García', '2025-05-24 20:44:51.489023', 3),
(9, b'1', '159', '07/28', '2025-05-28 11:48:54.215136', '7412852396328521', b'1', 'VISA', 'Juan Fernández', NULL, 2),
(10, b'1', '456', '11/32', '2025-05-28 11:51:55.981524', '2693258412300000', b'1', 'MASTERCARD', 'María Jiménez', NULL, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `extras`
--

CREATE TABLE `extras` (
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(1000) DEFAULT NULL,
  `disponible` bit(1) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `precio` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `extras`
--

INSERT INTO `extras` (`id`, `descripcion`, `disponible`, `nombre`, `precio`) VALUES
(1, 'Cama supletoria para habitación', b'1', 'Cama extra', 25),
(2, 'Cuna para bebé', b'1', 'Cuna', 15),
(3, 'Alojamiento para mascota', b'1', 'Mascota', 20),
(4, 'Nevera extra para la habitación', b'1', 'Nevera adicional', 10),
(5, 'Cena romántica en la habitación con champán', b'1', 'Cena romántica', 75),
(6, 'Masaje relajante en la habitación', b'1', 'Masaje', 80),
(7, 'Desayuno continental servido en la habitación', b'1', 'Desayuno en habitación', 15),
(8, 'Limpieza adicional de la habitación', b'1', 'Servicio de limpieza extra', 10),
(9, 'Planchado de ropa', b'1', 'Servicio de planchado', 15),
(10, 'Servicio de niñera por horas', b'1', 'Servicio de niñera', 25),
(11, 'Servicio de traslado desde/hacia el aeropuerto', b'1', 'Traslado aeropuerto', 40),
(12, 'Juego adicional de toallas', b'1', 'Toallas extra', 5),
(13, 'Juego adicional de sábanas', b'1', 'Sábanas extra', 5),
(14, 'Servicio completo de lavandería', b'1', 'Lavandería', 30),
(15, 'Decoración romántica, champán y chocolates', b'1', 'Paquete romántico', 100),
(16, 'Actividades para niños y desayuno familiar', b'1', 'Paquete familiar', 120),
(17, 'Visita guiada a los lugares más emblemáticos', b'1', 'Tour guiado', 45),
(18, 'Bicicletas para explorar la ciudad', b'1', 'Alquiler de bicicletas', 20),
(19, 'Sesión de entrenamiento personalizado', b'1', 'Entrenador personal', 60),
(20, 'Raquetas, pelotas y material deportivo', b'1', 'Alquiler de equipamiento deportivo', 25),
(21, 'Sesión fotográfica profesional', b'1', 'Servicio de fotógrafo', 150),
(22, 'Coche para uso durante la estancia', b'1', 'Alquiler de coche', 80),
(23, 'Sesión privada de yoga en la habitación o gimnasio', b'1', 'Clase de yoga', 45);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habitaciones`
--

CREATE TABLE `habitaciones` (
  `id` bigint(20) NOT NULL,
  `activa` bit(1) NOT NULL,
  `capacidad` int(11) NOT NULL,
  `descripcion` varchar(1000) DEFAULT NULL,
  `numero` varchar(255) NOT NULL,
  `precio_por_noche` double NOT NULL,
  `tipo` enum('ESTANDAR','SUPERIOR','SUITE') NOT NULL,
  `hotel_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `habitaciones`
--

INSERT INTO `habitaciones` (`id`, `activa`, `capacidad`, `descripcion`, `numero`, `precio_por_noche`, `tipo`, `hotel_id`) VALUES
(37, b'1', 2, 'Habitación con vista al mar', '101', 100, 'ESTANDAR', 1),
(38, b'1', 2, 'Habitación con balcón y vista al mar', '102', 150, 'SUPERIOR', 1),
(39, b'1', 4, 'Suite familiar con jacuzzi', '201', 250, 'SUITE', 1),
(40, b'1', 2, 'Habitación con decoración vintage', '101', 120, 'ESTANDAR', 2),
(41, b'1', 2, 'Habitación con terraza privada', '102', 180, 'SUPERIOR', 2),
(42, b'1', 3, 'Suite con bodega privada', '201', 280, 'SUITE', 2),
(43, b'1', 2, 'Habitación con vista al río', '101', 150, 'ESTANDAR', 3),
(44, b'1', 2, 'Habitación con balcón al río', '102', 200, 'SUPERIOR', 3),
(45, b'1', 4, 'Suite presidencial con terraza', '201', 350, 'SUITE', 3),
(46, b'1', 2, 'Habitación clásica londinense', '101', 200, 'ESTANDAR', 4),
(47, b'1', 2, 'Habitación con vista a la ciudad', '102', 300, 'SUPERIOR', 4),
(48, b'1', 4, 'Suite real con servicio de mayordomo', '201', 500, 'SUITE', 4),
(49, b'1', 2, 'Habitación con vista a la playa', '101', 250, 'ESTANDAR', 5),
(50, b'1', 2, 'Habitación con terraza al mar', '102', 350, 'SUPERIOR', 5),
(51, b'1', 4, 'Suite familiar con cocina', '201', 600, 'SUITE', 5),
(52, b'1', 2, 'Habitación con vista a la Torre Eiffel', '101', 250, 'ESTANDAR', 6),
(53, b'1', 2, 'Habitación con balcón a la Torre Eiffel', '102', 350, 'SUPERIOR', 6),
(54, b'1', 4, 'Suite presidencial con vista panorámica', '201', 600, 'SUITE', 6),
(55, b'1', 2, 'Habitación con vista al castillo', '101', 100, 'ESTANDAR', 7),
(56, b'1', 2, 'Habitación con balcón al castillo', '102', 150, 'SUPERIOR', 7),
(57, b'1', 3, 'Suite con chimenea', '201', 250, 'SUITE', 7),
(58, b'1', 2, 'Habitación con vista a la bahía', '101', 180, 'ESTANDAR', 8),
(59, b'1', 2, 'Habitación con terraza a la bahía', '102', 280, 'SUPERIOR', 8),
(60, b'1', 4, 'Suite familiar con vistas panorámicas', '201', 380, 'SUITE', 8),
(61, b'1', 2, 'Habitación con vista a la caldera', '101', 200, 'ESTANDAR', 9),
(62, b'1', 2, 'Habitación con jacuzzi privado', '102', 300, 'SUPERIOR', 9),
(63, b'1', 4, 'Suite con piscina privada', '201', 500, 'SUITE', 9),
(64, b'1', 2, 'Habitación con vista al palacio', '101', 100, 'ESTANDAR', 10),
(65, b'1', 2, 'Habitación con balcón al palacio', '102', 150, 'SUPERIOR', 10),
(66, b'1', 3, 'Suite con decoración histórica', '201', 250, 'SUITE', 10),
(67, b'1', 2, 'Habitación con vista al canal', '101', 200, 'ESTANDAR', 11),
(68, b'1', 2, 'Habitación con balcón al canal', '102', 300, 'SUPERIOR', 11),
(69, b'1', 4, 'Suite con acceso privado al canal', '201', 500, 'SUITE', 11);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habitacion_extras`
--

CREATE TABLE `habitacion_extras` (
  `habitacion_id` bigint(20) NOT NULL,
  `extra` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `habitacion_extras`
--

INSERT INTO `habitacion_extras` (`habitacion_id`, `extra`) VALUES
(37, 'Cama extra'),
(37, 'Cuna'),
(37, 'Mascota'),
(37, 'Nevera adicional'),
(37, 'Cena romántica'),
(37, 'Masaje'),
(37, 'Desayuno en habitación'),
(37, 'Servicio de limpieza extra'),
(37, 'Servicio de planchado'),
(37, 'Servicio de niñera'),
(37, 'Traslado aeropuerto'),
(37, 'Toallas extra'),
(37, 'Sábanas extra'),
(37, 'Lavandería'),
(37, 'Paquete romántico'),
(37, 'Paquete familiar'),
(37, 'Tour guiado'),
(37, 'Alquiler de bicicletas'),
(37, 'Entrenador personal'),
(37, 'Alquiler de equipamiento deportivo'),
(37, 'Servicio de fotógrafo'),
(37, 'Alquiler de coche'),
(37, 'Clase de yoga'),
(38, 'Cama extra'),
(38, 'Cuna'),
(38, 'Mascota'),
(38, 'Nevera adicional'),
(38, 'Cena romántica'),
(38, 'Masaje'),
(38, 'Desayuno en habitación'),
(38, 'Servicio de limpieza extra'),
(38, 'Servicio de planchado'),
(38, 'Servicio de niñera'),
(38, 'Traslado aeropuerto'),
(38, 'Toallas extra'),
(38, 'Sábanas extra'),
(38, 'Lavandería'),
(38, 'Paquete romántico'),
(38, 'Paquete familiar'),
(38, 'Tour guiado'),
(38, 'Alquiler de bicicletas'),
(38, 'Entrenador personal'),
(38, 'Alquiler de equipamiento deportivo'),
(38, 'Servicio de fotógrafo'),
(38, 'Alquiler de coche'),
(38, 'Clase de yoga'),
(39, 'Cama extra'),
(39, 'Cuna'),
(39, 'Mascota'),
(39, 'Nevera adicional'),
(39, 'Cena romántica'),
(39, 'Masaje'),
(39, 'Desayuno en habitación'),
(39, 'Servicio de limpieza extra'),
(39, 'Servicio de planchado'),
(39, 'Servicio de niñera'),
(39, 'Traslado aeropuerto'),
(39, 'Toallas extra'),
(39, 'Sábanas extra'),
(39, 'Lavandería'),
(39, 'Paquete romántico'),
(39, 'Paquete familiar'),
(39, 'Tour guiado'),
(39, 'Alquiler de bicicletas'),
(39, 'Entrenador personal'),
(39, 'Alquiler de equipamiento deportivo'),
(39, 'Servicio de fotógrafo'),
(39, 'Alquiler de coche'),
(39, 'Clase de yoga'),
(40, 'Cama extra'),
(40, 'Cuna'),
(40, 'Mascota'),
(40, 'Nevera adicional'),
(40, 'Cena romántica'),
(40, 'Masaje'),
(40, 'Desayuno en habitación'),
(40, 'Servicio de limpieza extra'),
(40, 'Servicio de planchado'),
(40, 'Servicio de niñera'),
(40, 'Traslado aeropuerto'),
(40, 'Toallas extra'),
(40, 'Sábanas extra'),
(40, 'Lavandería'),
(40, 'Paquete romántico'),
(40, 'Paquete familiar'),
(40, 'Tour guiado'),
(40, 'Alquiler de bicicletas'),
(40, 'Entrenador personal'),
(40, 'Alquiler de equipamiento deportivo'),
(40, 'Servicio de fotógrafo'),
(40, 'Alquiler de coche'),
(40, 'Clase de yoga'),
(41, 'Cama extra'),
(41, 'Cuna'),
(41, 'Mascota'),
(41, 'Nevera adicional'),
(41, 'Cena romántica'),
(41, 'Masaje'),
(41, 'Desayuno en habitación'),
(41, 'Servicio de limpieza extra'),
(41, 'Servicio de planchado'),
(41, 'Servicio de niñera'),
(41, 'Traslado aeropuerto'),
(41, 'Toallas extra'),
(41, 'Sábanas extra'),
(41, 'Lavandería'),
(41, 'Paquete romántico'),
(41, 'Paquete familiar'),
(41, 'Tour guiado'),
(41, 'Alquiler de bicicletas'),
(41, 'Entrenador personal'),
(41, 'Alquiler de equipamiento deportivo'),
(41, 'Servicio de fotógrafo'),
(41, 'Alquiler de coche'),
(41, 'Clase de yoga'),
(42, 'Cama extra'),
(42, 'Cuna'),
(42, 'Mascota'),
(42, 'Nevera adicional'),
(42, 'Cena romántica'),
(42, 'Masaje'),
(42, 'Desayuno en habitación'),
(42, 'Servicio de limpieza extra'),
(42, 'Servicio de planchado'),
(42, 'Servicio de niñera'),
(42, 'Traslado aeropuerto'),
(42, 'Toallas extra'),
(42, 'Sábanas extra'),
(42, 'Lavandería'),
(42, 'Paquete romántico'),
(42, 'Paquete familiar'),
(42, 'Tour guiado'),
(42, 'Alquiler de bicicletas'),
(42, 'Entrenador personal'),
(42, 'Alquiler de equipamiento deportivo'),
(42, 'Servicio de fotógrafo'),
(42, 'Alquiler de coche'),
(42, 'Clase de yoga'),
(43, 'Cama extra'),
(43, 'Cuna'),
(43, 'Mascota'),
(43, 'Nevera adicional'),
(43, 'Cena romántica'),
(43, 'Masaje'),
(43, 'Desayuno en habitación'),
(43, 'Servicio de limpieza extra'),
(43, 'Servicio de planchado'),
(43, 'Servicio de niñera'),
(43, 'Traslado aeropuerto'),
(43, 'Toallas extra'),
(43, 'Sábanas extra'),
(43, 'Lavandería'),
(43, 'Paquete romántico'),
(43, 'Paquete familiar'),
(43, 'Tour guiado'),
(43, 'Alquiler de bicicletas'),
(43, 'Entrenador personal'),
(43, 'Alquiler de equipamiento deportivo'),
(43, 'Servicio de fotógrafo'),
(43, 'Alquiler de coche'),
(43, 'Clase de yoga'),
(44, 'Cama extra'),
(44, 'Cuna'),
(44, 'Mascota'),
(44, 'Nevera adicional'),
(44, 'Cena romántica'),
(44, 'Masaje'),
(44, 'Desayuno en habitación'),
(44, 'Servicio de limpieza extra'),
(44, 'Servicio de planchado'),
(44, 'Servicio de niñera'),
(44, 'Traslado aeropuerto'),
(44, 'Toallas extra'),
(44, 'Sábanas extra'),
(44, 'Lavandería'),
(44, 'Paquete romántico'),
(44, 'Paquete familiar'),
(44, 'Tour guiado'),
(44, 'Alquiler de bicicletas'),
(44, 'Entrenador personal'),
(44, 'Alquiler de equipamiento deportivo'),
(44, 'Servicio de fotógrafo'),
(44, 'Alquiler de coche'),
(44, 'Clase de yoga'),
(45, 'Cama extra'),
(45, 'Cuna'),
(45, 'Mascota'),
(45, 'Nevera adicional'),
(45, 'Cena romántica'),
(45, 'Masaje'),
(45, 'Desayuno en habitación'),
(45, 'Servicio de limpieza extra'),
(45, 'Servicio de planchado'),
(45, 'Servicio de niñera'),
(45, 'Traslado aeropuerto'),
(45, 'Toallas extra'),
(45, 'Sábanas extra'),
(45, 'Lavandería'),
(45, 'Paquete romántico'),
(45, 'Paquete familiar'),
(45, 'Tour guiado'),
(45, 'Alquiler de bicicletas'),
(45, 'Entrenador personal'),
(45, 'Alquiler de equipamiento deportivo'),
(45, 'Servicio de fotógrafo'),
(45, 'Alquiler de coche'),
(45, 'Clase de yoga'),
(46, 'Cama extra'),
(46, 'Cuna'),
(46, 'Mascota'),
(46, 'Nevera adicional'),
(46, 'Cena romántica'),
(46, 'Masaje'),
(46, 'Desayuno en habitación'),
(46, 'Servicio de limpieza extra'),
(46, 'Servicio de planchado'),
(46, 'Servicio de niñera'),
(46, 'Traslado aeropuerto'),
(46, 'Toallas extra'),
(46, 'Sábanas extra'),
(46, 'Lavandería'),
(46, 'Paquete romántico'),
(46, 'Paquete familiar'),
(46, 'Tour guiado'),
(46, 'Alquiler de bicicletas'),
(46, 'Entrenador personal'),
(46, 'Alquiler de equipamiento deportivo'),
(46, 'Servicio de fotógrafo'),
(46, 'Alquiler de coche'),
(46, 'Clase de yoga'),
(47, 'Cama extra'),
(47, 'Cuna'),
(47, 'Mascota'),
(47, 'Nevera adicional'),
(47, 'Cena romántica'),
(47, 'Masaje'),
(47, 'Desayuno en habitación'),
(47, 'Servicio de limpieza extra'),
(47, 'Servicio de planchado'),
(47, 'Servicio de niñera'),
(47, 'Traslado aeropuerto'),
(47, 'Toallas extra'),
(47, 'Sábanas extra'),
(47, 'Lavandería'),
(47, 'Paquete romántico'),
(47, 'Paquete familiar'),
(47, 'Tour guiado'),
(47, 'Alquiler de bicicletas'),
(47, 'Entrenador personal'),
(47, 'Alquiler de equipamiento deportivo'),
(47, 'Servicio de fotógrafo'),
(47, 'Alquiler de coche'),
(47, 'Clase de yoga'),
(48, 'Cama extra'),
(48, 'Cuna'),
(48, 'Mascota'),
(48, 'Nevera adicional'),
(48, 'Cena romántica'),
(48, 'Masaje'),
(48, 'Desayuno en habitación'),
(48, 'Servicio de limpieza extra'),
(48, 'Servicio de planchado'),
(48, 'Servicio de niñera'),
(48, 'Traslado aeropuerto'),
(48, 'Toallas extra'),
(48, 'Sábanas extra'),
(48, 'Lavandería'),
(48, 'Paquete romántico'),
(48, 'Paquete familiar'),
(48, 'Tour guiado'),
(48, 'Alquiler de bicicletas'),
(48, 'Entrenador personal'),
(48, 'Alquiler de equipamiento deportivo'),
(48, 'Servicio de fotógrafo'),
(48, 'Alquiler de coche'),
(48, 'Clase de yoga'),
(49, 'Cama extra'),
(49, 'Cuna'),
(49, 'Mascota'),
(49, 'Nevera adicional'),
(49, 'Cena romántica'),
(49, 'Masaje'),
(49, 'Desayuno en habitación'),
(49, 'Servicio de limpieza extra'),
(49, 'Servicio de planchado'),
(49, 'Servicio de niñera'),
(49, 'Traslado aeropuerto'),
(49, 'Toallas extra'),
(49, 'Sábanas extra'),
(49, 'Lavandería'),
(49, 'Paquete romántico'),
(49, 'Paquete familiar'),
(49, 'Tour guiado'),
(49, 'Alquiler de bicicletas'),
(49, 'Entrenador personal'),
(49, 'Alquiler de equipamiento deportivo'),
(49, 'Servicio de fotógrafo'),
(49, 'Alquiler de coche'),
(49, 'Clase de yoga'),
(50, 'Cama extra'),
(50, 'Cuna'),
(50, 'Mascota'),
(50, 'Nevera adicional'),
(50, 'Cena romántica'),
(50, 'Masaje'),
(50, 'Desayuno en habitación'),
(50, 'Servicio de limpieza extra'),
(50, 'Servicio de planchado'),
(50, 'Servicio de niñera'),
(50, 'Traslado aeropuerto'),
(50, 'Toallas extra'),
(50, 'Sábanas extra'),
(50, 'Lavandería'),
(50, 'Paquete romántico'),
(50, 'Paquete familiar'),
(50, 'Tour guiado'),
(50, 'Alquiler de bicicletas'),
(50, 'Entrenador personal'),
(50, 'Alquiler de equipamiento deportivo'),
(50, 'Servicio de fotógrafo'),
(50, 'Alquiler de coche'),
(50, 'Clase de yoga'),
(51, 'Cama extra'),
(51, 'Cuna'),
(51, 'Mascota'),
(51, 'Nevera adicional'),
(51, 'Cena romántica'),
(51, 'Masaje'),
(51, 'Desayuno en habitación'),
(51, 'Servicio de limpieza extra'),
(51, 'Servicio de planchado'),
(51, 'Servicio de niñera'),
(51, 'Traslado aeropuerto'),
(51, 'Toallas extra'),
(51, 'Sábanas extra'),
(51, 'Lavandería'),
(51, 'Paquete romántico'),
(51, 'Paquete familiar'),
(51, 'Tour guiado'),
(51, 'Alquiler de bicicletas'),
(51, 'Entrenador personal'),
(51, 'Alquiler de equipamiento deportivo'),
(51, 'Servicio de fotógrafo'),
(51, 'Alquiler de coche'),
(51, 'Clase de yoga'),
(52, 'Cama extra'),
(52, 'Cuna'),
(52, 'Mascota'),
(52, 'Nevera adicional'),
(52, 'Cena romántica'),
(52, 'Masaje'),
(52, 'Desayuno en habitación'),
(52, 'Servicio de limpieza extra'),
(52, 'Servicio de planchado'),
(52, 'Servicio de niñera'),
(52, 'Traslado aeropuerto'),
(52, 'Toallas extra'),
(52, 'Sábanas extra'),
(52, 'Lavandería'),
(52, 'Paquete romántico'),
(52, 'Paquete familiar'),
(52, 'Tour guiado'),
(52, 'Alquiler de bicicletas'),
(52, 'Entrenador personal'),
(52, 'Alquiler de equipamiento deportivo'),
(52, 'Servicio de fotógrafo'),
(52, 'Alquiler de coche'),
(52, 'Clase de yoga'),
(53, 'Cama extra'),
(53, 'Cuna'),
(53, 'Mascota'),
(53, 'Nevera adicional'),
(53, 'Cena romántica'),
(53, 'Masaje'),
(53, 'Desayuno en habitación'),
(53, 'Servicio de limpieza extra'),
(53, 'Servicio de planchado'),
(53, 'Servicio de niñera'),
(53, 'Traslado aeropuerto'),
(53, 'Toallas extra'),
(53, 'Sábanas extra'),
(53, 'Lavandería'),
(53, 'Paquete romántico'),
(53, 'Paquete familiar'),
(53, 'Tour guiado'),
(53, 'Alquiler de bicicletas'),
(53, 'Entrenador personal'),
(53, 'Alquiler de equipamiento deportivo'),
(53, 'Servicio de fotógrafo'),
(53, 'Alquiler de coche'),
(53, 'Clase de yoga'),
(54, 'Cama extra'),
(54, 'Cuna'),
(54, 'Mascota'),
(54, 'Nevera adicional'),
(54, 'Cena romántica'),
(54, 'Masaje'),
(54, 'Desayuno en habitación'),
(54, 'Servicio de limpieza extra'),
(54, 'Servicio de planchado'),
(54, 'Servicio de niñera'),
(54, 'Traslado aeropuerto'),
(54, 'Toallas extra'),
(54, 'Sábanas extra'),
(54, 'Lavandería'),
(54, 'Paquete romántico'),
(54, 'Paquete familiar'),
(54, 'Tour guiado'),
(54, 'Alquiler de bicicletas'),
(54, 'Entrenador personal'),
(54, 'Alquiler de equipamiento deportivo'),
(54, 'Servicio de fotógrafo'),
(54, 'Alquiler de coche'),
(54, 'Clase de yoga'),
(55, 'Cama extra'),
(55, 'Cuna'),
(55, 'Mascota'),
(55, 'Nevera adicional'),
(55, 'Cena romántica'),
(55, 'Masaje'),
(55, 'Desayuno en habitación'),
(55, 'Servicio de limpieza extra'),
(55, 'Servicio de planchado'),
(55, 'Servicio de niñera'),
(55, 'Traslado aeropuerto'),
(55, 'Toallas extra'),
(55, 'Sábanas extra'),
(55, 'Lavandería'),
(55, 'Paquete romántico'),
(55, 'Paquete familiar'),
(55, 'Tour guiado'),
(55, 'Alquiler de bicicletas'),
(55, 'Entrenador personal'),
(55, 'Alquiler de equipamiento deportivo'),
(55, 'Servicio de fotógrafo'),
(55, 'Alquiler de coche'),
(55, 'Clase de yoga'),
(56, 'Cama extra'),
(56, 'Cuna'),
(56, 'Mascota'),
(56, 'Nevera adicional'),
(56, 'Cena romántica'),
(56, 'Masaje'),
(56, 'Desayuno en habitación'),
(56, 'Servicio de limpieza extra'),
(56, 'Servicio de planchado'),
(56, 'Servicio de niñera'),
(56, 'Traslado aeropuerto'),
(56, 'Toallas extra'),
(56, 'Sábanas extra'),
(56, 'Lavandería'),
(56, 'Paquete romántico'),
(56, 'Paquete familiar'),
(56, 'Tour guiado'),
(56, 'Alquiler de bicicletas'),
(56, 'Entrenador personal'),
(56, 'Alquiler de equipamiento deportivo'),
(56, 'Servicio de fotógrafo'),
(56, 'Alquiler de coche'),
(56, 'Clase de yoga'),
(57, 'Cama extra'),
(57, 'Cuna'),
(57, 'Mascota'),
(57, 'Nevera adicional'),
(57, 'Cena romántica'),
(57, 'Masaje'),
(57, 'Desayuno en habitación'),
(57, 'Servicio de limpieza extra'),
(57, 'Servicio de planchado'),
(57, 'Servicio de niñera'),
(57, 'Traslado aeropuerto'),
(57, 'Toallas extra'),
(57, 'Sábanas extra'),
(57, 'Lavandería'),
(57, 'Paquete romántico'),
(57, 'Paquete familiar'),
(57, 'Tour guiado'),
(57, 'Alquiler de bicicletas'),
(57, 'Entrenador personal'),
(57, 'Alquiler de equipamiento deportivo'),
(57, 'Servicio de fotógrafo'),
(57, 'Alquiler de coche'),
(57, 'Clase de yoga'),
(58, 'Cama extra'),
(58, 'Cuna'),
(58, 'Mascota'),
(58, 'Nevera adicional'),
(58, 'Cena romántica'),
(58, 'Masaje'),
(58, 'Desayuno en habitación'),
(58, 'Servicio de limpieza extra'),
(58, 'Servicio de planchado'),
(58, 'Servicio de niñera'),
(58, 'Traslado aeropuerto'),
(58, 'Toallas extra'),
(58, 'Sábanas extra'),
(58, 'Lavandería'),
(58, 'Paquete romántico'),
(58, 'Paquete familiar'),
(58, 'Tour guiado'),
(58, 'Alquiler de bicicletas'),
(58, 'Entrenador personal'),
(58, 'Alquiler de equipamiento deportivo'),
(58, 'Servicio de fotógrafo'),
(58, 'Alquiler de coche'),
(58, 'Clase de yoga'),
(59, 'Cama extra'),
(59, 'Cuna'),
(59, 'Mascota'),
(59, 'Nevera adicional'),
(59, 'Cena romántica'),
(59, 'Masaje'),
(59, 'Desayuno en habitación'),
(59, 'Servicio de limpieza extra'),
(59, 'Servicio de planchado'),
(59, 'Servicio de niñera'),
(59, 'Traslado aeropuerto'),
(59, 'Toallas extra'),
(59, 'Sábanas extra'),
(59, 'Lavandería'),
(59, 'Paquete romántico'),
(59, 'Paquete familiar'),
(59, 'Tour guiado'),
(59, 'Alquiler de bicicletas'),
(59, 'Entrenador personal'),
(59, 'Alquiler de equipamiento deportivo'),
(59, 'Servicio de fotógrafo'),
(59, 'Alquiler de coche'),
(59, 'Clase de yoga'),
(60, 'Cama extra'),
(60, 'Cuna'),
(60, 'Mascota'),
(60, 'Nevera adicional'),
(60, 'Cena romántica'),
(60, 'Masaje'),
(60, 'Desayuno en habitación'),
(60, 'Servicio de limpieza extra'),
(60, 'Servicio de planchado'),
(60, 'Servicio de niñera'),
(60, 'Traslado aeropuerto'),
(60, 'Toallas extra'),
(60, 'Sábanas extra'),
(60, 'Lavandería'),
(60, 'Paquete romántico'),
(60, 'Paquete familiar'),
(60, 'Tour guiado'),
(60, 'Alquiler de bicicletas'),
(60, 'Entrenador personal'),
(60, 'Alquiler de equipamiento deportivo'),
(60, 'Servicio de fotógrafo'),
(60, 'Alquiler de coche'),
(60, 'Clase de yoga'),
(61, 'Cama extra'),
(61, 'Cuna'),
(61, 'Mascota'),
(61, 'Nevera adicional'),
(61, 'Cena romántica'),
(61, 'Masaje'),
(61, 'Desayuno en habitación'),
(61, 'Servicio de limpieza extra'),
(61, 'Servicio de planchado'),
(61, 'Servicio de niñera'),
(61, 'Traslado aeropuerto'),
(61, 'Toallas extra'),
(61, 'Sábanas extra'),
(61, 'Lavandería'),
(61, 'Paquete romántico'),
(61, 'Paquete familiar'),
(61, 'Tour guiado'),
(61, 'Alquiler de bicicletas'),
(61, 'Entrenador personal'),
(61, 'Alquiler de equipamiento deportivo'),
(61, 'Servicio de fotógrafo'),
(61, 'Alquiler de coche'),
(61, 'Clase de yoga'),
(62, 'Cama extra'),
(62, 'Cuna'),
(62, 'Mascota'),
(62, 'Nevera adicional'),
(62, 'Cena romántica'),
(62, 'Masaje'),
(62, 'Desayuno en habitación'),
(62, 'Servicio de limpieza extra'),
(62, 'Servicio de planchado'),
(62, 'Servicio de niñera'),
(62, 'Traslado aeropuerto'),
(62, 'Toallas extra'),
(62, 'Sábanas extra'),
(62, 'Lavandería'),
(62, 'Paquete romántico'),
(62, 'Paquete familiar'),
(62, 'Tour guiado'),
(62, 'Alquiler de bicicletas'),
(62, 'Entrenador personal'),
(62, 'Alquiler de equipamiento deportivo'),
(62, 'Servicio de fotógrafo'),
(62, 'Alquiler de coche'),
(62, 'Clase de yoga'),
(63, 'Cama extra'),
(63, 'Cuna'),
(63, 'Mascota'),
(63, 'Nevera adicional'),
(63, 'Cena romántica'),
(63, 'Masaje'),
(63, 'Desayuno en habitación'),
(63, 'Servicio de limpieza extra'),
(63, 'Servicio de planchado'),
(63, 'Servicio de niñera'),
(63, 'Traslado aeropuerto'),
(63, 'Toallas extra'),
(63, 'Sábanas extra'),
(63, 'Lavandería'),
(63, 'Paquete romántico'),
(63, 'Paquete familiar'),
(63, 'Tour guiado'),
(63, 'Alquiler de bicicletas'),
(63, 'Entrenador personal'),
(63, 'Alquiler de equipamiento deportivo'),
(63, 'Servicio de fotógrafo'),
(63, 'Alquiler de coche'),
(63, 'Clase de yoga'),
(64, 'Cama extra'),
(64, 'Cuna'),
(64, 'Mascota'),
(64, 'Nevera adicional'),
(64, 'Cena romántica'),
(64, 'Masaje'),
(64, 'Desayuno en habitación'),
(64, 'Servicio de limpieza extra'),
(64, 'Servicio de planchado'),
(64, 'Servicio de niñera'),
(64, 'Traslado aeropuerto'),
(64, 'Toallas extra'),
(64, 'Sábanas extra'),
(64, 'Lavandería'),
(64, 'Paquete romántico'),
(64, 'Paquete familiar'),
(64, 'Tour guiado'),
(64, 'Alquiler de bicicletas'),
(64, 'Entrenador personal'),
(64, 'Alquiler de equipamiento deportivo'),
(64, 'Servicio de fotógrafo'),
(64, 'Alquiler de coche'),
(64, 'Clase de yoga'),
(65, 'Cama extra'),
(65, 'Cuna'),
(65, 'Mascota'),
(65, 'Nevera adicional'),
(65, 'Cena romántica'),
(65, 'Masaje'),
(65, 'Desayuno en habitación'),
(65, 'Servicio de limpieza extra'),
(65, 'Servicio de planchado'),
(65, 'Servicio de niñera'),
(65, 'Traslado aeropuerto'),
(65, 'Toallas extra'),
(65, 'Sábanas extra'),
(65, 'Lavandería'),
(65, 'Paquete romántico'),
(65, 'Paquete familiar'),
(65, 'Tour guiado'),
(65, 'Alquiler de bicicletas'),
(65, 'Entrenador personal'),
(65, 'Alquiler de equipamiento deportivo'),
(65, 'Servicio de fotógrafo'),
(65, 'Alquiler de coche'),
(65, 'Clase de yoga'),
(66, 'Cama extra'),
(66, 'Cuna'),
(66, 'Mascota'),
(66, 'Nevera adicional'),
(66, 'Cena romántica'),
(66, 'Masaje'),
(66, 'Desayuno en habitación'),
(66, 'Servicio de limpieza extra'),
(66, 'Servicio de planchado'),
(66, 'Servicio de niñera'),
(66, 'Traslado aeropuerto'),
(66, 'Toallas extra'),
(66, 'Sábanas extra'),
(66, 'Lavandería'),
(66, 'Paquete romántico'),
(66, 'Paquete familiar'),
(66, 'Tour guiado'),
(66, 'Alquiler de bicicletas'),
(66, 'Entrenador personal'),
(66, 'Alquiler de equipamiento deportivo'),
(66, 'Servicio de fotógrafo'),
(66, 'Alquiler de coche'),
(66, 'Clase de yoga'),
(67, 'Cama extra'),
(67, 'Cuna'),
(67, 'Mascota'),
(67, 'Nevera adicional'),
(67, 'Cena romántica'),
(67, 'Masaje'),
(67, 'Desayuno en habitación'),
(67, 'Servicio de limpieza extra'),
(67, 'Servicio de planchado'),
(67, 'Servicio de niñera'),
(67, 'Traslado aeropuerto'),
(67, 'Toallas extra'),
(67, 'Sábanas extra'),
(67, 'Lavandería'),
(67, 'Paquete romántico'),
(67, 'Paquete familiar'),
(67, 'Tour guiado'),
(67, 'Alquiler de bicicletas'),
(67, 'Entrenador personal'),
(67, 'Alquiler de equipamiento deportivo'),
(67, 'Servicio de fotógrafo'),
(67, 'Alquiler de coche'),
(67, 'Clase de yoga'),
(68, 'Cama extra'),
(68, 'Cuna'),
(68, 'Mascota'),
(68, 'Nevera adicional'),
(68, 'Cena romántica'),
(68, 'Masaje'),
(68, 'Desayuno en habitación'),
(68, 'Servicio de limpieza extra'),
(68, 'Servicio de planchado'),
(68, 'Servicio de niñera'),
(68, 'Traslado aeropuerto'),
(68, 'Toallas extra'),
(68, 'Sábanas extra'),
(68, 'Lavandería'),
(68, 'Paquete romántico'),
(68, 'Paquete familiar'),
(68, 'Tour guiado'),
(68, 'Alquiler de bicicletas'),
(68, 'Entrenador personal'),
(68, 'Alquiler de equipamiento deportivo'),
(68, 'Servicio de fotógrafo'),
(68, 'Alquiler de coche'),
(68, 'Clase de yoga'),
(69, 'Cama extra'),
(69, 'Cuna'),
(69, 'Mascota'),
(69, 'Nevera adicional'),
(69, 'Cena romántica'),
(69, 'Masaje'),
(69, 'Desayuno en habitación'),
(69, 'Servicio de limpieza extra'),
(69, 'Servicio de planchado'),
(69, 'Servicio de niñera'),
(69, 'Traslado aeropuerto'),
(69, 'Toallas extra'),
(69, 'Sábanas extra'),
(69, 'Lavandería'),
(69, 'Paquete romántico'),
(69, 'Paquete familiar'),
(69, 'Tour guiado'),
(69, 'Alquiler de bicicletas'),
(69, 'Entrenador personal'),
(69, 'Alquiler de equipamiento deportivo'),
(69, 'Servicio de fotógrafo'),
(69, 'Alquiler de coche'),
(69, 'Clase de yoga');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hoteles`
--

CREATE TABLE `hoteles` (
  `id` bigint(20) NOT NULL,
  `activo` bit(1) NOT NULL,
  `ciudad` varchar(255) NOT NULL,
  `descripcion` varchar(1000) DEFAULT NULL,
  `direccion` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `estrellas` int(11) NOT NULL,
  `latitud` double DEFAULT NULL,
  `longitud` double DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `pais` varchar(255) NOT NULL,
  `sitio_web` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `hoteles`
--

INSERT INTO `hoteles` (`id`, `activo`, `ciudad`, `descripcion`, `direccion`, `email`, `estrellas`, `latitud`, `longitud`, `nombre`, `pais`, `sitio_web`, `telefono`) VALUES
(1, b'1', 'Ponta Delgada', 'Descubre el paraíso en el corazón del Atlántico. Nuestro Hotel Azores Paradise te ofrece una experiencia única en Ponta Delgada, con vistas panorámicas al océano y un ambiente de serenidad incomparable.\n\nDisfruta de la belleza natural de las Azores mientras te alojas en un hotel que combina el lujo moderno con la autenticidad local. Nuestras habitaciones están diseñadas para maximizar las vistas al mar, y nuestros servicios premium aseguran una estancia inolvidable en este archipiélago portugués.', 'Rua do Mar 123, 9500-123 Ponta Delgada, Ilha de São Miguel, Açores, Portugal', 'info@azoresparadise.com', 4, 37.7412, -25.6756, 'Hotel Azores Paradise', 'Portugal', 'https://www.azoresparadise.com', '+351 296 123 456'),
(2, b'1', 'Burdeos', 'Sumérgete en la elegancia del mundo del vino en nuestro Hotel Burdeos Vintage. Ubicado en el corazón de la región vinícola más prestigiosa de Francia, nuestro hotel combina la tradición vitivinícola con el confort moderno.\n\nCada rincón está impregnado de la historia y la cultura del vino, desde nuestra decoración hasta nuestros servicios exclusivos. Disfruta de catas privadas, tours a bodegas cercanas y una gastronomía que celebra los mejores vinos de Burdeos.', 'Rue du Vin 45, 33000 Bordeaux, Nouvelle-Aquitaine, France', 'contact@bordeauxvintage.fr', 4, 44.8378, -0.5792, 'Hotel Burdeos Vintage', 'Francia', 'https://www.bordeauxvintage.fr', '+33 5 56 12 34 56'),
(3, b'1', 'Lisboa', 'El Hotel Lisboa Riverside redefine el lujo en la capital portuguesa. Con una ubicación privilegiada a orillas del río Tajo, cada habitación ofrece vistas espectaculares que capturan la esencia de Lisboa.\n\nNuestro diseño contemporáneo se mezcla perfectamente con elementos tradicionales portugueses, creando un ambiente sofisticado y acogedor. Disfruta de nuestra piscina infinita con vista al río, nuestro spa de clase mundial y una gastronomía que celebra lo mejor de la cocina portuguesa.', 'Avenida do Tejo 78, 1100-123 Lisboa, Distrito de Lisboa, Portugal', 'reservas@lisboriverside.pt', 3, 38.7072, -9.1356, 'Hotel Lisboa Riverside', 'Portugal', 'https://www.lisboriverside.pt', '+351 21 123 4567'),
(4, b'1', 'Londres', 'Experimenta el refinamiento británico en su máxima expresión en el Hotel London Royal. Situado en una de las zonas más exclusivas de Londres, nuestro hotel combina la elegancia clásica con el lujo moderno.\n\nCada detalle ha sido cuidadosamente seleccionado para ofrecer una experiencia verdaderamente real, desde nuestro servicio de mayordomo personal hasta nuestras suites con vistas panorámicas a los monumentos más emblemáticos de la ciudad.', '90 Kings Road, SW3 5XL London, Greater London, England, United Kingdom', 'stay@londonroyal.co.uk', 4, 51.4875, -0.169, 'Hotel London Royal', 'Reino Unido', 'https://www.londonroyal.co.uk', '+44 20 7946 0123'),
(5, b'1', 'Mahón', 'El Hotel Menorca Beach te invita a descubrir la joya más preciada del Mediterráneo. En primera línea de mar, nuestro establecimiento ofrece unas vistas impresionantes a una de las playas más espectaculares y con las aguas más cristalinas de la isla.\n\nNuestras habitaciones, diseñadas con un cuidado exquisito, te permitirán disfrutar de la brisa marina y los atardeceres más mágicos. Ven a vivir esta experiencia única, donde el tiempo parece moverse a otra velocidad.', 'Calle del Mar 34, 07701 Mahón, Illes Balears, España', 'info@hotelmenorcabeach.com', 5, 39.8886, 3.8724, 'Hotel Menorca Beach', 'España', 'https://www.hotelmenorcabeach.com', '+34 971 350 123'),
(6, b'1', 'París', 'El Hotel Paris Eiffel te ofrece una experiencia inolvidable en la Ciudad de la Luz. Con vistas directas a la Torre Eiffel, cada momento se convierte en una postal parisina.\n\nNuestro diseño interior combina la elegancia francesa con el confort moderno, creando un ambiente sofisticado y romántico. Disfruta de nuestra terraza panorámica, nuestro spa de lujo y una gastronomía que celebra lo mejor de la cocina francesa.', '56 Avenue des Champs-Élysées, 75008 Paris, Île-de-France, France', 'hello@pariseiffelhotel.fr', 5, 48.8698, 2.307, 'Hotel Paris Eiffel', 'Francia', 'https://www.pariseiffelhotel.fr', '+33 1 42 68 53 00'),
(7, b'1', 'Praga', 'El Hotel Prague Castle te transporta a la magia de la Edad Media en el corazón de Praga. Situado a los pies del majestuoso Castillo de Praga, nuestro hotel combina la arquitectura histórica con el lujo contemporáneo.\n\nCada habitación ofrece vistas únicas a los tejados de Praga y al castillo iluminado. Disfruta de nuestra bodega con los mejores vinos de la región, nuestro spa con tratamientos tradicionales checos y una gastronomía que celebra la rica herencia culinaria de Bohemia.', 'Castle Street 12, 118 00 Praha 1 – Hradčany, Praga, Región de Bohemia Central, República Checa', 'info@praguecastlehotel.cz', 3, 50.0907, 14.4006, 'Hotel Prague Castle', 'República Checa', 'https://www.praguecastlehotel.cz', '+420 224 812 345'),
(8, b'1', 'Santander', 'El Hotel Santander Bay te invita a descubrir la elegancia de la Costa Verde. Con vistas panorámicas a la bahía de Santander, nuestro hotel combina el lujo moderno con la tradición marinera de la ciudad.\n\nDisfruta de nuestra piscina infinita con vistas al mar, nuestro spa de lujo y una gastronomía que celebra los mejores productos del Cantábrico. Cada habitación está diseñada para maximizar las vistas a la bahía, creando una experiencia única en una de las ciudades más elegantes de España.', 'Paseo Marítimo 23, 39004 Santander, Cantabria, España', 'contacto@santanderbayhotel.es', 4, 43.4623, -3.809, 'Hotel Santander Bay', 'España', 'https://www.santanderbayhotel.es', '+34 942 210 987'),
(9, b'1', 'Oia', 'El Hotel Santorini Sunset redefine el concepto de lujo en la isla más romántica del Egeo. Con vistas espectaculares a la caldera y al atardecer más famoso del mundo, nuestro hotel ofrece una experiencia verdaderamente mágica.\n\nNuestras suites con piscina privada, decoradas en el estilo tradicional de Santorini, te permitirán disfrutar de la belleza de la isla en total privacidad. Disfruta de nuestra gastronomía que celebra los sabores del Mediterráneo y nuestro spa con tratamientos inspirados en la tradición griega.', 'Caldera Road 67, 84702 Oía, Isla de Santorini, Región Egeo Meridional, Grecia', 'reservations@santorinisunset.gr', 5, 36.4613, 25.3752, 'Hotel Santorini Sunset', 'Grecia', 'https://www.santorinisunset.gr', '+30 2286 071 234'),
(10, b'1', 'Split', 'El Hotel Split Palace te ofrece una experiencia única en el corazón del Palacio de Diocleciano. Nuestro hotel, integrado en el patrimonio histórico de la ciudad, combina la arquitectura romana con el confort moderno.\n\nCada habitación cuenta una historia diferente, con elementos originales del palacio y vistas únicas a la ciudad antigua. Disfruta de nuestra terraza panorámica, nuestro spa con tratamientos tradicionales dálmatas y una gastronomía que celebra los sabores del Adriático.', 'Diocletian Street 89, 21000 Split, Condado de Split-Dalmacia, Croacia', 'info@splitpalace.hr', 3, 43.5081, 16.4402, 'Hotel Split Palace', 'Croacia', 'https://www.splitpalace.hr', '+385 21 345 678'),
(11, b'1', 'Venecia', 'El Hotel Venice Canal te invita a vivir la magia de Venecia desde una perspectiva única. Con acceso directo a los canales, nuestro hotel te permite experimentar la ciudad como lo hacían los antiguos venecianos.\n\nNuestras suites, decoradas con antigüedades auténticas y obras de arte locales, ofrecen vistas espectaculares a los canales y a los palacios históricos. Disfruta de nuestro servicio de góndola privada, nuestro spa de lujo y una gastronomía que celebra la rica tradición culinaria veneciana.', 'Canal Grande 45, 30100 Venezia VE, Véneto, Italia', 'hello@venicecanal.it', 4, 45.437, 12.334, 'Hotel Venice Canal', 'Italia', 'https://www.venicecanal.it', '+39 041 522 3344');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel_servicios`
--

CREATE TABLE `hotel_servicios` (
  `hotel_id` bigint(20) NOT NULL,
  `servicio_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `hotel_servicios`
--

INSERT INTO `hotel_servicios` (`hotel_id`, `servicio_id`) VALUES
(5, 1),
(6, 1),
(9, 1),
(5, 2),
(6, 2),
(9, 2),
(5, 3),
(6, 3),
(9, 3),
(5, 4),
(6, 4),
(9, 4),
(5, 5),
(6, 5),
(9, 5),
(5, 6),
(6, 6),
(9, 6),
(5, 7),
(6, 7),
(9, 7),
(5, 8),
(6, 8),
(9, 8),
(5, 9),
(6, 9),
(9, 9),
(5, 10),
(6, 10),
(9, 10),
(5, 11),
(6, 11),
(9, 11),
(5, 12),
(6, 12),
(9, 12),
(5, 13),
(6, 13),
(9, 13),
(5, 14),
(6, 14),
(9, 14),
(5, 15),
(6, 15),
(9, 15),
(5, 16),
(6, 16),
(9, 16),
(5, 17),
(6, 17),
(9, 17),
(5, 18),
(6, 18),
(9, 18),
(5, 19),
(6, 19),
(9, 19),
(5, 20),
(6, 20),
(9, 20),
(5, 21),
(6, 21),
(9, 21),
(5, 22),
(6, 22),
(9, 22),
(5, 23),
(6, 23),
(9, 23),
(5, 24),
(6, 24),
(9, 24),
(5, 25),
(6, 25),
(9, 25),
(5, 26),
(6, 26),
(9, 26),
(5, 27),
(6, 27),
(9, 27),
(5, 28),
(6, 28),
(9, 28),
(5, 29),
(6, 29),
(9, 29),
(5, 30),
(6, 30),
(9, 30),
(3, 1),
(3, 2),
(3, 6),
(3, 7),
(3, 14),
(3, 18),
(3, 24),
(3, 25),
(3, 27),
(3, 29),
(7, 1),
(7, 2),
(7, 6),
(7, 7),
(7, 14),
(7, 18),
(7, 24),
(7, 25),
(7, 27),
(7, 29),
(10, 1),
(10, 2),
(10, 6),
(10, 7),
(10, 14),
(10, 18),
(10, 24),
(10, 25),
(10, 27),
(10, 29),
(1, 1),
(2, 1),
(4, 1),
(8, 1),
(11, 1),
(1, 2),
(2, 2),
(4, 2),
(8, 2),
(11, 2),
(1, 3),
(2, 3),
(4, 3),
(8, 3),
(11, 3),
(1, 4),
(2, 4),
(4, 4),
(8, 4),
(11, 4),
(1, 5),
(2, 5),
(4, 5),
(8, 5),
(11, 5),
(1, 6),
(2, 6),
(4, 6),
(8, 6),
(11, 6),
(1, 7),
(2, 7),
(4, 7),
(8, 7),
(11, 7),
(1, 8),
(2, 8),
(4, 8),
(8, 8),
(11, 8),
(1, 10),
(2, 10),
(4, 10),
(8, 10),
(11, 10),
(1, 14),
(2, 14),
(4, 14),
(8, 14),
(11, 14),
(1, 15),
(2, 15),
(4, 15),
(8, 15),
(11, 15),
(1, 16),
(2, 16),
(4, 16),
(8, 16),
(11, 16),
(1, 17),
(2, 17),
(4, 17),
(8, 17),
(11, 17),
(1, 18),
(2, 18),
(4, 18),
(8, 18),
(11, 18),
(1, 19),
(2, 19),
(4, 19),
(8, 19),
(11, 19),
(1, 21),
(2, 21),
(4, 21),
(8, 21),
(11, 21),
(1, 24),
(2, 24),
(4, 24),
(8, 24),
(11, 24),
(1, 25),
(2, 25),
(4, 25),
(8, 25),
(11, 25),
(1, 27),
(2, 27),
(4, 27),
(8, 27),
(11, 27),
(1, 28),
(2, 28),
(4, 28),
(8, 28),
(11, 28);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagos`
--

CREATE TABLE `pagos` (
  `id` bigint(20) NOT NULL,
  `estado` enum('PENDIENTE','COMPLETADO','FALLIDO','REEMBOLSADO') NOT NULL,
  `fecha_pago` datetime(6) NOT NULL,
  `metodo_pago` enum('TARJETA_CREDITO','TARJETA_DEBITO','TRANSFERENCIA','PAYPAL') NOT NULL,
  `monto` double NOT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  `referencia_pago` varchar(255) NOT NULL,
  `reserva_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pagos`
--

INSERT INTO `pagos` (`id`, `estado`, `fecha_pago`, `metodo_pago`, `monto`, `observaciones`, `referencia_pago`, `reserva_id`) VALUES
(1, 'COMPLETADO', '2025-05-21 10:37:22.099630', 'TARJETA_CREDITO', 395, NULL, 'PAY-ACAE448C', 1),
(2, 'COMPLETADO', '2025-05-23 14:23:44.678275', 'TARJETA_CREDITO', 780, NULL, 'PAY-61662968', 2),
(3, 'COMPLETADO', '2025-05-23 14:42:24.233859', 'PAYPAL', 2470, NULL, 'PAY-975F4AF7', 3),
(4, 'COMPLETADO', '2025-05-24 20:28:09.944773', 'PAYPAL', 740, NULL, 'PAY-AF1D6415', 5),
(5, 'COMPLETADO', '2025-05-24 20:45:35.049879', 'TARJETA_CREDITO', 635, NULL, 'PAY-38036AA7', 6),
(6, 'COMPLETADO', '2025-05-25 20:48:16.933302', 'PAYPAL', 525, NULL, 'PAY-B04132A3', 7),
(7, 'COMPLETADO', '2025-05-28 11:48:55.546620', 'TARJETA_CREDITO', 545, NULL, 'PAY-DC44A1DA', 8),
(8, 'COMPLETADO', '2025-05-28 11:49:48.253443', 'PAYPAL', 2020, NULL, 'PAY-D0BAEE11', 9),
(9, 'COMPLETADO', '2025-05-28 11:50:38.682589', 'TARJETA_CREDITO', 1175, NULL, 'PAY-A3DDCAC4', 10),
(10, 'COMPLETADO', '2025-05-28 11:51:57.263140', 'TARJETA_CREDITO', 1865, NULL, 'PAY-ECA9AB0B', 11),
(11, 'COMPLETADO', '2025-05-28 11:53:02.768547', 'TARJETA_CREDITO', 830, NULL, 'PAY-2E3D4E54', 12),
(12, 'COMPLETADO', '2025-05-28 11:53:46.470919', 'PAYPAL', 1530, NULL, 'PAY-19FA3558', 13),
(13, 'COMPLETADO', '2025-05-28 13:53:00.246218', 'TARJETA_CREDITO', 1085, NULL, 'PAY-F6466D14', 14),
(14, 'COMPLETADO', '2025-05-28 13:53:32.905334', 'PAYPAL', 1215, NULL, 'PAY-27DF1AF4', 15),
(15, 'COMPLETADO', '2025-05-28 13:54:42.346282', 'TARJETA_CREDITO', 1240, NULL, 'PAY-2E5DDEDF', 16);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservas`
--

CREATE TABLE `reservas` (
  `id` bigint(20) NOT NULL,
  `estado` enum('PENDIENTE','CONFIRMADA','EN_CURSO','COMPLETADA','CANCELADA','NO_SHOW') NOT NULL,
  `fecha_actualizacion` datetime(6) DEFAULT NULL,
  `fecha_creacion` datetime(6) NOT NULL,
  `fecha_entrada` datetime(6) NOT NULL,
  `fecha_salida` datetime(6) NOT NULL,
  `numero_huespedes` int(11) NOT NULL,
  `precio_total` double NOT NULL,
  `habitacion_id` bigint(20) NOT NULL,
  `usuario_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reservas`
--

INSERT INTO `reservas` (`id`, `estado`, `fecha_actualizacion`, `fecha_creacion`, `fecha_entrada`, `fecha_salida`, `numero_huespedes`, `precio_total`, `habitacion_id`, `usuario_id`) VALUES
(1, 'CONFIRMADA', '2025-05-21 10:37:22.209217', '2025-05-21 10:37:04.468145', '2025-05-21 22:00:00.000000', '2025-05-23 22:00:00.000000', 2, 395, 41, 1),
(2, 'CANCELADA', '2025-05-23 14:23:59.936858', '2025-05-23 11:02:45.246857', '2025-05-24 22:00:00.000000', '2025-05-26 22:00:00.000000', 4, 780, 60, 1),
(3, 'CONFIRMADA', '2025-05-23 14:42:24.257395', '2025-05-23 14:25:11.490510', '2025-06-23 22:00:00.000000', '2025-06-26 22:00:00.000000', 4, 2470, 51, 1),
(4, 'PENDIENTE', '2025-05-23 16:37:37.610000', '2025-05-23 16:37:37.648199', '2025-05-27 22:00:00.000000', '2025-05-28 22:00:00.000000', 2, 180, 43, 1),
(5, 'CANCELADA', '2025-05-24 20:46:23.963056', '2025-05-24 20:27:18.661450', '2025-05-24 22:00:00.000000', '2025-05-26 22:00:00.000000', 2, 740, 53, 3),
(6, 'CONFIRMADA', '2025-05-24 20:45:35.082591', '2025-05-24 20:39:15.893273', '2025-05-25 22:00:00.000000', '2025-05-28 22:00:00.000000', 2, 635, 61, 3),
(7, 'CONFIRMADA', '2025-05-25 20:48:16.996696', '2025-05-25 20:48:11.884450', '2025-05-28 22:00:00.000000', '2025-05-30 22:00:00.000000', 2, 525, 49, 2),
(8, 'CONFIRMADA', '2025-05-28 11:48:55.576856', '2025-05-28 11:48:23.818027', '2025-06-07 22:00:00.000000', '2025-06-11 22:00:00.000000', 2, 545, 40, 2),
(9, 'CONFIRMADA', '2025-05-28 11:49:48.274692', '2025-05-28 11:49:42.374969', '2025-06-21 22:00:00.000000', '2025-06-25 22:00:00.000000', 4, 2020, 63, 2),
(10, 'CONFIRMADA', '2025-05-28 11:50:38.707955', '2025-05-28 11:50:36.634525', '2025-06-30 22:00:00.000000', '2025-07-06 22:00:00.000000', 2, 1175, 58, 2),
(11, 'CONFIRMADA', '2025-05-28 11:51:57.285496', '2025-05-28 11:51:22.863576', '2025-06-28 22:00:00.000000', '2025-07-01 22:00:00.000000', 4, 1865, 51, 4),
(12, 'CONFIRMADA', '2025-05-28 11:53:02.792017', '2025-05-28 11:52:58.128156', '2025-06-22 22:00:00.000000', '2025-06-27 22:00:00.000000', 2, 830, 38, 4),
(13, 'CONFIRMADA', '2025-05-28 11:53:46.491039', '2025-05-28 11:53:44.196321', '2025-07-02 22:00:00.000000', '2025-07-08 22:00:00.000000', 2, 1530, 52, 4),
(14, 'CONFIRMADA', '2025-05-28 13:53:00.266462', '2025-05-28 13:52:55.610050', '2025-05-28 22:00:00.000000', '2025-05-30 22:00:00.000000', 4, 1085, 48, 1),
(15, 'CONFIRMADA', '2025-05-28 13:53:32.928392', '2025-05-28 13:53:30.201440', '2025-06-22 22:00:00.000000', '2025-06-26 22:00:00.000000', 2, 1215, 47, 1),
(16, 'CONFIRMADA', '2025-05-28 13:54:42.371009', '2025-05-28 13:54:40.146783', '2025-05-31 22:00:00.000000', '2025-06-06 22:00:00.000000', 2, 1240, 44, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva_extras`
--

CREATE TABLE `reserva_extras` (
  `cantidad` int(11) NOT NULL,
  `precio_total` double NOT NULL,
  `precio_unitario` double NOT NULL,
  `extra_id` bigint(20) NOT NULL,
  `reserva_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reserva_extras`
--

INSERT INTO `reserva_extras` (`cantidad`, `precio_total`, `precio_unitario`, `extra_id`, `reserva_id`) VALUES
(1, 25, 25, 1, 1),
(1, 20, 20, 3, 2),
(1, 20, 20, 3, 3),
(1, 20, 20, 3, 6),
(1, 20, 20, 3, 9),
(1, 10, 10, 4, 1),
(1, 75, 75, 5, 3),
(1, 80, 80, 6, 3),
(1, 80, 80, 6, 10),
(1, 15, 15, 7, 3),
(1, 15, 15, 7, 6),
(1, 15, 15, 7, 10),
(1, 15, 15, 7, 15),
(1, 10, 10, 8, 3),
(1, 25, 25, 10, 7),
(1, 40, 40, 11, 3),
(1, 40, 40, 11, 5),
(1, 40, 40, 11, 11),
(1, 40, 40, 11, 16),
(1, 5, 5, 12, 3),
(1, 30, 30, 14, 3),
(1, 30, 30, 14, 4),
(1, 30, 30, 14, 13),
(1, 100, 100, 15, 3),
(1, 45, 45, 17, 3),
(1, 45, 45, 17, 8),
(1, 20, 20, 18, 3),
(1, 20, 20, 18, 8),
(1, 60, 60, 19, 14),
(1, 25, 25, 20, 11),
(1, 25, 25, 20, 14),
(1, 150, 150, 21, 3),
(1, 80, 80, 22, 3),
(1, 80, 80, 22, 12);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicios`
--

CREATE TABLE `servicios` (
  `id` bigint(20) NOT NULL,
  `activo` bit(1) NOT NULL,
  `descripcion` varchar(1000) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `precio` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `servicios`
--

INSERT INTO `servicios` (`id`, `activo`, `descripcion`, `nombre`, `precio`) VALUES
(1, b'1', 'Estacionamiento privado para huéspedes', 'Parking', 15),
(2, b'1', 'Conexión WiFi de alta velocidad en todo el hotel', 'WiFi', 0),
(3, b'1', 'Spa con jacuzzi, sauna y sala de masajes', 'Spa', 50),
(4, b'1', 'Piscina climatizada con zona de solárium', 'Piscina', 0),
(5, b'1', 'Gimnasio equipado con máquinas modernas y sala de cardio', 'Gimnasio', 0),
(6, b'1', 'Restaurante con cocina local e internacional', 'Restaurante', 0),
(7, b'1', 'Servicio de consigna para equipaje', 'Consigna', 0),
(8, b'1', 'Servicio de guardería para niños con actividades', 'Guardería', 25),
(9, b'1', 'Visitas guiadas a la bodega con degustación', 'Bodega', 30),
(10, b'1', 'Salones para eventos y celebraciones', 'Eventos privados', 0),
(11, b'1', 'Accesos adaptados y servicios especiales', 'Instalaciones para huéspedes con problemas de movilidad', 0),
(12, b'1', 'Habitaciones diseñadas para máxima accesibilidad', 'Habitaciones adaptadas y accesibles', 0),
(13, b'1', 'Estaciones de carga para vehículos eléctricos', 'Punto de recarga para coches eléctricos', 10),
(14, b'1', 'Jardines, terrazas y áreas de descanso', 'Zonas exteriores', 0),
(15, b'1', 'Tratamientos de belleza y masajes', 'Belleza y bienestar', 40),
(16, b'1', 'Actividades y servicios especiales para familias', 'Familias y niños', 0),
(17, b'1', 'Clases y actividades deportivas guiadas', 'Actividades deportivas', 20),
(18, b'1', 'Información sobre atracciones cercanas', 'En las inmediaciones', 0),
(19, b'1', 'Sala de juegos y entretenimiento', 'Entretenimiento', 0),
(20, b'1', 'Servicios y facilidades para personas con discapacidad', 'Accesibilidad', 0),
(21, b'1', 'Salas de reuniones y centro de negocios', 'Instalaciones de negocios', 0),
(22, b'1', 'Alojamiento y servicios para mascotas', 'Mascotas', 20),
(23, b'1', 'Prácticas eco-friendly y servicios sostenibles', 'Sostenibilidad', 0),
(24, b'1', 'Salones y áreas de estar compartidas', 'Zonas comunes', 0),
(25, b'1', 'Atención 24 horas y servicios de conserjería', 'Servicios de recepción', 0),
(26, b'1', 'Servicio de seguridad 24 horas', 'Seguridad', 0),
(27, b'1', 'Limpieza diaria y servicio de lavandería', 'Servicios de limpieza', 0),
(28, b'1', 'Diferentes opciones de pensión completa y media pensión', 'Tipos de pensión', 0),
(29, b'1', 'Múltiples opciones de pago incluyendo tarjetas y efectivo', 'Formas de pago aceptadas en el hotel', 0),
(30, b'1', 'Tiendas y boutiques dentro del hotel', 'Tiendas', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` bigint(20) NOT NULL,
  `activo` bit(1) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `fecha_registro` datetime(6) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `rol` enum('PUBLICO','CLIENTE','ADMIN') NOT NULL,
  `telefono` varchar(255) NOT NULL,
  `token_expiracion` datetime(6) DEFAULT NULL,
  `token_recuperacion` varchar(255) DEFAULT NULL,
  `ultima_modificacion` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `activo`, `apellido`, `email`, `fecha_registro`, `nombre`, `password`, `rol`, `telefono`, `token_expiracion`, `token_recuperacion`, `ultima_modificacion`) VALUES
(1, b'1', 'Valero', 'mvalero@yahoo.es', '2025-05-18 11:18:49.769099', 'Marta', '012369', 'CLIENTE', '+34123456980', NULL, NULL, '2025-05-28 12:27:11.934451'),
(2, b'1', 'Fernández', 'jfernandez@yahoo.com', '2025-05-24 18:25:21.690313', 'Juan', '789456', 'CLIENTE', '+33658975423', NULL, NULL, NULL),
(3, b'1', 'García', 'pgarcia@outlook.it', '2025-05-24 18:27:36.456438', 'Pablo', '258741', 'CLIENTE', '+31458121236', NULL, NULL, '2025-05-28 12:32:14.119724'),
(4, b'1', 'Jiménez', 'mjimenez@hotmail.uk', '2025-05-24 18:34:22.135924', 'María', '147258', 'CLIENTE', '+32024909551', NULL, NULL, '2025-05-28 12:25:26.368422'),
(5, b'1', 'Admin', 'admin@habitahub.es', '2025-05-25 20:41:14.542278', 'Admin', '123456', 'ADMIN', '+34917894512', NULL, NULL, NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `datos_bancarios`
--
ALTER TABLE `datos_bancarios`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfun4i2x5ffcc0t0ti44ojqec0` (`usuario_id`);

--
-- Indices de la tabla `extras`
--
ALTER TABLE `extras`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `habitaciones`
--
ALTER TABLE `habitaciones`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrnrx4788kbajol7hnp3v8oj2h` (`hotel_id`);

--
-- Indices de la tabla `habitacion_extras`
--
ALTER TABLE `habitacion_extras`
  ADD KEY `FK1vfjij2gd2rs97fptv5ps9vpr` (`habitacion_id`);

--
-- Indices de la tabla `hoteles`
--
ALTER TABLE `hoteles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `hotel_servicios`
--
ALTER TABLE `hotel_servicios`
  ADD KEY `FKbglasjx8ao64cm928aajkre4m` (`servicio_id`),
  ADD KEY `FKlj1hx6d3nm09rvmif8f26xf68` (`hotel_id`);

--
-- Indices de la tabla `pagos`
--
ALTER TABLE `pagos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrd490715qtq2imjqrn0rwyhsl` (`reserva_id`);

--
-- Indices de la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKj1dyoxal4rnhdcxo4mv6bcivc` (`habitacion_id`),
  ADD KEY `FKcfh7qcr7oxomqk5hhbxdg2m7p` (`usuario_id`);

--
-- Indices de la tabla `reserva_extras`
--
ALTER TABLE `reserva_extras`
  ADD PRIMARY KEY (`extra_id`,`reserva_id`),
  ADD KEY `FK1uyqy7dkqsivw0keudjfylt0p` (`reserva_id`);

--
-- Indices de la tabla `servicios`
--
ALTER TABLE `servicios`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_kfsp0s1tflm1cwlj8idhqsad0` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `datos_bancarios`
--
ALTER TABLE `datos_bancarios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `extras`
--
ALTER TABLE `extras`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de la tabla `habitaciones`
--
ALTER TABLE `habitaciones`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- AUTO_INCREMENT de la tabla `hoteles`
--
ALTER TABLE `hoteles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `pagos`
--
ALTER TABLE `pagos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `reservas`
--
ALTER TABLE `reservas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `servicios`
--
ALTER TABLE `servicios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `datos_bancarios`
--
ALTER TABLE `datos_bancarios`
  ADD CONSTRAINT `FKfun4i2x5ffcc0t0ti44ojqec0` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `habitaciones`
--
ALTER TABLE `habitaciones`
  ADD CONSTRAINT `FKrnrx4788kbajol7hnp3v8oj2h` FOREIGN KEY (`hotel_id`) REFERENCES `hoteles` (`id`);

--
-- Filtros para la tabla `habitacion_extras`
--
ALTER TABLE `habitacion_extras`
  ADD CONSTRAINT `FK1vfjij2gd2rs97fptv5ps9vpr` FOREIGN KEY (`habitacion_id`) REFERENCES `habitaciones` (`id`);

--
-- Filtros para la tabla `hotel_servicios`
--
ALTER TABLE `hotel_servicios`
  ADD CONSTRAINT `FKbglasjx8ao64cm928aajkre4m` FOREIGN KEY (`servicio_id`) REFERENCES `servicios` (`id`),
  ADD CONSTRAINT `FKlj1hx6d3nm09rvmif8f26xf68` FOREIGN KEY (`hotel_id`) REFERENCES `hoteles` (`id`);

--
-- Filtros para la tabla `pagos`
--
ALTER TABLE `pagos`
  ADD CONSTRAINT `FKrd490715qtq2imjqrn0rwyhsl` FOREIGN KEY (`reserva_id`) REFERENCES `reservas` (`id`);

--
-- Filtros para la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD CONSTRAINT `FKcfh7qcr7oxomqk5hhbxdg2m7p` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `FKj1dyoxal4rnhdcxo4mv6bcivc` FOREIGN KEY (`habitacion_id`) REFERENCES `habitaciones` (`id`);

--
-- Filtros para la tabla `reserva_extras`
--
ALTER TABLE `reserva_extras`
  ADD CONSTRAINT `FK1uyqy7dkqsivw0keudjfylt0p` FOREIGN KEY (`reserva_id`) REFERENCES `reservas` (`id`),
  ADD CONSTRAINT `FKa2otdr37ly167idaqpwpd9nh4` FOREIGN KEY (`extra_id`) REFERENCES `extras` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
