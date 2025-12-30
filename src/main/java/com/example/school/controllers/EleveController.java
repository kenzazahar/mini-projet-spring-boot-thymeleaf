package com.example.school.controllers;

import com.example.school.entities.Cours;
import com.example.school.entities.Eleve;
import com.example.school.services.CoursService;
import com.example.school.services.EleveService;
import com.example.school.services.FiliereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/eleves")
public class EleveController {

    @Autowired
    private EleveService eleveService;

    @Autowired
    private FiliereService filiereService;

    @Autowired
    private CoursService coursService;

    @GetMapping
    public String listEleves(Model model) {
        model.addAttribute("eleves", eleveService.getAllEleves());
        return "eleves/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("eleve", new Eleve());
        model.addAttribute("filieres", filiereService.getAllFilieres());
        return "eleves/form";
    }

    @PostMapping("/save")
    public String saveEleve(@ModelAttribute Eleve eleve,
                            @RequestParam(required = false) Long filiereId) {
        // Gérer l'affectation de la filière
        if (filiereId != null && filiereId > 0) {
            eleve.setFiliere(filiereService.getFiliereById(filiereId));
        } else {
            eleve.setFiliere(null);
        }

        eleveService.saveEleve(eleve);
        return "redirect:/eleves";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Eleve eleve = eleveService.getEleveById(id);
        model.addAttribute("eleve", eleve);
        model.addAttribute("filieres", filiereService.getAllFilieres());
        return "eleves/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteEleve(@PathVariable Long id) {
        eleveService.deleteEleve(id);
        return "redirect:/eleves";
    }

    @GetMapping("/details/{id}")
    public String detailsEleve(@PathVariable Long id, Model model) {
        Eleve eleve = eleveService.getEleveById(id);
        model.addAttribute("eleve", eleve);
        model.addAttribute("allCours", coursService.getAllCours());
        return "eleves/details";
    }

    @PostMapping("/addCours/{eleveId}")
    public String addCoursToEleve(@PathVariable Long eleveId,
                                  @RequestParam Long coursId) {
        Eleve eleve = eleveService.getEleveById(eleveId);
        Cours cours = coursService.getCoursById(coursId);

        if (!eleve.getCours().contains(cours)) {
            eleve.getCours().add(cours);
            eleveService.saveEleve(eleve);
        }

        return "redirect:/eleves/details/" + eleveId;
    }

    @GetMapping("/removeCours/{eleveId}/{coursId}")
    public String removeCoursFromEleve(@PathVariable Long eleveId,
                                       @PathVariable Long coursId) {
        Eleve eleve = eleveService.getEleveById(eleveId);
        Cours cours = coursService.getCoursById(coursId);

        eleve.getCours().remove(cours);
        eleveService.saveEleve(eleve);

        return "redirect:/eleves/details/" + eleveId;
    }
}