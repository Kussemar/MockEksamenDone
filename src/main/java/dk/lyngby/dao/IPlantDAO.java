package dk.lyngby.dao;

import dk.lyngby.dto.PlantDTO;

import java.util.List;

public interface IPlantDAO extends IDao<PlantDTO, Integer>{
    List<PlantDTO> getPlantByType(String type);
}
