package Models;

public class Coach extends User {
    private int Annee_experience;
    private byte Certificat_valide;
    private SpecialiteC Specialite;
    private int Note;


    public Coach() {
    }

    public Coach(int id, String nom, String prenom, String image, String email, String MDP, byte certificat_valide, SpecialiteC specialite, int note, int annee_experience) {
        super(id, nom, prenom, image, email, MDP);
        Certificat_valide = certificat_valide;
        Specialite = specialite;
        Note = note;
        Annee_experience = annee_experience;
    }

    public Coach(String nom, String prenom, String image, String email, String MDP, int Annee_experience, byte Certificat_valide, SpecialiteC Specialite, int Note) {
        super(nom, prenom, image, email, MDP );
       this.Annee_experience = Annee_experience;
       this.Certificat_valide = Certificat_valide;
       this.Specialite = Specialite;
       this.Note = Note;

    }
    // Getters et Setters pour chaque attribut
    public int getAnnee_experience() {
        return Annee_experience;

    }
    public void setAnnee_experience(int Annee_experience) {
        this.Annee_experience = Annee_experience;

    }
    public byte getCertificat_valide() {
        return Certificat_valide;

    }

    public void setCertificat_valide(byte certificat_valide) {
        Certificat_valide = certificat_valide;

    }
    public SpecialiteC getSpecialite() {
        return Specialite;
    }
    public void setSpecialite(SpecialiteC Specialite) {
        this.Specialite = Specialite;
    }
    public int getNote() {
        return Note;
    }
    public void setNote(int Note) {
        this.Note = Note;

    }

    @Override
    public String toString() {
        return "coach{" +
                super.toString() +
                "Annee_experience=" + Annee_experience +
                ", Certificat_valide=" + Certificat_valide +
                ", Specialite=" + Specialite +
                ", Note=" + Note +
                '}';
    }


}

