INSERT INTO detallesdeusuario (id_usuario, nombre, apellidos, poblacion, email_personal, tlf, tlf2, codigo_postal, direccion, contacto_emergencia, pais)
VALUES
    (1, 'Ana', 'Pérez Gómez', 'Madrid', 'ana.admin@correo.com', '600111111', NULL, 28001, 'Calle Admin 1', '600111112', 'España'),
    (2, 'Luis', 'Martínez López', 'Barcelona', 'luis.jefe1@correo.com', '600222222', NULL, 08001, 'Calle Jefe 1', '600222223', 'España'),
    (3, 'Marta', 'Sánchez Ruiz', 'Valencia', 'marta.jefe2@correo.com', '600333333', NULL, 46001, 'Calle Jefe 2', '600333334', 'España'),
    (4, 'Carlos', 'Díaz Torres', 'Sevilla', 'carlos.user1@correo.com', '600444444', NULL, 41001, 'Calle User 1', '600444445', 'España'),
    (5, 'Elena', 'García Fernández', 'Sevilla', 'elena.user2@correo.com', '600555555', NULL, 41002, 'Calle User 2', '600555556', 'España'),
    (6, 'Javier', 'López Martín', 'Valencia', 'javier.user3@correo.com', '600666666', NULL, 46002, 'Calle User 3', '600666667', 'España'),
    (7, 'Lucía', 'Ramírez Castro', 'Valencia', 'lucia.user4@correo.com', '600777777', NULL, 46003, 'Calle User 4', '600777778', 'España'),
    (8, 'Pedro', 'Moreno Gil', 'Barcelona', 'pedro.visit1@correo.com', '600888888', NULL, 08002, 'Calle Visit 1', '600888889', 'España'),
    (9, 'Sara', 'Jiménez Ortiz', 'Barcelona', 'sara.visit2@correo.com', '600999999', NULL, 08003, 'Calle Visit 2', '600999990', 'España'),
    (10, 'Miguel', 'Hernández Vega', 'Valencia', 'miguel.visit3@correo.com', '601000000', NULL, 46004, 'Calle Visit 3', '601000001', 'España');

ALTER SEQUENCE detallesdeusuario_id_usuario_seq RESTART WITH 11;

INSERT INTO login (id_login, email_primario, password, token, last_login_day, id_usuario_jefe, id_usuario, activo)
VALUES
    (1, 'ana.admin@correo.com', 'adminpass', 'tokenadmin',  '2025-05-20', 1, 1, true),
    (2, 'luis.jefe1@correo.com', 'jefe1pass', 'tokenjefe1',  '2025-05-20', 2, 2, true),
    (3, 'marta.jefe2@correo.com', 'jefe2pass', 'tokenjefe2',  '2025-05-20', 3, 3, true),
    (4, 'carlos.user1@correo.com', 'user1pass', 'tokenuser1',  '2025-05-20', 2, 4, true),
    (5, 'elena.user2@correo.com', 'user2pass', 'tokenuser2',  '2025-05-20', 2, 5, true),
    (6, 'javier.user3@correo.com', 'user3pass', 'tokenuser3',  '2025-05-20', 3, 6, true),
    (7, 'lucia.user4@correo.com', 'user4pass', 'tokenuser4',  '2025-05-20', 3, 7, true),
    (8, 'pedro.visit1@correo.com', 'visit1pass', 'tokenvisit1',  '2025-05-20', 2, 8, true),
    (9, 'sara.visit2@correo.com', 'visit2pass', 'tokenvisit2', '2025-05-20', 2, 9, true),
    (10, 'miguel.visit3@correo.com', 'visit3pass', 'tokenvisit3',  '2025-05-20', 3, 10, true);

ALTER SEQUENCE login_id_login_seq RESTART WITH 11;

INSERT INTO roles (id_rol, descripcion, id_login)
VALUES
    (1, 'Admin', 1),
    (2, 'Jefe', 2),
    (3, 'Jefe', 3),
    (4, 'Usuario', 4),
    (5, 'Usuario', 5),
    (6, 'Usuario', 6),
    (7, 'Usuario', 7),
    (8, 'Visitante', 8),
    (9, 'Visitante', 9),
    (10, 'Visitante', 10);

ALTER SEQUENCE roles_id_rol_seq RESTART WITH 11;

INSERT INTO tiposausencias (id, descripcion)
VALUES
    (1, 'Vacaciones'),
    (2, 'No asiste'),
    (3, 'Personal'),
    (4, 'Salud');

ALTER SEQUENCE tiposausencias_id_seq RESTART WITH 11;


INSERT INTO ausencias (aprobado, calcular_dias, fecha_fin, fecha_inicio,
                       id_login, id_tipos_ausencias, documentos, justificacion)
VALUES
    (true,25,'2025-07-24', '2025-07-20',1, 1,null,null),
    (true,25,'2025-06-02', '2025-05-28',1, 1,null,null),
    (true,25,'2025-05-02', '2025-04-24',1, 1,null,null),
    (false,25,'2025-07-06', '2025-07-04',1, 1,null,null),
    (false,25,'2025-05-16', '2025-05-15',1, 2,null,null),
    (false,25,'2025-05-13', '2025-05-13',1, 2,null,null),
    (true,25,'2025-05-19', '2025-05-19',1, 2,null,null),
    (false,25,'2025-04-04', '2025-04-04',1, 2,null,null),
    (false,25,'2025-04-16', '2025-04-14',4, 2,null,null),
    (false,25,'2025-07-27', '2025-07-23',4, 1,null,null),
    (false,25,'2025-07-18', '2025-07-16',4, 1,null,null),
    (false,25,'2025-05-23', '2025-05-21',4, 2,'tokenuser1.21_05_2025.23_05_2025.0.just.pdf|tokenuser1.21_05_2025.23_05_2025.1.just.pdf','2//Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur rid'),
    (false,25,'2025-03-13', '2025-03-13',4, 2,'tokenuser1.13_03_2025.0.just.pdf|tokenuser1.13_03_2025.1.just.pdf','4//Soy un maquina'),
    (false,25,'2025-07-17', '2025-07-14',5, 1,null,null),
    (false,25,'2025-08-22', '2025-08-18',5, 1,null,null),
    (false,25,'2025-05-09', '2025-05-07',5, 2,null,null),
    (true,25,'2025-05-14', '2025-05-12',5, 1,null,null);

ALTER SEQUENCE ausencias_id_ausencia_seq RESTART WITH 11;


INSERT INTO tipo_tareas (tarea) VALUES
                                    ('Desarrollo'),
                                    ('Corrección de errores'),
                                    ('Documentación'),
                                    ('Testing'),
                                    ('Revisión de código'),
                                    ('Reunión');

