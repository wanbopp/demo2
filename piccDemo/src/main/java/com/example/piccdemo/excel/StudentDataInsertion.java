package com.example.piccdemo.excel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 插入100万条学生数据
 */

public class StudentDataInsertion {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/easy_excel?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";
    private static final int BATCH_SIZE = 1000;
    private static final int NUM_STUDENTS = 1000000;

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {

            // 建立数据库连接
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 关闭自动提交，开启事务
            conn.setAutoCommit(false);

            // 创建预编译的SQL插入语句
            String sql = "INSERT INTO students (student_id, name, age, grade) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            // 批量插入数据
            for (int i = 1; i <= NUM_STUDENTS; i++) {
                int studentId = i;
                String name = "Student " + studentId;
                int age = new Random().nextInt(8) + 18;
                double grade = 60 + new Random().nextDouble() * 40;

                stmt.setInt(1, studentId);
                stmt.setString(2, name);
                stmt.setInt(3, age);
                stmt.setDouble(4, grade);
                stmt.addBatch();

                // 每达到批量大小，执行批量插入
                if (i % BATCH_SIZE == 0) {
                    stmt.executeBatch();
                    conn.commit();
                    System.out.println("Inserted " + i + " students.");
                }
            }

            // 提交剩余的数据
            stmt.executeBatch();
            conn.commit();

            System.out.println("Data insertion completed.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭Statement和数据库连接
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
