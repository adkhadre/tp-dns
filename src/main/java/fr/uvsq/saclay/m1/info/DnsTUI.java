package fr.uvsq.saclay.m1.info;

import java.util.List;
import java.util.Scanner;

/**
 * Interface utilisateur textuelle (TUI) du DNS.
 * Cette classe lit les commandes saisies au clavier, les transforme
 * en objets Commande (patron Command) et affiche leurs résultats.
 */
public class DnsTUI {

    /** La base DNS sur laquelle portent les commandes. */
    private final Dns dns;

    /** Le scanner utilisé pour lire les saisies de l'utilisateur. */
    private final Scanner scanner;

    /**
     * Construit l'interface utilisateur en lisant sur l'entrée standard.
     *
     * @param dns la base DNS
     */
    public DnsTUI(Dns dns) {
        this.dns = dns;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Construit l'interface utilisateur avec un scanner donné.
     * Utile pour les tests (lecture depuis une chaîne au lieu du clavier).
     *
     * @param dns la base DNS
     * @param scanner le scanner utilisé pour lire les commandes
     */
    public DnsTUI(Dns dns, Scanner scanner) {
        this.dns = dns;
        this.scanner = scanner;
    }

    /**
     * Affiche l'aide de l'application : la liste des commandes disponibles
     * et leur syntaxe.
     */
    public void afficheAide() {
        System.out.println("========================================");
        System.out.println("           DNS - Gestionnaire");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Commandes disponibles :");
        System.out.println();
        System.out.println("  <adresse IP>                 Rechercher le nom");
        System.out.println("  <nom.qualifie>               Rechercher l'adresse IP");
        System.out.println("  ls <domaine>                 Lister les machines du domaine");
        System.out.println("  ls -a <domaine>              Lister par adresse IP");
        System.out.println("  add <IP> <nom.qualifie>      Ajouter une machine");
        System.out.println("  quit                         Quitter");
        System.out.println();
        System.out.println("========================================");
        System.out.println();
    }

    /**
     * Lit la prochaine commande saisie par l'utilisateur et la convertit
     * en objet Commande.
     *
     * L'ordre des tests est important :  "ls -a " doit être
     * testé avant  "ls ", sinon  "-a" serait pris pour un domaine.
     *
     * @return la commande correspondante, ou  null si la saisie
     *         n'est reconnue par aucune commande
     * @throws IllegalArgumentException si une adresse IP ou un nom de machine
     *         saisi est invalide
     */
    public Commande nextCommande() {
        // Invite de saisie (print et non println : l'utilisateur tape sur la même ligne)
        System.out.print("> ");

        // trim() retire les espaces au début et à la fin de la saisie
        String ligne = scanner.nextLine().trim();

        // Commande "quit"
        if (ligne.equals("quit")) {
            return new Quitter();
        }

        // Commande "add <IP> <nom.qualifie>"
        if (ligne.startsWith("add ")) {
            // Découpage sur les espaces : [add, IP, nom]
            String[] parties = ligne.split("\\s+");

            // Les constructeurs valident les valeurs saisies
            AdresseIP adresseIP = new AdresseIP(parties[1]);
            NomMachine nomMachine = new NomMachine(parties[2]);

            DnsItem item = new DnsItem(adresseIP, nomMachine);

            return new AjoutDns(dns, item);
        }

        // Commande "ls -a <domaine>" : liste triée par adresse IP
        if (ligne.startsWith("ls -a ")) {
            // On saute le préfixe pour ne garder que le domaine
            String domaine = ligne.substring(5).trim();

            return new RechercheDomaine(
                    dns,
                    domaine,
                    true);
        }

        // Commande "ls <domaine>" : liste triée par nom de machine
        if (ligne.startsWith("ls ")) {
            String domaine = ligne.substring(3).trim();

            return new RechercheDomaine(
                    dns,
                    domaine,
                    false);
        }

        // Une saisie de la forme "nombre.nombre.nombre.nombre" est une adresse IP :
        // on cherche le nom de machine associé
        if (ligne.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
            return new RechercheNom(
                    dns,
                    new AdresseIP(ligne));
        }

        // Toute autre saisie contenant un point est un nom qualifié :
        // on cherche l'adresse IP associée
        if (ligne.contains(".")) {
            return new RechercheIP(
                    dns,
                    new NomMachine(ligne));
        }

        // Saisie non reconnue
        return null;
    }

    /**
     * Affiche le résultat d'une commande selon son type :
     * une adresse IP ou un nom de machine sur une ligne,
     * une liste d'associations à raison d'une par ligne.
     *
     * @param resultat le résultat à afficher (adresse IP, nom de machine
     *                 ou liste de  DnsItem)
     */
    public void affiche(Object resultat) {
        if (resultat instanceof AdresseIP) {
            System.out.println(resultat);
        } else if (resultat instanceof NomMachine) {
            System.out.println(resultat);
        } else if (resultat instanceof List<?>) {
            List<?> resultats = (List<?>) resultat;

            // Chaque élément est un DnsItem (cast nécessaire à cause de List<?>)
            for (Object element : resultats) {
                DnsItem item = (DnsItem) element;
                System.out.println(item);
            }
        }
    }
}