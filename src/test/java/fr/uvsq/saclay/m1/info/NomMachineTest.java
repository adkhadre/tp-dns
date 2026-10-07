package fr.uvsq.saclay.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests de la classe NomMachine.
 */
class NomMachineTest {

    @Test
    void nomQualifieDoitEtreAccepte() {
        NomMachine nom = new NomMachine("www.example.com");

        assertEquals("www.example.com", nom.toString());
    }

    @Test
    void nomQualifieAvecPlusieursNiveauxDoitEtreAccepte() {
        NomMachine nom = new NomMachine("machine.info.uvsq.fr");

        assertEquals("machine.info.uvsq.fr", nom.toString());
    }

    @Test
    void nomSansDomaineDoitEtreRefuse() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new NomMachine("serveur1"));
    }

    @Test
    void nomVideDoitEtreRefuse() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new NomMachine(""));
    }

    @Test
    void nomNullDoitEtreRefuse() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new NomMachine(null));
    }

    @Test
    void deuxNomsIdentiquesDoiventEtreEgaux() {
        NomMachine nom1 = new NomMachine("www.example.com");
        NomMachine nom2 = new NomMachine("www.example.com");

        assertEquals(nom1, nom2);
        assertEquals(nom1.hashCode(), nom2.hashCode());
    }

    @Test
    void deuxNomsDifferentsNeDoiventPasEtreEgaux() {
        NomMachine nom1 = new NomMachine("www.example.com");
        NomMachine nom2 = new NomMachine("mail.example.com");

        assertNotEquals(nom1, nom2);
    }
    @Test
    void nomMachineDoitEtreCorrectementExtrait() {
        NomMachine nom = new NomMachine("www.uvsq.fr");

        assertEquals("www", nom.getNomMachine());
    }

    @Test
    void domaineDoitEtreCorrectementExtrait() {
        NomMachine nom = new NomMachine("www.uvsq.fr");

        assertEquals("uvsq.fr", nom.getDomaine());
    }
}