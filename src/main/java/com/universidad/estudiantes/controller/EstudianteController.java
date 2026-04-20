package com.universidad.estudiantes.controller;

import com.universidad.estudiantes.model.Estudiante;
import com.universidad.estudiantes.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService service;

    public EstudianteController(EstudianteService service) {
        this.service = service;
    }

    // ── GET /estudiantes ──────────────────────────────────────────────────────
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("estudiantes", service.listarTodos());
        return "estudiantes/lista";
    }

    // ── GET /estudiantes/nuevo ────────────────────────────────────────────────
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("titulo", "Nuevo Estudiante");
        return "estudiantes/formulario";
    }

    // ── POST /estudiantes/guardar ─────────────────────────────────────────────
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Estudiante estudiante,
                          BindingResult result,
                          Model model,
                          RedirectAttributes flash) {

        // Validar correo único (solo si no hay ya errores de Bean Validation)
        if (!result.hasErrors()) {
            boolean correoOcupado = service.correoEnUso(
                    estudiante.getCorreo(), estudiante.getId());
            if (correoOcupado) {
                result.rejectValue("correo", "correo.duplicado",
                        "Este correo ya está registrado por otro estudiante.");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("titulo",
                    estudiante.getId() == null ? "Nuevo Estudiante" : "Editar Estudiante");
            return "estudiantes/formulario";
        }

        service.guardar(estudiante);
        flash.addFlashAttribute("mensaje", "Estudiante guardado correctamente.");
        return "redirect:/estudiantes";
    }

    // ── GET /estudiantes/editar/{id} ──────────────────────────────────────────
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("estudiante", service.buscarPorId(id));
        model.addAttribute("titulo", "Editar Estudiante");
        return "estudiantes/formulario";
    }

    // ── GET /estudiantes/eliminar/{id} ────────────────────────────────────────
    @GetMapping("/eliminar/{id}")
    public String confirmarEliminar(@PathVariable Long id, Model model) {
        model.addAttribute("estudiante", service.buscarPorId(id));
        return "estudiantes/confirmar-eliminar";
    }

    // ── POST /estudiantes/eliminar/{id} ───────────────────────────────────────
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        service.eliminar(id);
        flash.addFlashAttribute("mensaje", "Estudiante eliminado correctamente.");
        return "redirect:/estudiantes";
    }
}
