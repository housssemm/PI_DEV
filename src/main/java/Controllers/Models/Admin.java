package Controllers.Models;

import Models.User;

public class Admin extends User {
    public Admin() {
    }

    public Admin(int id, String nom, String prenom, String image, String email, String MDP) {
        super(id, nom, prenom, image, email, MDP);
    }

    public Admin(String nom, String prenom, String image, String email, String MDP) {
        super(nom, prenom, image, email, MDP );
    }


    @Override
    public String toString() {
        return "admin{" +
                super.toString() +
                '}';
    };
    }

