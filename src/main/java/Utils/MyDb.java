//package Utils;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class MyDb {
//    private String url = "jdbc:mysql://localhost:3306/pi_dev";
//    private String user = "root";
//    private String password = "";
//    private Connection conn;
//    private static MyDb instance;
//
//    public static MyDb getInstance() {
//        if (instance == null) {
//            instance = new MyDb();
//        }
//        return instance;
//    }
//
//    public Connection getConn() {
//        return conn;
//    }
//
//    private Connection MyDb() {
//        try {
//            this.conn = DriverManager.getConnection(url, user, password);
//            System.out.println("Connection established");
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }}
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDb {
    private String url = "jdbc:mysql://localhost:3306/pi_dev";
    private String user = "root";
    private String password = "";
    private Connection conn;
    private static MyDb instance;

    // ✅ Constructeur correct
    private MyDb() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println(" Connection établie avec succès !");
        } catch (SQLException e) {
            System.out.println(" Erreur de connexion : " + e.getMessage());
        }
    }

    // ✅ Singleton pour obtenir l'instance
    public static MyDb getInstance() {
        if (instance == null) {
            instance = new MyDb();
        }
        return instance;
    }

    // ✅ Méthode pour obtenir la connexion
    public Connection getConn() {
        return conn;
    }
}
