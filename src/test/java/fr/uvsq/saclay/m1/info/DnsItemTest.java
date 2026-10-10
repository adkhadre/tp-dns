package fr.uvsq.saclay.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests de la classe DnsItem.
 */
class DnsItemTest {

    @Test
    void associationDoitEtreCorrectementCreee() {
        AdresseIP ip = new AdresseIP("192.168.0.1");
        NomMachine nom = new NomMachine("machine.domaine.local");

        DnsItem item = new DnsItem(ip, nom);

        assertEquals(ip, item.getAdresseIP());
        assertEquals(nom, item.getNomMachine());
    }

    @Test
    void adresseIPNullDoitEtreRefusee() {
        NomMachine nom = new NomMachine("machine.domaine.local");

        assertThrows(
                IllegalArgumentException.class,
                () -> new DnsItem(null, nom));
    }

    @Test
    void nomMachineNullDoitEtreRefuse() {
        AdresseIP ip = new AdresseIP("192.168.0.1");

        assertThrows(
                IllegalArgumentException.class,
                () -> new DnsItem(ip, null));
    }

    @Test
    void deuxAssociationsIdentiquesDoiventEtreEgales() {
        DnsItem item1 = new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("machine.domaine.local"));

        DnsItem item2 = new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("machine.domaine.local"));

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void deuxAssociationsDifferentesNeDoiventPasEtreEgales() {
        DnsItem item1 = new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("machine.domaine.local"));

        DnsItem item2 = new DnsItem(
                new AdresseIP("192.168.0.2"),
                new NomMachine("machine.domaine.local"));

        assertNotEquals(item1, item2);
    }

    @Test
    void toStringDoitRetournerAssociation() {
        DnsItem item = new DnsItem(
                new AdresseIP("192.168.0.1"),
                new NomMachine("machine.domaine.local"));

        assertEquals(
                "machine.domaine.local 192.168.0.1",
                item.toString());
    }
}