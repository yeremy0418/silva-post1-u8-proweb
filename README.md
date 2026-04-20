# estudiantes-post1-u8

CRUD completo de la entidad **Estudiante** usando Spring Boot 3.2, Spring Data JPA, Hibernate y MySQL.

---

## Requisitos previos

| Herramienta | Versión mínima |
|---|---|
| Java | 17 |
| Maven | 3.6 (o usar `./mvnw`) |
| MySQL | 8.x |

---

## 1. Configurar la base de datos MySQL

```sql
-- Conectarse como root
mysql -u root -p

CREATE DATABASE estudiantes_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'apppass';
GRANT ALL PRIVILEGES ON estudiantes_db.* TO 'appuser'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

> Con Docker (alternativa rápida):
> ```bash
> docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root --name mysql8 mysql:8
> ```

---

## 2. Configurar application.properties

El archivo `src/main/resources/application.properties` ya está listo con los valores por defecto.  
Si usas credenciales distintas, edita las líneas:

```properties
spring.datasource.username=appuser
spring.datasource.password=apppass
```

---

## 3. Ejecutar la aplicación

```bash
# Desde la raíz del proyecto:
./mvnw spring-boot:run

# O en Windows:
mvnw.cmd spring-boot:run
```

Hibernate creará automáticamente la tabla `estudiantes` en `estudiantes_db` al arrancar.

---

## 4. Verificar en MySQL

```sql
USE estudiantes_db;
SHOW TABLES;           -- debe mostrar: estudiantes
SELECT * FROM estudiantes;
```

---

## 5. Rutas disponibles

| Método | URL | Descripción |
|---|---|---|
| GET | `/estudiantes` | Lista todos los estudiantes |
| GET | `/estudiantes/nuevo` | Formulario de creación |
| POST | `/estudiantes/guardar` | Guarda (crea o actualiza) |
| GET | `/estudiantes/editar/{id}` | Formulario de edición |
| GET | `/estudiantes/eliminar/{id}` | Pantalla de confirmación |
| POST | `/estudiantes/eliminar/{id}` | Elimina el registro |

---

## 6. Estructura del proyecto

```
src/main/java/com/universidad/estudiantes/
├── EstudiantesApplication.java       ← Punto de entrada
├── controller/
│   └── EstudianteController.java     ← Rutas MVC
├── model/
│   └── Estudiante.java               ← Entidad JPA
├── repository/
│   └── EstudianteRepository.java     ← JpaRepository
└── service/
    └── EstudianteService.java        ← Lógica de negocio (@Transactional)

src/main/resources/
├── application.properties            ← Config DB + JPA
└── templates/estudiantes/
    ├── lista.html                    ← Vista principal
    ├── formulario.html               ← Crear / Editar
    └── confirmar-eliminar.html       ← Confirmación de borrado
```

---

## 7. Capturas de pantalla sugeridas

Documenta las siguientes vistas para la entrega:

1. **Lista de estudiantes** — `http://localhost:8080/estudiantes`
2. **Formulario de creación** — `http://localhost:8080/estudiantes/nuevo`
3. **Formulario con errores de validación** (campo vacío o correo duplicado)
4. **Confirmación de eliminación**
5. **Vista después de eliminar** (lista actualizada con mensaje flash)
6. **Consola MySQL** mostrando `SELECT * FROM estudiantes;`

---

## 8. Evidencias por Checkpoint

Las capturas están en la carpeta `capturas/` y fueron renombradas para que coincidan con la checklist.

### Checkpoint 1

- [checkpoint1-mysql-tabla-estudiantes.png](capturas/checkpoint1-mysql-tabla-estudiantes.png) — verificación en MySQL con `USE estudiantes_db;` y `SHOW TABLES;` mostrando la tabla `estudiantes`.

### Checkpoint 2

- [checkpoint2-lista-estudiantes.png](capturas/checkpoint2-lista-estudiantes.png) — lista en la aplicación con 3 estudiantes creados desde `/estudiantes/nuevo`.
- [checkpoint2-mysql-registros.png](capturas/checkpoint2-mysql-registros.png) — validación en MySQL con `SELECT * FROM estudiantes;` mostrando los registros persistidos.

### Checkpoint 3

- [checkpoint3-validacion-correo-duplicado.png](capturas/checkpoint3-validacion-correo-duplicado.png) — mensaje de validación al intentar usar un correo ya registrado.
- [checkpoint3-mysql-despues-editar-eliminar.png](capturas/checkpoint3-mysql-despues-editar-eliminar.png) — estado final en MySQL tras editar y eliminar estudiantes.
