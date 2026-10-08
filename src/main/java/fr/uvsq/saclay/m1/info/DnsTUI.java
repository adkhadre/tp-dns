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
}