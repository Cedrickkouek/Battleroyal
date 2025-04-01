import java.util.Random;

public class Armure {
    private String nom;
    private double defense;

    public Armure(String nom) {
        this.nom = nom;
    }

    public String afficherNom() {
        return nom;
    }

    public double getDefense() {
        return defense;
    }
    public void actualiseDefense(double point) {
        defense -= point;
        if (point <= 0) {
            defense = 0;
        }
    }

    public static class Builder{

        public static Armure buildCartable(){
            Random rand = new Random();
            Armure armure = new Armure("Cartable 3 pouces");
            armure.defense = rand.nextDouble(1, 5);
            return armure;
        }

        public static Armure buildBiscuits(){
            Random rand = new Random();
            Armure armure = new Armure("Buiscuits au chocolat");
            armure.defense = 20;
            return armure;
        }

        public static Armure buildCouvercleMarmite(){
            Random rand = new Random();
            Armure armure = new Armure("Couvercle de marmite");
            armure.defense = 8;
            return armure;
        }

        public static Armure buildVesteAntiballe (){
            Random rand = new Random();
            Armure armure = new Armure("Veste antiballe");
            armure.defense =  10;
            return armure;
        }
    }
}
