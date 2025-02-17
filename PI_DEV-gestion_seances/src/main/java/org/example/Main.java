package org.example;

import Models.Planning;
import Models.Seance;
import Models.Type;
import Services.PlanningService;
import Services.SeanceService;

import java.sql.Date;
import java.sql.Time;

public class Main {
    public static void main(String[] args) {
        SeanceService se = new SeanceService();
        PlanningService ps = new PlanningService();
        try {
            //////////////////////////crud seance/////////////////////////////
            Seance s1 = new Seance(
                    "Séance entrainement intensive - full body",
                    "Rejoignez cette séance de 120 minutes ",
                    Date.valueOf("2025-05-17"),
                    2,
                    3,
                    Type.ENREGISTRE,
                    "liennnnnnn",
                    1,
                    Time.valueOf("11:00:00"),
                    Time.valueOf("12:30:00"));
            Seance s2 = new Seance(
                    1,
                    "Séance de Relaxation - meditation",
                    "Rejoignez cette séance de 1heure : étirements pour une récupération optimale !",
                    Date.valueOf("2025-05-17"),
                    2,
                    3,
                    Type.ENREGISTRE,
                    "lien seance25525",
                    1,
                    Time.valueOf("6:00:00"),
                    Time.valueOf("7:00:00"));
                  se.create(s1);
                 se.update(s2);
                 se.delete(3);
                  System.out.println(se.getAll());
            System.out.println(se.getById(1));
            //////////////////////////////crud planning/////////////////////////////////
            Planning p1=new Planning(2, "semaine 12 decembre 2025", 550);
            Planning p2=new Planning(3,3, "semaine 2 sptembre 2000", 1050);
             ps.create(p1);
            ps.update(p2);
            ps.delete(6);
             System.out.println(ps.getAll());
            System.out.println(ps.getById(1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}