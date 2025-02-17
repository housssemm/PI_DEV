package Services;

import Models.EtatEvenement;
import Models.Evenement;

import Utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementService implements Crud <Evenement> {
    Connection conn;
    public EvenementService(){
        this.conn= MyDb.getInstance().getConn();
    }


    @Override
    public boolean create(Evenement obj) throws Exception {
        String sql ="insert into evenement(titre,description,dateDebut,dateFin,lieu,etat,prix,image,type,organisateur,capaciteMaximale)" +
                "values('"+obj.getTitre()+"','"+obj.getDescription()+"','"+obj.getDateDebut()+"','"+obj.getDateFin()+"'," +
                "'"+obj.getLieu()+"','"+obj.getEtat()+"','"+obj.getPrix()+"','"+obj.getImage()+"','"+obj.getType()+"','"+obj.getOrganisateur()+"','"+obj.getCapaciteMaximale()+"')";
        try{
            Statement st = conn.createStatement();
            int res = st.executeUpdate(sql);
            if (res > 0) {
                System.out.println("Ajout evenement avec succès !");
                return true ;
            } else {
                System.out.println("Aucune ajout de evenement à effectuée ");
            }}catch (Exception e){
            System.out.println(e.getMessage());
            return false ;
        }
        return false;
    }

    @Override
    public void update(Evenement obj) throws Exception {
        String sql = "update evenement set titre = ?,description= ?,dateDebut =?,dateFin=? ,lieu=?,etat=?,prix=? ,image=?,type=?,organisateur=?,capaciteMaximale=? where id = ? ";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, obj.getTitre());
        stmt.setString(2, obj.getDescription());
        stmt.setDate(3, new java.sql.Date(obj.getDateDebut().getTime()));
        stmt.setDate(4, new java.sql.Date(obj.getDateFin().getTime()));
        stmt.setString(5, obj.getLieu());
        stmt.setString(6, obj.getEtat().name());
        stmt.setDouble(7,obj.getPrix());
        stmt.setString(8,obj.getImage());
        stmt.setString(9,obj.getType());
        stmt.setString(10,obj.getOrganisateur());
        stmt.setInt(11,obj.getCapaciteMaximale());
        stmt.setInt(12, obj.getId());
        stmt.executeUpdate();
    }



@Override
    public void delete(int id) throws Exception {
        String req = "DELETE FROM evenement WHERE `id`=?";
        try (PreparedStatement pstmt = conn.prepareStatement(req)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();


            if (rowsAffected > 0) {
                System.out.println("Suppression evenement effectuée avec succès !");
            } else {
                System.out.println("Aucune ligne supprimée evenement. Vérifiez l'ID.");
            }
        } catch (SQLException e) {
            // Handle the exception more gracefully, e.g., log the error or display a user-friendly message
            e.printStackTrace();
        }
    }

    @Override
    public List<Evenement> getAll() throws Exception {
        String sql = "select * from evenement";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Evenement> evenements = new ArrayList<>();
        while (rs.next()) {
            Evenement obj = new Evenement();
            obj.setId(rs.getInt("id"));
            obj.setTitre(rs.getString("titre"));
            obj.setDescription(rs.getString("description"));
            obj.setDateDebut(rs.getDate("dateDebut"));
            obj.setDateFin(rs.getDate("dateFin"));
            obj.setLieu(rs.getString("lieu"));
            obj.setEtat(EtatEvenement.valueOf(rs.getString("etat")));
            obj.setPrix(rs.getDouble("prix"));
            obj.setImage(rs.getString("image"));
            obj.setType(rs.getString("type"));
            obj.setOrganisateur(rs.getString("organisateur"));
            obj.setCapaciteMaximale(rs.getInt("capaciteMaximale"));
            evenements.add(obj);
        }
        return evenements;
    }
//get by id
    @Override
    public Evenement getById(int id) throws Exception {
        String sql = "select * from evenement where id=?";
        Evenement obj = null;
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {

           String titre= rs.getString("titre");
           String description= rs.getString("description");
          Date dateDebut=  rs.getDate("dateDebut");
           Date dateFin= rs.getDate("dateFin");
          String lieu=  rs.getString("lieu");
            Models.EtatEvenement etat=Models.EtatEvenement.valueOf(rs.getString("etat"));
            Double prix =rs.getDouble("prix");
           String image= rs.getString("image");
           String type =rs.getString("type");
           String organisateur=rs.getString("organisateur");
            int capaciteMaximale=rs.getInt("capaciteMaximale");
             obj=new Evenement(id,titre,description,dateDebut,dateFin,lieu, image,prix, etat,type,organisateur,capaciteMaximale);
            return obj;

        }
        return obj;
    }
}
