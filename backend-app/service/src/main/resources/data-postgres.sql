insert into role (role_name) values ('ROLE_ADMIN'),
                                    ('ROLE_CLIENT');
insert into admin (id,email, password, name, surname, role_id) values
                                                    (nextval('users_id_gen'),'pera@gmail.com', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Pera', 'Peric',1);

insert into account_type (name, currency, monthly_subscription, overdraft, cash_credit_limit) values
    ('Regular', 'RSD', 150, false, 50000),
    ('Premium', 'RSD', 300, true, 100000),
    ('Platinum', 'RSD', 500, true, 300000);


INSERT INTO card_enum (account_type_id, cards)
VALUES (1, 'DINA'),
       (2, 'DINA'),
       (2, 'VISA'),
       (3, 'DINA'),
       (3, 'VISA_PLATINUM');

insert into account (account_number, account_date, account_type_id, total_balance, applicant_score) values
        ('1234567890123', '2023-01-01', 2, 10000, 0),
        ('1234560090173', '2022-01-01', 3, 10000, 0),
        ('1034561110173', '2021-01-01', 1, 10000, 0);

insert into employer (name, pib, NBS_certified, employer_status, started_operating) values
    ('PIO FOND', '111100009', true, 0, '2000-05-01'),
    ('MyFitWorld', '123456789', true, 0, '2000-05-01'),
    ('Apple', '123456800', true, 0, '2000-05-01'),
    ('Microsoft', '123061800', true, 0, '2000-05-01');

insert into client (id,email, password, name, surname, role_id, street_name, street_number, post_code, city, date_of_birth, employee_status, account_id, employer_id, verified, started_working, monthly_income, account_status) values
    (nextval('users_id_gen'),'ana@gmail.com', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Ana', 'Sam',2, 'Bozidara Milunovica', '7', '36000', 'Kraljevo', '2001-02-12', 0, 2, 2, true, '2022-01-01', 100000, 0),
    (nextval('users_id_gen'),'srdjan@gmail.com', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Srdjan', 'Djuric', 2, 'Laze Kostica', '10', '21000', 'Novi Sad', '2000-08-23', 0, 1, 3, true, '2021-01-01', 100000, 0),
    (nextval('users_id_gen'),'penzos@gmail.com', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Penzioner', 'Penzioner', 2, 'Laze Kostica', '3', '21000', 'Novi Sad', '1960-08-23', 1, 3, 1, true, null, 40000, 0);
