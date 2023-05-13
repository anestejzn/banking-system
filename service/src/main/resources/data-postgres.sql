insert into role (role_name) values ('ROLE_ADMIN'),
                                    ('ROLE_CLIENT');
insert into admin (id,email, password, name, surname, role_id) values
                                                    (nextval('users_id_gen'),'pera@gmail.com', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Pera', 'Peric',1);