INSERT INTO regiones(nombre) VALUES ('Hyrule');
INSERT INTO regiones(nombre) VALUES ('Mushroom Kingdom');
INSERT INTO regiones(nombre) VALUES ('Paldea');
INSERT INTO regiones(nombre) VALUES ('Weyard');
INSERT INTO regiones(nombre) VALUES ('Protectorate');
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES ('Hans','','hans@camelot.com',665287649,NOW(),4);
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES ('Mario','Mario','mario@nintendo.com',645678932,NOW(),2);
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES ('Link','','link@nintendo.com',683684432,NOW(),1);
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES ('Luigi','Mario','luigi@nintendo.com',667584735,NOW(),2);
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES ('Ganondorf','Dragmire','ganon@nintendo.com',664673894,NOW(),1);
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES ('Zelda','Nohassen','zelda@nintendo.com',685634839,NOW(),1);
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES ('Donkey','Kong','dk@nintendo.com',685747380,NOW(),2);
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES ('Diddy','Kong','diddy@nintendo.com',605854647,NOW(),2);
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES ('Rojo','','rojo@gamefreak.com',685037855,NOW(),4);
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES ('Heroe','','hero@square.com',635677855,NOW(),5);
INSERT INTO usuarios(username,password,enabled) VALUES ('DanClemens','$2a$10$jKV0O.4dk6vMtAlQkAxWxO6kH.n9Zsos/29HctqLJeQ/Pee.v/Gcy',1);
INSERT INTO usuarios(username,password,enabled) VALUES ('admin','$2a$10$t6Z8OiHgAh5nq1edipCZgeeBKYWXjM6jca5Hfcu045IpnpekYAnYe',1);
INSERT INTO roles(nombre) VALUES ('ROLE_USER');
INSERT INTO roles(nombre) VALUES ('ROLE_ADMIN');
INSERT INTO usuarios_roles(usuario_id,role_id) VALUES (1,1);
INSERT INTO usuarios_roles(usuario_id,role_id) VALUES (2,2);
INSERT INTO productos(nombre) VALUES("MONITOR AOC");
INSERT INTO productos(nombre) VALUES("MOUSE LINK");
INSERT INTO productos(nombre) VALUES("ORDENADOR DELL");
INSERT INTO productos(nombre) VALUES("DISCO DURO SSD");
INSERT INTO productos(nombre) VALUES("DISCO EXTERNO SATELLITE");
INSERT INTO ventas(iva,subtotal,total,cliente_id) VALUES(21.0,200.0,221.0,5);
INSERT INTO ventas_producto(venta_id,producto_id) VALUES(1,1);
INSERT INTO ventas_producto(venta_id,producto_id) VALUES(1,2);
INSERT INTO ventas_producto(venta_id,producto_id) VALUES(1,3);
