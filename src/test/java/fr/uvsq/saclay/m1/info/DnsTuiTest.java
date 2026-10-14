package fr.uvsq.saclay.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Tests de l'interface utilisateur DNS. */
class DnsTuiTest {

  @TempDir Path dossierTemporaire;

  @Test
  void uneAdresseIpDoitProduireUneRechercheNom() {
    Path fichierDns = dossierTemporaire.resolve("dns.txt");
    Dns dns = new Dns(fichierDns);

    Scanner scanner =
        new Scanner(new ByteArrayInputStream("192.168.0.1\n".getBytes(StandardCharsets.UTF_8)));

    DnsTui tui = new DnsTui(dns, scanner);

    Commande commande = tui.nextCommande();

    assertInstanceOf(RechercheNom.class, commande);
  }

  @Test
  void uneCommandeLsAvecOptionDoitTrierParIp() {
    Path fichierDns = dossierTemporaire.resolve("dns.txt");
    Dns dns = new Dns(fichierDns);

    DnsItem item1 = new DnsItem(new AdresseIp("192.168.0.10"), new NomMachine("www.domaine.local"));

    DnsItem item2 = new DnsItem(new AdresseIp("192.168.0.2"), new NomMachine("mail.domaine.local"));

    dns.addItem(item1);
    dns.addItem(item2);

    Scanner scanner =
        new Scanner(
            new ByteArrayInputStream("ls -a domaine.local\n".getBytes(StandardCharsets.UTF_8)));

    DnsTui tui = new DnsTui(dns, scanner);

    Commande commande = tui.nextCommande();

    @SuppressWarnings("unchecked")
    java.util.List<DnsItem> resultat = (java.util.List<DnsItem>) commande.execute();

    assertEquals(item2, resultat.get(0));
    assertEquals(item1, resultat.get(1));
  }

  @Test
  void uneCommandeAddDoitProduireUnAjoutDns() {
    Path fichierDns = dossierTemporaire.resolve("dns.txt");
    Dns dns = new Dns(fichierDns);

    Scanner scanner =
        new Scanner(
            new ByteArrayInputStream(
                "add 192.168.0.10 machine.test.local\n".getBytes(StandardCharsets.UTF_8)));

    DnsTui tui = new DnsTui(dns, scanner);

    Commande commande = tui.nextCommande();

    assertInstanceOf(AjoutDns.class, commande);

    commande.execute();

    assertEquals(
        new DnsItem(new AdresseIp("192.168.0.10"), new NomMachine("machine.test.local")),
        dns.getItem(new AdresseIp("192.168.0.10")));
  }

  @Test
  void afficheDoitAfficherAdresseIp() {
    ByteArrayOutputStream sortie = new ByteArrayOutputStream();
    PrintStream ancienneSortie = System.out;

    System.setOut(new PrintStream(sortie));

    try {
      DnsTui tui = new DnsTui(new Dns(dossierTemporaire.resolve("dns.txt")));

      tui.affiche(new AdresseIp("193.51.31.90"));
    } finally {
      System.setOut(ancienneSortie);
    }

    assertEquals("193.51.31.90" + System.lineSeparator(), sortie.toString());
  }

  @Test
  void afficheDoitAfficherLeNomMachine() {
    ByteArrayOutputStream sortie = new ByteArrayOutputStream();
    PrintStream ancienneSortie = System.out;

    System.setOut(new PrintStream(sortie));

    try {
      DnsTui tui = new DnsTui(new Dns(dossierTemporaire.resolve("dns.txt")));

      tui.affiche(new NomMachine("www.uvsq.fr"));
    } finally {
      System.setOut(ancienneSortie);
    }

    assertEquals("www.uvsq.fr" + System.lineSeparator(), sortie.toString());
  }

  @Test
  void afficheDoitAfficherUneListeDeMachines() {
    Path fichierDns = dossierTemporaire.resolve("dns.txt");
    Dns dns = new Dns(fichierDns);

    Scanner scanner = new Scanner(new ByteArrayInputStream("\n".getBytes(StandardCharsets.UTF_8)));

    DnsTui tui = new DnsTui(dns, scanner);

    DnsItem item1 = new DnsItem(new AdresseIp("193.51.25.12"), new NomMachine("ecampus.uvsq.fr"));

    DnsItem item2 = new DnsItem(new AdresseIp("193.51.31.90"), new NomMachine("www.uvsq.fr"));

    List<DnsItem> resultats = List.of(item1, item2);

    ByteArrayOutputStream sortie = new ByteArrayOutputStream();
    PrintStream ancienneSortie = System.out;

    System.setOut(new PrintStream(sortie));

    tui.affiche(resultats);

    System.setOut(ancienneSortie);

    assertEquals(
        "ecampus.uvsq.fr 193.51.25.12"
            + System.lineSeparator()
            + "www.uvsq.fr 193.51.31.90"
            + System.lineSeparator(),
        sortie.toString(StandardCharsets.UTF_8));
  }

  @Test
  void quitDoitRetournerUneCommandeQuitter() {
    Scanner scanner = new Scanner("quit\n");

    DnsTui tui = new DnsTui(new Dns(dossierTemporaire.resolve("dns.txt")), scanner);

    Commande commande = tui.nextCommande();

    assertTrue(commande instanceof Quitter);
  }
}
