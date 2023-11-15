package constants;
public class SpaceType {
    public static final String ELECTRIC = "electric";
    public static final String HANDICAP = "handicap";
    public static final String COMPACT_CAR = "compact car";
    public static final String REGULAR = "regular";

    public static String[] getSpaceTypes(){
        return new String[]{ELECTRIC,HANDICAP,COMPACT_CAR,REGULAR};
    }
}
