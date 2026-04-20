package com.universidad.estudiantes.service;

import com.universidad.estudiantes.model.Estudiante;
import com.universidad.estudiantes.repository.EstudianteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstudianteService {

    private final EstudianteRepository repo;

    public EstudianteService(EstudianteRepository repo) {
        this.repo = repo;
    }

    /**
     * Retorna todos los estudiantes ordenados por apellido.
     */
    public List<Estudiante> listarTodos() {
        return repo.findAll();
    }

    /**
     * Busca un estudiante por su ID.
     * Lanza RuntimeException si no existe.
     */
    public Estudiante buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));
    }

    /**
     * Guarda o actualiza un estudiante (JPA decide por presencia del ID).
     */
    @Transactional
    public Estudiante guardar(Estudiante estudiante) {
        return repo.save(estudiante);
    }

    /**
     * Elimina un estudiante por su ID.
     */
    @Transactional
    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    /**
     * Verifica si un correo ya está en uso por otro estudiante.
     */
    public boolean correoEnUso(String correo, Long idActual) {
        return repo.findByCorreo(correo)
                .map(e -> !e.getId().equals(idActual))
                .orElse(false);
    }
}
