package Controllers;

import Models.panierProduit;
import Models.produit;
import Services.PanierProduitService;
import Services.SmsService;
import Services.produitService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Models.etatP.Payé;

public class PaiementPopUpController {

    @FXML
    private Label priceLabel1;
    @FXML
    private Button payerBtn;
    @FXML
    private TextField cvc;
    @FXML
    private TextField mm;
    @FXML
    private TextField ncarte;
    @FXML
    private TextField nomT;
    @FXML
    private TextField yy;
    @FXML
    private TextField numT;
    private double totalMontant;
    private panierProduit panierProduit;
    private int panierId; // ID du panier à payer
    private PanierProduitService panierService = new PanierProduitService();
    private produitService prodService = new produitService();
    private PanierProduitService paniProdService = new PanierProduitService();
    private SmsService smsService = new SmsService();

    public PaiementPopUpController() throws SQLException {
    }

    public void initialize() {
        payerBtn.setOnAction(this::handlePayment);
    }
    public void setTotalPrice(String totalPrice) {
        priceLabel1.setText(totalPrice);
    }

    // Setter pour stocker le total numérique (utile pour le paiement)
    public void setTotalMontant(double totalMontant) {
        this.totalMontant = totalMontant;
    }

    // Méthode pour configurer l'ID du panier
    public void setPanierId(int panierId) throws Exception {
        System.out.println("Panier ID reçu : " + panierId); // Debug
        this.panierId = panierId;
        this.panierProduit = panierService.getById(panierId);
        if (this.panierProduit == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Panier introuvable !");
        }
    }

    private String createStripeToken() throws StripeException {
        // Configuration de Stripe
        Stripe.apiKey = "sk_test_51QwSRHGbxwIj6q0UedmOumzd0Xp9vkDEy9RwTnMEk54f5azaOtoMZekL4LlgYuYsimBOenpuDIy6x75okwZ0L9tF00k2uc9n7R";

        // Paramètres de la carte
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", ncarte.getText());
        cardParams.put("exp_month", Integer.parseInt(mm.getText()));
        cardParams.put("exp_year", Integer.parseInt("20" + yy.getText()));
        cardParams.put("cvc", cvc.getText());

        // Créer un token Stripe
        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("card", cardParams);

        // Mode test
        if (isTestingMode()) {
            return "tok_visa";
        }

        Token token = Token.create(tokenParams);
        return token.getId();
    }

    @FXML
    private void handlePayment(ActionEvent event) {
        try {
            // Vérifier si le panier est valide
            if (this.panierProduit == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Panier introuvable !");
                return;
            }
            // Valider les champs de paiement
            if (!validatePaymentFields()) {
                return;
            }
            // Créer un token Stripe
            String stripeToken = createStripeToken();
            if (stripeToken == null || stripeToken.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la génération du token Stripe !");
                return;
            }
            // Calculer le montant en centimes
            long amount = (long) (totalMontant * 100); // Montant en centimes

            // Créer une charge Stripe
            Charge charge = Charge.create(new HashMap<String, Object>() {{
                put("amount", amount);
                put("currency", "usd");
                put("source", stripeToken);
                put("description", "Paiement pour le panier ID: " + panierId);
            }});

            // Vérifier si le paiement a réussi
            if (charge.getPaid()) {
                try {
                    panierService.updatePaymentStatusByPanierId(panierId, Payé);
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Paiement effectué avec succès !");

                    // Récupérer le numéro de téléphone de l'utilisateur
                    String userPhoneNumber = numT.getText();

                    // Créer le message de confirmation avec les détails des produits
                    StringBuilder messageBuilder = new StringBuilder();
                    messageBuilder.append("Votre paiement de ").append(totalMontant).append(" TND a été effectué avec succès.\n\n");

                    // Ajouter les détails des produits achetés
                    messageBuilder.append("Détails de votre panier :\n");
                    // Parcourir chaque produit dans panierProduit
                    List<panierProduit> panierProduits = paniProdService.getProduitsDansPanier(panierId);
                    for (panierProduit produitPanier : panierProduits) {
                        // Récupérer les informations du produit via produitId
                        produit produit = prodService.getById(produitPanier.getProduitId());
                        if (produit != null) {
                            messageBuilder.append("Produit : ").append(produit.getNom())  // Utiliser getNom() pour le nom du produit
                                    .append(" | Quantité : ").append(produitPanier.getQuantite()) // Utiliser la quantité de panierProduit
                                    .append("\n");
                        }
                    }
                    String message = messageBuilder.toString();
                    smsService.sendSms(userPhoneNumber, message);

                    paniProdService.supprimerProduitsParPanierId(panierId);
                    paniProdService.getProduitsDansPanier(panierId);
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la mise à jour de l'état du paiement : " + e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec du paiement.");
            }
        } catch (StripeException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Problème de paiement : " + e.getMessage());
        }
    }
    private boolean validatePaymentFields() {
        if (ncarte.getText().isEmpty() || ncarte.getText().length() != 16) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Numéro de carte invalide !");
            return false;
        }
        if (mm.getText().isEmpty() || yy.getText().isEmpty() ||
                Integer.parseInt(mm.getText()) > 12 || Integer.parseInt(yy.getText()) < 24) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Date d'expiration invalide !");
            return false;
        }
        if (cvc.getText().isEmpty() || cvc.getText().length() != 3) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "CVC invalide !");
            return false;
        }
        if (nomT.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Nom du titulaire invalide !");
            return false;
        }
        if (numT.getText().isEmpty() || !numT.getText().matches("^\\+216\\d{8}$")) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Numéro de téléphone invalide !");
            return false;
        }
        return true;
    }
    private boolean isTestingMode() {
        return true;
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
