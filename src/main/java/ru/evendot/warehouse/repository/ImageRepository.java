package ru.evendot.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.evendot.warehouse.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
