USE master
GO

USE SCOTT
GO

-- EJERCICIO 1: RECIBE UN NOMBRE DE UN DEPARTAMENTO Y DEVUELVE SU CODIGO
CREATE OR ALTER FUNCTION DeveloperCodDept(@dept varchar(50))
RETURNS int
AS BEGIN
	DECLARE @res int
	SELECT @res = DEPTNO FROM DEPT
	WHERE DNAME = @dept
	RETURN @res
END

-- EJECUTA EL EJ1
SELECT dbo.DeveloperCodDept('ACCOUNTING') AS 'DEPTNO'

-- EJERCICIO 2: RECIBE UN NOMBRE DE DEPARTAMENTO Y MUESTRA EN PANTALLA EL Nº DE EMPLEADOS DE DICHO DEPARTAMENTO
CREATE OR ALTER PROCEDURE HallarNumEmp(
	@dept varchar(50)
) AS BEGIN
	SET NOCOUNT ON
	SELECT COUNT(E.EMPNO) FROM EMP E
	INNER JOIN DEPT D ON E.DEPTNO = D.DEPTNO
	WHERE E.DEPTNO = dbo.DeveloperCodDept(@dept)
END

-- EJECUTA EL EJ2
EXEC HallarNumEmp 'ACCOUNTING'

-- EJERCICIO 3: Realiza una función llamada CalcularCosteSalarial que reciba un nombre de departamento y devuelva la suma de los salarios y comisiones de los empleados de dicho departamento. Trata las excepciones que consideres necesarias.
CREATE OR ALTER FUNCTION CalcularCosteSalarial(@dept varchar(50))
RETURNS money
AS BEGIN
	DECLARE @res money
	DECLARE @sal money
	DECLARE @comm money
	SELECT @sal = E.SAL FROM EMP E
	INNER JOIN DEPT D ON E.DEPTNO = D.DEPTNO
	WHERE E.DEPTNO = dbo.DeveloperCodDept(@dept)
	SELECT @comm = ISNULL(E.COMM, 0) FROM EMP E
	INNER JOIN DEPT D ON E.DEPTNO = D.DEPTNO
	WHERE E.DEPTNO = dbo.DeveloperCodDept(@dept)
	SET @res = @sal + @comm
	IF @res < 0
	BEGIN
		SET @res = 0
	END
	RETURN @res
END

-- EJECUTA EL EJ3
SELECT dbo.CalcularCosteSalarial('RESEARCH')

-- EJERCICIO 4: Realiza un procedimiento MostrarCostesSalariales que muestre los nombres de todos los departamentos y el coste salarial de cada uno de ellos. Puedes usar la función del ejercicio 3.
CREATE OR ALTER PROCEDURE MostrarCostesSalariales AS BEGIN
	
	SET NOCOUNT ON

	-- 1. DECLARA LAS VARIABLES QUE CONTENDRAN LOS VALORES DE LAS COLUMNAS
	DECLARE @dnombre varchar(50), @esal money

	-- 2. DECLARA EL CURSOR
	DECLARE costsal CURSOR FOR
	SELECT D.DNAME, SUM(E.SAL) FROM DEPT D
	INNER JOIN EMP E ON D.DEPTNO = E.DEPTNO
	GROUP BY D.DNAME

	-- 3. ABRE EL CURSOR
	OPEN costsal

	-- 4. LEE EL PRIMER REGISTRO
	FETCH NEXT FROM costsal INTO @dnombre, @esal

	-- 5. BUCLE: LEE CADA REGISTRO HASTA QUE NO HAYAN MAS REGISTROS EN EL CURSOR
	WHILE @@FETCH_STATUS = 0
	BEGIN
	PRINT 'Nombre del departamento: ' + @dnombre + '. Coste salarial: ' + CONVERT(VARCHAR(10), @esal)
	FETCH NEXT FROM costsal INTO @dnombre, @esal
	END

	-- 6. CIERRA Y DESALOCA EL CURSOR
	CLOSE costsal
	DEALLOCATE costsal

END

-- EJECUTA EL EJ4
EXEC MostrarCostesSalariales

-- EJ5: Realiza un procedimiento MostrarAbreviaturas que muestre las tres primeras letras del nombre de cada empleado.
CREATE OR ALTER PROCEDURE MostrarAbreviaturas as BEGIN
	SET NOCOUNT ON
	SELECT SUBSTRING(ENAME, 0, 4) AS ENAME FROM EMP
END

-- EJECUTA EL EJ5
EXEC MostrarAbreviaturas

-- EJ6: Realiza un procedimiento MostrarMasAntiguos que muestre el nombre del empleado más antiguo de cada departamento junto con el nombre del departamento. Trata las excepciones que consideres necesarias.
CREATE OR ALTER PROCEDURE MostrarMasAntiguos AS BEGIN
	SET NOCOUNT ON
	DECLARE @ename VARCHAR(50), @dname VARCHAR(50), @hiredate DATE, @dept int
	SET @dept = 10
	WHILE @dept < (SELECT TOP 1 DEPTNO FROM DEPT ORDER BY DEPTNO DESC)
	BEGIN
		DECLARE EmpMasAntiguo CURSOR FOR
		SELECT TOP 1 E.ENAME, D.DNAME, E.HIREDATE FROM DEPT D
		INNER JOIN EMP E ON E.DEPTNO = D.DEPTNO
		WHERE E.DEPTNO = @dept
		ORDER BY E.HIREDATE
		OPEN EmpMasAntiguo
		FETCH NEXT FROM EmpMasAntiguo INTO @ename, @dname, @hiredate
		WHILE @@FETCH_STATUS = 0
		BEGIN
			PRINT 'Nombre: ' + @ename + '. Departamento: ' + @dname
			+ '. Fecha de contrato: ' + CONVERT(VARCHAR(10), @hiredate)
			FETCH NEXT FROM EmpMasAntiguo INTO @ename, @dname, @hiredate
			SET @dept = @dept + 10
		END
		CLOSE EmpMasAntiguo
		DEALLOCATE EmpMasAntiguo
	END
END

-- EJECUTA EL EJ6
EXEC MostrarMasAntiguos

-- EJ7: Realiza un procedimiento MostrarJefes que reciba el nombre de un departamento y muestre los nombres de los empleados de ese departamento que son jefes de otros empleados. Trata las excepciones que consideres necesarias.
CREATE OR ALTER PROCEDURE MostrarJefes (
	@dname VARCHAR(50)
) AS BEGIN
	SET NOCOUNT ON
	DECLARE @ename VARCHAR(50)
	DECLARE Jefes CURSOR FOR
	SELECT E.ENAME FROM EMP E
	INNER JOIN DEPT D ON E.DEPTNO = D.DEPTNO
	WHERE D.DNAME = @dname 
	AND E.EMPNO IN (SELECT MGR FROM EMP)
	OPEN Jefes
	FETCH NEXT FROM Jefes INTO @ename
	WHILE @@FETCH_STATUS = 0
	BEGIN
		PRINT 'Nombre: ' + @ename + '. Departamento: ' + @dname
		FETCH NEXT FROM Jefes INTO @ename
	END
	CLOSE Jefes
	DEALLOCATE Jefes
END

-- EJECUTA EL EJ7
EXEC MostrarJefes 'ACCOUNTING'

-- EJ8: Realiza un procedimiento MostrarMejoresVendedores que muestre los nombres de los dos vendedores con más comisiones. Trata las excepciones que consideres necesarias.
CREATE OR ALTER PROCEDURE MostrarMejoresVendedores
AS BEGIN
	SET NOCOUNT ON
	SELECT TOP 2 ENAME FROM EMP
	ORDER BY COMM DESC
END

-- EJECUTA EL EJ8
EXEC MostrarMejoresVendedores

-- EJ10: Realiza un procedimiento RecortarSueldos que recorte el sueldo un 20% a los empleados cuyo nombre empiece por la letra que recibe como parámetro. Trata las excepciones que consideres necesarias
CREATE OR ALTER PROCEDURE RecortarSueldo (
@letra CHAR(1)) AS BEGIN
	SET NOCOUNT ON
	UPDATE EMP SET SAL = SAL * 0.80
	WHERE SUBSTRING(ENAME, 0, 2) = @letra
	print 'Los sueldos han sido recortados'
END

-- EJECUTA EL EJ10
EXEC RecortarSueldo 'C'

-- EJ11: Realiza un procedimiento BorrarBecarios que borre a los dos empleados más nuevos de cada departamento. Trata las excepciones que consideres necesarias.
CREATE OR ALTER PROCEDURE BorrarBecarios
AS BEGIN
	SET NOCOUNT ON
	DECLARE @dept int, @empno int
	SET @dept = 10
	WHILE @dept <= (SELECT TOP 1 DEPTNO FROM EMP ORDER BY DEPTNO DESC)
	BEGIN
		DECLARE Borrado CURSOR FOR
		SELECT TOP 2 EMPNO FROM EMP 
		WHERE DEPTNO = @dept
		ORDER BY HIREDATE DESC
		OPEN Borrado
		FETCH NEXT FROM Borrado INTO @empno
		WHILE @@FETCH_STATUS = 0
		BEGIN
			DELETE FROM EMP WHERE EMPNO = @empno
			FETCH NEXT FROM Borrado INTO @empno
		END
		CLOSE Borrado
		DEALLOCATE Borrado
		SET @dept = @dept + 10
	END
	SET @dept = 10
END

-- EJECUTA EL EJ11
EXEC BorrarBecarios