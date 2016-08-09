CREATE TABLE Receiving(
	supplier VARCHAR(50) NOT NULL,
	label VARCHAR(50)NOT NULL,
	dateReceived Date NOT NULL,
	quantity int NOT NULL,
	net_selable int,
	incomplete int,
	defective int,
);