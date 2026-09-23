CREATE TABLE productos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    precio NUMERIC(12, 2) NOT NULL CHECK (precio > 0),
    stock INTEGER NOT NULL CHECK (stock >= 0),
    categoria_id BIGINT NOT NULL REFERENCES categorias (id),
    sede_id BIGINT NOT NULL REFERENCES sedes (id),
    UNIQUE (nombre, sede_id)
);

CREATE INDEX idx_productos_categoria_id ON productos (categoria_id);
CREATE INDEX idx_productos_sede_id ON productos (sede_id);
