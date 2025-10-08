-- TABLA DE USUARIOS
CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL
);


-- TABLA DE LOS TABLEROS
CREATE TABLE Tablero (
    id_tablero INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT, 
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_usuario_creador INT NOT NULL,
    FOREIGN KEY (id_usuario_creador) REFERENCES Usuario (id_usuario)
);


-- TABLA INTERMEDIA PARA N:M USUARIOS-TABLERO
CREATE TABLE MiembroTablero (
    id_usuario INT NOT NULL,
    id_tablero INT NOT NULL,
    rol VARCHAR(20) DEFAULT 'lector',
    PRIMARY KEY (id_usuario, id_tablero),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_tablero) REFERENCES Tablero(id_tablero)
);


-- TABLA DE LISTAS
CREATE TABLE Lista (
    id_lista INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    orden INT NOT NULL,
    id_tablero INT NOT NULL,
    FOREIGN KEY (id_tablero) REFERENCES Tablero(id_tablero)
);


-- TABLA DE TARJETAS
CREATE TABLE Tarjeta (
    id_tarjeta INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    -- 'TEXT' es suficiente en MySQL para descripciones largas.
    descripcion TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_vencimiento DATETIME, -- Usamos DATETIME para vencimiento sin valor predeterminado
    orden INT NOT NULL,
    id_lista INT NOT NULL,
    FOREIGN KEY (id_lista) REFERENCES Lista(id_lista)
);


-- TABLA DE COMENTARIOS
CREATE TABLE Comentario (
    id_comentario INT AUTO_INCREMENT PRIMARY KEY,
    -- 'TEXT' es suficiente en MySQL para contenido largo.
    contenido TEXT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_usuario INT NOT NULL,
    id_tarjeta INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_tarjeta) REFERENCES Tarjeta(id_tarjeta)
);


-- TABLA DE ETIQUETAS
CREATE TABLE Etiqueta (
    id_etiqueta INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    color VARCHAR(20),
    id_tablero INT NOT NULL,
    FOREIGN KEY (id_tablero) REFERENCES Tablero(id_tablero)
);


-- TABLA INTERMEDIA PARA N:M TARJETAS-ETIQUETAS
CREATE TABLE TarjetaEtiqueta (
    id_tarjeta INT NOT NULL,
    id_etiqueta INT NOT NULL,
    PRIMARY KEY (id_tarjeta, id_etiqueta),
    FOREIGN KEY (id_tarjeta) REFERENCES Tarjeta(id_tarjeta),
    FOREIGN KEY (id_etiqueta) REFERENCES Etiqueta(id_etiqueta)
);


-- TABLA DE ARCHIVOS (OPCIONAL)
CREATE TABLE Archivo (
    id_archivo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    url TEXT NOT NULL,
    id_tarjeta INT NOT NULL,
    FOREIGN KEY (id_tarjeta) REFERENCES Tarjeta(id_tarjeta)
);
