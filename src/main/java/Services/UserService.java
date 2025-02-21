
package Services;

import Models.User;
import Utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    Connection conn;

    public UserService(Connection conn) {
        this.conn = MyDb.getInstance().getConn();
    }


    public int createAndReturnId(User obj) {
        String sql = "INSERT INTO user (nom, prenom, image, email, MDP) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, obj.getNom());
            pst.setString(2, obj.getPrenom());
            pst.setString(3, obj.getImage());
            pst.setString(4, obj.getEmail());
            pst.setString(5, obj.getMDP());

            int res = pst.executeUpdate();
            if (res > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Récupérer l'ID généré
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Indique une erreur
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE user SET nom = ?, prenom = ?, image = ?, email = ?, MDP = ? WHERE id = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, user.getNom());
            pst.setString(2, user.getPrenom());
            pst.setString(3, user.getImage());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getMDP());
            pst.setInt(6, user.getId());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println(" ✅ Utilisateur mis à jour avec succès !");
                return true;
            } else {
                System.out.println("Aucune mise à jour pour l'utilisateur.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteUser(int id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            int res = pst.executeUpdate();
            if (res > 0) {
                System.out.println("✅ Utilisateur supprimé avec succès !");
                return true;
            } else {
                System.out.println("❌ Aucune suppression d'utilisateur effectuée.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("image"),
                        rs.getString("email"),
                        rs.getString("MDP")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM user WHERE id = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("image"),
                            rs.getString("email"),
                            rs.getString("MDP")
                    );
                } else {
                    System.out.println("Aucun utilisateur trouvé avec l'id : " + id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean login(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND MDP = ?";

        try {
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setString(1, email);
            prepare.setString(2, password);
            ResultSet result = prepare.executeQuery();

            return result.next(); // Retourne vrai si un utilisateur existe
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public User getUserByEmail(String email) {
        User user = null;
        String query = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("image"),
                        resultSet.getString("email"),
                        resultSet.getString("MDP")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
