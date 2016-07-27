create table Inventory(
custom_label VARCHAR(50) NOT NULL,
net_saleable INT NOT NULL,
returns INT NOT NULL,
defective INT NOT NULL,
incomplete INT NOT NULL,
notes VARCHAR(256),
quantity INT NOT NULL,
date_received DATE
);