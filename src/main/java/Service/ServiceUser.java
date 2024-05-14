package Service;

import Entities.User;
import Utils.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.Role;
import javafx.scene.control.Alert;

import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

public class ServiceUser implements IService<User> {
    private Connection connection = Datasource.getConn();

    public ServiceUser() {
    }


    @Override
    public void add(User user) throws SQLException {
        String query = "INSERT INTO user (username, first_name, last_name, numero, email, roles, password, is_verified, reset_token) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getFirst_name());
        statement.setString(3, user.getLast_name());
        statement.setInt(4, user.getNumero());
        statement.setString(5, user.getEmail());
        statement.setString(6, user.getRoles());
        statement.setString(7, user.getPassword());
        statement.setBoolean(8, user.getIs_verified());
        statement.setString(9, user.getReset_token());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void update(User user) throws SQLException {
        String query = "UPDATE user SET username = ?, first_name = ?, last_name = ?, numero = ?, email = ?, roles = ?, password = ?, is_verified = ?, reset_token = ?, verification_code = ? "
                + "WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getFirst_name());
        statement.setString(3, user.getLast_name());
        statement.setInt(4, user.getNumero());
        statement.setString(5, user.getEmail());
        statement.setString(6, user.getRoles());
        System.out.println("Updating this password : "+user.getPassword());
        statement.setString(7, user.getPassword());
        statement.setBoolean(8, user.getIs_verified());
        statement.setString(9, user.getReset_token());
        statement.setInt(10, user.getVerificationCode());
        statement.setInt(11,user.getId());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void delete(User user) throws SQLException {
        String query = "DELETE FROM user WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, user.getId());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public User findById(int id) throws SQLException {
        String query = "SELECT * FROM user WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = mapResultSetToUser(resultSet);
        }
        resultSet.close();
        statement.close();
        return user;
    }

    @Override
    public List<User> ReadAll() throws SQLException {
        String query = "SELECT * FROM user";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            User user = mapResultSetToUser(resultSet);
            userList.add(user);
        }
        resultSet.close();
        statement.close();
        System.out.println(userList.toString());
        return userList;
    }
    public Map<Boolean, Integer> getusersByEtat() {
        Map<Boolean, Integer> reclamationByetat = new HashMap<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Obtain the connection directly within the method
            connection = Datasource.getConn();

            String query = "SELECT is_verified, COUNT(*) AS count FROM user GROUP BY is_verified";            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                boolean is_verified = resultSet.getBoolean("is_verified");
                int count = resultSet.getInt("count");
                reclamationByetat.put(is_verified, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the ResultSet, PreparedStatement, and Connection
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                // Do not close the connection here to keep it open
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return reclamationByetat;
    }
    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String first_name = resultSet.getString("first_name");
        String last_name = resultSet.getString("last_name");
        int numero = resultSet.getInt("numero");
        String email = resultSet.getString("email");
        String roles = resultSet.getString("roles");
        String password = resultSet.getString("password");
        boolean is_verified = resultSet.getBoolean("is_verified");
        String reset_token = resultSet.getString("reset_token");
        User user= new User(id, username, first_name, last_name, numero, email, roles, password, is_verified, reset_token);
        System.out.println("verification code = "+resultSet.getInt("verification_code"));
        user.setVerificationCode(resultSet.getInt("verification_code"));
        return user;
    }
    public User findByEmail(String email) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = mapResultSetToUser(resultSet);
        }
        resultSet.close();
        statement.close();
        return user;
    }
    public boolean emailExists(String email){
        String query = "SELECT  COUNT(*) FROM user WHERE email = ?";
        try{ PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; //if count>0 email exists
            }
        }catch(SQLException e){
            System.err.println("error checking email existence "+e.getMessage());
        }
        return false;
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public User login(String email, String password) throws SQLException {
        User user = null;
        if (!emailExists(email)) {
            System.out.println("email doesn't exist");
            showAlert("email non existante");
            return null;
        }
        String query = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
          //   statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setFirst_name(resultSet.getString("first_name"));
                    user.setLast_name(resultSet.getString("last_name"));
                    user.setIs_verified(resultSet.getBoolean("is_verified"));
                    user.setNumero(resultSet.getInt("numero"));
                    user.setUsername(resultSet.getString("username"));
                    user.setRoles(resultSet.getString("roles"));
                    if (BCrypt.checkpw(password,user.getPassword()))
                        return user; // User found with the given email, password, and role
                    else
                        return null;
            }
            return null; // No user found with the given email, password, and role
        }
    }

    public boolean checkpassword(User user, String newPassword) {
        String hashedPasswordFromDB = user.getPassword(); // Already hashed in the database

        // Check if the provided password matches the hashed password from the database
        return BCrypt.checkpw(newPassword, hashedPasswordFromDB);
    }

    public User getOneUser(String email) throws SQLException {
        String req = "SELECT * FROM `user` where email = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        User user = new User();
        user.setId(-999);
        while (rs.next()) {
            user=mapResultSetToUser(rs);
        }
        ps.close();
        return user;
    }

}