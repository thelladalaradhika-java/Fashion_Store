package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.User;

public interface UserDAO {

    // 🔐 Authentication
    User login(String email, String password);

    // 🆕 Registration
    boolean registerUser(User user);

    // 🔍 Getters
    User getUserById(int userId);
    User getUserByEmail(String email);
    User getUserByPhone(String phone);
    List<User> getAllUsers();

    // ✅ Validation
    boolean emailExists(String email);
    boolean phoneExists(String phone);

    // ✏️ Update
    boolean updateUser(User user);
    boolean updatePassword(int userId, String newPassword);

    // ❌ Delete
    boolean deleteUser(int userId);
}