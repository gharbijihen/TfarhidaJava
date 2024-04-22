package Service;

import Entities.User;
import Utils.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Entities.Role;


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
        String query = "UPDATE user SET username = ?, first_name = ?, last_name = ?, numero = ?, email = ?, roles = ?, password = ?, is_verified = ?, reset_token = ? "
                + "WHERE id = ?";
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
        statement.setInt(10, user.getId());
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
        return new User(id, username, first_name, last_name, numero, email, roles, password, is_verified, reset_token);
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
}