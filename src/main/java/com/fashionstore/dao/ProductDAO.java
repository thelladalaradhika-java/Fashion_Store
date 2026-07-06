package com.fashionstore.dao;

import java.util.List;   // ✅ REQUIRED
import com.fashionstore.model.Product;

public interface ProductDAO {

    boolean addProduct(Product product);

    Product getProductById(int productId);

    List<Product> getAllProducts(int limit, int offset);

    List<Product> getProductsByCategory(int categoryId);

    List<Product> getProductsByPriceRange(double minPrice, double maxPrice);

    List<Product> getProductsByBrand(String brand);

    List<Product> searchProducts(String keyword);

    List<Product> getAvailableProducts();

    boolean updateProduct(Product product);

    boolean deleteProduct(int productId);
}

