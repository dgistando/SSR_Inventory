CREATE TRIGGER new_sale ON Sales
AFTER INSERT
AS
BEGIN
	DECLARE @sold_qty int,@firstname varchar(50),@lastname varchar(50), @location varchar(50), @custom_label varchar(50);
	SELECT @firstname = dbo.Sales.firstname FROM Sales;
	SELECT @lastname = dbo.Sales.lastname FROM Sales;
	SELECT @location = dbo.Sales.location FROM Sales;
	SELECT @sold_qty = dbo.Sales.sold_qty FROM Sales;
	SELECT @custom_label = dbo.Sales.custom_label FROM Sales;
	
	IF EXISTS (SELECT * FROM Customer WHERE @firstname=firstname AND @lastname=lastname)
	BEGIN
		--removing one form inventory
		UPDATE Inventory SET net_saleable = net_saleable-@sold_qty WHERE @custom_label = custom_label;
		UPDATE Inventory SET quantity = quantity-@sold_qty WHERE @custom_label = custom_label;
	END
	ELSE
	BEGIN
		--customer doesnt yet exist so just add them to the database
		INSERT INTO Customer Values(@firstname, @lastname, @location);
		UPDATE Inventory SET net_saleable = net_saleable-@sold_qty WHERE @custom_label = custom_label;
		UPDATE Inventory SET quantity = quantity-@sold_qty WHERE @custom_label = custom_label;
	END
	
END