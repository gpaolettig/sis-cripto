-- Insertar la empresa
INSERT INTO `sis-cripto`.`User` (`dni_user`, `name_user`, `surname_user`, `gender_user`, `email_user`, `tel_user`) 
VALUES ('99999999', 'Stack', 'Trace', 'male', 'stacktrace@gmail.com', '1234567890');
-- Insertar la wallet de la empresa
INSERT INTO `sis-cripto`.`Wallet` (`id_wallet`, `User_dni_user`, `balance_wallet`) 
VALUES (UNHEX('a1be8fe4173d45bf8a107160cd7108c3'), '99999999', 0.00);

-- Insertar la moneda BTC (Bitcoin)
INSERT INTO `sis-cripto`.`Currency` (`ticker_currency`, `name_currency`, `value_currency`) 
VALUES ('BTC', 'Bitcoin', 100.00);

-- Insertar la moneda ETH (Ethereum)
INSERT INTO `sis-cripto`.`Currency` (`ticker_currency`, `name_currency`, `value_currency`) 
VALUES ('ETH', 'Ethereum', 20.00);

-- Insertar la moneda ARS (Peso Argentino)
INSERT INTO `sis-cripto`.`Currency` (`ticker_currency`, `name_currency`, `value_currency`) 
VALUES ('ARS', 'Peso Argentino', 1.00);

-- insertar usuarios con billeteras, 

