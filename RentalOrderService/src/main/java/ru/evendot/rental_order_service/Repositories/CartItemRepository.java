package ru.evendot.rental_order_service.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.evendot.rental_order_service.Models.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
//    CartItem save(CartItem cartItem);

    void deleteAllByCartId(Long id);
}
