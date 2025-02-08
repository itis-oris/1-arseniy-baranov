package semestrovaya.rakoedov4.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductOrder {

    private int po_id;
    private int order_id;
    private int product_id;
    private int amount;
    private Double total_price;

    public ProductOrder(int order_id, int product_id, int amount, Double total_price) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.amount = amount;
        this.total_price = total_price;
    }

}
