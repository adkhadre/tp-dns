package fr.uvsq.saclay.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests de la commande RechercheIP.
 */
class RechercheIPTest {

    @TempDir
    Path dossierTemporaire;

    @Test
    void rechercheIPDoitRetournerLaBonneAdresseIP() {
        Path fichierDns = dossierTemporaire.resolve("dns.txt");
        Dns dns = new Dns(fichierDns);

        DnsItem item = new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("machine.domaine.local"));

        dns.addItem(item);

        Commande commande = new RechercheIP(
                dns,
                new NomMachine("machine.domaine.local"));

        assertEquals(
                new AdresseIP("192.168.0.1"),
                commande.execute());
    }
}