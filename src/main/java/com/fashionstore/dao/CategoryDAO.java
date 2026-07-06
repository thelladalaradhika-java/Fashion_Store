package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Category;

public interface CategoryDAO {

    // 🆕 Add
    boolean addCategory(Category category);

    // 🔍 Get Methods
    Category getCategoryById(int categoryId);
    List<Category> getAllCategories();

    // ✏️ Update
    boolean updateCategory(Category category);

    // ❌ Delete
    boolean deleteCategory(int categoryId);
}
