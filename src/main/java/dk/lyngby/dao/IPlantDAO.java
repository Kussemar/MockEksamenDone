package dk.lyngby.dao;

import dk.lyngby.dto.PlantDTO;

import java.util.List;

public interface IPlantDAO<T> extends IDao<T, Integer>{
    List<T> getPlantByType(String type);

    // T er bare en type, en placeholder
}
