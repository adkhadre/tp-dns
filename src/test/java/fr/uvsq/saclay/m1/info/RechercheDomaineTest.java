package fr.uvsq.saclay.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests de la commande RechercheDomaine.
 */
class RechercheDomaineTest {

    @TempDir
    Path dossierTemporaire;

    @Test
    void rechercheDomaineDoitRetournerLesMachinesDuDomaine() {
        Path fichierDns = dossierTemporaire.resolve("dns.txt");
        Dns dns = new Dns(fichierDns);

        DnsItem item1 = new DnsItem(
                new AdresseIP("192.168.0.10"),
                new NomMachine("www.domaine.local"));

        DnsItem item2 = new DnsItem(
                new AdresseIP("192.168.0.2"),
                new NomMachine("mail.domaine.local"));

        dns.addItem(item1);
        dns.addItem(item2);

        Commande commande =
                new RechercheDomaine(
                        dns, "domaine.local", false);

        @SuppressWarnings("unchecked")
        List<DnsItem> resultat =
                (List<DnsItem>) commande.execute();

        assertEquals(2, resultat.size());
        assertEquals(item2, resultat.get(0));
        assertEquals(item1, resultat.get(1));
    }
}