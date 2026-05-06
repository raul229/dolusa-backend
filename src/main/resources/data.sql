INSERT IGNORE INTO tipo_area (id_tipo, nombre, descripcion) VALUES
  (1, 'Pista',    'Área central de baile'),
  (2, 'Barra',    'Servicio de bebidas y cócteles'),
  (3, 'VIP',      'Zona exclusiva con servicio dedicado'),
  (4, 'Terraza',  'Espacio al aire libre'),
  (5, 'Lounge',   'Zona chill con sofás'),
  (6, 'Servicio', 'Uso interno — no reservable');

INSERT IGNORE INTO area (id_area, id_tipo, nombre, piso, capacidad, precio_base, reservable, estado) VALUES
  (1, 1, 'Pista de baile',        1, 200, 15.00, 1, 'activa'),
  (2, 2, 'Barra principal',       1,  60, 20.00, 1, 'activa'),
  (3, 6, 'Entrada / Recepción',   1,  40,  0.00, 0, 'activa'),
  (4, 6, 'Baños',                 1,   0,  0.00, 0, 'activa'),
  (5, 3, 'Zona VIP',              2,  30, 80.00, 1, 'activa'),
  (6, 4, 'Terraza',               2,  80, 30.00, 1, 'activa'),
  (7, 5, 'Zona lounge',           2,  50, 25.00, 1, 'activa'),
  (8, 6, 'DJ Booth',              2,   0,  0.00, 0, 'activa');

INSERT IGNORE INTO estado_reserva (id_estado, nombre) VALUES
  (1, 'pendiente'),
  (2, 'confirmada'),
  (3, 'asistio'),
  (4, 'cancelada');

INSERT IGNORE INTO metodo_pago (id_metodo, nombre) VALUES
  (1, 'Efectivo'),
  (2, 'Yape'),
  (3, 'Transferencia'),
  (4, 'Tarjeta');

-- Default admin user for dev.
INSERT IGNORE INTO usuario_sistema (id_usuario, nombre, usuario, password, rol, activo) VALUES
  (1, 'Administrador', 'admin', '{noop}admin123', 'admin', 1);
