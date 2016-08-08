CREATE TABLE users(
	userID INT IDENTITY(1,1) NOT NULL,
	username VARCHAR(10) NOT NULL,
	password VARCHAR(20)
	PRIMARY KEY(userID,username)
);