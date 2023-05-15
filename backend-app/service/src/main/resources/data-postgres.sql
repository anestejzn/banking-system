insert into role (role_name) values ('ROLE_ADMIN'),
                                    ('ROLE_CLIENT');
insert into admin (id,email, password, name, surname, role_id) values
                                                    (nextval('users_id_gen'),'pera@gmail.com', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Pera', 'Peric',1);
insert into employer (name, pib) values
    ('MyFitWorld', '123456789');

insert into client (id,email, password, name, surname, role_id, street_name, street_number, post_code, city, date_of_birth, employee_status, employer_id, verified) values
    (nextval('users_id_gen'),'ana@gmail.com', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Ana', 'Sam',2, 'Bozidara Milunovica', 7, 36000, 'Kraljevo', '2001-02-12',1, 1, true );