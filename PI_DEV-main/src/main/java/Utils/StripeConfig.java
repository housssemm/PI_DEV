package Utils;

import com.stripe.Stripe;

public class StripeConfig {
    private static final String SECRET_KEY = "sk_test_51QwZ5fH2PLJVXcLsZl7pJSavS7UUEFDq0O5i54JVifuuvgSy7s3Dgus2EJrQgGwU6klpTiZEnl5gC3gESEeOT78p00wQTk1uck";

    public static void init() {
        Stripe.apiKey = SECRET_KEY;
    }
}