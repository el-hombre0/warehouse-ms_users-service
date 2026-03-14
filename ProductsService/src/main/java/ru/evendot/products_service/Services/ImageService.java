package ru.evendot.products_service.Services;

import org.springframework.web.multipart.MultipartFile;
import ru.evendot.products_service.DTOs.ImageDTO;
import ru.evendot.products_service.Exceptions.ResourceNotFoundException;
import ru.evendot.products_service.Models.Image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ImageService {
    List<ImageDTO> saveImages(Long productId, List<MultipartFile> files)
            throws ResourceNotFoundException, IOException, SQLException;
//    ImageDTO saveImage(Long productId, MultipartFile file)
//            throws ResourceNotFoundException, IOException, SQLException;
    Image getImageById(Long imageId);
//    List<Image> getImages(Long imageId);
    void updateImage(MultipartFile file, Long imageId) throws IOException, SQLException;
    void deleteImageById(Long imageId);
}
