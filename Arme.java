import java.util.Random;

public class Arme {

    private String nom;
    private double degats;
    private int munition;
    private int precision;
    private Typearme typearme;

    public Arme(String nomObjet) {
        this.nom = nomObjet;
        this.degats = 0;
        this.munition = 0;
        this.precision = 0;
    }

    public String getType(){
        return typearme.toString();
    }

    public int getMunition(){

        return munition;
    }
    public double getDegats(){

        return degats;
    }

    private String afficherNom() {
        return nom;
    }


    public boolean peutAttaquer(){
        if(typearme.equals(Typearme.ARMEAFEU) && munition != 0){
            return true;
        }
        if(typearme.equals(Typearme.ARMEBLANCHE)){
            return true;
        }
        return false;
    }

    public String afficherWeapon(){
        String result = afficherNom();
        if (typearme.toString().equals(Typearme.ARMEAFEU.toString())){
            result += " ("+Terminal.Color.RED.colorize(""+munition)+" munitiions) ";
        } else{
            afficherNom();
        }
        return result;
    }

    public void attaquer(Joueur joueur, double tauxPrecision){
        Random random = new Random();
        int chanceAttaque = random.nextInt(1, 101);
        if(chanceAttaque <= tauxPrecision){
            joueur.degatsSante(this.degats);
        } else{
            System.out.println();
            System.out.println("l'attaque a echouée: !");
            System.out.println();
        }
    }

    public static class Builder{
        public static Arme buildMarteau(){
            Random rand = new Random();
            Arme arme = new Arme("Marteau en fer");
            arme.degats = 0.4;
            arme.precision = 60;
            arme.typearme = Typearme.ARMEBLANCHE;
            return arme;
        }

        public static Arme buildGantsBoxe(){
            Random rand = new Random();
            Arme arme = new Arme("Gants de boxe");
            arme.degats = 2.5;
            arme.precision =  55;
            arme.typearme = Typearme.ARMEBLANCHE;
            return arme;
        }

        public static Arme buildCorde(){
            Random rand = new Random();
            Arme arme = new Arme("Corde 4 mètres");
            arme.degats = 0.6;
            arme.precision = 40;
            arme.typearme = Typearme.ARMEBLANCHE;
            return arme;
        }

        public static Arme buildBatarangMousse(){
            Random rand = new Random();
            Arme arme = new Arme("Batarang en mousse");
            arme.degats = 0.1;
            arme.precision = 15;
            arme.typearme = Typearme.ARMEBLANCHE;
            return arme;
        }

        public static Arme buildFanEnPapier(){
            Random rand = new Random();
            Arme arme = new Arme("Fan en papier");
            arme.precision = 40;
            arme.degats = 0.1;
            arme.typearme = Typearme.ARMEBLANCHE;
            return arme;
        }

        public static Arme buildLanceGrenade(){
            Random rand = new Random();
            Arme arme = new Arme("Lance Grenade");
            arme.degats = 5.0;
            arme.munition = 3;
            arme.precision= 70;
            arme.typearme = Typearme.ARMEAFEU;
            return arme;
        }

        public static Arme buildSabre(){
            Random rand = new Random();
            Arme arme = new Arme("Sabre");
            arme.degats = 4.0;
            arme.precision = 45;
            arme.typearme = Typearme.ARMEBLANCHE;
            return arme;
        }

        public static Arme buildPistolet(){
            Random rand = new Random();
            Arme arme = new Arme("Pistolet");
            arme.munition = 8;
            arme.degats = 1.0;
            arme.precision = 60;
            arme.typearme = Typearme.ARMEAFEU;
            return arme;
        }

        public static Arme buildFusil(){
            Random rand = new Random();
            Arme arme = new Arme("Fusil");
            arme.munition = 8;
            arme.precision = 60;
            arme.degats = 2.0;
            arme.typearme = Typearme.ARMEAFEU;
            return arme;
        }
    }


}
