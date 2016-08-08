CREATE TRIGGER noInserts ON Editing
FOR INSERT
AS
BEGIN
	DECLARE @usern varchar(50);
	SELECT @usern = usern FROM Editing;
--check if the rows are = 1 if so reject the insert
	IF COUNT(usern) = 1
	RAISERROR('Insert rejected, no more than on editor allowed at a time',16,1);
	ROLLBACK TRANSACTION;
	RETURN;

END