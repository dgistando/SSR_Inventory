CREATE TRIGGER addReceiving ON Receiving
AFTER INSERT
AS
BEGIN
	DECLARE @label varchar(50), @net int, @quantity int, @incomplete int, @defective int, @date Date;
	
	SELECT @label = label FROM Receiving;
	SELECT @net = net_selable FROM Receiving;
	SELECT @incomplete = incomplete FROM Receiving;
	SELECT @quantity = quantity FROM Receiving;
	SELECT @defective = defective FROM Receiving;
	SELECT @date = dateReceived FROM Receiving

	IF EXISTS(SELECT * FROM Inventory WHERE custom_label = @label)
		BEGIN
			UPDATE Inventory SET net_saleable = net_saleable+@net,
								 incomplete = incomplete+@incomplete,
								 defective = defective+@defective
			  WHERE custom_label = @label;
		END
	ELSE
		BEGIN
			INSERT INTO Inventory(custom_label,net_saleable,returns,incomplete,defective,date_received,quantity,notes,packinginfo) VALUES(@label,@net,0,@incomplete,@defective,@date,@quantity,'','');
		END
END