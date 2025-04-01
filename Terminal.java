import java.util.Scanner;

public class Terminal {

    public static void pause(long milliseconds) {
        System.out.println();
        for (int i = 4; i >= 1; i--){
            System.out.println("Prochaine vague dans "+Terminal.Color.RED.colorize(i+"s"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println();
    }

    public static Integer readInt(){
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        try{
            return Integer.valueOf(choice);
        } catch (NumberFormatException e) {
            // Cas d'erreur
            System.out.println("Choix invalide!");
            return null;
        }
    }

    public static Integer readInt(String choix){

        try{
            return Integer.valueOf(choix);
        } catch (NumberFormatException e) {
            // Cas d'erreur
            System.out.println("Choix invalide!");
            return null;
        }
    }

    public static enum Color{
        BLACK((char)27 + "[30m"),
        RED((char)27 + "[31m"),
        GREEN((char)27 + "[32m"),
        YELLOW((char)27 + "[33m"),
        BLUE((char)27 + "[34m"),
        PURPLE((char)27 + "[35m"),
        CYAN((char)27 + "[36m"),
        WHITE((char)27 + "[37m");

        private String charCode;

        Color(String charCode){
            this.charCode = charCode;
        }

        public String colorize(String message){
            return charCode + message + (char)27 + "[0m";
        }
    }
}
