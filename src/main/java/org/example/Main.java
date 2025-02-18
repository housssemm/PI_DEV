package org.example;

import Models.*;
import Models.SpecialiteC;
import Models.User;
import Services.*;
import Services.UserService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Création d'une instance du service
        CoachService coachService = new CoachService();
InvestisseurProduitService investisseurProduitService = new InvestisseurProduitService();
investisseurProduitService.getAll().forEach(System.out::println);
        // Création d'un objet Coach
        Adherent adherent = new Adherent(
                "farah111", // Nom
                "benyeder", // Prénom
                "image.jpg", // Image
                "jean.dupont@farah.com", // Email
                "password123",
                // Mot de passe
                12, 22, 122 , GenreG.Homme, ObjP.Avoir_des_muscles, NiveauA.Debutant);


        // Tentative d'ajout du coach
        //coachService.createCoach(coach);


        //delete by id
//        coachService.deleteCoach(12);


        //get all coaches
//        List<Coach> coaches = coachService.getAll();
//
//        if (!coaches.isEmpty()) {
//            System.out.println("📋 Liste des coaches :");
//            for (Coach c : coaches) {
//                System.out.println(c);
//            }


//       // update
//        Coach coachToUpdate = new Coach( 11,"farah111", "Jean", "image_updated.jpg", "jean.dupont@example.com", "newpassword123456",
//                (byte) 1, SpecialiteC.gymnastique, 3, 5);
//
//        coachService.updateCoachWithUser(coachToUpdate);

            // get by id coach
//        Coach c = coachService.getCoachById(11);
//        if (coach != null) {
//            System.out.println("Coach trouvé : " + c);
//
//        }

            //get by id user
//        UserService userService = new UserService();
//        User user = userService.getUserById(11);
//        if (user != null) {
//            System.out.println("Utilisateur trouvé : " + user);
//        } else {
//            System.out.println("Utilisateur non trouvé.");
//        }
        }
    }

