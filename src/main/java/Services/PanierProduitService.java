package Services;
import Models.etatP;
import Models.panierProduit;
import Utils.MyDb;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class PanierProduitService implements Crud<panierProduit> {
    Connection conn;

    public PanierProduitService() {
        this.conn = MyDb.getInstance().getConn();
    }
    @Override
    public boolean create(panierProduit obj) throws Exception {
        throw new UnsupportedOperationException("Create Not supported");
    }

    public boolean ajouterPlusieursProduitsPanier(List<panierProduit> panierProduitLists) throws Exception {
        String sql = "INSERT INTO PanierProduit (produitId, panierId, quantite, date, montant, etat_paiement) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            for (panierProduit panierProds : panierProduitLists) {
                statement.setInt(1, panierProds.getProduitId());
                statement.setInt(2, panierProds.getPanierId());
                statement.setInt(3, panierProds.getQuantite());
                statement.setTimestamp(4, Timestamp.valueOf(panierProds.getDate()));
                statement.setFloat(5, panierProds.getMontant());
                statement.setString(6, panierProds.getEtat_paiement().name());
                statement.addBatch(); // Ajout de la requête à la batch
            }

            statement.executeBatch(); // Exécute toutes les requêtes en une seule fois
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout des produits au panier : " + e.getMessage());
            return false;
        }
        System.out.println("Tous les produits ont été ajoutés avec succès !");
        return true;
    }
    @Override
    public void delete(int id)throws Exception {
        String sql = "DELETE FROM PanierProduit WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Produit supprimé du panier avec succès !");
            } else {
                System.out.println("Aucun produit trouvé avec les IDs spécifiés.");
            }
        }
    }

    public void modifierQuantiteProduitDansPanier(int id, int nouvelleQuantite) throws Exception {
        String sql = "UPDATE PanierProduit SET quantite = ? WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            // Définir les paramètres dans la requête SQL
            statement.setInt(1, nouvelleQuantite); // Utiliser nouvelleQuantite ici
            statement.setInt(2, id); // Utiliser id pour spécifier quel produit à mettre à jour

            // Exécuter la mise à jour
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Quantité mise à jour avec succès !");
            } else {
                System.out.println("Aucun produit trouvé avec les IDs spécifiés.");
            }
        }
    }


    @Override
    public void update(panierProduit obj)throws Exception {
        throw new UnsupportedOperationException("Update not supported.");
    }


    @Override
    public List<panierProduit> getAll(){
        throw new UnsupportedOperationException("getAll not supported.");
    }

    public List<panierProduit> getProduitsDansPanier(int panierId) throws Exception {
        String sql = "SELECT * FROM PanierProduit WHERE panierId = ?";
        List<panierProduit> produitsDansPanier = new ArrayList<>();

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, panierId); // Définir le panierId
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                panierProduit panierProd = new panierProduit();
                panierProd.setId(rs.getInt("id"));
                panierProd.setProduitId(rs.getInt("produitId"));
                panierProd.setPanierId(rs.getInt("panierId"));
                panierProd.setQuantite(rs.getInt("quantite"));
                panierProd.setDate(rs.getTimestamp("date").toLocalDateTime());
                panierProd.setMontant(rs.getFloat("montant"));
                panierProd.setEtat_paiement(etatP.valueOf(rs.getString("etat_paiement")));

                produitsDansPanier.add(panierProd);
            }
        }
        return produitsDansPanier;
    }
    @Override
    public panierProduit getById(int id) throws Exception {
        String sql = "select * from panierProduit where id=?";
        panierProduit obj = null;
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int idpanier = rs.getInt("Id panier");
            int idproduit = rs.getInt("Id produit");
            int quantite = rs.getInt("Quantite");
            LocalDateTime date=rs.getTimestamp("date").toLocalDateTime();
            float montant = rs.getFloat("montant");
            etatP etat_paiement= etatP.valueOf(rs.getString("etat"));
            obj=new panierProduit(id,idproduit,idpanier,quantite,date,montant,etat_paiement);
            return obj;
        }
        return obj;
    }
}
