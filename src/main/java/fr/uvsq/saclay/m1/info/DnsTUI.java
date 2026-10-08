package fr.uvsq.saclay.m1.info;

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
     * Lit la prochaine commande saisie par l'utilisateur.
     *
     * @return la commande correspondante
     */
    public Commande nextCommande() {
        String ligne = scanner.nextLine().trim();

        if (ligne.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
            return new RechercheNom(
                    dns,
                    new AdresseIP(ligne));
        }

        return null;
    }
}