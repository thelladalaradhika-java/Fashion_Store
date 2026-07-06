package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.model.Category;
import com.fashionstore.util.DBConnection;

public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public boolean addCategory(Category category) {
        boolean status = false;

        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO categories (category_name) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, category.getCategoryName());

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public Category getCategoryById(int categoryId) {
        Category category = null;

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM categories WHERE category_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM categories";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category category = new Category();

                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));

                list.add(category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean updateCategory(Category category) {
        boolean status = false;

        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE categories SET category_name = ? WHERE category_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, category.getCategoryName());
            ps.setInt(2, category.getCategoryId());

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public boolean deleteCategory(int categoryId) {
        boolean status = false;

        try {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM categories WHERE category_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, categoryId);

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}