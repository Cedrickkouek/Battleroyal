import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Battleroyale {
    private int jourJeu;
    private int chanceElimination;
    private ArrayList<Joueur> participants;
    private ArrayList<Ration> rations;
    private Joueur joueur;
    private int maximumCombat;
    private int nombreCombatManche;
    private int debutAleatoireCombat;
    private int tourJoueur;
    private Joueur ennemiJoueur;
    int indexEnnemie;

    public Battleroyale() {
        participants = new ArrayList<>();
        rations = new ArrayList<>();
    }

    public void jouer(){
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        String responseEchangeArme = null;


        int tourSansRienFaire = 0;
        maximumCombat = 0;
        nombreCombatManche = 0;
        jourJeu = 1;

        joueur = new Joueur();
        genererParticipants();


        //TODO: Faire boucle tant que jour >= 1 et jour <=3 et joueur defaite != false et particpants > 1
        do {
            updateElimination(jourJeu);
            joueur.afficherCaracteristiques();
            eliminerParticipants(chanceElimination);
            initVariableCombat();

            while((joueur.getNombreCombat() < nombreCombatManche) && (!joueur.getPerteCombat())){
                //TODO: Faire Logigramme d'un combat
                //TODO: end combat quand soit je perd, soit je fuis, sois ennemie perd ou il fuit gerer ca
                indexEnnemie = rand.nextInt(participants.size());
                ennemiJoueur = participants.get(indexEnnemie);
                tourJoueur = rand.nextInt(2)+1;

                do{

                    System.out.println("Combat " + (joueur.getNombreCombat() + 1) + " sur " + nombreCombatManche);

                    if(tourSansRienFaire > 0){
                        tourJoueur = 2;
                        tourSansRienFaire--;
                        System.out.println("Vous ne faites rien");
                    } else if (tourSansRienFaire == 0 && joueur.getNeRienFaire()) {
                        joueur.setNeRienFaire(false);
                        if (!joueur.getPerteCombat()){
                            joueur.setEstEvader(true);
                        }
                    }
                    if(tourJoueur == 1){
                        joueur.jouer(ennemiJoueur, jourJeu);
                        if(joueur.getNeRienFaire()){
                            tourSansRienFaire = 2;
                        }
                    } else{
                        ennemiJoueur.jouer(joueur, jourJeu);
                    }

                    switch(tourJoueur){
                        case 1:
                            tourJoueur = 2;
                            break;
                        case 2:
                            tourJoueur = 1;
                            break;
                    }
                } while(!joueur.getPerteCombat() && !joueur.getEstEvader() && !ennemiJoueur.getPerteCombat() && !ennemiJoueur.getEstEvader());

                if(ennemiJoueur.getPerteCombat()){
                    System.out.printf("%s %s vous avez remporté le combat", joueur.getPrenom(), joueur.getNom());
                    Joueur participantElimine = participants.remove(indexEnnemie);
                    //TODO: Recuperer Arme ou non
                    do {
                        System.out.printf("Voulez-vous echanger votre arme avec celle de %s %s qui est %s (O/N) ?", participantElimine.getPrenom(), participantElimine.getNom(), participantElimine.getArmeActual());
                        responseEchangeArme = scanner.nextLine();
                        boolean estReponseCorrect = (responseEchangeArme.equalsIgnoreCase("O") || responseEchangeArme.equalsIgnoreCase("N"));

                        if(!estReponseCorrect){
                            System.out.println("Erreur d'entrée! ");
                            responseEchangeArme = null;
                        }
                    } while(responseEchangeArme == null);

                    if(responseEchangeArme.equalsIgnoreCase("O")){
                        joueur.recupererArme(participantElimine.getArmeActual());
                    }
                    Ration lapin = Ration.Builder.buildLapin(participantElimine.getPrenom()+" "+participantElimine.getNom());
                    joueur.ajouterRation(lapin);
                }
            }
            jourJeu++;

        } while(jourJeu<=3 && !joueur.getPerteCombat() && !joueur.getEstEvader() && participants.size() > 1 && !ennemiJoueur.getEstEvader());
        //TODO: Faire Logigramme AI

        //TODO: Faire Logique chasse
        //TODO: Améliorer UI
        //TODO: Refactor le code (optimiser)
    }

    private void genererParticipants() {
        Random rand = new Random();

        ArrayList<String> prenomsParticipants = recupererInformation("prenomsParticipant.txt");
        ArrayList<String> nomsParticipants = recupererInformation("nomsParticipants.txt");
        int indexValeurPrenom = 0, indexValeurNom = 0, compteur = 41;

        while(compteur > 0) {
            indexValeurPrenom = rand.nextInt(compteur);
            indexValeurNom = rand.nextInt(compteur);
            Joueur joueur = new Joueur(
                    nomsParticipants.get(indexValeurNom),
                    prenomsParticipants.get(indexValeurPrenom)
            );
            prenomsParticipants.remove(indexValeurNom);
            nomsParticipants.remove(indexValeurPrenom);

            participants.add(joueur);
            compteur--;
        }

    }

    private void updateElimination(int jourJeu){
        switch (jourJeu){
            case 1:
                chanceElimination = 45;
                break;
            case 2:
                chanceElimination = 30;
                break;
            case 3:
                chanceElimination = 20;
                break;
        }
    }

    private void initVariableCombat(){
        Random random = new Random();
        if(participants.size()>=4){
            maximumCombat = 4;
            nombreCombatManche = random.nextInt(1, maximumCombat+1);
        } else{
            maximumCombat = participants.size();
            nombreCombatManche = random.nextInt(1, maximumCombat+1);
        }
        debutAleatoireCombat = random.nextInt(2)+1;
    }

    private ArrayList<String> recupererInformation(String fichierprenom) {
        ArrayList<String> prenomsParticipants = new ArrayList<>();
        try{
            File fichierPrenom = new File(fichierprenom);
            Scanner ouvrirFichier = new Scanner(fichierPrenom);
            while(ouvrirFichier.hasNextLine()){
                String prenom = ouvrirFichier.nextLine();
                prenomsParticipants.add(prenom);
            }
        } catch(FileNotFoundException e){
            System.out.printf("Erreur de lecture du fichier %s",fichierprenom);
        }
        return prenomsParticipants;
    }

    private void eliminerParticipants(int pourcentage) {
        Random rand = new Random();

        int nombreTotal = participants.size();
        int nombreAEliminer = (int) ((pourcentage / 100.0) * nombreTotal);

        System.out.println("Élimination de " + nombreAEliminer + " participants...");

        while(nombreAEliminer > 0 && participants.size() > 1){
            int indexAleatoire = rand.nextInt(participants.size());

            if (indexAleatoire != 0){
                Joueur participantElimine = participants.remove(indexAleatoire);
                Ration lapin = Ration.Builder.buildLapin(participantElimine.getPrenom()+" "+participantElimine.getNom());
                rations.add(lapin);
                nombreAEliminer--;
            }
        }
        System.out.println();
        System.out.println("Il reste " + participants.size() + " joueurs en jeu.");
        System.out.println();
        System.out.println();
    }



}
