package Services;

import Models.Seance;
import Models.Type;
import Utils.MyDb;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class SeanceService implements Crud<Seance> {
    Connection conn = MyDb.getInstance().getConn();

    public SeanceService() {}

    @Override
    public boolean create(Seance obj) {
        String sql = "INSERT INTO seance (Titre, Description, Date, LienVideo, Type, heureDebut, heureFin, idCoach, idAdherent, Planning_id) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, obj.getTitre());
            pstmt.setString(2, obj.getDescription());
            pstmt.setDate(3, obj.getDate());
            pstmt.setString(4, obj.getLienVideo());
            pstmt.setString(5, obj.getType().toString());
            pstmt.setTime(6, obj.getHeureDebut());
            pstmt.setTime(7, obj.getHeureFin());
            pstmt.setInt(8, obj.getIdCoach());
            pstmt.setInt(9, obj.getIdAdherent());
            pstmt.setInt(10, obj.getPlanningId());

            int res = pstmt.executeUpdate();

            if (res > 0) {
                System.out.println("Ajout de seance avec succès !");
                return true;
            } else {
                System.out.println("Aucun seance ajouté.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout de seance : " + e.getMessage());
            return false;
        }
    }

            @Override
    public void update(Seance obj) throws Exception {
        String sql = "UPDATE Seance SET Titre= ?, Description= ?, Date= ?, LienVideo= ?, Type= ?, heureDebut= ?, heureFin= ? WHERE id = ?";
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        stmt.setString(1, obj.getTitre());
        stmt.setString(2, obj.getDescription());
        stmt.setDate(3, obj.getDate());
        stmt.setString(4, obj.getLienVideo());
        stmt.setString(5, obj.getType().toString());
        stmt.setTime(6, obj.getHeureDebut());
        stmt.setTime(7, obj.getHeureFin());
        stmt.setInt(8, obj.getId());
        stmt.executeUpdate();
    }
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Seance WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Séance supprimée avec succès !");
            } else {
                System.out.println("Aucune séance trouvée avec l'ID " + id + ".");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la séance : " + e.getMessage());
        }
    }
            @Override
    public List<Seance> getAll() throws Exception {
        String sql = "select * from Seance";
        Statement stmt = this.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Seance> seances = new ArrayList();

        while(rs.next()) {
            Seance seance = new Seance();
            seance.setId(rs.getInt("id"));
            seance.setTitre(rs.getString("Titre"));
            seance.setDescription(rs.getString("Description"));
            seance.setDate(rs.getDate("Date"));
            seance.setType(Type.valueOf(rs.getString("Type")));
            seance.setLienVideo(rs.getString("LienVideo"));
            seance.setHeureDebut(rs.getTime("HeureDebut"));
            seance.setHeureFin(rs.getTime("HeureFin"));
            seance.setIdCoach(rs.getInt("idCoach"));
            seance.setIdAdherent(rs.getInt("idAdherent"));
            seance.setPlanningId(rs.getInt("planning_id"));
            seances.add(seance);
        }

        return seances;
    }
    @Override
    public Seance getById(int id) throws Exception {
        String sql = "select * from seance where id=?";
        Seance obj = null;
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String Titre = rs.getString("Titre");
            String Description = rs.getString("Description");
            Date date = rs.getDate("Date");
            String LienVideo = rs.getString("LienVideo");
            Time HeureDebut = rs.getTime("HeureDebut");
            Time HeureFin = rs.getTime("HeureFin");
            Type Type = Models.Type.valueOf(rs.getString("Type"));
            int idCoach = rs.getInt("idCoach");
            int idAdherent = rs.getInt("idAdherent");
            int planning_id = rs.getInt("planning_id");
            obj = new Seance(id, Titre, Description, date, idCoach, idAdherent, Type, LienVideo, planning_id, HeureDebut, HeureFin);
            return obj;
        } else {
            return obj;
        }
    }

    public List<Seance> getSeancesByPlanningId(int idPlanning) {
        List<Seance> seances = new ArrayList<>();
        String query = "SELECT * FROM seance WHERE Planning_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idPlanning);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Seance seance = new Seance(
                        resultSet.getString("titre"),
                        resultSet.getString("description"),
                        resultSet.getDate("date"),
                        resultSet.getInt("idCoach"),
                        resultSet.getInt("idAdherent"),
                        Type.valueOf(resultSet.getString("type")),
                        resultSet.getString("lienVideo"),
                        resultSet.getInt("Planning_id"),
                        resultSet.getTime("heureDebut"),
                        resultSet.getTime("heureFin")
                );
                seances.add(seance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seances;
    }

}

