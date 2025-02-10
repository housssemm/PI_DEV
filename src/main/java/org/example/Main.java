package org.example;

import Models.EtatEvenement;
import Models.Evenement;
import Models.Participation;
import Services.EvenementService;
import Services.ParticpationService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

//        //Evenement
//        EvenementService es =new EvenementService();
//        try{
//           Evenement evenement = new Evenement(4,"Conférence Fitness", "Une conférence sur la nutrition et lentraînement.",
//                   Timestamp.valueOf(LocalDateTime.of(2025, 7, 10, 9, 0)),
//                   Timestamp.valueOf(LocalDateTime.of(2025, 8, 10, 17, 0)),
//                    "Salle des Congrès, Paris", "conference_fitness.jpg", 50.0, EtatEvenement.ACTIF,
//                    "boxe", "Coach y", 150);
////        es.create(evenement);
////            es.update(evenement);
////            System.out.println(es.getAll());
////            es.delete(4);
//            //System.out.println(es.getAll().stream().map(Evenement::toString).collect(Collectors.joining("\n")));
//            System.out.println("the evenement with id 1 is :"+es.getById(2));
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }

//        //ParticipantEvenement
//        ParticipantEvenementService pes =new ParticipantEvenementService();
//        try{
//            ParticipantEvenement p =new ParticipantEvenement(4,111,Timestamp.valueOf(LocalDateTime.of(2025, 5, 10, 9, 0)), etatPaiement.PAYE,2);
////        pes.create(p);
////            pes.delete(2);
//         System.out.println(pes.getAll().stream().map(ParticipantEvenement::toString).collect(Collectors.joining("\n")));
////     pes.update(p);
////            System.out.println(pes.getById(4));
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
        //Participation
        ParticpationService ps = new ParticpationService();
        try {
            Participation pp = new Participation(1,1);
//            ps.create(pp);
//            ps.delete(4);
            System.out.println(ps.getAll().stream().map(Participation::toString).collect(Collectors.joining("\n")));
        }catch (Exception e) {
            System.out.println(e.getMessage());}
        }
   }

