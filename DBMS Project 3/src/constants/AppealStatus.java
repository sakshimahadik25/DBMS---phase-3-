package constants;

public class AppealStatus {
    public static final String IN_PROGRESS = "In Progress";
    public static final String REJECT = "Reject";
    public static final String ACCEPT = "Accept";
    public static final String NO_APPEAL = "No Appeal";

    public static String[] getAppealStatuses() {
        return new String[]{IN_PROGRESS, REJECT, ACCEPT, NO_APPEAL};
    }
}
