package ru.evendot.userservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.evendot.userservice.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
}
