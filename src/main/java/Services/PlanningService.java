package Services;

import Models.Planning;
import Utils.MyDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlanningService implements Crud<Planning> {
    Connection conn = MyDb.getInstance().getConn();

    public PlanningService() {
    }

    @Override
    public boolean create(Planning obj) {
        String sql = "INSERT INTO Planning (titre, tarif, idCoach) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, obj.getTitre());
            pstmt.setDouble(2, obj.getTarif());
            pstmt.setInt(3, obj.getIdcoach());

            int res = pstmt.executeUpdate();

            if (res > 0) {
                System.out.println("Ajout de planning avec succès !");
                return true;
            } else {
                System.out.println("Aucun planning ajouté.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
            return false;
        }
    }
    @Override
    public void update(Planning obj) throws Exception {
        String sql = "UPDATE Planning SET titre = ?,tarif = ? WHERE id = ?";
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        stmt.setString(1, obj.getTitre());
        stmt.setDouble(2, obj.getTarif());
        stmt.setInt(3, obj.getIdPlanning());
        stmt.executeUpdate();
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM planning WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("planning supprimée avec succès !");
            } else {
                System.out.println("Aucune planning trouvée avec l'ID " + id + ".");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la planning : " + e.getMessage());
        }
    }
    @Override
    public List<Planning> getAll() throws Exception {
        String sql = "select * from Planning";
        Statement stmt = this.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Planning> plannings = new ArrayList();

        while(rs.next()) {
            Planning planning = new Planning();
            planning.setIdPlanning(rs.getInt("id"));
            planning.setIdcoach(rs.getInt("idCoach"));
            planning.setTitre(rs.getString("titre"));
            planning.setTarif(rs.getDouble("tarif"));
            plannings.add(planning);
        }

        return plannings;
    }
    @Override
    public Planning getById(int id) throws Exception {
        String sql = "select * from planning where id=?";
        Planning obj = null;
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String titre = rs.getString("titre");
            int idCoach = rs.getInt("idCoach");
            Double tarif = rs.getDouble("tarif");
            obj = new Planning(id, idCoach, titre, tarif);
            return obj;
        } else {
            return obj;
        }
    }
}
