package dk.lyngby.model;

import dk.lyngby.dto.ResellerDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "resellers")
@Getter
@NoArgsConstructor
@Setter
public class Reseller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reseller_id")
    private int id;

    @Column(name = "reseller_name")
    private String name;

    @Column(name = "reseller_address")
    private String address;

    @Column(name = "reseller_phone")
    private String phone;

    @ManyToMany(mappedBy = "resellers", cascade = CascadeType.ALL)

    private Set<Plant> plants;

    public Reseller(String name, String address, String phone, Set<Plant> plants) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.plants = plants;
    }

    public Reseller(ResellerDTO dto){
        this.name = dto.getName();
        this.address = dto.getAddress();
        this.phone = dto.getPhone();
    }
}
