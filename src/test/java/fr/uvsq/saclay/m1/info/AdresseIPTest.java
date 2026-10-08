package fr.uvsq.saclay.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests de la classe AdresseIP.
 */
class AdresseIPTest {

    @Test
    void adresseValideDoitEtreAcceptee() {
        AdresseIP adresse = new AdresseIP("192.168.1.1");

        assertEquals("192.168.1.1", adresse.toString());
    }

    @Test
    void adresseAvecZeroDoitEtreAcceptee() {
        AdresseIP adresse = new AdresseIP("0.0.0.0");

        assertEquals("0.0.0.0", adresse.toString());
    }

    @Test
    void adresseAvec255DoitEtreAcceptee() {
        AdresseIP adresse = new AdresseIP("255.255.255.255");

        assertEquals("255.255.255.255", adresse.toString());
    }

    @Test
    void adresseAvecMoinsDeQuatrePartiesDoitEtreRefusee() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AdresseIP("192.168.1"));
    }

    @Test
    void adresseAvecPlusDeQuatrePartiesDoitEtreRefusee() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AdresseIP("192.168.1.1.5"));
    }

    @Test
    void nombreSuperieurA255DoitEtreRefuse() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AdresseIP("192.168.1.256"));
    }

    @Test
    void nombreNegatifDoitEtreRefuse() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AdresseIP("192.168.-1.1"));
    }

    @Test
    void caractereNonNumeriqueDoitEtreRefuse() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AdresseIP("192.168.1.a"));
    }

    @Test
    void partieVideDoitEtreRefusee() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AdresseIP("192.168..1"));
    }

    @Test
    void adresseVideDoitEtreRefusee() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AdresseIP(""));
    }

    @Test
    void adresseNullDoitEtreRefusee() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AdresseIP(null));
    }

    @Test
    void deuxAdressesIdentiquesDoiventEtreEgales() {
        AdresseIP adresse1 = new AdresseIP("192.168.1.1");
        AdresseIP adresse2 = new AdresseIP("192.168.1.1");

        assertEquals(adresse1, adresse2);
        assertEquals(adresse1.hashCode(), adresse2.hashCode());
    }

    @Test
    void deuxAdressesDifferentesNeDoiventPasEtreEgales() {
        AdresseIP adresse1 = new AdresseIP("192.168.1.1");
        AdresseIP adresse2 = new AdresseIP("192.168.1.2");

        assertNotEquals(adresse1, adresse2);
    }
    @Test
    void uneAdresseIPDoitPouvoirEtreComparee() {
        AdresseIP ip1 = new AdresseIP("192.168.0.2");
        AdresseIP ip2 = new AdresseIP("192.168.0.10");

        assertTrue(ip1.compareTo(ip2) < 0);
        assertTrue(ip2.compareTo(ip1) > 0);
    }
}