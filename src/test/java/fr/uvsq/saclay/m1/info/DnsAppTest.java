package fr.uvsq.saclay.m1.info;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests de l'application DNS.
 */
class DnsAppTest {

    @TempDir
    Path dossierTemporaire;

    @Test
    void runDoitExecuterLesCommandes() {
        Path fichierDns = dossierTemporaire.resolve("dns.txt");

        Dns dns = new Dns(fichierDns);

        Scanner scanner = new Scanner(
                "add 192.168.0.10 test.domaine.local\n"
                        + "test.domaine.local\n"
                        + "quit\n");

        DnsTUI tui = new DnsTUI(dns, scanner);
        DnsApp app = new DnsApp(tui);

        ByteArrayOutputStream sortie = new ByteArrayOutputStream();
        PrintStream ancienneSortie = System.out;

        System.setOut(new PrintStream(sortie));

        try {
            app.run();
        } finally {
            System.setOut(ancienneSortie);
        }

        assertTrue(
                sortie.toString().contains("192.168.0.10"));
    }
    @Test
    void runDoitAfficherUneErreurSiLeNomExisteDeja() {
        Path fichierDns = dossierTemporaire.resolve("dns.txt");

        Dns dns = new Dns(fichierDns);

        Scanner scanner = new Scanner(
                "add 192.168.0.10 test.domaine.local\n"
                        + "add 192.168.0.20 test.domaine.local\n"
                        + "quit\n");

        DnsTUI tui = new DnsTUI(dns, scanner);
        DnsApp app = new DnsApp(tui);

        ByteArrayOutputStream sortie = new ByteArrayOutputStream();
        PrintStream ancienneSortie = System.out;

        System.setOut(new PrintStream(sortie));

        try {
            app.run();
        } finally {
            System.setOut(ancienneSortie);
        }

        assertTrue(
                sortie.toString().contains(
                        "ERREUR : Ce nom de machine existe déjà !"));
    }
}