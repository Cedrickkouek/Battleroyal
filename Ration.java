import java.util.Random;

public class Ration {
    private String nom;
    private double sante;

    public Ration(String nom) {
        this.nom = nom;
    }

    public double getSante() {
        return sante;
    }

    public String afficherNom() {
        return nom;
    }

    public static class Builder{
        public static Ration buildBalouneEau(){
            Random rand = new Random();
            Ration ration = new Ration("Baloune d'eau");
            ration.sante = 25;
            return ration;
        }

        public static Ration buildBiscuits(){
            Random rand = new Random();
            Ration ration = new Ration("Buiscuits au chocolat");
            ration.sante = 20;
            return ration;
        }

        public static Ration buildLapin(String nomLapin){
            Random rand = new Random();
            Ration ration = new Ration(nomLapin);
            ration.sante = 40.0;
            return ration;
        }
    }
}
