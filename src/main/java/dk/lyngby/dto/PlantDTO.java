package dk.lyngby.dto;

import dk.lyngby.model.Plant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter

public class PlantDTO {
    int id;
    String plantType;
    String name;
    int maxHeight;
    float price;

    public PlantDTO(int id, String plantType, String name, int maxHeight, float price) {
        this.id = id;
        this.plantType = plantType;
        this.name = name;
        this.maxHeight = maxHeight;
        this.price = price;
    }

    public PlantDTO(Plant plant){
        this.id = plant.getId();
        this.plantType = plant.getType();
        this.name = plant.getName();
        this.maxHeight = plant.getMaxHeight();
        this.price = plant.getPrice();
    }

}

