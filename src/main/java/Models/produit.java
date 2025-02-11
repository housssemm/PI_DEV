package Models;

public class produit {
    private int id;
    private int idInvestisseur;
    private String nom;
    private String description;
    private String image;
    private etat etat;
    private int categorieId;
    private int quantite;
    public produit() {}
    public produit(int id,int idInvestisseur,String nom, String description, String image, etat etat, int categorieId,int quantite) {
        this.id = id;
        this.idInvestisseur = idInvestisseur;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.etat = etat;
        this.categorieId = categorieId;
        this.quantite = quantite;
    }
    public produit(int idInvestisseur,String nom, String description, String image, etat etat, int categorieId,int quantite) {
        this.idInvestisseur = idInvestisseur;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.etat = etat;
        this.categorieId = categorieId;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInvestisseur() {
        return idInvestisseur;
    }

    public void setIdInvestisseur(int idInvestisseur) {
        this.idInvestisseur = idInvestisseur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public etat getEtat() {
        return etat;
    }

    public void setEtat(Models.etat etat) {
        this.etat = etat;
    }

    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }
    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    @Override
    public String toString() {
        return "produit{" +
                "id=" + id +
                ", idInvestisseur=" + idInvestisseur +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", etat='" + etat + '\'' +
                ", categorieId=" + categorieId +
                ", quantite=" + quantite +
                '}';
    }

}
