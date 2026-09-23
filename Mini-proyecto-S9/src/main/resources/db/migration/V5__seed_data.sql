INSERT INTO categorias (nombre) VALUES ('Perifericos'), ('Muebles'), ('Pantallas');

INSERT INTO sedes (nombre, ciudad) VALUES ('Sede Central', 'Lima'), ('Sede Norte', 'Trujillo');

INSERT INTO productos (nombre, precio, stock, categoria_id, sede_id) VALUES
    ('Teclado mecanico', 189.90, 15, 1, 1),
    ('Silla ergonomica', 459.00, 5, 2, 1),
    ('Monitor 27 pulgadas', 899.00, 3, 3, 2);
