USE TransactSQL
GO

-- PLAN: CURSOR PARA LOS QUE ENOCARRERA SEAN 32

-- 1. DECLARA LAS VARIABLES QUE CONTENDRAN LOS VALORES DE LAS COLUMNAS
DECLARE @enombre varchar(40), @edomicilio varchar(40), @efecnacim date, @carrera varchar(40)

-- 2. DECLARA EL CURSOR
DECLARE estudian32 CURSOR FOR
SELECT Enombre, Edomicilio, Efecnacim, Cnombre FROM estudian
INNER JOIN carrera ON estudian.Enocarrera = carrera.Cnocarrera
WHERE Enocarrera = 32

-- 3. ABRE EL CURSOR
OPEN estudian32

-- 4. LEE EL PRIMER REGISTRO
FETCH NEXT FROM estudian32 INTO @enombre, @edomicilio, @efecnacim, @carrera

-- 5. BUCLE: LEE CADA REGISTRO HASTA QUE NO HAYAN MAS REGISTROS EN EL CURSOR
WHILE @@FETCH_STATUS = 0
BEGIN
PRINT 'El estudiante en la carrera de ' + @carrera + ', cuyo nombre es ' + @enombre + ', con el domicilio '
+ @edomicilio + ' y nacido en la fecha ' + CONVERT(VARCHAR(10), @efecnacim) + ' oficialmente
no tiene derechos por negro'
FETCH NEXT FROM estudian32 INTO @enombre, @edomicilio, @efecnacim, @carrera
END

-- 6. CIERRA Y DESALOCA EL CURSOR
CLOSE estudian32
DEALLOCATE estudian32