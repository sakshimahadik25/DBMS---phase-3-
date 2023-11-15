package constants;

public class PaymentStatus {
    public static final String DUE = "Due";
    public static final String PAID = "Paid";
    public static final String WAIVED = "Waived";

    public static String[] getPaymentStatuses() {
        return new String[]{DUE, PAID, WAIVED};
    }
}
