package pos.restaurant.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private Long id;
    private Long tableId;
    private Long employeeId;
    private Long dishId;
    private double price;
    private String specialInstructions;
    private String status;
    private String orderTime;
    private String serveTime;
    private boolean isServed;
    private boolean isPaid;
    private boolean isCompleted;
    private boolean isTakeaway;
    private boolean isDelivery;
    private boolean isCollection;


}
