--CREAS LA TABLA practica

CREATE TABLE persona(
	id SERIAL ,
	nombre VARCHAR(255),
	foto TEXT
);

INSERT INTO persona (nombre, foto)
VALUES ('Juan Pérez', 'instalarangular.PNG'),
		 ('Juan Pérez', 'paisaje.jpeg');

SELECT * FROM persona;