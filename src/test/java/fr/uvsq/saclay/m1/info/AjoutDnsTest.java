package fr.uvsq.saclay.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests de la commande AjoutDns.
 */
class AjoutDnsTest {

    @TempDir
    Path dossierTemporaire;

    @Test
    void ajoutDnsDoitAjouterUneAssociation() {
        Path fichierDns = dossierTemporaire.resolve("dns.txt");
        Dns dns = new Dns(fichierDns);

        DnsItem item = new DnsItem(
                new AdresseIP("192.168.0.10"),
                new NomMachine("machine.test.local"));

        Commande commande = new AjoutDns(dns, item);

        assertEquals(item, commande.execute());
        assertEquals(1, dns.taille());
    }
}