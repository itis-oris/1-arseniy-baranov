package semestrovaya.rakoedov4.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Product {

    private int product_id;
    private String name;
    private String description;
    private double price;
    private String image;

}
