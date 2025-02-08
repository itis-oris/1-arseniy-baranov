package semestrovaya.rakoedov4.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor//
@NoArgsConstructor
public class Cart {

    private int cart_id;
    private int client_id;
    private int product_id;
    private int amount;

    public Cart( int client_id, int product_id, int amount) {
        this.client_id = client_id;
        this.product_id = product_id;
        this.amount = amount;
    }

}
