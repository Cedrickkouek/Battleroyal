import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Joueur {
    private String nom;
    private String prenom;
    private double sante;
    private double tauxDefense;
    private double tauxAttaque;
    private double tauxPrecision;
    private double tauxChance;
    private boolean perteCombat;
    private ArrayList<Arme> armes;
    private Arme armeActual;
    private Armure armure;
    private boolean armureActive;
    private ArrayList<Ration> rations;
    private Ration rationActual;
    private int nombreCombat;
    private int armeActif;
    private boolean isEnnemi;
    private boolean estEvader;
    private boolean neRienFaire;

    public Joueur(String nom, String prenom) {
        this.prenom = prenom;
        this.nom = nom;
        initJoueur();
        perteCombat = false;
        isEnnemi = true;
    }
    public Joueur(){
        lectureNom();
        lecturePrenom();
        initJoueur();
        isEnnemi = false;
    }

    private void initJoueur(){
        Random rand = new Random();
        this.tauxDefense =  rand.nextDouble(0, 12);
        this.tauxAttaque = rand.nextDouble(1, 5);
        this.tauxPrecision = rand.nextDouble(20, 80);
        this.tauxChance = rand.nextDouble(1, 40);
        armes = new ArrayList<>();
        rations = new ArrayList<>();
        perteCombat = false;
        armureActive = false;
        estEvader = false;
        neRienFaire = false;
        armure = null;
        armeActual = null;
        sante = 100.0;
    }


    public void remplirSac(){
        Random random =  new Random();
        ArrayList<Arme> armesDisponibles = new ArrayList<>();
        ArrayList<Armure> armuresDisponibles = new ArrayList<>();
        ArrayList<Ration> rationsDisponibles = new ArrayList<>();
        int nombreArme = random.nextInt(2) + 1;
        int indexArmure;

        armesDisponibles.add(Arme.Builder.buildMarteau());
        armesDisponibles.add(Arme.Builder.buildCorde());
        armesDisponibles.add(Arme.Builder.buildSabre());
        armesDisponibles.add(Arme.Builder.buildGantsBoxe());
        armesDisponibles.add(Arme.Builder.buildLanceGrenade());
        armesDisponibles.add(Arme.Builder.buildBatarangMousse());
        armesDisponibles.add(Arme.Builder.buildFanEnPapier());
        armesDisponibles.add(Arme.Builder.buildFusil());
        armesDisponibles.add(Arme.Builder.buildPistolet());

        armuresDisponibles.add(Armure.Builder.buildCartable());
        armuresDisponibles.add(Armure.Builder.buildVesteAntiballe());
        armuresDisponibles.add(Armure.Builder.buildCouvercleMarmite());

        rationsDisponibles.add(Ration.Builder.buildBalouneEau());
        rationsDisponibles.add(Ration.Builder.buildBiscuits());

        for (int i = 0; i < nombreArme; i++) {
            int indexAleatoire = random.nextInt(armesDisponibles.size());
            this.armes.add(armesDisponibles.get(indexAleatoire));
        }

        indexArmure = random.nextInt(armuresDisponibles.size());
        if(indexArmure >= armuresDisponibles.size()){
            indexArmure--;
        }
        armure = armuresDisponibles.get(indexArmure);

        for (int i = 0; i < rationsDisponibles.size(); i++) {
            rations.add(rationsDisponibles.get(i));
        }
    }

    public void jouer(Joueur participant, int jourJeu){
        Integer choix;
        Integer choixArme;
        Integer choixRation = 0;
        Integer choixInventaire;
        String responseArmure;
        String retourMenu;
        Scanner scanner = new Scanner(System.in);


        if(!isEnnemi){
            do {
                displayMenu();
                System.out.print("Votre choix: ");
                choix = Terminal.readInt();
                System.out.println();
                System.out.println();
                if (choix != null) {
                    boolean isStaticChoice = (choix == 99 || choix == 1 || choix == 2 || choix == 3 || choix == 4);

                    if(!isStaticChoice){
                        System.out.println("Choix invalide. Veuillez sélectionner une option du menu");
                        System.out.println();
                        choix = null;
                    }
                }
            }while(choix == null);

            if(choix == 99){
                do{
                    displayInventaire();
                    retourMenu = scanner.nextLine();
                    if(retourMenu.equalsIgnoreCase("R")){
                        return;
                    }
                    choixInventaire = Terminal.readInt(retourMenu);
                    System.out.println();
                    System.out.println();

                    boolean isChoixInventaire = (choixInventaire != null && (choixInventaire == 1 || choixInventaire == 2 || choixInventaire == 3));

                    if(!isChoixInventaire){
                        choixInventaire = null;
                        retourMenu = null;
                        System.out.println();
                    }

                    if(isChoixInventaire){
                        if(choixInventaire == 1){

                            if (armes.isEmpty()) {
                                displayArmes();

                                retourMenu = scanner.nextLine();
                                if (retourMenu.equalsIgnoreCase("R")) {
                                    return;
                                }

                                System.out.println();
                            } else{
                                do {
                                    displayArmes();
                                    retourMenu = scanner.nextLine();

                                    if (retourMenu.equalsIgnoreCase("R")) {
                                        return;
                                    }

                                    choixArme = Terminal.readInt(retourMenu);

                                    System.out.println();
                                    boolean isWeaponChoice = (choixArme <= armes.size() && choixArme >= 0);

                                    if(!isWeaponChoice){
                                        System.out.println();
                                        System.out.println("Choix invalide. Veuillez sélectionner une parmis les armes");
                                        choixArme = null;
                                        System.out.println();
                                    }

                                    if(isWeaponChoice){
                                        armeActual = armes.get(choixArme-1);
                                        if(!armeActual.peutAttaquer()){
                                            System.out.println("Cette arme ne peut pas attaquer.");
                                            choixArme = null;
                                        } else{
                                            armeActual = armes.get(choixArme - 1);
                                            System.out.println("Vous avez choisi : " + armeActual.afficherWeapon());
                                            System.out.println();
                                        }
                                    }
                                } while (choixArme == null);
                            }

                        }else if (choixInventaire == 2) {
                            if (armure == null){
                                displayArmures();

                                System.out.println();
                            } else{
                                displayArmures();
                                do {
                                    responseArmure = scanner.nextLine().trim();
                                    if(!responseArmure.equalsIgnoreCase("O") && !responseArmure.equalsIgnoreCase("N")){
                                        System.out.println();
                                        System.out.println("Reponse incorrect veuillez réessayer!");
                                        System.out.println();
                                    }
                                    System.out.println();
                                } while(!responseArmure.equalsIgnoreCase("O") && !responseArmure.equalsIgnoreCase("N"));
                                if(responseArmure.equalsIgnoreCase("O")){
                                    if(armure.getDefense()==0){
                                        System.out.println();
                                        System.out.println("Votre armure a déja été utilisé!");
                                        System.out.println();
                                    } else{
                                        armureActive = true;
                                    }
                                } else{
                                    return;
                                }
                                System.out.println();
                            }
                        } else{

                            if(rations.isEmpty()){
                                displayRations();
                                retourMenu = scanner.nextLine();
                                if (retourMenu.equalsIgnoreCase("R")) {
                                    return;
                                }
                                System.out.println();
                            } else {
                                do {
                                    displayRations();
                                    retourMenu = scanner.nextLine();
                                    if (retourMenu.equalsIgnoreCase("R")) {
                                        return;
                                    }
                                    choixRation = Terminal.readInt(retourMenu);
                                    System.out.println();

                                    boolean isRationChoice = (choixRation != null && (choixRation <= rations.size() && choixRation >= 0));
                                    if(!isRationChoice){
                                        System.out.println();
                                        retourMenu = null;
                                        choixRation = null;
                                        System.out.println();
                                    }
                                    if (isRationChoice){
                                        if (!rations.isEmpty()){
                                            displayRations();
                                            System.out.println();
                                            rationActual = rations.get(choixRation-1);
                                        } else{
                                            displayRations();
                                        }
                                    }
                                } while (choixRation == null && retourMenu == null);
                            }
                        }
                    }

                } while(choixInventaire == null && retourMenu == null);

            } else if(choix == 4){
                System.out.println("Vous ne faites rien...");
            } else if(choix == 1){
                // Attaque de l'arme choisie
                if (armeActual != null) {
                    armeActual.attaquer(participant, tauxPrecision);
                } else{
                    System.out.println("Aucune arme choisi! Veuillez choisir une arme dans l'inventaire");
                    System.out.println();
                }
            } else if(choix == 2){
                if(!rations.isEmpty()){
                    if(rationActual != null){
                        System.out.printf("Vous mangez la ration %s qui vous donnera %f point de vie!", rationActual.afficherNom(), rationActual.getSante());
                        setVitalite(rationActual.getSante());
                        rationActual = null;
                        rations.remove(choixRation-1);
                        hud();
                    } else{
                        System.out.println("Aucune ration selectionné! Veuillez choisir une ration dans l'inventaire");
                    }
                } else{
                    System.out.println("Vous n'avez aucune ration dans votre sac! Faites un autre choix");
                    System.out.println();
                    return;
                }
            } else if(choix == 3){
                setNeRienFaire(true);
            }
            //TODO: GERER MANGER()
            //TODO: GERER S'EVADER
        } else{
            prendreDecision(participant, jourJeu);
        }
    }

    public void setNeRienFaire(boolean neRienFaire){
        this.neRienFaire = neRienFaire;
    }


    public void ajouterRation(Ration ration){
        System.out.printf("Vous avez une nouvelle ration %s", ration.afficherNom());
        this.rations.add(ration);
    }

    public void recupererArme(Arme arme){
        System.out.printf("Votre actuelle est %s ", arme.afficherWeapon());
        this.armeActual = arme;
    }
    public boolean getNeRienFaire(){
        return neRienFaire;
    }

    public void evasionReussie(){
        estEvader = true;
    }

    public boolean getEstEvader(){
        return estEvader;
    }

    public void setEstEvader(boolean estEvader){
        this.estEvader = estEvader;
    }

    public boolean getPerteCombat(){
        return this.perteCombat;
    }

    public int getNombreCombat(){
        return nombreCombat;
    }

    public void setVitalite(double vie){
        this.sante += vie;
    }

    public void setNombreCombat(int n){
        this.nombreCombat = n;
    }

    public void degatsSante(double tauxAttaque){
        //TODO: S armure active, mettre degats sur ARMURE jusqu'a armure desactiver
        this.sante -= (tauxAttaque-tauxDefense);
        if (sante < 0){
            sante = 0;
            perteCombat = true;

        }

    }



    //TODO: MERCI A CHAT GPT
    private void prendreDecision(Joueur adversaire, int jourJeu) {
        Random rand = new Random();
        int choix;

        // Évaluation de la situation
        double ratioSante = this.sante / adversaire.getVitalite();

        // Logique de fuite améliorée
        if (ratioSante < 0.3 && jourJeu!=3) {
            System.out.println(nom + " se rend compte de sa situation critique et tente de fuir !");
            choix = 5; // S'évader
        } else {
            // Stratégie basée sur les armes
            if (armeActual != null && adversaire.getNomArmeActual() != null) {
                String monType = armeActual.getType();
                String typeAdversaire = adversaire.armeActual.getType();

                // Adaptation à l'arme adverse
                if (monType.equals("ARMEBLANCHE") && typeAdversaire.equals("ARMEAFEU")) {
                    for (Arme arme : armes) {
                        if (arme.getType().equals("ARMEAFEU") && arme.peutAttaquer()) {
                            armeActual = arme;
                            System.out.println(nom + " change d'arme pour " + armeActual.afficherWeapon() +
                                    " en réponse à l'arme de " + adversaire.getNom() + ".");
                            break;
                        }
                    }
                }
            }

            // Choix aléatoire pondéré
            double randomValue = rand.nextDouble();
            if (sante < 30 && !rations.isEmpty()) {
                choix = 2; // Manger une ration si la santé est basse
            } else if (armeActual != null && armeActual.peutAttaquer()) {
                choix = 1; // Attaquer si une arme est disponible
            } else if (armure != null && !armureActive) {
                choix = 3; // Activer l'armure si possible
            } else {
                // Choix aléatoire pondéré
                if (randomValue < 0.4) {
                    choix = 1; // Attaquer
                } else if (randomValue < 0.7) {
                    choix = 4; // Ne rien faire
                } else {
                    choix = 5; // S'évader
                }
            }
        }

        // Exécution de l'action choisie
        switch (choix) {
            case 1:
                System.out.println(prenom+" "+nom+" vous attaque !");
                if(armeActual == null){
                    if (!armes.isEmpty()) {
                        armeActual = armes.get(0);
                        for (Arme arme : armes) {
                            if (arme.peutAttaquer() && arme.getDegats() > armeActual.getDegats()) {
                                armeActual = arme;
                            }
                        }
                    } else {
                        System.out.println(nom + " n'a pas d'arme disponible pour attaquer.");
                        break;
                    }
                }

                armeActual.attaquer(adversaire, tauxPrecision);
                break;
            case 2:
                System.out.println(nom + " mange une ration pour récupérer des forces.");
                if (!rations.isEmpty()) {
                    rationActual = rations.remove(0);
                    setVitalite(rationActual.getSante());
                }
                break;
            case 3:
                System.out.println(nom + " active son armure.");
                armureActive = true;
                break;
            case 4:
                System.out.println(nom + " ne fait rien ce tour-ci...");
                break;
            case 5:
                System.out.println(nom + " s'évade !");
                setEstEvader(true);
                break;
        }
    }


    public String getNomArmeActual(){
        return armeActual.afficherWeapon();
    }

    public Arme getArmeActual(){
        return armeActual;
    }

    public double getVitalite(){
        return sante;
    }

    public void afficherCaracteristiques(){
        Scanner scanner = new Scanner(System.in);
        String reponse = "O";

        System.out.println();
        System.out.printf("Bienvenue %s %s dans l'acte BR qui vous force à combattre vos camarades sur une ile magique. \n" +
                "Vous avez trois jours pour éliminer tous les autres participants. Une seule personne doit survivre \n" +
                "à la fin du troisième jour.", prenom, nom);
        System.out.println();

        System.out.println();
        System.out.print("Récupérer votre sac de combat et démarrer la partie (O/N) ?  ");
        reponse = scanner.nextLine();
        if(reponse.equalsIgnoreCase("O")){
            remplirSac();
        }
        System.out.println();

        System.out.println();
        if(armes.isEmpty() && armure == null && rations.isEmpty()){
            System.out.println("Votre sac est vide.");
        } else {
            System.out.println("Votre sac de combat contient: ");
            if(armes != null && !armes.isEmpty()){
                System.out.println("("+armes.size()+")"+" armes");
            }
            if (armure != null) {
                System.out.print("Armure: "+armure.afficherNom());
                System.out.println();
            }
            if(rations != null && !rations.isEmpty()){
                System.out.println("("+rations.size()+")"+" Ration(s): ");
                for (int i = 0; i < rations.size(); i++) {
                    rations.get(i).afficherNom();
                }
            }

        }
        System.out.println();

        hud();
    }



    public String getNom(){
        return nom;
    }

    public String getPrenom(){
        return prenom;
    }

    private void hud(){
        int s = (int) sante/10;
        int a = scaleToTen(tauxAttaque, 1, 5);
        int d = scaleToTen(tauxDefense, 0, 12);
        int p = scaleToTen(tauxPrecision, 20, 80);
        int c = scaleToTen(tauxChance, 1, 40);

        System.out.println("\033[1;32m--- Caractéristiques du joueur ---\033[0m");
        System.out.print("Vitalité: ");
        System.out.print("\n[");

        for (int i = 0; i <s ; i++) {
            System.out.print(Terminal.Color.RED.colorize("="));  //Icon unicode
        }
        for (int i = 0; i < 10-s; i++) {
            System.out.print(" ");
        }
        System.out.print("]");
        System.out.println();

        System.out.print("Attaque: ");
        System.out.print("\n[");
        for (int i = 0; i <a ; i++) {
            System.out.print(Terminal.Color.RED.colorize("="));  //Icon unicode
        }
        for (int i = 0; i < 10-a; i++) {
            System.out.print(" ");
        }
        System.out.print("]");
        System.out.println();

        System.out.print("Défense: ");
        System.out.print("\n[");
        for (int i = 0; i <d ; i++) {
            System.out.print(Terminal.Color.RED.colorize("="));  //Icon unicode
        }
        for (int i = 0; i < 10-d; i++) {
            System.out.print(" ");
        }
        System.out.print("]");
        System.out.println();

        System.out.print("Précision: ");
        System.out.print("\n[");
        for (int i = 0; i <p ; i++) {
            System.out.print(Terminal.Color.RED.colorize("="));  //Icon unicode
        }
        for (int i = 0; i < 10-p; i++) {
            System.out.print(" ");
        }
        System.out.print("]");
        System.out.println();

        System.out.print("Chance: ");
        System.out.print("\n[");
        for (int i = 0; i <c ; i++) {
            System.out.print(Terminal.Color.RED.colorize("="));  //Icon unicode
        }
        for (int i = 0; i < 10-c; i++) {
            System.out.print(" ");
        }
        System.out.print("]");
        System.out.println();
        System.out.println();

    }

    public int scaleToTen(double value, double min, double max) {

        return (int) (((value - min) / (max - min)) * 10);
    }

    private void lectureNom(){
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Quel est votre nom ? ");
        this.nom = scanner.nextLine();
        System.out.println();
    }

    private void lecturePrenom(){
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Quel est votre prénom ? ");
        this.prenom = scanner.nextLine();
        System.out.println();
    }

    public void consulterInventaireArme(){
        boolean armeTrouve = false;

        for (int i = 0; i < armes.size(); i++) {
            Arme arme = armes.get(i);
            if(arme.getType().equals(Typearme.ARMEAFEU.toString()) || arme.getType().equals(Typearme.ARMEBLANCHE.toString())){
                System.out.println((i + 1) + " - " + arme.afficherWeapon());
                armeTrouve = true;
            };
        }
        if(!armeTrouve){
            System.out.println("Vous ne possédez aucune arme dans votre sac.");
        }
    }

    private void displayInventaire(){
        System.out.println("INVENTAIRE");
        System.out.println();
        System.out.println("1 - Armes");
        System.out.println("2 - Armure");
        System.out.println("3 - Ration ");
        System.out.println();
        System.out.print("Entre votre choix ou 'R' pour revenir: ");
    }

    private void displayRations(){
        System.out.println("RATIONS");

        if(rations != null && !rations.isEmpty()){
            for (int i = 0; i < rations.size(); i++) {
                Ration ration = rations.get(i);
                System.out.println((i + 1) + " - " + ration.afficherNom());
            }
            System.out.println();
            System.out.print("Entrez un choix de ration ou 'R' pour revenir: ");
        } else{
            System.out.println();
            System.out.println("Vous n'avez pas de rations");
            System.out.println();
            System.out.println(" - "+"Retour (R)");
            System.out.println();
        }
    }

    private void displayArmures(){
        if(armure == null){
            System.out.println("Vous n'avez pas d'armure!");
        } else{
            System.out.println(" - "+armure.afficherNom());
            System.out.println();
            System.out.print("Voulez-vous utiliser votre armure ? (O/N):   ");
        }
    }

    private void displayArmes(){
        if(armes == null){
            System.out.println("Vous n'avez pas d'armes!");
        } else {
            for (int i = 0; i < armes.size(); i++) {
                Arme arme = armes.get(i);
                System.out.println((i + 1) + " - " + arme.afficherWeapon());
            }
            System.out.print("Entrez un choix d'arme ou 'R' pour revenir: ");
        }
    }

    public void displayMenu(){

        System.out.println("1 - Attaquer");
        System.out.println("2 - Manger");
        System.out.println("3 - S'évader ");
        System.out.println("4 - Ne rien faire ");
        System.out.println("99 - Inventaire");
        System.out.println();

    }



}
