package Services;

import Models.panier;
import Utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class panierService implements Crud<panier>{
    Connection conn;
    public panierService() {
        this.conn = MyDb.getInstance().getConn();
    }
    @Override
    public boolean create(panier obj) throws Exception {
        // Vérification de validité des données
        if (obj.getAdherentId() != null && obj.getCoachId() != null) {
            throw new Exception("Vous ne pouvez pas ajouter à la fois un adhérent et un coach dans le même panier.");
        }
        if (obj.getAdherentId() == null && obj.getCoachId() == null) {
            throw new Exception("Un panier doit être associé à un adhérent ou un coach.");
        }
        // Préparation de la requête SQL
        String sql = "INSERT INTO panier (adherentId, coachId) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Définition des paramètres
            if (obj.getAdherentId() != null) {
                stmt.setInt(1, obj.getAdherentId()); // Adhérent ID
                stmt.setNull(2, Types.INTEGER);     // Coach ID = null
            } else {
                stmt.setNull(1, Types.INTEGER);     // Adhérent ID = null
                stmt.setInt(2, obj.getCoachId());   // Coach ID
            }
            // Exécution de la requête
            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ajout réussi dans le panier !");
                return true;
            } else {
                System.out.println("Aucune ajout panier n'a été effectuée.");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    @Override
    public void update(panier obj) {
        throw new UnsupportedOperationException("Update not supported.");
    }
    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Delete not supported.");
    }
    //  vider le panier d'un adherent
    public void deleteByAdherent(int adherentId) throws Exception {
        String sql = "DELETE FROM panier WHERE adherentId = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
        stmt.setInt(1, adherentId);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Le panier d'adherent avec ID " + adherentId + " a été vidé avec succès.");
            } else {
                System.out.println("Aucun panier trouvé pour adherent avec ID " + adherentId + ".");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression du panier pour l'adherent avec ID " + adherentId + ": " + e.getMessage());
        }
    }
    //  vider le panier d'un coach
    public void deleteByCoach(int coachId) throws Exception {
        String sql = "DELETE FROM panier WHERE coachId = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
        stmt.setInt(1, coachId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Le panier de coach avec ID " + coachId + " a été vidé avec succès.");
            } else {
                System.out.println("Aucun panier trouvé pour coach avec ID " + coachId + ".");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression du panier pour le coach avec ID " + coachId + ": " + e.getMessage());
        }
    }
    @Override
    public List<panier> getAll() throws Exception {
        String sql = "select * from panier";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<panier> paniers = new ArrayList<>();
        while (rs.next()) {
            panier panier = new panier();
            panier.setId(rs.getInt("id"));
            panier.setAdherentId(rs.getInt("adherentId"));
            panier.setCoachId(rs.getInt("coachId"));
            paniers.add(panier);
        }
        return paniers;
    }
    @Override
    public panier getById(int id) throws Exception {
        String sql = "select * from panier where id=?";
        panier obj = null;
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int idCoach = rs.getInt("coachId");
            int idAdherent = rs.getInt("adherentId");
            obj=new panier(id,idCoach,idAdherent);
            return obj;
        }
        return obj;
    }
}
