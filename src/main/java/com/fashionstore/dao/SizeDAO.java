package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Size;

public interface SizeDAO {

    // 🆕 Add size
    boolean addSize(Size size);

    // 🔍 Get all sizes
    List<Size> getAllSizes();

    // 🔍 Get by ID
    Size getSizeById(int sizeId);

    // ❌ Delete size
    boolean deleteSize(int sizeId);
}