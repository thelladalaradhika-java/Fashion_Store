package com.fashionstore.util;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDB {

    public static void main(String[] args) {
        System.out.println("Testing Database Connection...");

        // Using try-with-resources to catch SQLExceptions and automatically close the connection
        try (Connection con = DBConnection.getConnection()) {
            
            if (con != null && !con.isClosed()) {
                System.out.println("✅ SUCCESS: Connection to 'fashion_store' is working perfectly!");
                System.out.println("Database Product Name: " + con.getMetaData().getDatabaseProductName());
            } else {
                System.out.println("❌ FAILED: Connection object is null or closed!");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ FAILED: Connection is NOT working!");
            System.out.println("Error details: " + e.getMessage());
            e.printStackTrace();
        }
    }
}