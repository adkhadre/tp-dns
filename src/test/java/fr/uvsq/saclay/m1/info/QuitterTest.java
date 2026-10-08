package fr.uvsq.saclay.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests de la commande Quitter.
 */
class QuitterTest {

    @Test
    void quitterDoitRetournerVrai() {
        Commande commande = new Quitter();

        assertEquals(true, commande.execute());
    }
}