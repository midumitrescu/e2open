-- add 2 depots
insert into PUBLIC.depots (id, version, address) values (1, 1, 'Berlin')
insert into PUBLIC.depots (id, version, address) values (2, 1, 'Frankfurt')
insert into PUBLIC.depots (id, version, address) values (3, 1, 'Berlin')
insert into PUBLIC.depots (id, version, address) values (4, 1, 'Frankfurt')
-- add 2 parts
insert into parts (id, version, name) values (1, 1, 'Monitor')
insert into parts (id, version, name) values (2, 1, 'Keyboard')
insert into parts (id, version, name) values (3, 1, 'Mouse')
insert into parts (id, version, name) values (4, 1, 'Head Sets')
-- add 2 customers
insert into customers (id, version, name) values (1, 1, 'SAP Gmbh')
insert into customers (id, version, name) values (2, 1, 'e2open Gmbh')
insert into customers (id, version, name) values (3, 1, 'Bosch Gmbh')
insert into customers (id, version, name) values (4, 1, 'Siemens Gmbh')
insert into customers (id, version, name) values (5, 1, 'Yakuza Gmbh')

-- add 5 deliveries for SAP
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (1, 1, 11, 1, 1, 1, 10, DATE '2014-10-24', DATE '2014-10-24', 10000, 'EURO')
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (2, 1, 12, 2, 1, 1, 20, DATE '2015-10-24', DATE '2015-09-01', 20000, 'EURO')
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (3, 1, 13, 1, 1, 1, 30, DATE '2016-10-24', DATE '2016-10-24', 30000, 'EURO')
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (4, 1, 14, 1, 1, 1, 40, DATE '2017-10-24', DATE '2017-11-01', 40000, 'EURO')
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (5, 1, 15, 2, 1, 1, 50, DATE '2018-10-24', DATE '2018-10-24', 50000, 'EURO')

-- add 7 deliveries e2open
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (6, 1, 20, 1, 1, 2, 10, DATE '2014-10-24', DATE '2014-10-24', 10000, 'DOLLAR')
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (7, 1, 21, 2, 1, 2, 20, DATE '2015-10-24', DATE '2015-09-01', 20000, 'DOLLAR')
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (8, 1, 22, 1, 1, 2, 30, DATE '2016-10-24', DATE '2016-10-24', 30000, 'DOLLAR')
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (9, 1, 23, 1, 1, 2, 40, DATE '2017-10-24', DATE '2017-11-01', 40000, 'DOLLAR')
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (10, 1, 24, 2, 1, 2, 50, DATE '2018-10-24', DATE '2018-10-24', 50000, 'DOLLAR')
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (11, 1, 25, 2, 1, 2, 50, DATE '2019-10-24', DATE '2019-10-24', 50000, 'DOLLAR')
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (12, 1, 26, 2, 1, 2, 50, DATE '2020-10-24', DATE '2020-10-24', 50000, 'DOLLAR')

