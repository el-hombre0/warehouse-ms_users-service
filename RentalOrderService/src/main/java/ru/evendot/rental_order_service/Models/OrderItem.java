package ru.evendot.rental_order_service.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = "order") // Eliminates recursion for order and orderItem when creating a new order
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;
    private Long productId;

    /**
     * Количество единиц товара в заказе
     */
    private int quantity;

    private Double price;

    public OrderItem(Order order, Long productId, int quantity, Double price) {
        this.order = order;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
