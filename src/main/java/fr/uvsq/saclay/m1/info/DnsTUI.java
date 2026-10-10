package fr.uvsq.saclay.m1.info;

import java.util.List;
import java.util.Scanner;

/**
 * Interface utilisateur textuelle du DNS.
 */
public class DnsTUI {

    private final Dns dns;
    private final Scanner scanner;

    /**
     * Construit l'interface utilisateur.
     *
     * @param dns la base DNS
     */
    public DnsTUI(Dns dns) {
        this.dns = dns;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Construit l'interface utilisateur avec un scanner donné.
     *
     * @param dns la base DNS
     * @param scanner le scanner utilisé pour lire les commandes
     */
    public DnsTUI(Dns dns, Scanner scanner) {
        this.dns = dns;
        this.scanner = scanner;
    }

    /**
     * Affiche l'aide de l'application.
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
     * Lit la prochaine commande saisie par l'utilisateur.
     *
     * @return la commande correspondante
     */
    public Commande nextCommande() {
        System.out.print("> ");

        String ligne = scanner.nextLine().trim();

        if (ligne.equals("quit")) {
            return new Quitter();
        }

        if (ligne.startsWith("add ")) {
            String[] parties = ligne.split("\\s+");

            AdresseIP adresseIP = new AdresseIP(parties[1]);
            NomMachine nomMachine = new NomMachine(parties[2]);

            DnsItem item = new DnsItem(adresseIP, nomMachine);

            return new AjoutDns(dns, item);
        }

        if (ligne.startsWith("ls -a ")) {
            String domaine = ligne.substring(5).trim();

            return new RechercheDomaine(
                    dns,
                    domaine,
                    true);
        }

        if (ligne.startsWith("ls ")) {
            String domaine = ligne.substring(3).trim();

            return new RechercheDomaine(
                    dns,
                    domaine,
                    false);
        }

        if (ligne.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
            return new RechercheNom(
                    dns,
                    new AdresseIP(ligne));
        }

        if (ligne.contains(".")) {
            return new RechercheIP(
                    dns,
                    new NomMachine(ligne));
        }

        return null;
    }

    /**
     * Affiche le résultat d'une commande.
     *
     * @param resultat le résultat à afficher
     */
    public void affiche(Object resultat) {
        if (resultat instanceof AdresseIP) {
            System.out.println(resultat);
        } else if (resultat instanceof NomMachine) {
            System.out.println(resultat);
        } else if (resultat instanceof List<?>) {
            List<?> resultats = (List<?>) resultat;

            for (Object element : resultats) {
                DnsItem item = (DnsItem) element;
                System.out.println(item);
            }
        }
    }
}
