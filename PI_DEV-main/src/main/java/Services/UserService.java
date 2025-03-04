/*package Services;

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
}*/
/*package Services;

import Models.User;
import Utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements Crud<User> {
    Connection conn;

    public UserService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public boolean create(User obj) throws Exception {
        String sql = "INSERT INTO user (firstName, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, obj.getFirstName());
            stmt.setString(2, obj.getLastName());
            stmt.setInt(3, obj.getAge());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        obj.setId(generatedKeys.getInt(1)); // Assurez-vous que la méthode setId existe
                        System.out.println("Utilisateur ajouté avec succès !");
                        return true; // Insertion réussie
                    }
                }
            } else {
                System.out.println("Aucun utilisateur ajouté.");
                return false; // Aucune ligne insérée
            }
        } catch (SQLException e) {
            throw new Exception("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
        return false; // En cas d'erreur
    }

    @Override
    public void update(User obj) throws Exception {
        String sql = "UPDATE user SET firstName = ?, lastName = ?, age = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getFirstName());
            stmt.setString(2, obj.getLastName());
            stmt.setInt(3, obj.getAge());
            stmt.setInt(4, obj.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(User obj) throws Exception {
        String sql = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, obj.getId());
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
        String sql = "SELECT * FROM user";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id")); // Assurez-vous d'inclure l'ID
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getInt("age"));
                users.add(user);
            }
            return users;
        }
    }
}*/

package Services;

import Models.User;
import Utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements Crud<User> {
    private Connection conn;

    public UserService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public boolean create(User obj) throws Exception {
        String sql = "INSERT INTO user (firstName, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, obj.getFirstName());
            stmt.setString(2, obj.getLastName());
            stmt.setInt(3, obj.getAge());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        obj.setId(generatedKeys.getInt(1)); // Assurez-vous que la méthode setId existe
                        System.out.println("Utilisateur ajouté avec succès !");
                        return true; // Insertion réussie
                    }
                }
            } else {
                System.out.println("Aucun utilisateur ajouté.");
                return false; // Aucune ligne insérée
            }
        } catch (SQLException e) {
            throw new Exception("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
        return false; // En cas d'erreur
    }

    @Override
    public void update(User obj) throws Exception {
        String sql = "UPDATE user SET firstName = ?, lastName = ?, age = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getFirstName());
            stmt.setString(2, obj.getLastName());
            stmt.setInt(3, obj.getAge());
            stmt.setInt(4, obj.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Aucun utilisateur trouvé avec l'ID " + obj.getId());
            }

            System.out.println("Utilisateur mis à jour avec succès !");
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws Exception { // Changer ici pour correspondre à l'interface Crud
        String sql = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Aucun utilisateur trouvé avec l'ID " + id);
            }
            System.out.println("Utilisateur supprimé avec succès !");
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        }
    }

    @Override
    public List<User> getAll() throws Exception {
        String sql = "SELECT * FROM user";
        List<User> users = new ArrayList<>();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id")); // Assurez-vous d'inclure l'ID
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getInt("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
        return users;
    }

    @Override
    public User getById(int id) throws Exception {
        String sql = "SELECT * FROM user WHERE id = ?";
        User user = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getInt("age"));
            } else {
                throw new Exception("Aucun utilisateur trouvé avec l'ID " + id);
            }
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }

        return user; // Retourne l'utilisateur trouvé
    }
}