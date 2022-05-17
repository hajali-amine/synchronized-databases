-- Create a Database named BO
--Execute the following queries

CREATE TABLE product_sale(
                             id SERIAL PRIMARY KEY NOT NULL,
                             date DATE,
                             region VARCHAR(255),
                             product VARCHAR(255),
                             qty INTEGER,
                             cost FLOAT,
                             amt REAL,
                             tax FLOAT,
                             total NUMERIC,
                             sent BOOLEAN DEFAULT FALSE
);



INSERT INTO product_sale(date, region, product, qty, cost, amt, tax, total) VALUES(
                                                                                            '2021-12-31','tunisie','pull',2,25,12.2,7.5,60
                                                                                        );

INSERT INTO product_sale(date, region, product, qty, cost, amt, tax, total) VALUES(
                                                                                            '2021-12-31','france','pantalon',2,25,12.2,7.5,60
                                                                                        );

INSERT INTO product_sale(date, region, product, qty, cost, amt, tax, total) VALUES(
                                                                                            '2021-5-25','tunisie','gants',2,25,12.2,7.5,60
                                                                                        );