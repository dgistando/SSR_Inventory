create table Inventory(
custom_label VARCHAR(50) NOT NULL,
quantity INT NOT NULL,
defective BIT NOT NULL,
returns BIT NOT NULL,
incomplete BIT NOT NULL DEFAULT 0,
date_received DATE,
notes VARCHAR(256)
);