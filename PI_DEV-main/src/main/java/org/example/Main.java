/*package org.example;

import Models.Offre;
import Models.User;
import Services.UserService;
import Utils.MyDb;

public class Main {
    public static void main(String[] args) {
        UserService us = new UserService();
        try {
             User user = new User(4,20, "houssemme", "labidi");
            User user1 = new User(5,20, "baha", "arbi");

//             us.create(user);
//             us.create(user1);
//             System.out.println("User created");
//             us.update(user);
//            us.delete(user1);
//            System.out.println("User deleted");
            System.out.println(us.getAll());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}*/
/*package org.example;
import Models.OffreCoach;
import Models.OffreProduit;
import Models.Etat;
import Services.OffreCoachService;
import Services.OffreProduitService;
import java.util.Date;
import java.time.LocalDate;




public class Main {
    public static void main(String[] args) {
        OffreCoachService offreCoachService = new OffreCoachService();
        OffreProduitService offreProduitService = new OffreProduitService();

        try {
            // Test de création d'une OffreCoach
            //OffreCoach offreCoach = new OffreCoach(25, "Séance test", "Coaching test", new Date(), Etat.ACTIF, 2, 140.0, 4, 10);
            //offreCoachService.create(offreCoach);
            //System.out.println("OffreCoach créée : " + offreCoach);

            // Test de création d'une OffreProduit
            OffreProduit offreProduit = new OffreProduit(0, "Produit test", "Description du produit test", new Date(), Etat.ACTIF, 1, 150.0, 100, 24);
            int id = offreProduit.getId(); // Obtiens l'ID de l'objet

            //offreProduitService.create(offreProduit);
            //System.out.println("OffreProduit créée : " + offreProduit);

            // Test de mise à jour
            //offreCoach.setNouveauTarif(120.0);
            //offreCoach.setDuree_validite(java.sql.Date.valueOf(LocalDate.of(2025, 2, 22)));
            //offreCoachService.update(offreCoach);
            //System.out.println("OffreCoach mise à jour : " + offreCoach);

            // Test de suppression
            //offreCoachService.delete(offreCoach);
            //System.out.println("OffreCoach supprimee : " + offreCoach.getIdCoach());
            //offreProduitService.delete(offreProduit);
            offreProduitService.delete(id);
            //System.out.println("OffreProduit supprimée : " + offreProduit.getId());

            // Afficher toutes les offres
            //offreCoachService.getAll();
            //offreProduitService.getAll();

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}*/
/*package org.example;

import Models.OffreCoach;
import Models.OffreProduit;
import Models.Etat;
import Services.OffreCoachService;
import Services.OffreProduitService;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        OffreCoachService offreCoachService = new OffreCoachService();
        OffreProduitService offreProduitService = new OffreProduitService();

        try {
            // Test de création d'une OffreCoach
            OffreCoach offreCoach = new OffreCoach(12, "Séance rayen", "Coaching hamza", new Date(), Etat.ACTIF, 2, 120.0, 4, 10);
            //offreCoachService.create(offreCoach);
            //System.out.println("OffreCoach créée : " + offreCoach);

            // Test de création d'une OffreProduit
            OffreProduit offreProduit = new OffreProduit(23, "Produit test", "Description du produit test", new Date(), Etat.ACTIF, 1, 150.0, 100, 24);
            //offreProduitService.create(offreProduit);
            //System.out.println("OffreProduit créée : " + offreProduit);

            // Test de mise à jour d'une OffreCoach
            //offreCoach.setNouveauTarif(200.0);
            //offreCoach.setDescription("coaching houss");
            //offreCoach.setDuree_validite(new java.sql.Date(System.currentTimeMillis())); // Remplace par une date valide
            //offreCoachService.update(offreCoach);
            //System.out.println("OffreCoach mise à jour : " + offreCoach);

            // Test de mise à jour d'une OffreProduit
            //offreProduit.setNouveauPrix(160.0);
            //offreProduitService.update(offreProduit);
            //System.out.println("OffreProduit mise à jour : " + offreProduit);

            // Test de suppression d'une OffreCoach
            //offreCoachService.delete(offreCoach.getId()); // Utilise l'ID de l'offre
            //System.out.println("OffreCoach supprimée : " + offreCoach.getId());

            // Test de suppression d'une OffreProduit
            //offreProduitService.delete(offreProduit.getId()); // Utilise l'ID de l'offre
            //System.out.println("OffreProduit supprimée : " + offreProduit.getId());

            // Afficher toutes les offres
            //System.out.println("Liste des OffreCoachs : " + offreCoachService.getAll());
            //System.out.println("Liste des OffreProduits : " + offreProduitService.getAll());

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}*/

package org.example;
import Models.OffreCoach;
import Models.OffreProduit;
import Models.Offre;
import Models.Etat;
import Services.OffreService;
import Services.OffreCoachService;
import Services.OffreProduitService;



import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        OffreCoachService offreCoachService = new OffreCoachService();
        OffreProduitService offreProduitService = new OffreProduitService();
        OffreService offreService = new OffreService();

        try {
            // Créer une nouvelle OffreCoach
            OffreCoach offreCoach = new OffreCoach(5, "Coaching baha", "Séances de coaching personnalisé", new Date(), Etat.ACTIF, 2, 100.0, 5, 10);


            // Insérer l'OffreCoach dans la base de données
            //offreCoachService.create(offreCoach);

            // Créer une nouvelle Offre (remplacez par une récupération si nécessaire)
            Offre offre1 = new Offre(6, "Nom original", "Description originale", new Date(), Etat.ACTIF);

            // Modifier le nom et la description de l'Offre
            //offre1.setNom("Nom mis à jour");
            //offre1.setDescription("Description mise à jour");

            // Appeler la méthode de mise à jour
            //offreService.update(offre1);

            //offreService.delete(offre1.getId());


            // Créer une nouvelle OffreProduit
            OffreProduit offreProduit = new OffreProduit(4, "Produit A", "Description du produit A", new Date(), Etat.ACTIF, 1, 50.0, 100, 20);


            // Insérer l'OffreProduit dans la base de données
            //offreProduitService.create(offreProduit);



            // Récupérer et afficher toutes les offres coach
            List<Offre> allCoaches = offreCoachService.getAll();
            System.out.println("Liste des offres coach :");
            for (Offre offre : allCoaches) {
                System.out.println(offre);
            }

            // Récupérer et afficher toutes les offres produit
            List<Offre> allProduits = offreProduitService.getAll();
            System.out.println("Liste des offres produit :");
            for (Offre offre : allProduits) {
                System.out.println(offre);
            }

            // Mettre à jour une offre coach
            //offreCoach.setNouveauTarif(99.0);

            //offreCoachService.update(offreCoach);


            // Mettre à jour une offre produit
            //offreProduit.setNouveauPrix(45.0);
            //offreProduitService.update(offreProduit);


            // Supprimer une offre coach
            //offreCoachService.delete(offreCoach.getId());



            // Supprimer une offre produit
            //offreProduitService.delete(offreProduit.getId());



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
