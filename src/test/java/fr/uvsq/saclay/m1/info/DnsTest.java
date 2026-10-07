package fr.uvsq.saclay.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}