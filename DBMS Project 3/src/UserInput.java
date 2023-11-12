import java.util.Scanner;

public class UserInput {
    private final static Scanner sc = new Scanner(System.in);
    public static String getString(String text){
        System.out.print(text + ": ");
        return sc.nextLine();
    }
    public static int getInt(String text){
        System.out.print(text + ": ");
        return Integer.parseInt(sc.nextLine());
    }

    @Override
    protected void finalize(){
        try{
            sc.close();
        } catch(Exception e){
            System.out.println("Error in destructor: " + e.getMessage());
        }
    }
}
