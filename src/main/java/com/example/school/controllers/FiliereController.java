package com.example.school.controllers;

import com.example.school.entities.Filiere;
import com.example.school.repositories.CoursRepository;
import com.example.school.repositories.EleveRepository;
import com.example.school.services.FiliereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/filieres")
public class FiliereController {

    @Autowired
    private FiliereService filiereService;

    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private CoursRepository coursRepository;

    @GetMapping
    public String listFilieres(Model model) {
        model.addAttribute("filieres", filiereService.getAllFilieres());
        return "filieres/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("filiere", new Filiere());
        return "filieres/form";
    }

    @PostMapping("/save")
    public String saveFiliere(@ModelAttribute Filiere filiere) {
        filiereService.saveFiliere(filiere);
        return "redirect:/filieres";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("filiere", filiereService.getFiliereById(id));
        return "filieres/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteFiliere(@PathVariable Long id) {
        filiereService.deleteFiliere(id);
        return "redirect:/filieres";
    }

    @GetMapping("/details/{id}")
    public String detailsFiliere(@PathVariable Long id, Model model) {
        Filiere filiere = filiereService.getFiliereById(id);
        model.addAttribute("filiere", filiere);
        model.addAttribute("eleves", eleveRepository.findByFiliereId(id));
        model.addAttribute("cours", coursRepository.findByFiliereId(id));
        return "filieres/details";
    }
}