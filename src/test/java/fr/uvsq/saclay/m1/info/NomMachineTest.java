package fr.uvsq.saclay.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests de la classe NomMachine.
 */
class NomMachineTest {

    @Test
    void nomValideDoitEtreAccepte() {
        NomMachine nom = new NomMachine("www.example.com");

        assertEquals("www.example.com", nom.toString());
    }

    @Test
    void nomSimpleDoitEtreAccepte() {
        NomMachine nom = new NomMachine("serveur1");

        assertEquals("serveur1", nom.toString());
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
}