package com.junggomarket.dao;

   import com.junggomarket.dto.UserDTO;
    import com.junggomarket.utils.DBConnection;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;


    public class UserDAO {
      public int insertUser(UserDTO user) throws SQLException {
           Connection conn = null;
         PreparedStatement pstmt = null;
          int result = 0;
          try {
             conn = DBConnection.getConnection();
               String sql = "INSERT INTO users (username, password, email, phone, role) VALUES (?, ?, ?, ?, ?)";
             pstmt = conn.prepareStatement(sql);
               pstmt.setString(1, user.getUsername());
               pstmt.setString(2, user.getPassword());
             pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getPhone());
              pstmt.setString(5, "user");
               result = pstmt.executeUpdate();

          } catch (SQLException e) {
              e.printStackTrace();
              throw e;
         } finally {
            if(pstmt != null) pstmt.close();
              DBConnection.close(conn);
           }
        return result;
      }
    public UserDTO selectUserByUsername(String username) throws SQLException {
         Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;
         UserDTO user = null;

       try {
            conn = DBConnection.getConnection();
          String sql = "SELECT * FROM users WHERE username = ?";
           pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, username);
         rs = pstmt.executeQuery();

        if (rs.next()) {
             user = new UserDTO();
             user.setUserId(rs.getInt("user_id"));
              user.setUsername(rs.getString("username"));
             user.setPassword(rs.getString("password"));
              user.setEmail(rs.getString("email"));
             user.setPhone(rs.getString("phone"));
              user.setRole(rs.getString("role"));
         }
      } catch (SQLException e) {
          e.printStackTrace();
         throw e;
      } finally {
        if (rs != null) rs.close();
          if (pstmt != null) pstmt.close();
          DBConnection.close(conn);
       }
     return user;
  }
     public List<UserDTO> selectUserList() throws SQLException {
       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;
        List<UserDTO> userList = new ArrayList<>();

     try {
        conn = DBConnection.getConnection();
          String sql = "SELECT * FROM users";
         pstmt = conn.prepareStatement(sql);
          rs = pstmt.executeQuery();

     while (rs.next()) {
        UserDTO user = new UserDTO();
         user.setUserId(rs.getInt("user_id"));
           user.setUsername(rs.getString("username"));
          user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
         user.setPhone(rs.getString("phone"));
        user.setRole(rs.getString("role"));
        userList.add(user);
        }
    } catch (SQLException e) {
       e.printStackTrace();
         throw e;
    } finally {
      if (rs != null) rs.close();
         if (pstmt != null) pstmt.close();
          DBConnection.close(conn);
      }
     return userList;
  }
 }