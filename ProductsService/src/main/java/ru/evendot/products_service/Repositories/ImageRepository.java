package ru.evendot.products_service.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.evendot.products_service.Models.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
