-- Hoteles
INSERT INTO hoteles (nombre, direccion, ciudad, pais, estrellas, descripcion, activo)
SELECT 'Hotel Azores Paradise', 'Rua do Mar 123', 'Ponta Delgada', 'Portugal', 4, 'Hotel con vistas al océano Atlántico', true
WHERE NOT EXISTS (SELECT 1 FROM hoteles WHERE nombre = 'Hotel Azores Paradise');

INSERT INTO hoteles (nombre, direccion, ciudad, pais, estrellas, descripcion, activo)
SELECT 'Hotel Burdeos Vintage', 'Rue du Vin 45', 'Burdeos', 'Francia', 4, 'Hotel en el corazón de la región vinícola', true
WHERE NOT EXISTS (SELECT 1 FROM hoteles WHERE nombre = 'Hotel Burdeos Vintage');

INSERT INTO hoteles (nombre, direccion, ciudad, pais, estrellas, descripcion, activo)
SELECT 'Hotel Lisboa Riverside', 'Avenida do Tejo 78', 'Lisboa', 'Portugal', 5, 'Hotel con vistas al río Tajo', true
WHERE NOT EXISTS (SELECT 1 FROM hoteles WHERE nombre = 'Hotel Lisboa Riverside');

INSERT INTO hoteles (nombre, direccion, ciudad, pais, estrellas, descripcion, activo)
SELECT 'Hotel London Royal', 'Kings Road 90', 'Londres', 'Reino Unido', 5, 'Hotel de lujo en el centro de Londres', true
WHERE NOT EXISTS (SELECT 1 FROM hoteles WHERE nombre = 'Hotel London Royal');

INSERT INTO hoteles (nombre, direccion, ciudad, pais, estrellas, descripcion, activo)
SELECT 'Hotel Menorca Beach', 'Calle del Mar 34', 'Mahón', 'España', 4, 'Hotel frente a la playa', true
WHERE NOT EXISTS (SELECT 1 FROM hoteles WHERE nombre = 'Hotel Menorca Beach');

INSERT INTO hoteles (nombre, direccion, ciudad, pais, estrellas, descripcion, activo)
SELECT 'Hotel Paris Eiffel', 'Avenue des Champs-Élysées 56', 'París', 'Francia', 5, 'Hotel con vistas a la Torre Eiffel', true
WHERE NOT EXISTS (SELECT 1 FROM hoteles WHERE nombre = 'Hotel Paris Eiffel');

INSERT INTO hoteles (nombre, direccion, ciudad, pais, estrellas, descripcion, activo)
SELECT 'Hotel Prague Castle', 'Castle Street 12', 'Praga', 'República Checa', 4, 'Hotel cerca del Castillo de Praga', true
WHERE NOT EXISTS (SELECT 1 FROM hoteles WHERE nombre = 'Hotel Prague Castle');

INSERT INTO hoteles (nombre, direccion, ciudad, pais, estrellas, descripcion, activo)
SELECT 'Hotel Santander Bay', 'Paseo Marítimo 23', 'Santander', 'España', 4, 'Hotel con vistas a la bahía', true
WHERE NOT EXISTS (SELECT 1 FROM hoteles WHERE nombre = 'Hotel Santander Bay');

INSERT INTO hoteles (nombre, direccion, ciudad, pais, estrellas, descripcion, activo)
SELECT 'Hotel Santorini Sunset', 'Caldera Road 67', 'Oia', 'Grecia', 5, 'Hotel con vistas al atardecer en la caldera', true
WHERE NOT EXISTS (SELECT 1 FROM hoteles WHERE nombre = 'Hotel Santorini Sunset');

INSERT INTO hoteles (nombre, direccion, ciudad, pais, estrellas, descripcion, activo)
SELECT 'Hotel Split Palace', 'Diocletian Street 89', 'Split', 'Croacia', 4, 'Hotel junto al Palacio de Diocleciano', true
WHERE NOT EXISTS (SELECT 1 FROM hoteles WHERE nombre = 'Hotel Split Palace');

INSERT INTO hoteles (nombre, direccion, ciudad, pais, estrellas, descripcion, activo)
SELECT 'Hotel Venice Canal', 'Canal Grande 45', 'Venecia', 'Italia', 5, 'Hotel con acceso directo a los canales', true
WHERE NOT EXISTS (SELECT 1 FROM hoteles WHERE nombre = 'Hotel Venice Canal');

-- Habitaciones para cada hotel
-- Azores Paradise
INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 1, '101', 'ESTANDAR', 2, 100.00, 'Habitación con vista al mar', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 1 AND numero = '101');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 1, '102', 'SUPERIOR', 2, 150.00, 'Habitación con balcón y vista al mar', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 1 AND numero = '102');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 1, '201', 'SUITE', 4, 250.00, 'Suite familiar con jacuzzi', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 1 AND numero = '201');

-- Burdeos Vintage
INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 2, '101', 'ESTANDAR', 2, 120.00, 'Habitación con decoración vintage', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 2 AND numero = '101');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 2, '102', 'SUPERIOR', 2, 180.00, 'Habitación con terraza privada', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 2 AND numero = '102');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 2, '201', 'SUITE', 3, 280.00, 'Suite con bodega privada', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 2 AND numero = '201');

-- Lisboa Riverside
INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 3, '101', 'ESTANDAR', 2, 150.00, 'Habitación con vista al río', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 3 AND numero = '101');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 3, '102', 'SUPERIOR', 2, 200.00, 'Habitación con balcón al río', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 3 AND numero = '102');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 3, '201', 'SUITE', 4, 350.00, 'Suite presidencial con terraza', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 3 AND numero = '201');

-- London Royal
INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 4, '101', 'ESTANDAR', 2, 200.00, 'Habitación clásica londinense', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 4 AND numero = '101');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 4, '102', 'SUPERIOR', 2, 300.00, 'Habitación con vista a la ciudad', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 4 AND numero = '102');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 4, '201', 'SUITE', 4, 500.00, 'Suite real con servicio de mayordomo', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 4 AND numero = '201');

-- Menorca Beach
INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 5, '101', 'ESTANDAR', 2, 120.00, 'Habitación con vista a la playa', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 5 AND numero = '101');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 5, '102', 'SUPERIOR', 2, 180.00, 'Habitación con terraza al mar', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 5 AND numero = '102');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 5, '201', 'SUITE', 4, 280.00, 'Suite familiar con cocina', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 5 AND numero = '201');

-- Paris Eiffel
INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 6, '101', 'ESTANDAR', 2, 250.00, 'Habitación con vista a la Torre Eiffel', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 6 AND numero = '101');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 6, '102', 'SUPERIOR', 2, 350.00, 'Habitación con balcón a la Torre Eiffel', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 6 AND numero = '102');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 6, '201', 'SUITE', 4, 600.00, 'Suite presidencial con vista panorámica', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 6 AND numero = '201');

-- Prague Castle
INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 7, '101', 'ESTANDAR', 2, 100.00, 'Habitación con vista al castillo', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 7 AND numero = '101');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 7, '102', 'SUPERIOR', 2, 150.00, 'Habitación con balcón al castillo', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 7 AND numero = '102');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 7, '201', 'SUITE', 3, 250.00, 'Suite con chimenea', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 7 AND numero = '201');

-- Santander Bay
INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 8, '101', 'ESTANDAR', 2, 120.00, 'Habitación con vista a la bahía', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 8 AND numero = '101');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 8, '102', 'SUPERIOR', 2, 180.00, 'Habitación con terraza a la bahía', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 8 AND numero = '102');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 8, '201', 'SUITE', 4, 280.00, 'Suite familiar con vistas panorámicas', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 8 AND numero = '201');

-- Santorini Sunset
INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 9, '101', 'ESTANDAR', 2, 200.00, 'Habitación con vista a la caldera', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 9 AND numero = '101');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 9, '102', 'SUPERIOR', 2, 300.00, 'Habitación con jacuzzi privado', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 9 AND numero = '102');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 9, '201', 'SUITE', 4, 500.00, 'Suite con piscina privada', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 9 AND numero = '201');

-- Split Palace
INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 10, '101', 'ESTANDAR', 2, 100.00, 'Habitación con vista al palacio', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 10 AND numero = '101');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 10, '102', 'SUPERIOR', 2, 150.00, 'Habitación con balcón al palacio', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 10 AND numero = '102');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 10, '201', 'SUITE', 3, 250.00, 'Suite con decoración histórica', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 10 AND numero = '201');

-- Venice Canal
INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 11, '101', 'ESTANDAR', 2, 200.00, 'Habitación con vista al canal', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 11 AND numero = '101');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 11, '102', 'SUPERIOR', 2, 300.00, 'Habitación con balcón al canal', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 11 AND numero = '102');

INSERT INTO habitaciones (hotel_id, numero, tipo, capacidad, precio_por_noche, descripcion, activa)
SELECT 11, '201', 'SUITE', 4, 500.00, 'Suite con acceso privado al canal', true
WHERE NOT EXISTS (SELECT 1 FROM habitaciones WHERE hotel_id = 11 AND numero = '201');

-- Servicios disponibles
INSERT INTO servicios (nombre, descripcion, precio, activo)
SELECT 'Parking', 'Estacionamiento privado para huéspedes', 15.00, true
WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Parking');

INSERT INTO servicios (nombre, descripcion, precio, activo)
SELECT 'WiFi', 'Conexión WiFi de alta velocidad en todo el hotel', 0.00, true
WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'WiFi');

INSERT INTO servicios (nombre, descripcion, precio, activo)
SELECT 'Spa', 'Spa con jacuzzi, sauna y sala de masajes', 50.00, true
WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Spa');

INSERT INTO servicios (nombre, descripcion, precio, activo)
SELECT 'Piscina', 'Piscina climatizada', 0.00, true
WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Piscina');

INSERT INTO servicios (nombre, descripcion, precio, activo)
SELECT 'Gimnasio', 'Gimnasio equipado con máquinas modernas', 0.00, true
WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Gimnasio');

INSERT INTO servicios (nombre, descripcion, precio, activo)
SELECT 'Restaurante', 'Restaurante con cocina local e internacional', 0.00, true
WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Restaurante');

INSERT INTO servicios (nombre, descripcion, precio, activo)
SELECT 'Consigna', 'Servicio de consigna para equipaje', 0.00, true
WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Consigna');

INSERT INTO servicios (nombre, descripcion, precio, activo)
SELECT 'Guardería', 'Servicio de guardería para niños', 25.00, true
WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Guardería');

INSERT INTO servicios (nombre, descripcion, precio, activo)
SELECT 'Bodega', 'Visitas guiadas a la bodega', 30.00, true
WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Bodega');

-- Relación hotel-servicios
INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 1, 1
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 1 AND servicio_id = 1);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 1, 2
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 1 AND servicio_id = 2);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 1, 3
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 1 AND servicio_id = 3);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 1, 4
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 1 AND servicio_id = 4);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 1, 5
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 1 AND servicio_id = 5);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 1, 6
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 1 AND servicio_id = 6);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 1, 7
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 1 AND servicio_id = 7);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 1, 8
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 1 AND servicio_id = 8);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 2, 1
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 2 AND servicio_id = 1);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 2, 2
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 2 AND servicio_id = 2);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 2, 3
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 2 AND servicio_id = 3);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 2, 4
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 2 AND servicio_id = 4);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 2, 5
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 2 AND servicio_id = 5);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 2, 6
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 2 AND servicio_id = 6);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 2, 7
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 2 AND servicio_id = 7);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 2, 9
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 2 AND servicio_id = 9);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 3, 1
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 3 AND servicio_id = 1);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 3, 2
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 3 AND servicio_id = 2);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 3, 3
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 3 AND servicio_id = 3);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 3, 4
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 3 AND servicio_id = 4);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 3, 5
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 3 AND servicio_id = 5);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 3, 6
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 3 AND servicio_id = 6);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 3, 7
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 3 AND servicio_id = 7);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 3, 8
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 3 AND servicio_id = 8);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 4, 1
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 4 AND servicio_id = 1);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 4, 2
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 4 AND servicio_id = 2);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 4, 3
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 4 AND servicio_id = 3);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 4, 4
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 4 AND servicio_id = 4);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 4, 5
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 4 AND servicio_id = 5);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 4, 6
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 4 AND servicio_id = 6);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 4, 7
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 4 AND servicio_id = 7);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 4, 8
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 4 AND servicio_id = 8);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 5, 1
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 5 AND servicio_id = 1);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 5, 2
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 5 AND servicio_id = 2);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 5, 3
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 5 AND servicio_id = 3);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 5, 4
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 5 AND servicio_id = 4);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 5, 5
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 5 AND servicio_id = 5);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 5, 6
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 5 AND servicio_id = 6);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 5, 7
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 5 AND servicio_id = 7);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 5, 8
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 5 AND servicio_id = 8);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 6, 1
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 6 AND servicio_id = 1);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 6, 2
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 6 AND servicio_id = 2);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 6, 3
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 6 AND servicio_id = 3);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 6, 4
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 6 AND servicio_id = 4);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 6, 5
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 6 AND servicio_id = 5);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 6, 6
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 6 AND servicio_id = 6);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 6, 7
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 6 AND servicio_id = 7);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 6, 8
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 6 AND servicio_id = 8);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 7, 1
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 7 AND servicio_id = 1);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 7, 2
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 7 AND servicio_id = 2);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 7, 3
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 7 AND servicio_id = 3);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 7, 4
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 7 AND servicio_id = 4);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 7, 5
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 7 AND servicio_id = 5);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 7, 6
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 7 AND servicio_id = 6);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 7, 7
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 7 AND servicio_id = 7);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 8, 1
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 8 AND servicio_id = 1);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 8, 2
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 8 AND servicio_id = 2);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 8, 3
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 8 AND servicio_id = 3);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 8, 4
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 8 AND servicio_id = 4);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 8, 5
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 8 AND servicio_id = 5);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 8, 6
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 8 AND servicio_id = 6);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 8, 7
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 8 AND servicio_id = 7);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 8, 8
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 8 AND servicio_id = 8);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 9, 1
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 9 AND servicio_id = 1);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 9, 2
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 9 AND servicio_id = 2);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 9, 3
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 9 AND servicio_id = 3);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 9, 4
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 9 AND servicio_id = 4);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 9, 5
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 9 AND servicio_id = 5);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 9, 6
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 9 AND servicio_id = 6);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 9, 7
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 9 AND servicio_id = 7);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 9, 8
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 9 AND servicio_id = 8);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 10, 1
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 10 AND servicio_id = 1);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 10, 2
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 10 AND servicio_id = 2);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 10, 3
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 10 AND servicio_id = 3);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 10, 4
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 10 AND servicio_id = 4);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 10, 5
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 10 AND servicio_id = 5);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 10, 6
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 10 AND servicio_id = 6);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 10, 7
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 10 AND servicio_id = 7);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 11, 1
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 11 AND servicio_id = 1);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 11, 2
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 11 AND servicio_id = 2);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 11, 3
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 11 AND servicio_id = 3);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 11, 4
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 11 AND servicio_id = 4);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 11, 5
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 11 AND servicio_id = 5);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 11, 6
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 11 AND servicio_id = 6);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 11, 7
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 11 AND servicio_id = 7);

INSERT INTO hotel_servicios (hotel_id, servicio_id)
SELECT 11, 8
WHERE NOT EXISTS (SELECT 1 FROM hotel_servicios WHERE hotel_id = 11 AND servicio_id = 8);

-- Extras disponibles para reservas
INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Cama extra', 'Cama supletoria para habitación', 25.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Cama extra');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Cuna', 'Cuna para bebé', 15.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Cuna');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Mascota', 'Alojamiento para mascota', 20.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Mascota');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Nevera adicional', 'Nevera extra para la habitación', 10.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Nevera adicional');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Cena romántica', 'Cena romántica en la habitación con champán', 75.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Cena romántica');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Masaje', 'Masaje relajante en la habitación', 80.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Masaje');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Desayuno en habitación', 'Desayuno continental servido en la habitación', 15.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Desayuno en habitación');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Servicio de limpieza extra', 'Limpieza adicional de la habitación', 10.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Servicio de limpieza extra');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Servicio de planchado', 'Planchado de ropa', 15.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Servicio de planchado');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Servicio de niñera', 'Servicio de niñera por horas', 25.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Servicio de niñera');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Traslado aeropuerto', 'Servicio de traslado desde/hacia el aeropuerto', 40.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Traslado aeropuerto');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Toallas extra', 'Juego adicional de toallas', 5.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Toallas extra');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Sábanas extra', 'Juego adicional de sábanas', 5.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Sábanas extra');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Lavandería', 'Servicio completo de lavandería', 30.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Lavandería');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Paquete romántico', 'Decoración romántica, champán y chocolates', 100.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Paquete romántico');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Paquete familiar', 'Actividades para niños y desayuno familiar', 120.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Paquete familiar');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Tour guiado', 'Visita guiada a los lugares más emblemáticos', 45.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Tour guiado');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Alquiler de bicicletas', 'Bicicletas para explorar la ciudad', 20.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Alquiler de bicicletas');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Entrenador personal', 'Sesión de entrenamiento personalizado', 60.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Entrenador personal');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Alquiler de equipamiento deportivo', 'Raquetas, pelotas y material deportivo', 25.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Alquiler de equipamiento deportivo');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Servicio de fotógrafo', 'Sesión fotográfica profesional', 150.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Servicio de fotógrafo');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Alquiler de coche', 'Coche para uso durante la estancia', 80.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Alquiler de coche');

INSERT INTO extras (nombre, descripcion, precio, disponible)
SELECT 'Clase de yoga', 'Sesión privada de yoga en la habitación o gimnasio', 45.00, true
WHERE NOT EXISTS (SELECT 1 FROM extras WHERE nombre = 'Clase de yoga'); 