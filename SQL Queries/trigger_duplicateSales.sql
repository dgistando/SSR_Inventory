CREATE TRIGGER Check_Sale
ON Sales
FOR INSERT
AS
BEGIN
	DECLARE @firstname varchar(50), @lastname varchar(50), @dateSold DATE,@customLabel varchar(50), @part char(2);
	SELECT @firstname = Sales.firstname FROM Sales;
	SELECT @lastname = Sales.lastname FROM Sales;
	SELECT @dateSold = Sales.date_sold FROM Sales;
	SELECT @customLabel = Sales.custom_label FROM Sales;
	SELECT @part = Sales.part FROM Sales;
	
	IF EXISTS(SELECT Sales.firstname,Sales.lastname,Sales.custom_label,Sales.date_sold,Sales.part FROM Sales 
			  WHERE @firstname=firstname AND
					@lastname=lastname AND
					@dateSold=date_sold AND
					@customLabel=custom_label AND
					@part=part)
	BEGIN
			     RAISERROR('The user was already listed on this sheetfor this part today', 16, 1)
				 rollback transaction 
	END
END