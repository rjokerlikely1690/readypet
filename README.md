# 🚀 Microservicio PetSpa - Sistema de Gestión Completo

## 📋 Descripción

Este es un microservicio Spring Boot que implementa un sistema completo de gestión para un spa de mascotas, incluyendo autenticación JWT, gestión de usuarios, mascotas, citas, y productos. Diseñado para evaluaciones de programación fullstack con arquitectura limpia y estándares de Spring Initializr.

## 🏗️ Arquitectura

### Tecnologías Utilizadas
- **Spring Boot 3.2.3** - Framework principal
- **Spring Data JPA** - Persistencia de datos
- **Spring Security** - Seguridad y autenticación
- **Spring HATEOAS** - Documentación REST
- **H2 Database** - Base de datos en memoria
- **JWT** - Autenticación stateless
- **Maven** - Gestión de dependencias
- **JUnit 5** - Testing unitario
- **Lombok** - Reducción de código boilerplate

### Estructura del Proyecto
```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── controller/     # Controladores REST
│   │   ├── model/         # Entidades JPA
│   │   ├── repository/    # Repositorios Spring Data
│   │   ├── service/       # Lógica de negocio
│   │   ├── security/      # Configuración de seguridad
│   │   ├── dto/           # Objetos de transferencia
│   │   └── DemoApplication.java
│   └── resources/
│       ├── application.properties
│       ├── data.sql       # Datos de prueba
│       └── schema.sql     # Esquema de BD
└── test/
    └── java/com/example/demo/
        └── service/       # Tests unitarios
```

## 🚀 Funcionalidades

### 🔐 Autenticación y Autorización
- **JWT (JSON Web Tokens)** para autenticación stateless
- **Spring Security** para protección de endpoints
- **Roles de usuario**: ADMIN, USER, VETERINARIO
- **Encriptación de contraseñas** con BCrypt

### 👥 Gestión de Usuarios
- **CRUD completo** de usuarios
- **Sistema de roles** (ADMIN, USER, VETERINARIO)
- **Validación de email único**
- **Soft delete** (borrado lógico)

### 🐕 Gestión de Mascotas
- **CRUD completo** de mascotas
- **Asociación con usuarios**
- **Información detallada** (especie, raza, peso, etc.)
- **Búsqueda por usuario y especie**

### 📅 Gestión de Citas
- **CRUD completo** de citas
- **Estados de cita** (PENDIENTE, CONFIRMADA, EN_PROGRESO, COMPLETADA, CANCELADA)
- **Asociación con usuarios y mascotas**
- **Filtros por estado y fechas**

### 📦 Gestión de Productos
- **CRUD completo** de productos
- **Control de stock**
- **Categorización**

### 🔗 Documentación HATEOAS
- **Enlaces hipermedia** en todas las respuestas
- **Navegación REST** intuitiva
- **Relaciones entre recursos** claramente definidas

## 🛠️ Instalación y Configuración

### Prerrequisitos
- Java 17 o superior
- Maven 3.6 o superior

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone <repository-url>
   cd demo
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **Ejecutar tests**
   ```bash
   mvn test
   ```

4. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

### Configuración de Base de Datos

La aplicación utiliza H2 Database en memoria con los siguientes parámetros:
- **URL**: `jdbc:h2:mem:demodb`
- **Usuario**: `sa`
- **Contraseña**: `password`
- **Console H2**: `http://localhost:8080/api/h2-console`

## 📡 API REST

### Base URL
```
http://localhost:8080/api
```

### Endpoints de Autenticación

#### 🔐 Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "admin@petspa.com",
  "password": "password"
}
```

#### 📝 Registro
```http
POST /auth/register
Content-Type: application/json

{
  "nombre": "Nuevo Usuario",
  "email": "nuevo@example.com",
  "password": "password123"
}
```

### Endpoints de Usuarios

#### 👥 Obtener todos los usuarios
```http
GET /usuarios
Authorization: Bearer <jwt-token>
```

#### 👤 Obtener usuario por ID
```http
GET /usuarios/{id}
Authorization: Bearer <jwt-token>
```

#### ➕ Crear usuario
```http
POST /usuarios
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "nombre": "Nuevo Usuario",
  "email": "nuevo@example.com",
  "password": "password123",
  "rol": "USER"
}
```

#### 🗑️ Eliminar usuario
```http
DELETE /usuarios/{id}
Authorization: Bearer <jwt-token>
```

### Endpoints de Mascotas

#### 🐕 Obtener todas las mascotas
```http
GET /mascotas
Authorization: Bearer <jwt-token>
```

#### 🐾 Obtener mascota por ID
```http
GET /mascotas/{id}
Authorization: Bearer <jwt-token>
```

#### ➕ Crear mascota
```http
POST /mascotas
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "nombre": "Luna",
  "especie": "Perro",
  "raza": "Golden Retriever",
  "fechaNacimiento": "2020-03-15",
  "color": "Dorado",
  "peso": 25.5,
  "observaciones": "Muy juguetona",
  "usuario": {
    "id": 1
  }
}
```

#### 🐕 Obtener mascotas por usuario
```http
GET /mascotas/usuario/{usuarioId}
Authorization: Bearer <jwt-token>
```

#### 🐕 Obtener mascotas por especie
```http
GET /mascotas/especie/{especie}
Authorization: Bearer <jwt-token>
```

### Endpoints de Citas

#### 📅 Obtener todas las citas
```http
GET /citas
Authorization: Bearer <jwt-token>
```

#### 📋 Obtener cita por ID
```http
GET /citas/{id}
Authorization: Bearer <jwt-token>
```

#### ➕ Crear cita
```http
POST /citas
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "fechaHora": "2024-01-20T10:00:00",
  "tipoServicio": "Baño y corte",
  "observaciones": "Corte de pelo corto",
  "usuario": {
    "id": 1
  },
  "mascota": {
    "id": 1
  }
}
```

#### 📅 Obtener citas por usuario
```http
GET /citas/usuario/{usuarioId}
Authorization: Bearer <jwt-token>
```

#### 📅 Obtener citas por mascota
```http
GET /citas/mascota/{mascotaId}
Authorization: Bearer <jwt-token>
```

#### 📅 Obtener citas por estado
```http
GET /citas/estado/{estado}
Authorization: Bearer <jwt-token>
```

#### ✏️ Actualizar estado de cita
```http
PUT /citas/{id}/estado?estado=CONFIRMADA
Authorization: Bearer <jwt-token>
```

### Endpoints de Productos

#### 📦 Obtener todos los productos
```http
GET /products
Authorization: Bearer <jwt-token>
```

#### 🔍 Obtener producto por ID
```http
GET /products/{id}
Authorization: Bearer <jwt-token>
```

## 🔐 Seguridad

### Roles de Usuario
- **ADMIN**: Acceso completo a todas las funcionalidades
- **USER**: Gestión de sus propias mascotas y citas
- **VETERINARIO**: Gestión de citas y mascotas

### Configuración JWT
- **Secret**: Configurado en `application.properties`
- **Expiración**: 24 horas por defecto
- **Algoritmo**: HS256

### Endpoints Protegidos
- `/api/auth/**` - Público (login/registro)
- `/api/products/**` - Público (para demo)
- `/api/usuarios/**` - Requiere autenticación
- `/api/mascotas/**` - Requiere autenticación
- `/api/citas/**` - Requiere autenticación

## 🧪 Testing

### Ejecutar Tests
```bash
mvn test
```

### Cobertura de Tests
- **AuthServiceTest**: Pruebas de autenticación y registro
- **ProductServiceTest**: Pruebas de gestión de productos
- **Integration Tests**: Pruebas de endpoints REST

## 📊 Datos de Prueba

La aplicación incluye datos de prueba automáticos:

### Usuarios
- **Admin**: `admin@petspa.com` / `password`
- **Usuario**: `juan@email.com` / `password`
- **Usuario**: `maria@email.com` / `password`
- **Veterinario**: `carlos@vet.com` / `password`

### Mascotas
- **Luna** (Golden Retriever) - Usuario: Juan
- **Mittens** (Siamés) - Usuario: Juan
- **Rocky** (Bulldog Francés) - Usuario: María
- **Whiskers** (Persa) - Usuario: María

### Citas
- 4 citas con diferentes estados y servicios
- Asociadas a usuarios y mascotas específicos

### Productos
- 10 productos de diferentes categorías
- Precios y stock variados

## 🚀 Despliegue

### Desarrollo Local
```bash
mvn spring-boot:run
```

### Producción
```bash
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## 📝 Criterios de Evaluación Cumplidos

### ✅ Backend Development
- [x] Spring Boot 3.x
- [x] Maven como gestor de dependencias
- [x] JPA/Hibernate para persistencia
- [x] Base de datos H2 (desarrollo)

### ✅ CRUD Operations
- [x] Operaciones CRUD completas para todas las entidades
- [x] Validación de datos con Bean Validation
- [x] Manejo de errores centralizado
- [x] Soft delete (borrado lógico)

### ✅ RESTful Communication
- [x] Endpoints REST estándar
- [x] Códigos de estado HTTP apropiados
- [x] JSON como formato de datos
- [x] Documentación HATEOAS completa

### ✅ Security & Authentication
- [x] Spring Security configurado
- [x] JWT Authentication implementado
- [x] Roles de usuario (ADMIN, USER, VETERINARIO)
- [x] Encriptación de contraseñas con BCrypt
- [x] Filtros de seguridad personalizados

### ✅ Unit Testing
- [x] Tests unitarios con JUnit 5
- [x] Mocking con Mockito
- [x] Cobertura de servicios principales
- [x] Tests de integración

### ✅ Documentation
- [x] README completo y detallado
- [x] Documentación de API con ejemplos
- [x] Guía de instalación paso a paso
- [x] Documentación de seguridad

### ✅ Microservices Architecture
- [x] Estructura modular
- [x] Separación de responsabilidades
- [x] Configuración centralizada
- [x] Dependencias bien definidas

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 📞 Contacto

- **Autor**: [Tu Nombre]
- **Email**: [tu.email@example.com]
- **Proyecto**: [https://github.com/tuusuario/demo]

---

**¡Sistema PetSpa completamente funcional y listo para evaluación! 🐕✨** 