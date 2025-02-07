package Services;

import Models.User;
import Utils.MyDb;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements Crud<User> {
    Connection conn;

    public UserService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(User obj) throws Exception {
        String sql = "insert into user (firstName, lastName, age) values ('" +
                obj.getFirstName() + "','" + obj.getLastName() + "','" +
                obj.getAge() + "')";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }

    @Override
    public void update(User obj) throws Exception {
        String sql = "update user set firstName = ?,lastName = ?,age = ? where id = ? ";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, obj.getFirstName());
        stmt.setString(2, obj.getLastName());
        stmt.setInt(3, obj.getAge());
        stmt.setInt(4, obj.getId());
        stmt.executeUpdate();
    }

//    @Override
//    public void delete(User obj) throws Exception {
//    String sql="delete from user where id = ? ";
//    PreparedStatement stmt = conn.prepareStatement(sql);
//    stmt.setInt(1, obj.getId());
//    stmt.executeUpdate();
//        stmt.close();
//    }
@Override
public void delete(User obj) throws Exception {
    String sql = "DELETE FROM user WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, obj.getId());

        // Vérifier si un utilisateur a été supprimé
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new Exception("Aucun utilisateur trouvé avec l'ID " + obj.getId());
        }

        System.out.println("Utilisateur supprimé avec succès !");
    } catch (SQLException e) {
        throw new Exception("Erreur lors de la suppression de l'utilisateur: " + e.getMessage());
    }
}


    @Override
    public List<User> getAll() throws Exception {
        String sql = "select * from user";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setAge(rs.getInt("age"));
            users.add(user);
        }
        return users;
    }
}