package Services;

import Models.*;
import Utils.MyDb;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ParticipantEvenementService implements Crud <ParticipantEvenement>{
    Connection conn;
    public ParticipantEvenementService(){
        this.conn= MyDb.getInstance().getConn();
    }
   @Override
    public boolean create(ParticipantEvenement obj) throws Exception {
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       String formattedDate = dateFormat.format(obj.getDateInscription());
        String sql="insert into participantevenement(evenementId,userId,date_inscription,etat_paiement)" +
                " values('"+obj.getIdEvenement()+"','"+obj.getIdParticipant()+"'," +
                "'"+formattedDate +"','"+obj.getEtatPaiement()+"')";
        try{
        Statement st = conn.createStatement();
        int res = st.executeUpdate(sql);
        if (res > 0) {
            System.out.println("Ajout partispant avec succès !");
            return true ;
        } else {
            System.out.println("Aucune ajout de partispant à effectuée ");
        }}catch (Exception e){
        System.out.println(e.getMessage());
        return false ;
        }
        return false;
    }


    @Override
    public void update(ParticipantEvenement obj) {
        String req = "UPDATE participantevenement SET  userId=?, evenementId=?, date_inscription=?, etat_paiement=?  WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(req)) {
            pstmt.setInt(1, obj.getIdParticipant());
            pstmt.setInt(2, obj.getIdEvenement());
            pstmt.setDate(3, new java.sql.Date(obj.getDateInscription().getTime()));
            pstmt.setString(4, obj.getEtatPaiement().name());
            pstmt.setInt(5, obj.getId());


            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Modification ParticipantEvenement effectuée avec succès !");
            } else {
                System.out.println("Vérifier l' id de ParticipantEvenement");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//@Override
//public void update(ParticipantEvenement obj) throws SQLException {
//    String req = "UPDATE participantevenement SET  userId=?, evenementId=?, date_inscription=?, etat_paiement=?  WHERE id= ?";
//   PreparedStatement pstmt = conn.prepareStatement(req);
//
//        pstmt.setInt(1, obj.getIdParticipant());
//        pstmt.setInt(2, obj.getIdEvenement());
//        pstmt.setDate(3, new java.sql.Date(obj.getDateInscription().getTime()));
//        pstmt.setString(4, obj.getEtatPaiement().name());
//        pstmt.setInt(5, obj.getId());
//
//
//         pstmt.executeUpdate();
//
//
//}

@Override
    public void delete(int id) throws Exception {
        String req = "DELETE FROM participantevenement WHERE `id`=?";
        try (PreparedStatement pstmt = conn.prepareStatement(req)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();


            if (rowsAffected > 0) {
                System.out.println("Suppression partispant effectuée avec succès !");
            } else {
                System.out.println("Aucune ligne supprimée a participantevenement. Vérifiez l'ID .");
            }
        } catch (SQLException e) {
            // Handle the exception more gracefully, e.g., log the error or display a user-friendly message
            e.printStackTrace();
        }
    }


    @Override
    public List<ParticipantEvenement> getAll() throws Exception {
        String sql = "select * from participantevenement";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<ParticipantEvenement> participantEvenements = new ArrayList<>();
        while (rs.next()) {
            ParticipantEvenement participant = new ParticipantEvenement();
            participant.setIdEvenement(rs.getInt("evenementId"));
            participant.setIdParticipant(rs.getInt("userId"));
            participant.setDateInscription(rs.getDate("date_inscription"));
            participant.setEtatPaiement(etatPaiement.valueOf(rs.getString("etat_paiement")));
            participant.setId(rs.getInt("id"));
            participantEvenements.add(participant);

        }
        return participantEvenements;
    }
    @Override
    public ParticipantEvenement getById(int id) {
        String req = "SELECT * FROM participantevenement WHERE id = ?";
        ParticipantEvenement post = null;
        try (PreparedStatement ps = conn.prepareStatement(req)) {
            ps.setInt(1, id); // Set the value of the id parameter
            ResultSet res = ps.executeQuery();

            if (res.next()) {


                int idParticipant = res.getInt("userId");
                Models.etatPaiement etatPaiement   = Models.etatPaiement.valueOf(res.getString("etat_paiement"));
                Date  dateInscription     = res.getDate("date_inscription");

                int idEvenement = res.getInt("evenementId");



                post = new ParticipantEvenement(id,idParticipant,  dateInscription,  etatPaiement,  idEvenement);
                return post;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;

    }





//        public List<User> getParticipantsByEvent(int eventId) {
//            List<User> participants = new ArrayList<>();
//            String query = "SELECT userId FROM participantevenement WHERE evenementId = ?";
//
////            try (Connection conn = DatabaseConnection.getInstance().getConnection();
//                try( PreparedStatement stmt = conn.prepareStatement(query)) {
//
//                stmt.setInt(1, eventId);
//                ResultSet rs = stmt.executeQuery();
//
//                while (rs.next()) {
//                    participants.add(new User(rs.getString("nom"), rs.getString("email"),rs.getString("prenom"),rs.getString("image")));
//                }
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//            return participants;
//        }


//
//    public List<User> getParticipantsByEvent(int eventId) {
//        List<User> participants = new ArrayList<>();
//        String query = "SELECT u.nom, u.email, u.prenom, u.image " +
//                "FROM participantevenement pe " +
//                "JOIN user u ON pe.userId = u.id " +  // Assurez-vous que la colonne 'id' dans 'user' est correcte
//                "WHERE pe.evenementId = ?";
//
//        try (PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setInt(1, eventId);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                participants.add(new User(
//                        rs.getString("nom"),
//                        rs.getString("email"),
//                        rs.getString("prenom"),
//                        rs.getString("image")
//                ));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return participants;
//    }

    public List<User> getParticipantsByEvent(int eventId) {
        List<User> participants = new ArrayList<>();
        String query = "SELECT u.id, u.nom, u.prenom, u.email, u.image " +
                "FROM participantevenement p " +
                "JOIN user u ON p.userId = u.id " +
                "WHERE p.evenementId = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                participants.add(new User( rs.getString("nom"),
                        rs.getString("prenom"), rs.getString("email"),
                        rs.getString("image")));
               // rs.getString("image"),rs.getString("MDP")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }
    // Add this to ParticipantEvenementService class
    public void updatePaymentStatus(int userId, int eventId) {
        String query = "UPDATE participant_evenement SET etat_paiement = ? WHERE participant_id = ? AND evenement_id = ?";
        try (
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, etatPaiement.PAYE.name());
            pstmt.setInt(2, userId);
            pstmt.setInt(3, eventId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating payment status: " + e.getMessage());
        }
    }
}




