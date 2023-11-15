package constants;
public class PermitCategory {
    public static final String INVALID_PERMIT = "Invalid Permit";
    public static final String EXPIRED_PERMIT = "Expired Permit";
    public static final String NO_PERMIT = "No Permit";
//    private static final String [] categories = {"Invalid Permit", "Expired Permit", "No Permit"};

    public static String[] getCategories() {
        return new String[]{INVALID_PERMIT, EXPIRED_PERMIT, NO_PERMIT};
    }
}
