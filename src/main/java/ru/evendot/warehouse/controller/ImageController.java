package ru.evendot.warehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.evendot.warehouse.dto.ImageDTO;
import ru.evendot.warehouse.exception.ResourceNotFoundException;
import ru.evendot.warehouse.model.Image;
import ru.evendot.warehouse.model.response.DataResponse;
import ru.evendot.warehouse.service.ImageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<DataResponse> uploadImage(@RequestParam List<MultipartFile> images,
                                                    @RequestParam Long productId) {
        try{
            List<ImageDTO> imageDTOS = imageService.saveImages(productId, images);
            return ResponseEntity.ok(new DataResponse("Images uploaded successfully!",
                    imageDTOS));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DataResponse(
                    "Product with id " + productId + " not found!", null));
        } catch (IOException | SQLException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DataResponse(
                    "An error occurred while processing the images!", null));
        }

    }


    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        Path path = Paths.get(image.getPath());
//        File file = new File(image.getPath());
        try {
//            BufferedImage resource = ImageIO.read(file);
            ByteArrayResource res = new ByteArrayResource(Files.readAllBytes(path));
            return ResponseEntity.ok().contentType(
                    MediaType.parseMediaType(image.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + image.getFileType() + "\"").body(res);
        }
        catch (IOException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
//        ByteArrayResource resource = new ByteArrayResource(
//                image.getPath().getBytes()
//        );
//        ByteArrayResource resource = new ByteArrayResource(
//                image.getBlob().getBytes(1, (int) image.getBlob().length())
//        );

    }


    @PutMapping("/{imageId}")
    public ResponseEntity<DataResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);
            imageService.updateImage(file, image.getId());
            return ResponseEntity.ok(new DataResponse("The image updated successfully!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DataResponse(e.getMessage(), null));
        } catch (IOException | SQLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DataResponse(
                "An error occurred!", null));
        }
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<DataResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            imageService.deleteImageById(image.getId());
            return ResponseEntity.ok(new DataResponse("Image deleted successfully!",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DataResponse(e.getMessage(), null));
        }
    }
}
