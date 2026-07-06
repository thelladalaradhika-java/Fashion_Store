 
package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.model.Product;
import com.fashionstore.util.DBConnection;

public class ProductDAOImpl implements ProductDAO {

    // 🆕 ADD PRODUCT
    @Override
    public boolean addProduct(Product product) {
        boolean status = false;

        String sql = "INSERT INTO products (name, description, price, category_id, stock, brand, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getCategoryId());
            ps.setInt(5, product.getStock());
            ps.setString(6, product.getBrand());
            ps.setString(7, product.getImageUrl());

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // 🔍 GET BY ID
    @Override
    public Product getProductById(int productId) {

        Product product = null;

        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = extractProduct(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }

    // 📄 GET ALL (PAGINATION)
    @Override
    public List<Product> getAllProducts(int limit, int offset) {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM products LIMIT ? OFFSET ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 📂 CATEGORY
    @Override
    public List<Product> getProductsByCategory(int categoryId) {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE category_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 💰 PRICE RANGE
    @Override
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE price BETWEEN ? AND ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🏷 BRAND
    @Override
    public List<Product> getProductsByBrand(String brand) {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE brand = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, brand);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔎 SEARCH
    @Override
    public List<Product> searchProducts(String keyword) {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE name LIKE ? OR description LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 📦 AVAILABLE PRODUCTS
    @Override
    public List<Product> getAvailableProducts() {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE stock > 0";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✏️ UPDATE
    @Override
    public boolean updateProduct(Product product) {

        boolean status = false;

        String sql = "UPDATE products SET name=?, description=?, price=?, category_id=?, stock=?, brand=?, image_url=? WHERE product_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getCategoryId());
            ps.setInt(5, product.getStock());
            ps.setString(6, product.getBrand());
            ps.setString(7, product.getImageUrl());
            ps.setInt(8, product.getProductId());

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // ❌ DELETE
    @Override
    public boolean deleteProduct(int productId) {

        boolean status = false;

        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // 🔁 COMMON METHOD (RESULTSET → OBJECT)
    private Product extractProduct(ResultSet rs) throws Exception {

        Product p = new Product();

        p.setProductId(rs.getInt("product_id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getDouble("price"));
        p.setCategoryId(rs.getInt("category_id"));
        p.setStock(rs.getInt("stock"));
        p.setBrand(rs.getString("brand"));
        p.setImageUrl(rs.getString("image_url"));
        p.setCreatedAt(rs.getTimestamp("created_at"));

        return p;
    }
}