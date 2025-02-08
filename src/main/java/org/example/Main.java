package org.example;

import Models.EtatEvenement;
import Models.Evenement;
import Models.User;
import Services.EvenementService;
import Services.UserService;
import Utils.MyDb;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Collectors;

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
        //Evenement
        EvenementService es =new EvenementService();
        try{
           Evenement evenement = new Evenement(2,"Conférence Fitness", "Une conférence sur la nutrition et lentraînement.",
                   Timestamp.valueOf(LocalDateTime.of(2025, 7, 10, 9, 0)),
                   Timestamp.valueOf(LocalDateTime.of(2025, 8, 10, 17, 0)),
                    "Salle des Congrès, Paris", "conference_fitness.jpg", 50.0, EtatEvenement.EXPIRE,
                    "gym", "Coach y", 150);
       //  es.create(evenement);
//            es.update(evenement);
//            System.out.println(es.getAll());
//            es.delete(evenement);
            System.out.println(es.getAll().stream().map(Evenement::toString).collect(Collectors.joining("\n")));

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}