package dk.lyngby.model;

import dk.lyngby.dto.PlantDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "plants")
@Getter
@NoArgsConstructor
@Setter
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private int id;

    @Column(name = "plant_type")
    private String type;

    @Column(name = "plant_name")
    private String name;

    @Column(name = "max_height")
    private int maxHeight;

    @Column(name = "price")
    private float price;

    @ManyToMany
            @JoinColumn(name = "reseller_id",nullable = false)
    private Set<Reseller> resellers = new HashSet<>();

    public Plant(String type, String name, int maxHeight, float price) {
        this.type = type;
        this.name = name;
        this.maxHeight = maxHeight;
        this.price = price;
    }

    public Plant (PlantDTO dto){
        //this.id = dto.getId();
        this.type = dto.getPlantType();
        this.name = dto.getName();
        this.maxHeight = dto.getMaxHeight();
        this.price = dto.getPrice();
    }
}
