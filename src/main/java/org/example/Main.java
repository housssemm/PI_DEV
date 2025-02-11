package org.example;
import Models.*;
import Services.PanierProduitService;
import Services.categorieService;
import Services.produitService;
import Services.panierService;


public class Main {
    public static void main(String[] args) {
        produitService prods = new produitService();
        categorieService categs = new categorieService();
        panierService paniers = new panierService();
        PanierProduitService panieprods = new PanierProduitService();

        try {
            Categorie categ = new Categorie(2,"fitness");
            Categorie categ2 = new Categorie(6,"gym");
            Categorie categ1 = new Categorie(5,"sport");
            categs.create(categ2);
            categs.create(categ1);
            categs.update(categ1);
            categs.delete(6);

            produit prod = new produit( 2, "Haltères", "Haltères réglables pour musculation", "https://example.com/dumbbells.jpg", etat.Stock, 2, 50);
            produit prod1 = new produit(3, 2, "Chaussures de running", "Chaussures légères et respirante", "https://example.com/runningshoes.jpg", etat.Stock, 3, 60);
            produit prod2 = new produit(2, 2, "Tapis de course", "Tapis de course pliable avec écran LED", "https://example.com/treadmill.jpg", etat.Stock, 8, 500);
            prods.create(prod);
            prods.update(prod1);
            prods.delete(5);

            panier p =new panier(null,5);
            panier p1 =new panier(1,null);
            panier p2 =new panier(1,2);

            paniers.create(p);
            paniers.create(p2);
            paniers.deleteByCoach(p.getCoachId());
            paniers.deleteByAdherent(p.getAdherentId());

            // Créer des objets panierProduit à ajouter à la liste
            List<panierProduit> panierProduitLists = new ArrayList<>();

            // Ajout de produits au panier
            panierProduit panierprod1 = new panierProduit(8, 2, 5, LocalDateTime.now(), 150.0f, etatP.En_Attente);
            panierProduit panierprod2 = new panierProduit(8, 1, 3, LocalDateTime.now(), 90.0f, etatP.En_Attente);

            panierProduit panierprod3 = new panierProduit(9, 3, 1, LocalDateTime.now(), 100.0f, etatP.En_Attente);
            panierProduit panierprod4 = new panierProduit(9, 2, 3, LocalDateTime.now(), 20.0f, etatP.En_Attente);
            panierProduitLists.add(panierprod3);
            panierProduitLists.add(panierprod4);
            panieprods.ajouterPlusieursProduitsPanier(panierProduitLists);
            System.out.println(panieprods.getProduitsDansPanier(9));
            panieprods.delete(4);
            panieprods.modifierQuantiteProduitDansPanier(13,2);


            System.out.println(categs.getAll());
            System.out.println(prods.getAll());
            System.out.println(paniers.getAll());


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

