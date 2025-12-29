package com.example.school.controllers;

import com.example.school.entities.Cours;
import com.example.school.services.CoursService;
import com.example.school.services.FiliereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cours")
public class CoursController {

    @Autowired
    private CoursService coursService;

    @Autowired
    private FiliereService filiereService;

    @GetMapping
    public String listCours(Model model) {
        model.addAttribute("coursList", coursService.getAllCours());
        return "cours/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("cours", new Cours());
        model.addAttribute("filieres", filiereService.getAllFilieres());
        return "cours/form";
    }

    @PostMapping("/save")
    public String saveCours(@ModelAttribute Cours cours, @RequestParam Long filiereId) {
        cours.setFiliere(filiereService.getFiliereById(filiereId));
        coursService.saveCours(cours);
        return "redirect:/cours";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("cours", coursService.getCoursById(id));
        model.addAttribute("filieres", filiereService.getAllFilieres());
        return "cours/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteCours(@PathVariable Long id) {
        coursService.deleteCours(id);
        return "redirect:/cours";
    }
}