package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.SizeDAO;
import com.fashionstore.model.Size;
import com.fashionstore.util.DBConnection;

public class SizeDAOImpl implements SizeDAO {

    @Override
    public boolean addSize(Size size) {

        String sql = "INSERT INTO sizes (size_name) VALUES (?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, size.getSizeName());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Size> getAllSizes() {

        List<Size> list = new ArrayList<>();

        String sql = "SELECT * FROM sizes";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Size s = new Size();

                s.setSizeId(rs.getInt("size_id"));
                s.setSizeName(rs.getString("size_name"));

                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Size getSizeById(int sizeId) {

        String sql = "SELECT * FROM sizes WHERE size_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sizeId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Size s = new Size();

                s.setSizeId(rs.getInt("size_id"));
                s.setSizeName(rs.getString("size_name"));

                return s;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean deleteSize(int sizeId) {

        String sql = "DELETE FROM sizes WHERE size_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sizeId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
