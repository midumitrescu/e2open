-- add 2 depots
insert into PUBLIC.depots (id, version, address) values (1, 1, 'Berlin')
insert into PUBLIC.depots (id, version, address) values (2, 1, 'Frankfurt')
-- add 2 parts
insert into parts (id, version, name) values (1, 1, 'Monitor')
insert into parts (id, version, name) values (2, 1, 'Keyboard')
-- add 2 customers
insert into customers (id, version, name) values (1, 1, 'Company1')
insert into customers (id, version, name) values (2, 1, 'Company2')

-- add 5 deliveries                                                                                                            //id[1],version[2],deliv number[3], part[4], depot[5], customer[6], quantity[7], dates
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (1, 1, 100, 1, 1, 1, 10, DATE '2014-10-24', DATE '2014-10-24', 10000, 'EURO')
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (2, 1, 200, 2, 2, 1, 20, DATE '2015-10-24', DATE '2015-09-01', 20000, 'EURO')
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (3, 1, 300, 1, 1, 1, 30, DATE '2016-10-24', DATE '2016-10-24', 30000, 'EURO')
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (4, 1, 400, 1, 1, 1, 40, DATE '2017-10-24', DATE '2017-11-01', 40000, 'EURO')
insert into delivery_plans (id, version, delivery_number, part, depot, customer, quantity,  due_date, delivery_date, margin_value,  currency) values (5, 1, 500, 2, 2, 2, 50, DATE '2018-10-24', DATE '2018-10-24', 50000, 'YEN')

