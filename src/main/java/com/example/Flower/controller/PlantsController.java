package com.example.Flower.controller;

import com.example.Flower.entity.Plants;
import com.example.Flower.repository.PlantsRepository;
import com.example.Flower.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class PlantsController {

    private final PlantsRepository plantsRepository;
    private final PlantService plantService;

    @Autowired
    public PlantsController(PlantsRepository plantsRepository, PlantService plantService) {
        this.plantsRepository = plantsRepository;
        this.plantService = plantService;
    }

    @GetMapping("/plants")
    public String getPlants(Model model) {
        List<Plants> plants = plantsRepository.findAll();
        model.addAttribute("plants", plants);
        return "plants";
    }

    @PostMapping("/plants")
    public String uploadPlant(@RequestParam("name") String name,
                              @RequestParam("ypImage") MultipartFile ypImage,
                              @RequestParam("mpImage") MultipartFile mpImage,
                              Model model) {
        try {
            plantService.savePlantWithImages(name, ypImage, mpImage);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed to upload images");
            return "error";
        }
        return "redirect:/plants";
    }
}