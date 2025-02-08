package semestrovaya.rakoedov4.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Order {
    private int order_id;
    private int client_id;
    private int stage_id;
    private java.sql.Timestamp created_at;
    private java.sql.Timestamp updated_at;
    private double total_price;
    private List<ProductOrder> productOrders;

    public Order(int client_id, int stage_id, Timestamp created_at, Timestamp updated_at, double total_price) {
        this.client_id = client_id;
        this.stage_id = stage_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.total_price = total_price;
    }

    public Order(int order_id, int client_id, int stage_id, Timestamp created_at, Timestamp updated_at, double total_price) {
        this.order_id = order_id;
        this.client_id = client_id;
        this.stage_id = stage_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.total_price = total_price;
    }
}
