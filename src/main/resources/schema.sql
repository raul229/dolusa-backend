-- Schema aligned to /home/raul/Descargas/WhatSie/files/dolusa_bd.sql

CREATE TABLE IF NOT EXISTS tipo_area (
    id_tipo     INT          AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(50)  NOT NULL UNIQUE,
    descripcion VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS area (
    id_area      INT           AUTO_INCREMENT PRIMARY KEY,
    id_tipo      INT           NOT NULL,
    nombre       VARCHAR(100)  NOT NULL,
    piso         TINYINT       NOT NULL DEFAULT 1,
    capacidad    SMALLINT      NOT NULL,
    precio_base  DECIMAL(8,2)  NOT NULL DEFAULT 0.00,
    reservable   TINYINT(1)    NOT NULL DEFAULT 1,
    estado       ENUM('activa','inactiva') NOT NULL DEFAULT 'activa',
    CONSTRAINT fk_area_tipo FOREIGN KEY (id_tipo) REFERENCES tipo_area(id_tipo)
);

CREATE TABLE IF NOT EXISTS evento (
    id_evento   INT           AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(150)  NOT NULL,
    fecha       DATE          NOT NULL,
    hora_inicio TIME          NOT NULL,
    hora_fin    TIME          NOT NULL,
    descripcion TEXT,
    estado      ENUM('programado','activo','finalizado','cancelado') NOT NULL DEFAULT 'programado',
    creado_en   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS evento_area (
    id_evento       INT           NOT NULL,
    id_area         INT           NOT NULL,
    precio_especial DECIMAL(8,2),
    PRIMARY KEY (id_evento, id_area),
    CONSTRAINT fk_ea_evento FOREIGN KEY (id_evento) REFERENCES evento(id_evento),
    CONSTRAINT fk_ea_area   FOREIGN KEY (id_area)   REFERENCES area(id_area)
);

CREATE TABLE IF NOT EXISTS cliente (
    id_cliente  INT           AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100)  NOT NULL,
    apellido    VARCHAR(100)  NOT NULL,
    dni         CHAR(8)       NOT NULL UNIQUE,
    celular     VARCHAR(15)   NOT NULL,
    email       VARCHAR(150),
    creado_en   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS estado_reserva (
    id_estado   INT          AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(50)  NOT NULL UNIQUE
);

-- Must exist before reserva/historial_reserva due to FK
CREATE TABLE IF NOT EXISTS usuario_sistema (
    id_usuario  INT           AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100)  NOT NULL,
    usuario     VARCHAR(50)   NOT NULL UNIQUE,
    password    VARCHAR(255)  NOT NULL,
    rol         ENUM('admin','cajero') NOT NULL DEFAULT 'cajero',
    activo      TINYINT(1)    NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS reserva (
    id_reserva        INT       AUTO_INCREMENT PRIMARY KEY,
    id_cliente        INT       NOT NULL,
    id_evento         INT       NOT NULL,
    id_area           INT       NOT NULL,
    id_estado         INT       NOT NULL,
    id_usuario        INT       NOT NULL,
    cantidad_personas SMALLINT  NOT NULL DEFAULT 1,
    observacion       VARCHAR(300),
    creado_en         DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_res_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    CONSTRAINT fk_res_evento  FOREIGN KEY (id_evento)  REFERENCES evento(id_evento),
    CONSTRAINT fk_res_area    FOREIGN KEY (id_area)    REFERENCES area(id_area),
    CONSTRAINT fk_res_estado  FOREIGN KEY (id_estado)  REFERENCES estado_reserva(id_estado),
    CONSTRAINT fk_res_usuario FOREIGN KEY (id_usuario) REFERENCES usuario_sistema(id_usuario)
);

CREATE TABLE IF NOT EXISTS historial_reserva (
    id_historial  INT       AUTO_INCREMENT PRIMARY KEY,
    id_reserva    INT       NOT NULL,
    id_estado_ant INT,
    id_estado_nvo INT       NOT NULL,
    id_usuario    INT       NOT NULL,
    fecha_cambio  DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    nota          VARCHAR(300),
    CONSTRAINT fk_hr_reserva    FOREIGN KEY (id_reserva)    REFERENCES reserva(id_reserva),
    CONSTRAINT fk_hr_estado_ant FOREIGN KEY (id_estado_ant) REFERENCES estado_reserva(id_estado),
    CONSTRAINT fk_hr_estado_nvo FOREIGN KEY (id_estado_nvo) REFERENCES estado_reserva(id_estado),
    CONSTRAINT fk_hr_usuario    FOREIGN KEY (id_usuario)    REFERENCES usuario_sistema(id_usuario)
);

CREATE TABLE IF NOT EXISTS metodo_pago (
    id_metodo INT         AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS pago (
    id_pago      INT           AUTO_INCREMENT PRIMARY KEY,
    id_reserva   INT           NOT NULL UNIQUE,
    id_metodo    INT           NOT NULL,
    monto        DECIMAL(8,2)  NOT NULL,
    estado_pago  ENUM('pendiente','completado','anulado') NOT NULL DEFAULT 'completado',
    comprobante  VARCHAR(50)   NOT NULL,
    fecha_pago   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pago_reserva FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva),
    CONSTRAINT fk_pago_metodo  FOREIGN KEY (id_metodo)  REFERENCES metodo_pago(id_metodo)
);
