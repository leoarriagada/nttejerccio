CREATE TABLE usuario (
   id INT NOT NULL,
   nombre VARCHAR(50) NOT NULL,
   correo VARCHAR(50) NOT NULL,
   password VARCHAR(50) NOT NULL,
   creado datetime NOT NULL,
   modificado datetime,
   last_login datetime,
   token VARCHAR(50) NOT NULL,
   activo bit not null,
   PRIMARY KEY (id) 
);
CREATE UNIQUE INDEX index_correo on usuario (correo);
CREATE TABLE telefono (
   id INT NOT NULL,
   id_usuario int not null,
   numero VARCHAR(15) NOT NULL,
   codciudad VARCHAR(5) NOT NULL,
   codpais VARCHAR(5) NOT NULL,
   PRIMARY KEY (id) ,
   foreign key (id_usuario) references usuario(id)
   
);

INSERT INTO usuario VALUES (1, 
	 'admin', 
	 'admin', 
	 'admin', 
	 CURRENT_TIMESTAMP, 
	 null, 
	 null, 'admin', 1);