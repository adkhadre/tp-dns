package fr.uvsq.saclay.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Tests de la classe Dns.
 */
class DnsTest {

    @Test
    void unDnsNouvellementCreeDoitEtreVide() {
        Dns dns = new Dns();

        assertEquals(0, dns.taille());
    }
    @Test
    void uneAssociationDoitPouvoirEtreAjoutee() {
        Dns dns = new Dns();

        DnsItem item = new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("machine.domaine.local"));

        dns.addItem(item);

        assertEquals(1, dns.taille());
    }

    @Test
    void uneAdresseIPDejaExistanteDoitEtreRefusee() {
        Dns dns = new Dns();

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
        Dns dns = new Dns();

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
        Dns dns = new Dns();

        dns.addItem(new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("machine.domaine.local")));

        dns.addItem(new DnsItem(
                new AdresseIP("192.168.0.2"),
                new NomMachine("autre.domaine.local")));

        assertEquals(2, dns.taille());
    }
}