--drop database TransactSql
CREATE DATABASE TransactSql
GO
use TransactSql


--
-- Estructura de tabla para la tabla 'carrera'
--
if exists (select * from sysobjects where name = 'carrera' and xtype = 'U')
DROP TABLE carrera

CREATE TABLE carrera (
  Cnocarrera char(4)  NOT NULL,
  Cnombre varchar(15)  NOT NULL,
  Cedificio varchar(6)  NOT NULL,
  Ccoord char(8)  NOT NULL,
  PRIMARY KEY (Cnocarrera)
) ;

--
-- Volcado de datos para la tabla 'carrera'
--

INSERT INTO carrera (Cnocarrera, Cnombre, Cedificio, Ccoord) VALUES
('32', 'computacion', 'A202', '75656566'),
('38', 'Electronica', 'A501', '75521245'),
('40', 'Mecanica', 'A501', '75165232');

if exists (select * from sysobjects where name = 'academic' and xtype = 'U')
DROP TABLE academic


CREATE TABLE academic (
  Anoemplead char(8)   NOT NULL,
  Anombre varchar(42)   NOT NULL,
  Adomicilio varchar(40)   NOT NULL,
  Afecalta date NOT NULL,
  Anumayuda integer NOT NULL,
  Asueldo decimal(5,2) NOT NULL,
  Anocarrera char(4) NOT NULL,
  PRIMARY KEY (Anoemplead),
  FOREIGN KEY (Anocarrera) REFERENCES CARRERA (Cnocarrera)
) 

--
-- Volcado de datos para la tabla 'academic'
--

INSERT INTO academic (Anoemplead, Anombre, Adomicilio, Afecalta, Anumayuda, Asueldo, Anocarrera) VALUES
('72654545', 'Juan Mendez', 'Aragon', '1985-02-01', 2, '999.99', '32'),
('75165232', 'Alberto Alvarez', 'Ecatepec', '1991-02-01', 8, '999.99', '40'),
('75212122', 'Carlos Cuenca', 'Aragon', '1982-01-01', 5, '999.99', '38'),
('75521245', 'Armando Cruz', 'Cd. Neza', '1983-11-01', 2, '999.99', '38'),
('75656566', 'Pedro Benitez', 'Cd. Neza', '1983-02-02', 2, '999.99', '32');

-- --------------------------------------------------------


--
-- Estructura de tabla para la tabla 'estudian'
--
if exists (select * from sysobjects where name = 'estudian' and xtype = 'U')
DROP TABLE estudian


CREATE TABLE estudian (
  Enocuenta char(8)  NOT NULL,
  Enombre varchar(40)  NOT NULL,
  Edomicilio varchar(40)  NOT NULL,
  Etelefono char(10)  DEFAULT NULL,
  Efecnacim date NOT NULL,
  Enocarrera char(4)  NOT NULL,
  PRIMARY KEY (Enocuenta),
  FOREIGN KEY (Enocarrera) REFERENCES CARRERA (Cnocarrera)
  
) ;

--
-- Volcado de datos para la tabla 'estudian'
--

INSERT INTO estudian (Enocuenta, Enombre, Edomicilio, Etelefono, Efecnacim, Enocarrera) VALUES
('82323233', 'Pedro Sauza', 'Ecatepec', NULL, '1967-12-11', '40'),
('84254321', 'Carolina Biset', 'ARAGON', '7715421', '1968-02-01', '32'),
('84254322', 'Alejandro Sauza', 'Cd. Neza', '8545454', '1969-05-05', '38'),
('84321212', 'Juan Bisset', 'Aragon', '7212212', '1953-02-05', '32');

-- --------------------------------------------------------

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla 'materia'
--

if exists (select * from sysobjects where name = 'materia' and xtype = 'U')
DROP TABLE materia

CREATE TABLE materia (
  Mnomateria char(4)  NOT NULL,
  Mnombre varchar(42)  NOT NULL,
  Mcred smallint DEFAULT NULL,
  Mcostolab decimal(5,2) DEFAULT NULL,
  Mnocarrera char(4)  NOT NULL,
  PRIMARY KEY (Mnomateria),
  FOREIGN KEY (Mnocarrera) REFERENCES CARRERA (Cnocarrera)
) ;

--
-- Volcado de datos para la tabla materia
--

INSERT INTO materia (Mnomateria, Mnombre, Mcred, Mcostolab, Mnocarrera) VALUES
('0024', 'Circuitos Electricos', 10, '150.00', '40'),
('0028', 'Analisis Dinamico de Maquinas', 8, '100.00', '38'),
('0056', 'Estructuras Discretas', 7, '0.00', '32'),
('0076', 'Bases de Datos', 8, '100.00', '32'),
('0119', 'Estructuras de Datos', 8, '0.00', '32'),
('0130', 'Elementos de Maquinas', 8, '50.00', '38'),
('0134', 'Sistemas Digitales', 8, '50.00', '32'),
('0138', 'Dispositivos Electronicos', 10, '90.00', '40'),
('0559', 'Memorias y Perifericos', 10, '100.00', '32'),
('0561', 'Microcomputadoras', 10, '500.00', '32');

--
-- Estructura de tabla para la tabla 'grupo'
--
if exists (select * from sysobjects where name = 'grupo' and xtype = 'U')
DROP TABLE grupo

CREATE TABLE grupo (
  Gnogrupo char(4)  NOT NULL,
  Gnomateria char(4)  NOT NULL,
  Gnomaestro char(8)  DEFAULT NULL,
  Gdias varchar(6)  NOT NULL,
  Ghora varchar(15)  NOT NULL,
  Gsalon varchar(6)  NOT NULL,
  PRIMARY KEY (Gnogrupo),
  FOREIGN KEY (Gnomateria) REFERENCES MATERIA (Mnomateria)

) ;

--
-- Volcado de datos para la tabla 'grupo'
--

INSERT INTO grupo (Gnogrupo, Gnomateria, Gnomaestro, Gdias, Ghora, Gsalon) VALUES
('1157', '0076', '72654545', 'LUMIVI', '11:30 13:30', 'A211'),
('1158', '0134', NULL, 'MAJU', '07:00 8:30', 'A521'),
('1159', '0119', '75656566', 'SA', '07:00 12:00', 'A121'),
('2501', '0138', '75165232', 'LUMIVI', '17:00 18:30', 'A213'),
('2504', '0024', '75165232', 'MAJU', '17:00 19:00', 'A212'),
('2705', '0028', '75212122', 'MAJU', '17:00 19:00', 'A525'),
('2706', '0130', '75521245', 'LUMIVI', '17:00 18:30', 'A808');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla 'inscrip'
--
if exists (select * from sysobjects where name = 'inscrip' and xtype = 'U')
DROP TABLE inscrip


CREATE TABLE inscrip (
  Inogrupo char(4)  DEFAULT NULL,
  Inocuenta char(10)  NOT NULL,
  Ifecalta date NOT NULL,
  Ihoraalta char(6)  NOT NULL,
  Inocarrera char(4)  NOT NULL,
  FOREIGN KEY (Inocarrera) REFERENCES CARRERA (Cnocarrera)
) ;

--
-- Volcado de datos para la tabla inscrip
--

INSERT INTO inscrip (Inogrupo, Inocuenta, Ifecalta, Ihoraalta, Inocarrera) VALUES
('1158', '84254321', '1994-02-01', '1130', '32'),
('1158', '84254321', '1994-02-01', '1130', '32'),
('1157', '84321212', '1994-02-02', '1150', '32'),
('1158', '84321212', '1994-02-02', '1150', '32'),
('1157', '84254322', '1994-02-15', '1550', '38'),
('1158', '84254322', '1994-02-15', '1550', '38'),
('1157', '82323233', '1994-02-16', '1420', '40'),
('1158', '82323233', '1994-02-16', '1420', '40');


/*
if exists (select * from sysobjects where name = 'cmateria' and xtype = 'U')
DROP TABLE cmateria

CREATE TABLE cmateria (
  Mnomateria char(4)  NOT NULL,
  Mnombre varchar(42)  NOT NULL,
  Mcred smallint DEFAULT NULL,
  Mcostolab decimal(5,2) DEFAULT NULL,
  Mfees decimal(5,2) DEFAULT NULL,
  Mnocarrera char(4)  NOT NULL,
  PRIMARY KEY (Mnomateria),
  FOREIGN KEY (Mnocarrera) REFERENCES CARRERA (Cnocarrera)
) ;--
*/