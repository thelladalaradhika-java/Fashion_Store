package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.ProductSize;

public interface ProductSizeDAO {

    // 🆕 Add size for product
    boolean addProductSize(ProductSize productSize);

    // 🔍 Get sizes by product
    List<ProductSize> getSizesByProduct(int productId);

    // ❌ Delete size
    boolean deleteProductSize(int id);
}
