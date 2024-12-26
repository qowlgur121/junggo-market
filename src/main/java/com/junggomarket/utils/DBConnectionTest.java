package com.junggomarket.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionTest {
  public static void main(String[] args) {
   Connection conn = null;
   try {
     conn = DBConnection.getConnection();
     if (conn != null) {
       System.out.println("데이터베이스 연결 성공!");
      } else {
         System.out.println("데이터베이스 연결 실패!");
        }
     } catch (SQLException e) {
        System.out.println("데이터베이스 연결 실패!");
         e.printStackTrace();
      } finally {
         DBConnection.close(conn);
       }
   }
}