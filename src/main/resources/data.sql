-- Datos de prueba para el microservicio Demo
-- Este archivo se ejecuta automáticamente al iniciar la aplicación

-- Insertar productos de prueba
INSERT INTO products (name, description, price, stock, category, created_at, updated_at, active) VALUES
('Laptop HP Pavilion', 'Laptop de 15 pulgadas con procesador Intel i5', 899.99, 25, 'Electrónicos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Mouse Inalámbrico', 'Mouse óptico inalámbrico con batería recargable', 29.99, 50, 'Accesorios', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Teclado Mecánico', 'Teclado mecánico con switches Cherry MX Blue', 89.99, 15, 'Accesorios', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Monitor Samsung 24"', 'Monitor LED de 24 pulgadas Full HD', 199.99, 30, 'Electrónicos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Auriculares Sony', 'Auriculares inalámbricos con cancelación de ruido', 149.99, 20, 'Audio', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Webcam Logitech', 'Webcam HD 1080p con micrófono integrado', 79.99, 40, 'Accesorios', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Disco Duro 1TB', 'Disco duro externo USB 3.0 de 1TB', 59.99, 35, 'Almacenamiento', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Memoria RAM 8GB', 'Módulo de memoria RAM DDR4 de 8GB', 45.99, 60, 'Componentes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Tarjeta Gráfica RTX 3060', 'Tarjeta gráfica NVIDIA RTX 3060 12GB', 399.99, 5, 'Componentes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Fuente de Poder 650W', 'Fuente de poder modular de 650W 80+ Gold', 89.99, 12, 'Componentes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

-- Datos de prueba para usuarios (password: password)
INSERT INTO usuarios (nombre, email, password, rol, activo) VALUES
('Admin Principal', 'admin@petspa.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN', true),
('Juan Pérez', 'juan@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'USER', true),
('María García', 'maria@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'USER', true),
('Dr. Carlos López', 'carlos@vet.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'VETERINARIO', true);

-- Datos de prueba para mascotas
INSERT INTO mascotas (nombre, especie, raza, fecha_nacimiento, color, peso, observaciones, usuario_id, activo) VALUES
('Luna', 'Perro', 'Golden Retriever', '2020-03-15', 'Dorado', 25.5, 'Muy juguetona y sociable', 2, true),
('Mittens', 'Gato', 'Siamés', '2019-07-22', 'Crema y marrón', 4.2, 'Le gusta dormir en lugares altos', 2, true),
('Rocky', 'Perro', 'Bulldog Francés', '2021-01-10', 'Negro', 12.0, 'Necesita ejercicio regular', 3, true),
('Whiskers', 'Gato', 'Persa', '2018-11-05', 'Blanco', 5.8, 'Requiere cepillado diario', 3, true);

-- Datos de prueba para citas
INSERT INTO citas (fecha_hora, tipo_servicio, observaciones, estado, usuario_id, mascota_id, activo) VALUES
('2024-01-15 10:00:00', 'Baño y corte', 'Corte de pelo corto', 'CONFIRMADA', 2, 1, true),
('2024-01-16 14:30:00', 'Vacunación', 'Vacuna anual', 'PENDIENTE', 2, 2, true),
('2024-01-17 09:00:00', 'Revisión general', 'Chequeo de salud', 'PENDIENTE', 3, 3, true),
('2024-01-18 16:00:00', 'Baño y corte', 'Corte de pelo largo', 'PENDIENTE', 3, 4, true); 