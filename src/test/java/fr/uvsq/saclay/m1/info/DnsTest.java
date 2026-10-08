package fr.uvsq.saclay.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests de la classe Dns.
 */
class DnsTest {

    @TempDir
    Path dossierTemporaire;

    private Path nouveauFichierDns() {
        return dossierTemporaire.resolve("dns.txt");
    }

    @Test
    void laBaseDnsDoitEtreChargee() {
        Dns dns = new Dns();

        assertEquals(3, dns.taille());
    }

    @Test
    void uneAssociationDoitPouvoirEtreAjoutee() {
        Dns dns = new Dns(nouveauFichierDns());

        DnsItem item = new DnsItem(
                new AdresseIP("192.168.0.10"),
                new NomMachine("machine.test.local"));

        dns.addItem(item);

        assertEquals(1, dns.taille());
    }

    @Test
    void uneAdresseIPDejaExistanteDoitEtreRefusee() {
        Dns dns = new Dns(nouveauFichierDns());

        dns.addItem(new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("machine.domaine.local")));

        assertThrows(
                IllegalArgumentException.class,
                () -> dns.addItem(new DnsItem(
                        new AdresseIP("192.168.0.1"),
                        new NomMachine("autre.domaine.local"))));
    }

    @Test
    void unNomMachineDejaExistantDoitEtreRefuse() {
        Dns dns = new Dns(nouveauFichierDns());

        dns.addItem(new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("machine.domaine.local")));

        assertThrows(
                IllegalArgumentException.class,
                () -> dns.addItem(new DnsItem(
                        new AdresseIP("192.168.0.2"),
                        new NomMachine("machine.domaine.local"))));
    }

    @Test
    void uneAssociationAvecIPEtNomDifferentsDoitEtreAcceptee() {
        Dns dns = new Dns(nouveauFichierDns());

        dns.addItem(new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("machine.domaine.local")));

        dns.addItem(new DnsItem(
                new AdresseIP("192.168.0.2"),
                new NomMachine("autre.domaine.local")));

        assertEquals(2, dns.taille());
    }

    @Test
    void rechercheParAdresseIPDoitRetournerLeBonItem() {
        Dns dns = new Dns(nouveauFichierDns());

        DnsItem item = new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("machine.domaine.local"));

        dns.addItem(item);

        assertEquals(
                item,
                dns.getItem(new AdresseIP("192.168.0.1")));
    }

    @Test
    void rechercheParNomMachineDoitRetournerLeBonItem() {
        Dns dns = new Dns(nouveauFichierDns());

        DnsItem item = new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("machine.domaine.local"));

        dns.addItem(item);

        assertEquals(
                item,
                dns.getItem(new NomMachine("machine.domaine.local")));
    }

    @Test
    void rechercheParAdresseIPInconnueDoitRetournerNull() {
        Dns dns = new Dns(nouveauFichierDns());

        dns.addItem(new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("machine.domaine.local")));

        assertEquals(
                null,
                dns.getItem(new AdresseIP("192.168.0.2")));
    }

    @Test
    void rechercheParNomMachineInconnuDoitRetournerNull() {
        Dns dns = new Dns(nouveauFichierDns());

        dns.addItem(new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("machine.domaine.local")));

        assertEquals(
                null,
                dns.getItem(new NomMachine("autre.domaine.local")));
    }

    @Test
    void rechercheParDomaineDoitRetournerLesBonnesAssociations() {
        Dns dns = new Dns(nouveauFichierDns());

        DnsItem item1 = new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("www.domaine.local"));

        DnsItem item2 = new DnsItem(
                new AdresseIP("192.168.0.2"),
                new NomMachine("mail.domaine.local"));

        DnsItem item3 = new DnsItem(
                new AdresseIP("192.168.0.3"),
                new NomMachine("pc.autre.fr"));

        dns.addItem(item1);
        dns.addItem(item2);
        dns.addItem(item3);

        List<DnsItem> resultat = dns.getItems("domaine.local");

        assertEquals(2, resultat.size());
        assertEquals(item2, resultat.get(0));
        assertEquals(item1, resultat.get(1));
    }

    @Test
    void rechercheParDomaineInconnuDoitRetournerUneListeVide() {
        Dns dns = new Dns(nouveauFichierDns());

        dns.addItem(new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("www.domaine.local")));

        List<DnsItem> resultat = dns.getItems("inconnu.local");

        assertEquals(0, resultat.size());
    }
}
