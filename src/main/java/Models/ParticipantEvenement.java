package Models;
import java.util.Date;

public class ParticipantEvenement {
    private int idParticipant;
    private Date dateInscription;
    private String etatPaiement;
    private int idEvenement;

    public ParticipantEvenement() {}
    public ParticipantEvenement(int idParticipant, Date dateInscription, String etatPaiement, int idEvenement) {
        this.idParticipant = idParticipant;
        this.dateInscription = dateInscription;
        this.etatPaiement = etatPaiement;
        this.idEvenement = idEvenement;
    }
    public ParticipantEvenement(Date dateInscription, String etatPaiement, int idEvenement) {
        this.dateInscription = dateInscription;
        this.etatPaiement = etatPaiement;
        this.idEvenement = idEvenement;
    }

    public int getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(int idParticipant) {
        this.idParticipant = idParticipant;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getEtatPaiement() {
        return etatPaiement;
    }

    public void setEtatPaiement(String etatPaiement) {
        this.etatPaiement = etatPaiement;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    @Override
    public String toString() {
        return "ParticipantEvenement{" +
                "idParticipant=" + idParticipant +
                ", dateInscription=" + dateInscription +
                ", etatPaiement='" + etatPaiement + '\'' +
                ", idEvenement=" + idEvenement +
                '}';
    }
}
