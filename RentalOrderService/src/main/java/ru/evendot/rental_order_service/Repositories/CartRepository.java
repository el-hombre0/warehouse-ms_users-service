package ru.evendot.rental_order_service.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.evendot.rental_order_service.Models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
//    Optional<Cart> findById(Long id);
    Cart findByUserId(Long userId);
//    Cart save(Cart cart);

//    void deleteById(Long id);

//    Cart updateCart(Cart cart);
}
