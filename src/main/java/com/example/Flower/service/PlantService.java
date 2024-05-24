package com.example.Flower.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import com.example.Flower.entity.Plants;
import com.example.Flower.repository.PlantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PlantService {

    private final PlantsRepository plantsRepository;
    private final String UPLOAD_DIR = "src/main/resources/static/Plants/";

    @Autowired
    public PlantService(PlantsRepository plantsRepository) {
        this.plantsRepository = plantsRepository;
    }

    public void savePlantWithImages(String name, MultipartFile ypImage, MultipartFile mpImage) throws IOException {
        String ypImagePath = saveImage(ypImage);
        String mpImagePath = saveImage(mpImage);

        Plants plant = new Plants();
        plant.setName(name);
        plant.setYP_picture(Objects.requireNonNull(ypImagePath).getBytes());
        plant.setMP_picture(Objects.requireNonNull(mpImagePath).getBytes());

        plantsRepository.save(plant);
    }

    private String saveImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            return null;
        }

        String fileName = image.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(image.getInputStream(), filePath);

        return "/Plants/" + fileName;
    }
}