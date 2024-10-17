USE master
GO

USE PracticaJuvenil
GO

-- EJERCICIO 1: MOSTRAR POR PANTALLA UN LISTADO DE LOS CUATRO LIBROS MAS PRESTADOS Y LOS SOCIOS A LOS QUE HAN SIDO PRESTADOS
CREATE OR ALTER PROCEDURE listadocuatromasprestados AS BEGIN
	DECLARE @nombre varchar(50), @vendidos int, @genero varchar(50), 
	@dnisocio varchar(50), @fechaprestamo date
		
	IF NOT EXISTS (SELECT * FROM libros)
	BEGIN
		RAISERROR ('ERROR: la tabla libros esta vacia', 16, 1)
	END
	ELSE IF NOT EXISTS (SELECT * FROM socios)
	BEGIN
		RAISERROR ('ERROR: la tabla socios esta vacia', 16, 1)
	END
	ELSE IF (SELECT COUNT(DISTINCT RefLibro) FROM prestamos) < 4
	BEGIN
		RAISERROR ('ERROR: hay menos de cuatro libros distintos prestados', 16, 1)
	END
	ELSE BEGIN
		DECLARE cuatrolibros CURSOR FOR
		SELECT TOP 4 L.Nombre, COUNT(P.RefLibro), L.Genero
		FROM prestamos P INNER JOIN Libros L ON P.RefLibro = L.RefLibro
		GROUP BY L.Nombre, L.Genero ORDER BY COUNT(P.RefLibro) DESC

		OPEN cuatrolibros
		
		FETCH NEXT FROM cuatrolibros INTO @nombre, @vendidos, @genero

		WHILE @@FETCH_STATUS = 0
		BEGIN
			PRINT @nombre + ', ' + CONVERT(VARCHAR, @vendidos) + ' libros vendidos, ' + @genero

			DECLARE socios CURSOR FOR
			SELECT Dni, FechaPrestamo FROM prestamos
			WHERE RefLibro IN (SELECT RefLibro FROM libros
			WHERE Nombre = @nombre)

			OPEN socios
			
			FETCH NEXT FROM socios INTO @dnisocio, @fechaprestamo
			WHILE @@FETCH_STATUS = 0
			BEGIN
				PRINT @dnisocio + ', ' + CONVERT(VARCHAR, @fechaprestamo)
				FETCH NEXT FROM socios INTO @dnisocio, @fechaprestamo
			END

			CLOSE socios
			DEALLOCATE socios

			FETCH NEXT FROM cuatrolibros INTO @nombre, @vendidos, @genero
		END

		CLOSE cuatrolibros
		DEALLOCATE cuatrolibros

	END
	
END

-- EJECUTA EL EJ1
EXEC listadocuatromasprestados

-- EJ2: PASA COMO PARAMETRO DE ENTRADA EL NOMBRE DE UNO DE LOS MODULOS EXISTENTES EN LA BD Y VISUALICE EL NOMBRE DE LOS ALUMNOS QUE LO HAN CURSADO JUNTO A SU NOTA.
-- AL FINAL DEL LISTADO, DEBEN APARECER EL Nº DE SUSPENSOS, APROBADOS, NOTABLES Y SOBRESALIENTES
-- ASIMISMO, DEBEN APARECER AL FINAL LOS NOMBRES Y NOTAS DE LOS ALUMNOS QUE TENGAN LA NOTA MAS ALTA Y LA MAS BAJA
-- DEBES COMPROBAR QUE LAS TABLAS TENGAN ALMACENADA INFORMACION Y QUE EXISTA EL MODULO CUYO NOMBRE PASAMOS COMO PARAMETRO AL PROCEDIMIENTO
CREATE OR ALTER PROCEDURE infoalumnos(
@nombreModulo varchar(25)) AS BEGIN

	IF NOT EXISTS (SELECT * FROM NOTAS)
	BEGIN
		RAISERROR ('ERROR: la tabla notas esta vacia', 16, 1)
	END
	ELSE IF NOT EXISTS (SELECT * FROM ALUMNOS)
	BEGIN
		RAISERROR ('ERROR: la tabla alumnos esta vacia', 16, 1)
	END
	ELSE IF NOT EXISTS (SELECT * FROM ASIGNATURAS)
	BEGIN
		RAISERROR ('ERROR: la tabla asignaturas esta vacia', 16, 1)
	END
	ELSE IF NOT EXISTS (SELECT NOMBRE FROM ASIGNATURAS WHERE NOMBRE = @nombreModulo)
	BEGIN
		RAISERROR ('ERROR: la asignatura introducida no existe', 16, 1)
	END
	ELSE BEGIN

		PRINT 'Modulo: ' + @nombreModulo
		DECLARE @nombre varchar(50), @nota int, @suspensos int, @aprobados int, @notables int, @sobresalientes int, @alumalta varchar(50), @alumbaja varchar(50), @notalta int, @notbaja int
		SET @sobresalientes = 0
		SET @notables = 0
		SET @aprobados = 0
		SET @suspensos = 0
		SET @alumalta = ''
		SET @alumbaja = ''
		SET @notalta = 0
		SET @notbaja = 10
		DECLARE nomape CURSOR FOR
		SELECT AL.APENOM, N.NOTA FROM NOTAS N
		INNER JOIN ALUMNOS AL ON N.DNI = AL.DNI
		INNER JOIN ASIGNATURAS ASI ON N.COD = ASI.COD
		WHERE ASI.NOMBRE = @nombreModulo

		OPEN nomape

		FETCH NEXT FROM nomape INTO @nombre, @nota

		WHILE @@FETCH_STATUS = 0
		BEGIN
			IF @nota >= 9
			BEGIN
				SET @sobresalientes = @sobresalientes + 1
			END
			ELSE IF @nota >= 7
			BEGIN
				SET @notables = @notables + 1
			END
			ELSE IF @nota >= 5
			BEGIN
				SET @aprobados = @aprobados + 1
			END
			ELSE
			BEGIN
				SET @suspensos = @suspensos + 1
			END
			IF @nota > @notalta
			BEGIN
				SET @alumalta = @nombre
				SET @notalta = @nota
			END
			IF @nota < @notbaja
			BEGIN
				SET @alumbaja = @nombre
				SET @notbaja = @nota
			END
			PRINT @nombre + ', ' + CONVERT(VARCHAR, @nota)
			FETCH NEXT FROM nomape INTO @nombre, @nota
		END

		CLOSE nomape
		DEALLOCATE nomape

		PRINT 'Sobresalientes: ' + CONVERT(VARCHAR, @sobresalientes)
		PRINT 'Notables: ' + CONVERT(VARCHAR, @notables)
		PRINT 'Aprobados: ' + CONVERT(VARCHAR, @aprobados)
		PRINT 'Suspensos: ' + CONVERT(VARCHAR, @suspensos)
		PRINT 'Nota mas alta: ' + @alumalta + ', ' + CONVERT(VARCHAR, @notalta)
		PRINT 'Nota mas baja: ' + @alumbaja + ', ' + CONVERT(VARCHAR, @notbaja)
	END

END

-- EJECUTA EL EJ2
EXEC infoalumnos 'FOL'
EXEC infoalumnos 'FEZ'

-- EJERCICIO 3A1: REALIZA UN PROCEDIMIENTO PARA ACTUALIZAR LA TABLA PRODUCTOS CON LAS VENTAS REALIZADAS QUE ESTAN EN LA TABLA VENTAS
CREATE OR ALTER PROCEDURE actualizarVentas AS BEGIN
	DECLARE @codproducto varchar(5), @stock int
	DECLARE fila CURSOR FOR
	SELECT CodProducto, Stock FROM PRODUCTOS P
	OPEN fila
	FETCH NEXT FROM fila INTO @codproducto, @stock
	WHILE @@FETCH_STATUS = 0
	BEGIN
		BEGIN TRANSACTION
		BEGIN TRY
			UPDATE PRODUCTOS SET Stock = Stock - (SELECT SUM(UnidadesVendidas) FROM VENTAS WHERE CodProducto = @codproducto)
			IF (SELECT ISNULL(Stock, 0) FROM PRODUCTOS WHERE CodProducto = @codproducto) < (SELECT SUM(UnidadesVendidas) FROM VENTAS WHERE CodProducto = @codproducto)
			BEGIN
				RAISERROR('ERROR: el stock de uno de los productos se quedaria negativo de hacer este update', 16, 1)
				RETURN
			END
			COMMIT TRANSACTION
			PRINT 'Operacion realizada correctamente'
		END TRY
		BEGIN CATCH
			ROLLBACK TRANSACTION
			PRINT 'ERROR: operacion revertida'
		END CATCH
		FETCH NEXT FROM fila INTO @codproducto, @stock
	END
	CLOSE fila
	DEALLOCATE fila
END

-- EJECUTA EL EJ3A1
EXEC actualizarVentas

-- EJERCICIO 3A2: ACTUALIZA LA TABLA PRODUCTOS CON LAS MODIFICACIONES HECHAS EN 3A MEDIANTE TRIGGERS DE LA SIGUIENTE FORMA:
-- SI SE AUMENTAN LAS UNIDADES VENDIDAS DE UNA VENTA YA REALIZADA, SE DEBERA ACTUALIZAR EL STOCK DE LA TABLA PRODUCTOS
-- SI SE REALIZA UNA DEVOLUCION, SE DEBERA ACTUALIZAR EL STOCK DE LA TABLA PRODUCTOS. SI SE DEVUELVEN TODAS LAS UNIDADES, SE DEBE BORRAR ESA VENTA DE LA TABLA VENTAS
CREATE OR ALTER TRIGGER AUTOMAT ON VENTAS AFTER UPDATE AS BEGIN
	SET NOCOUNT ON
	IF UPDATE (UnidadesVendidas)
	BEGIN
		DECLARE @codventa varchar(10), @ventas int, @codprod varchar(10)

		DECLARE lineaVenta CURSOR FOR
		SELECT CodVenta, UnidadesVendidas, CodProducto FROM ventas

		OPEN lineaVenta
		FETCH FROM lineaVenta INTO @codventa, @ventas, @codprod
		WHILE @@FETCH_STATUS = 0
		BEGIN
			IF (SELECT UnidadesVendidas FROM inserted WHERE CodVenta = @codventa) > (SELECT UnidadesVendidas FROM deleted WHERE CodVenta = @codventa)
			BEGIN
				PRINT 'Incremento de ventas. El stock ha sido reducido.'
				UPDATE productos SET Stock = Stock - @ventas WHERE CodProducto = @codprod
			END
			ELSE IF (SELECT UnidadesVendidas FROM inserted WHERE CodVenta = @codventa) < (SELECT UnidadesVendidas FROM deleted WHERE CodVenta = @codventa)
			BEGIN
				PRINT 'Decremento de ventas. El stock ha sido aumentado'
				IF (SELECT UnidadesVendidas FROM inserted WHERE CodVenta = @codventa) < 0
				BEGIN
					PRINT 'ERROR: Cantidad de productos a devolver mayor que las ventas actuales. Se procederan a devolver solamente las unidades restantes'
					UPDATE productos SET Stock = 0 WHERE CodProducto = @codprod
				END
				ELSE BEGIN
					UPDATE productos SET Stock = Stock + @ventas WHERE CodProducto = @codprod
				END
				IF (SELECT UnidadesVendidas FROM inserted WHERE CodVenta = @codventa) = 0
				BEGIN
					PRINT 'Esta venta no tiene unidades vendidas. Se procedera a eliminar la venta en cuestion'
					DELETE FROM ventas WHERE UnidadesVendidas = 0 AND CodVenta = @codventa
				END
			END
			FETCH FROM lineaVenta INTO @codventa, @ventas, @codprod
		END

		CLOSE lineaVenta
		DEALLOCATE lineaVenta

	END
END

-- ACTIVA/DESACTIVA EL EJ3A2
ENABLE TRIGGER AUTOMAT ON VENTAS
DISABLE TRIGGER AUTOMAT ON VENTAS

-- EJERCICIO 3B: REALIZA UN PROCEDIMIENTO QUE PRESENTE POR PANTALLA UN LISTADO DE VENTAS MEDIANTE CURSORES
CREATE OR ALTER PROCEDURE listadoVentasProductos AS BEGIN
	DECLARE @nomProducto varchar(30), @codProducto varchar(10), @unidadesvendidas int, @importe money, @importeTotal money
	DECLARE lineaProducto CURSOR FOR
	SELECT P.Nombre, COUNT(V.UnidadesVendidas) * P.PrecioUnitario FROM VENTAS V
	INNER JOIN PRODUCTOS P ON P.CodProducto = V.CodProducto
	GROUP BY P.Nombre, P.PrecioUnitario
	OPEN lineaProducto
	FETCH NEXT FROM lineaProducto INTO @nomProducto, @importeTotal
	WHILE @@FETCH_STATUS = 0
	BEGIN
		PRINT 'Linea Producto: ' + @nomProducto
		DECLARE datosProducto CURSOR FOR
		SELECT V.CodProducto, V.UnidadesVendidas, P.PrecioUnitario * V.UnidadesVendidas FROM VENTAS V
		INNER JOIN PRODUCTOS P ON P.CodProducto = V.CodProducto
		WHERE P.Nombre = @nomProducto
		OPEN datosProducto
		FETCH NEXT FROM datosProducto INTO @codProducto, @unidadesVendidas, @importe
		WHILE @@FETCH_STATUS = 0
		BEGIN
			PRINT CONCAT('Cod. producto: ' + @codProducto, ' | Unidades vendidas: ', @unidadesVendidas, ' | Importe total: ', @importe)
			FETCH NEXT FROM datosProducto INTO @codProducto, @unidadesVendidas, @importe
		END
		CLOSE datosProducto
		DEALLOCATE datosProducto
		PRINT 'Importe total ' + @nomProducto + ': ' + CONVERT(VARCHAR, @importeTotal)
		FETCH NEXT FROM lineaProducto INTO @nomProducto, @importeTotal
	END
	CLOSE lineaProducto
	DEALLOCATE lineaProducto
END

-- EJECUTA EL EJ3B
EXEC listadoVentasProductos