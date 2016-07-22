CREATE TABLE Returned(
	RMA# varchar(50) NOT NULL,
	firstname varchar(50) NOT NULL,
	lastname varchar(50) NOT NULL,
	source varchar(10) NOT NULL,
	custom_label varchar(50) NOT NULL,
	date_returned date,
	defective BIT NOT NULL DEFAULT 0,
	resell BIT NOT NULL DEFAULT 0
);