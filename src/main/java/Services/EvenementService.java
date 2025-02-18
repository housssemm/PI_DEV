//package Services;
//
//import Models.EtatEvenement;
//import Models.Evenement;
//import Utils.MyDb;
//import javafx.scene.image.Image;
//import javafx.scene.image.WritableImage;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class EvenementService implements Crud<Evenement> {
//    private Connection conn;
//
//    public EvenementService() {
//        this.conn = MyDb.getInstance().getConn();
//        if (this.conn == null) {
//            System.out.println("Database connection is not established.");
//        }
//    }
//
//    private byte[] imageToByteArray(Image image) {
//        if (image == null) {
//            return new byte[0]; // Return empty array if no image
//        }
//
//        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
//            // Convert Image to BufferedImage
//            WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
//            writableImage.getPixelWriter().setPixels(0, 0, (int) image.getWidth(), (int) image.getHeight(), image.getPixelReader(), 0, 0);
//
//            // Convert WritableImage to BufferedImage (manually)
//            BufferedImage bufferedImage = new BufferedImage((int) image.getWidth(), (int) image.getHeight(), BufferedImage.TYPE_INT_ARGB);
//            for (int y = 0; y < image.getHeight(); y++) {
//                for (int x = 0; x < image.getWidth(); x++) {
//                    bufferedImage.setRGB(x, y, (int) image.getPixelReader().getColor(x, y).toArgb());
//                }
//            }
//
//            // Write the BufferedImage to ByteArrayOutputStream
//            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);  // "png" can be replaced with other formats
//            return byteArrayOutputStream.toByteArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new byte[0];
//        }
//    }
//
//
//    @Override
//    public boolean create(Evenement obj) throws Exception {
//        String sql = "INSERT INTO evenement (titre, description, dateDebut, dateFin, lieu, etat, prix, image, type, organisateur, capaciteMaximale) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, obj.getTitre());
//            stmt.setString(2, obj.getDescription());
//            stmt.setDate(3, java.sql.Date.valueOf(obj.getDateDebut()));
//            stmt.setDate(4, java.sql.Date.valueOf(obj.getDateFin()));
//            stmt.setString(5, obj.getLieu());
//            stmt.setString(6, obj.getEtat().name());
//            stmt.setDouble(7, obj.getPrix());
//
//            // Convert the Image to byte[] for storing in the database
//            stmt.setBytes(8, imageToByteArray(obj.getImage()));
//
//            stmt.setString(9, obj.getType());
//            stmt.setString(10, obj.getOrganisateur());
//            stmt.setInt(11, obj.getCapaciteMaximale());
//
//            int res = stmt.executeUpdate();
//            if (res > 0) {
//                System.out.println("Ajout evenement avec succès !");
//                return true;
//            } else {
//                System.out.println("Aucune ajout de evenement effectuée.");
//            }
//        } catch (SQLException e) {
//            System.out.println("Erreur lors de l'ajout de l'événement: " + e.getMessage());
//            return false;
//        }
//        return false;
//    }
//
//    @Override
//    public void update(Evenement obj) throws Exception {
//        String sql = "UPDATE evenement SET titre = ?, description = ?, dateDebut = ?, dateFin = ?, lieu = ?, etat = ?, prix = ?, image = ?, type = ?, organisateur = ?, capaciteMaximale = ? WHERE id = ?";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, obj.getTitre());
//            stmt.setString(2, obj.getDescription());
//            stmt.setDate(3, java.sql.Date.valueOf(obj.getDateDebut()));
//            stmt.setDate(4, java.sql.Date.valueOf(obj.getDateFin()));
//            stmt.setString(5, obj.getLieu());
//            stmt.setString(6, obj.getEtat().name());
//            stmt.setDouble(7, obj.getPrix());
//
//            // Convert the Image to byte[] for storing in the database
//            stmt.setBytes(8, imageToByteArray(obj.getImage()));
//
//            stmt.setString(9, obj.getType());
//            stmt.setString(10, obj.getOrganisateur());
//            stmt.setInt(11, obj.getCapaciteMaximale());
//            stmt.setInt(12, obj.getId());
//
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("Erreur lors de la mise à jour de l'événement: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void delete(int id) throws Exception {
//        String req = "DELETE FROM evenement WHERE id = ?";
//        try (PreparedStatement pstmt = conn.prepareStatement(req)) {
//            pstmt.setInt(1, id);
//            int rowsAffected = pstmt.executeUpdate();
//
//            if (rowsAffected > 0) {
//                System.out.println("Suppression événement effectuée avec succès !");
//            } else {
//                System.out.println("Aucune ligne supprimée. Vérifiez l'ID.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public List<Evenement> getAll() throws Exception {
//        String sql = "SELECT * FROM evenement";
//        List<Evenement> evenements = new ArrayList<>();
//        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
//            while (rs.next()) {
//                Evenement obj = new Evenement();
//                obj.setId(rs.getInt("id"));
//                obj.setTitre(rs.getString("titre"));
//                obj.setDescription(rs.getString("description"));
//                obj.setDateDebut(rs.getDate("dateDebut").toLocalDate());
//                obj.setDateFin(rs.getDate("dateFin").toLocalDate());
//                obj.setLieu(rs.getString("lieu"));
//                obj.setEtat(EtatEvenement.valueOf(rs.getString("etat")));
//                obj.setPrix(rs.getDouble("prix"));
//
//                // Convert byte[] to Image when retrieving from database
//                byte[] imageBytes = rs.getBytes("image");
//                Image image = new Image(new java.io.ByteArrayInputStream(imageBytes));
//                obj.setImage((Blob) image);
//
//                obj.setType(rs.getString("type"));
//                obj.setOrganisateur(rs.getString("organisateur"));
//                obj.setCapaciteMaximale(rs.getInt("capaciteMaximale"));
//                evenements.add(obj);
//            }
//        } catch (SQLException e) {
//            System.out.println("Erreur lors de la récupération des événements: " + e.getMessage());
//        }
//        return evenements;
//    }
//
//    @Override
//    public Evenement getById(int id) throws Exception {
//        String sql = "SELECT * FROM evenement WHERE id = ?";
//        Evenement obj = null;
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    obj = new Evenement(
//                            id,
//                            rs.getString("titre"),
//                            rs.getString("description"),
//                            rs.getDate("dateDebut").toLocalDate(),
//                            rs.getDate("dateFin").toLocalDate(),
//                            rs.getString("lieu"),
//                            new Image(new java.io.ByteArrayInputStream(rs.getBytes("image"))),  // Convert byte[] to Image
//                            rs.getDouble("prix"),
//                            EtatEvenement.valueOf(rs.getString("etat")),
//                            rs.getString("type"),
//                            rs.getString("organisateur"),
//                            rs.getInt("capaciteMaximale")
//                    );
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Erreur lors de la récupération de l'événement: " + e.getMessage());
//        }
//        return obj;
//    }
//}



package Services;

import Models.EtatEvenement;
import Models.Evenement;

import Utils.MyDb;

import java.io.ByteArrayInputStream;
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
    String sql = "INSERT INTO evenement (titre, description, dateDebut, dateFin, lieu, etat, prix, image, type, organisateur, capaciteMaximale) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, obj.getTitre());
        stmt.setString(2, obj.getDescription());
        stmt.setDate(3, java.sql.Date.valueOf(obj.getDateDebut()));
        stmt.setDate(4, java.sql.Date.valueOf(obj.getDateFin()));
        stmt.setString(5, obj.getLieu());
        stmt.setString(6, obj.getEtat().name());
        stmt.setDouble(7, obj.getPrix());

        // Check if the image is provided and handle it as a Blob
        if (obj.getImage() != null && obj.getImage().length > 0) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(obj.getImage());
            stmt.setBinaryStream(8, byteArrayInputStream, obj.getImage().length);
        } else {
            stmt.setNull(8, Types.BLOB);  // If no image, set it to NULL
        }

        stmt.setString(9, obj.getType());
        stmt.setString(10, obj.getOrganisateur());
        stmt.setInt(11, obj.getCapaciteMaximale());

        int res = stmt.executeUpdate();
        if (res > 0) {
            System.out.println("Ajout evenement avec succès !");
            return true;
        } else {
            System.out.println("Aucune ajout de evenement à effectuée");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
    return false;
}


    @Override
    public void update(Evenement obj) throws Exception {
        String sql = "UPDATE evenement SET titre = ?, description = ?, dateDebut = ?, dateFin = ?, lieu = ?, etat = ?, prix = ?, image = ?, type = ?, organisateur = ?, capaciteMaximale = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getTitre());
            stmt.setString(2, obj.getDescription());
            stmt.setDate(3, java.sql.Date.valueOf(obj.getDateDebut()));
            stmt.setDate(4, java.sql.Date.valueOf(obj.getDateFin()));
            stmt.setString(5, obj.getLieu());
            stmt.setString(6, obj.getEtat().name());
            stmt.setDouble(7, obj.getPrix());

            // Handle image: if it's not null, convert it to BinaryStream; if null, set to NULL
            if (obj.getImage() != null && obj.getImage().length > 0) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(obj.getImage());
                stmt.setBinaryStream(8, byteArrayInputStream, obj.getImage().length);
            } else {
                stmt.setNull(8, Types.BLOB);  // If no image, set it to NULL
            }

            stmt.setString(9, obj.getType());
            stmt.setString(10, obj.getOrganisateur());
            stmt.setInt(11, obj.getCapaciteMaximale());
            stmt.setInt(12, obj.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        String sql = "SELECT * FROM evenement";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Evenement> evenements = new ArrayList<>();

        while (rs.next()) {
            Evenement obj = new Evenement();
            obj.setId(rs.getInt("id"));
            obj.setTitre(rs.getString("titre"));
            obj.setDescription(rs.getString("description"));
            obj.setDateDebut(rs.getDate("dateDebut").toLocalDate());
            obj.setDateFin(rs.getDate("dateFin").toLocalDate());
            obj.setLieu(rs.getString("lieu"));
            obj.setEtat(EtatEvenement.valueOf(rs.getString("etat")));
            obj.setPrix(rs.getDouble("prix"));

            // Retrieve image as byte[] and set
            Blob blob = rs.getBlob("image");
            if (blob != null) {
                obj.setImage(blob.getBytes(1, (int) blob.length()));
            }

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
        String sql = "SELECT * FROM evenement WHERE id = ?";
        Evenement obj = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    obj = new Evenement();
                    obj.setId(rs.getInt("id"));
                    obj.setTitre(rs.getString("titre"));
                    obj.setDescription(rs.getString("description"));
                    obj.setDateDebut(rs.getDate("dateDebut").toLocalDate());
                    obj.setDateFin(rs.getDate("dateFin").toLocalDate());
                    obj.setLieu(rs.getString("lieu"));
                    obj.setEtat(EtatEvenement.valueOf(rs.getString("etat")));
                    obj.setPrix(rs.getDouble("prix"));

                    // Retrieve image as byte[] and set
                    Blob blob = rs.getBlob("image");
                    if (blob != null) {
                        obj.setImage(blob.getBytes(1, (int) blob.length()));  // Convert Blob to byte[]
                    }

                    obj.setType(rs.getString("type"));
                    obj.setOrganisateur(rs.getString("organisateur"));
                    obj.setCapaciteMaximale(rs.getInt("capaciteMaximale"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }


}