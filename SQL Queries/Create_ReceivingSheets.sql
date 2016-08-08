CREATE TABLE ReceivingSheets(
	supplier VARCHAR(20) NOT NULL,
	dateReceived DATE NOT NULL,
	invoice VARCHAR(30),
	verified BIT NOT NULL default 0 
);