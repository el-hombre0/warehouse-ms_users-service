package ru.evendot.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.evendot.warehouse.dto.ImageDTO;
import ru.evendot.warehouse.exception.ResourceNotFoundException;
import ru.evendot.warehouse.model.Image;
import ru.evendot.warehouse.model.Product;
import ru.evendot.warehouse.repository.ImageRepository;
import ru.evendot.warehouse.service.ImageService;
import ru.evendot.warehouse.service.ProductService;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

//    private final static String IMAGES_DIRECTORY = "/app/data/images/";
    private final static String IMAGES_DIRECTORY = "/home/stas/Programming/WarehouseManagementSystem/warehouseServer/data_storage/images/";
    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Transactional
    public List<ImageDTO> saveImages(Long productId, List<MultipartFile> files)
            throws ResourceNotFoundException, IOException, SQLException{


        Product product = productService.getProductById(productId);
        List<ImageDTO> savedImageDTOs = new ArrayList<>();
        for (MultipartFile file : files){
            byte[] bytes = file.getBytes();
            Path path = Paths.get(IMAGES_DIRECTORY + file.getOriginalFilename());
            Files.write(path, bytes);

            Image image = new Image();
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());

//            image.setBlob(new SerialBlob(file.getBytes()));
            image.setPath(path.toString());

            image.setProduct(product);

            String buildDownloadUrl = "/api/v1/images/";
            String downloadUrl = buildDownloadUrl + image.getId();
            image.setDownloadUrl(downloadUrl);
            Image savedImage = imageRepository.save(image);

            savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
            imageRepository.save(savedImage);

            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setId(savedImage.getId());
            imageDTO.setFileName(savedImage.getFileName());
            imageDTO.setDownloadUrl(savedImage.getDownloadUrl());
            savedImageDTOs.add(imageDTO);
        }
        return savedImageDTOs;
    }

//    public ImageDTO saveImage(Long productId, MultipartFile file){
//        Product product = productService.getProductById(productId);
//        try {
//            Image image = new Image();
//            image.setFileName(file.getOriginalFilename());
//            image.setFileType(file.getContentType());
//
//
//            image.setBlob(new SerialBlob(file.getBytes()));
//
//
//            image.setProduct(product);
//
//            String buildDownloadUrl = "/api/v1/images/";
//            String downloadUrl = buildDownloadUrl + image.getId();
//            image.setDownloadUrl(downloadUrl);
//            Image savedImage = imageRepository.save(image);
//
//            savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
//            imageRepository.save(savedImage);
//
//            ImageDTO imageDTO = new ImageDTO();
//            imageDTO.setId(savedImage.getId());
//            imageDTO.setFileName(savedImage.getFileName());
//            imageDTO.setDownloadUrl(savedImage.getDownloadUrl());
//            return imageDTO;
//        }
//        catch (IOException | SQLException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }


    @Transactional
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException(
                "Image with id " + imageId + " does not exist!"));
    }

    public List<Image> getImages(Long productID) {
        var product = productService.getProductById(productID);
        return product.getImages();
    }

    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        image.setFileName(file.getName());
        image.setFileType(file.getContentType());
//        try {
//            image.setBlob(new SerialBlob(file.getBytes()));
//        } catch (IOException | SQLException e) {
//            throw new RuntimeException(e);
//        }
        imageRepository.save(image);
    }

    public void deleteImageById(Long imageId) {
        imageRepository.findById(imageId).ifPresentOrElse(imageRepository :: delete , () -> {
            throw new ResourceNotFoundException("Image with id " + imageId + " does not exist!");
        });
    }
}
