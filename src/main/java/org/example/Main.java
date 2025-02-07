package org.example;

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
}