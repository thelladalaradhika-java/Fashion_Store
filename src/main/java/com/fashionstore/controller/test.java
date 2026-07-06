package com.fashionstore.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class test {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/fashion_store?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String pass = "Radhikamohan@02";

        System.out.println("=== STARTING DATABASE VERIFICATION ===");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 Statement stmt = conn.createStatement()) {
                
                System.out.println("✅ Connected to MySQL successfully!");

                // Check table status and row counts
                try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM products")) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        System.out.println("📊 Total row count inside 'products' table: " + count);
                    }
                }

                // Verify column metadata names
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM products LIMIT 1")) {
                    ResultSetMetaData meta = rs.getMetaData();
                    System.out.println("\n📋 Available columns detected in your table:");
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        System.out.println("   -> Column " + i + ": " + meta.getColumnName(i) + " (" + meta.getColumnTypeName(i) + ")");
                    }
                    
                    if (!rs.isBeforeFirst() && rs.getRow() == 0) {
                        System.out.println("\n❌ WARNING: Your table contains 0 records. Java cannot display anything!");
                    } else {
                        System.out.println("\n Sample record read test:");
                        while(rs.next()) {
                            System.out.println("   ID: " + rs.getInt(1));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("❌ ERROR: Connection or Query crashed!");
            e.printStackTrace();
        }
        System.out.println("=== END OF VERIFICATION ===");
    }
}