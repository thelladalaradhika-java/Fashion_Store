package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.ProductSizeDAO;
import com.fashionstore.model.ProductSize;
import com.fashionstore.util.DBConnection;

public class ProductSizeDAOImpl implements ProductSizeDAO {

    // 🆕 ADD PRODUCT SIZE
    @Override
    public boolean addProductSize(ProductSize ps) {

        boolean status = false;

        String sql = "INSERT INTO product_size (product_id, size_id, stock_quantity) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement psmt = con.prepareStatement(sql)) {

            psmt.setInt(1, ps.getProductId());
            psmt.setInt(2, ps.getSizeId());
            psmt.setInt(3, ps.getStockQuantity());

            status = psmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // 🔍 GET SIZES BY PRODUCT
    @Override
    public List<ProductSize> getSizesByProduct(int productId) {

        List<ProductSize> list = new ArrayList<>();

        String sql = "SELECT * FROM product_size WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement psmt = con.prepareStatement(sql)) {

            psmt.setInt(1, productId);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                ProductSize ps = new ProductSize();

                ps.setId(rs.getInt("id"));
                ps.setProductId(rs.getInt("product_id"));
                ps.setSizeId(rs.getInt("size_id"));
                ps.setStockQuantity(rs.getInt("stock_quantity"));

                list.add(ps);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ❌ DELETE SIZE
    @Override
    public boolean deleteProductSize(int id) {

        boolean status = false;

        String sql = "DELETE FROM product_size WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement psmt = con.prepareStatement(sql)) {

            psmt.setInt(1, id);

            status = psmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}