package dk.lyngby.dao.impl;

import dk.lyngby.dao.IPlantDAO;
import dk.lyngby.dto.PlantDTO;
import dk.lyngby.exception.ApiException;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlantDAOMock implements IPlantDAO<PlantDTO> {
    private static List<PlantDTO> mockDB;
    private static PlantDAOMock instance;

    public static PlantDAOMock getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new PlantDAOMock();
        }
        return instance;
    }

    private PlantDAOMock() {
        mockDB = populateMockDB();
    }

    @Override
    public List<PlantDTO> readAll() {
        return mockDB;
    }

    @Override
    public PlantDTO read(Integer id) throws ApiException {
        for (PlantDTO plantDTO : mockDB) {
            if (plantDTO.getId() == id) {
                return plantDTO;
            }
        }
        return null;
    }

    @Override
    public List<PlantDTO> getPlantByType(String type) {
        List<PlantDTO> result = new ArrayList<>();
        for (PlantDTO plantDTO : mockDB) {
            if (plantDTO.getPlantType().equals(type)) {
                result.add(plantDTO);
            }
        }
        return result;
    }

    @Override
    public PlantDTO create(PlantDTO plant) {
        mockDB.add(plant);
        return plant;
    }


    public List<PlantDTO> populateMockDB() {
        ArrayList<PlantDTO> plants = new ArrayList<>();
       /* plants.add(new PlantDTO(1, "Rose", "Albertine", 400, 199.5f));
        plants.add(new PlantDTO(2, "Bush", "Aronia", 400, 169.5f));
        plants.add(new PlantDTO(3, "FruitsAndBerries", "AromaApple", 350, 399.5f));
        plants.add(new PlantDTO(4, "Rhododendron", "Astrid", 40, 269.5f));
        plants.add(new PlantDTO(5, "Rose", "The DarkLady", 100, 199.5f));

        */
        return plants;
    }

    public List<PlantDTO> getPlantsByMaxHeight(int maxHeight) {
        return mockDB.stream()
                .filter(plant -> plant.getMaxHeight() <= maxHeight)
                .collect(Collectors.toList());
    }

    public List<String> getPlantNames() {
        return mockDB.stream()
                .map(PlantDTO::getName)
                .toList();
    }

    public List<PlantDTO> sortPlantsByName() {
        return mockDB.stream()
                .sorted(Comparator.comparing(PlantDTO::getName))
                .collect(Collectors.toList());
    }


    @Override
    public PlantDTO update(Integer integer, PlantDTO plantDTO) {
        for (PlantDTO plant : mockDB) {
            if (plant.getId() == integer) {
                plant.setName(plantDTO.getName());
                plant.setPlantType(plantDTO.getPlantType());
                plant.setMaxHeight(plantDTO.getMaxHeight());
                plant.setPrice(plantDTO.getPrice());
                return plant;
            }
        }
        return null;
    }

    @Override
    public void delete(Integer id) throws ApiException {
        for (PlantDTO plant : mockDB) {
            if (plant.getId() == id) {
                mockDB.remove(plant);
                return;
            }
        }
        throw new ApiException(404, "Plant with id: " + id + " not found");
    }

}