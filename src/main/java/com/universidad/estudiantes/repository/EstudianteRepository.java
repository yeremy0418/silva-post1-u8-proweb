package com.universidad.estudiantes.repository;

import com.universidad.estudiantes.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    // Consulta derivada: buscar por carrera (sin distinguir mayúsculas)
    List<Estudiante> findByCarreraIgnoreCase(String carrera);

    // Consulta derivada: búsqueda parcial por nombre o apellido
    List<Estudiante> findByNombreContainingOrApellidoContaining(String nombre, String apellido);

    // Útil para validar correo único al editar
    Optional<Estudiante> findByCorreo(String correo);
}
