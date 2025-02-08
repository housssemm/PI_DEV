package Services;

import Models.EtatEvenement;
import Models.Evenement;
import Models.User;
import Utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EvenementService implements Crud <Evenement> {
    Connection conn;
    public EvenementService(){
        this.conn= MyDb.getInstance().getConn();
    }
    @Override
    public void create(Evenement obj) throws Exception {
    String sql ="insert into evenement(titre,description,dateDebut,dateFin,lieu,etat,prix,image,type,organisateur,capaciteMaximale)" +
            "values('"+obj.getTitre()+"','"+obj.getDescription()+"','"+obj.getDateDebut()+"','"+obj.getDateFin()+"'," +
            "'"+obj.getLieu()+"','"+obj.getEtat()+"','"+obj.getPrix()+"','"+obj.getImage()+"','"+obj.getType()+"','"+obj.getOrganisateur()+"','"+obj.getCapaciteMaximale()+"')";
        Statement st = conn.createStatement();
        st.executeUpdate(sql);
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
    public void delete(Evenement obj) throws Exception {
        String sql = "DELETE FROM evenement WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId());
        stmt.executeUpdate();
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

}
