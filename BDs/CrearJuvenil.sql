USE master
GO

drop database PracticaJuvenil
CREATE DATABASE PracticaJuvenil
Use PracticaJuvenil
--Script Ejercicio 1 Pr�ctica Juvenil
--DROP TABLE socios;

CREATE TABLE socios
(
	Dni              VARCHAR(10) CONSTRAINT s_dni_no_nulo NOT NULL,
	Nombre           VARCHAR(20) CONSTRAINT s_nom_no_nulo NOT NULL,
	Direccion        VARCHAR(20),
	Penalizaciones   int DEFAULT 0,
	CONSTRAINT socios_pk PRIMARY KEY (Dni)
);


--DROP TABLE libros;

CREATE TABLE libros
(
	RefLibro         VARCHAR(10) CONSTRAINT l_ref_no_nula NOT NULL,
	Nombre           VARCHAR(30) CONSTRAINT l_nom_no_nulo NOT NULL,
	Autor            VARCHAR(20) CONSTRAINT l_aut_no_nulo NOT NULL,
	Genero           VARCHAR(10),
	AnoPublicacion   int, 
	Editorial        VARCHAR(10),
	CONSTRAINT libros_pk PRIMARY KEY (RefLibro)
);


--DROP TABLE prestamos;

CREATE TABLE prestamos
(
	Dni                  VARCHAR(10) CONSTRAINT p_dni_no_nulo NOT NULL,
	RefLibro             VARCHAR(10) CONSTRAINT p_lib_no_nulo NOT NULL,
	FechaPrestamo        DATE         CONSTRAINT p_fec_no_nula NOT NULL,
	Duracion             int    DEFAULT(24),
	CONSTRAINT Dni_Ref_fech_pk  PRIMARY KEY(Dni, RefLibro, FechaPrestamo),
	CONSTRAINT Dni_fk 	    FOREIGN KEY (Dni) references socios,
	CONSTRAINT Ref_fk 	    FOREIGN KEY (RefLibro) references libros
);

-- INSERTS

INSERT INTO socios VALUES ('111-A', 'David',   'Sevilla Este', 2);
INSERT INTO socios VALUES ('222-B', 'Mariano', 'Los Remedios', 3);

INSERT INTO socios (DNI, Nombre, Direccion)
VALUES ('333-C', 'Raul',    'Triana'      );

INSERT INTO socios (DNI, Nombre, Direccion)
VALUES ('444-D', 'Rocio',   'La Oliva'    );

INSERT INTO socios VALUES ('555-E', 'Marilo',  'Triana',       2);
INSERT INTO socios VALUES ('666-F', 'Benjamin','Montequinto',  5);

INSERT INTO socios (DNI, Nombre, Direccion)
VALUES ('777-G', 'Carlos',  'Los Remedios');

INSERT INTO socios VALUES ('888-H', 'Manolo',  'Montequinto',  2);


INSERT INTO libros
VALUES('E-1', 'El valor de educar', 'Savater',    'Ensayo', 1994, 'Alfaguara');
INSERT INTO libros
VALUES('N-1', 'El Quijote',         'Cervantes',  'Novela', 1602, 'Anagrama');
INSERT INTO libros
VALUES('E-2', 'La Republica',       'Plat�n',     'Ensayo', -230, 'Anagrama');
INSERT INTO libros
VALUES('N-2', 'Tombuctu',           'Auster',     'Novela', 1998, 'Planeta');
INSERT INTO libros
VALUES('N-3', 'Todos los nombres',  'Saramago',   'Novela', 1995, 'Planeta');
INSERT INTO libros
VALUES('E-3', 'Etica para Amador',  'Savater',    'Ensayo', 1991, 'Alfaguara');
INSERT INTO libros
VALUES('P-1', 'Rimas y Leyendas',   'Becquer',    'Poesia', 1837, 'Anagrama');
INSERT INTO libros
VALUES('P-2', 'Las flores del mal', 'Baudelaire', 'Poesia', 1853, 'Anagrama');
INSERT INTO libros
VALUES('P-3', 'El fulgor',          'Valente',    'Poesia', 1998, 'Alfaguara');
INSERT INTO libros
VALUES('N-4', 'Lolita',             'Nabokov',    'Novela', 1965, 'Planeta');
INSERT INTO libros
VALUES('C-1', 'En salvaje compa�ia','Rivas',      'Cuento', 2001, 'Alfaguara');


INSERT INTO prestamos VALUES('111-A','E-1', '17/12/00',24);
INSERT INTO prestamos VALUES('333-C','C-1', '15/12/01',48);
INSERT INTO prestamos VALUES('111-A','N-1', '17/12/01',24);
INSERT INTO prestamos VALUES('444-D','E-1', '17/12/01',48);
--INSERT INTO prestamos VALUES('111-A','C-2', '17/12/01',72);

INSERT INTO prestamos (DNI, RefLibro, FechaPrestamo) 
VALUES('777-G','N-1', '07/12/01');

INSERT INTO prestamos VALUES('111-A','N-1', '16/12/01',48);

-- EJERCICIO 2

CREATE TABLE ALUMNOS (
DNI VARCHAR(10) NOT NULL PRIMARY KEY,
APENOM VARCHAR(30),
DIREC VARCHAR(30),
POBLA VARCHAR(15),
TELEF VARCHAR(10)
)

CREATE TABLE ASIGNATURAS (
COD INT NOT NULL PRIMARY KEY,
NOMBRE VARCHAR(25)
)

CREATE TABLE NOTAS (
DNI VARCHAR(10) NOT NULL,
COD INT NOT NULL,
NOTA int,
CONSTRAINT PK_NOTAS PRIMARY KEY (DNI, COD),
CONSTRAINT FK_NOTAS_ALUMNOS FOREIGN KEY (DNI) REFERENCES ALUMNOS(DNI),
CONSTRAINT FK_NOTAS_ASIGNATURAS FOREIGN KEY (COD) REFERENCES ASIGNATURAS(COD)
)

INSERT INTO ASIGNATURAS VALUES (1,'Prog. Leng. Estr.');
INSERT INTO ASIGNATURAS VALUES (2,'Sist. Informáticos');
INSERT INTO ASIGNATURAS VALUES (3,'Análisis');
INSERT INTO ASIGNATURAS VALUES (4,'FOL');
INSERT INTO ASIGNATURAS VALUES (5,'RET');
INSERT INTO ASIGNATURAS VALUES (6,'Entornos Gráficos');
INSERT INTO ASIGNATURAS VALUES (7,'Aplic. Entornos 4ªGen');
INSERT INTO ALUMNOS VALUES
('12344345','Alcalde García, Elena', 'C/Las Matas, 24','Madrid','917766545');
INSERT INTO ALUMNOS VALUES
('4448242','Cerrato Vela, Luis', 'C/Mina 28 - 3A', 'Madrid','916566545');
INSERT INTO ALUMNOS VALUES
('56882942','Díaz Fernández, María', 'C/Luis Vives 25',
'Móstoles','915577545');
INSERT INTO NOTAS VALUES('12344345', 1,6);
INSERT INTO NOTAS VALUES('12344345', 2,5);
INSERT INTO NOTAS VALUES('12344345', 3,6);
INSERT INTO NOTAS VALUES('4448242', 4,6);
INSERT INTO NOTAS VALUES('4448242', 5,8);
INSERT INTO NOTAS VALUES('4448242', 6,4);
INSERT INTO NOTAS VALUES('4448242', 7,5);
INSERT INTO NOTAS VALUES('56882942', 5,7);
INSERT INTO NOTAS VALUES('56882942', 6,8);
INSERT INTO NOTAS VALUES('56882942', 7,9);

-- EJERCICIO 3
CREATE TABLE productos (
CodProducto VARCHAR(10) CONSTRAINT p_cod_no_nulo NOT NULL,
Nombre VARCHAR(20) CONSTRAINT p_nom_no_nulo NOT NULL,
LineaProducto VARCHAR(10),
PrecioUnitario INT,
Stock INT,
PRIMARY KEY (CodProducto)
);

CREATE TABLE ventas (
CodVenta VARCHAR(10) CONSTRAINT cod_no_nula NOT NULL,
CodProducto VARCHAR(10) CONSTRAINT pro_no_nulo NOT NULL,
FechaVenta DATE,
UnidadesVendidas INT,
PRIMARY KEY (CodVenta),
CONSTRAINT FK_VENTAS_PRODUCTOS FOREIGN KEY (CodProducto) REFERENCES productos(CodProducto)
);

INSERT INTO productos VALUES ('1','Procesador P133', 'Proc',15000,20);
INSERT INTO productos VALUES ('2','Placa base VX', 'PB', 18000,15);
INSERT INTO productos VALUES ('3','Simm EDO 16Mb', 'Memo', 7000,30);
INSERT INTO productos VALUES ('4','Disco SCSI 4Gb', 'Disc',38000, 5);
INSERT INTO productos VALUES ('5','Procesador K6-2', 'Proc',18500,10);
INSERT INTO productos VALUES ('6','Disco IDE 2.5Gb', 'Disc',20000,25);
INSERT INTO productos VALUES ('7','Procesador MMX', 'Proc',15000, 5);
INSERT INTO productos VALUES ('8','Placa Base Atlas','PB', 12000, 3);
INSERT INTO productos VALUES ('9','DIMM SDRAM 32Mb', 'Memo',17000,12);
INSERT INTO ventas VALUES('V1', '2', '22/09/97',2);
INSERT INTO ventas VALUES('V2', '4', '22/09/97',1);
INSERT INTO ventas VALUES('V3', '6', '23/09/97',3);
INSERT INTO ventas VALUES('V4', '5', '26/09/97',5);
INSERT INTO ventas VALUES('V5', '9', '28/09/97',3);
INSERT INTO ventas VALUES('V6', '4', '28/09/97',1);
INSERT INTO ventas VALUES('V7', '6', '02/10/97',2);
INSERT INTO ventas VALUES('V8', '6', '02/10/97',1);
INSERT INTO ventas VALUES('V9', '2', '04/10/97',4);
INSERT INTO ventas VALUES('V10','9', '04/10/97',4);
INSERT INTO ventas VALUES('V11','6', '05/10/97',2);
INSERT INTO ventas VALUES('V12','7', '07/10/97',1);
INSERT INTO ventas VALUES('V13','4', '10/10/97',3);
INSERT INTO ventas VALUES('V14','4', '16/10/97',2);
INSERT INTO ventas VALUES('V15','3', '18/10/97',3);
INSERT INTO ventas VALUES('V16','4', '18/10/97',5);
INSERT INTO ventas VALUES('V17','6', '22/10/97',2);
INSERT INTO ventas VALUES('V18','6', '02/11/97',2);
INSERT INTO ventas VALUES('V19','2', '04/11/97',3);
INSERT INTO ventas VALUES('V20','9', '04/12/97',3);