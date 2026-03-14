package ru.evendot.products_service.Services;

import ru.evendot.products_service.Models.Product;
import ru.evendot.products_service.Models.Requests.product.CreateProductRequest;
import ru.evendot.products_service.Models.Requests.product.UpdateProductRequest;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();

//    List<Product> getProductByCategory(String category);

    Product getProduct(Long article);

    Product getProductById(Long productId);

    Product addProduct(CreateProductRequest product);

    void deleteProductByArticle(Long article);

    void deleteProductById(Long productId);

    Product updateProduct(Long id, UpdateProductRequest product);
}
