package dk.lyngby.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResellerDTO {
    int id;
    String name;
    String address;
    String phone;
}
