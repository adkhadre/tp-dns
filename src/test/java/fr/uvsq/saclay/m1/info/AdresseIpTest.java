package fr.uvsq.saclay.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests de la classe AdresseIP. */
class AdresseIpTest {

  @Test
  void adresseValideDoitEtreAcceptee() {
    AdresseIp adresse = new AdresseIp("192.168.1.1");

    assertEquals("192.168.1.1", adresse.toString());
  }

  @Test
  void adresseAvecZeroDoitEtreAcceptee() {
    AdresseIp adresse = new AdresseIp("0.0.0.0");

    assertEquals("0.0.0.0", adresse.toString());
  }

  @Test
  void adresseAvec255DoitEtreAcceptee() {
    AdresseIp adresse = new AdresseIp("255.255.255.255");

    assertEquals("255.255.255.255", adresse.toString());
  }

  @Test
  void adresseAvecMoinsDeQuatrePartiesDoitEtreRefusee() {
    assertThrows(IllegalArgumentException.class, () -> new AdresseIp("192.168.1"));
  }

  @Test
  void adresseAvecPlusDeQuatrePartiesDoitEtreRefusee() {
    assertThrows(IllegalArgumentException.class, () -> new AdresseIp("192.168.1.1.5"));
  }

  @Test
  void nombreSuperieurA255DoitEtreRefuse() {
    assertThrows(IllegalArgumentException.class, () -> new AdresseIp("192.168.1.256"));
  }

  @Test
  void nombreNegatifDoitEtreRefuse() {
    assertThrows(IllegalArgumentException.class, () -> new AdresseIp("192.168.-1.1"));
  }

  @Test
  void caractereNonNumeriqueDoitEtreRefuse() {
    assertThrows(IllegalArgumentException.class, () -> new AdresseIp("192.168.1.a"));
  }

  @Test
  void partieVideDoitEtreRefusee() {
    assertThrows(IllegalArgumentException.class, () -> new AdresseIp("192.168..1"));
  }

  @Test
  void adresseVideDoitEtreRefusee() {
    assertThrows(IllegalArgumentException.class, () -> new AdresseIp(""));
  }

  @Test
  void adresseNullDoitEtreRefusee() {
    assertThrows(IllegalArgumentException.class, () -> new AdresseIp(null));
  }

  @Test
  void deuxAdressesIdentiquesDoiventEtreEgales() {
    AdresseIp adresse1 = new AdresseIp("192.168.1.1");
    AdresseIp adresse2 = new AdresseIp("192.168.1.1");

    assertEquals(adresse1, adresse2);
    assertEquals(adresse1.hashCode(), adresse2.hashCode());
  }

  @Test
  void deuxAdressesDifferentesNeDoiventPasEtreEgales() {
    AdresseIp adresse1 = new AdresseIp("192.168.1.1");
    AdresseIp adresse2 = new AdresseIp("192.168.1.2");

    assertNotEquals(adresse1, adresse2);
  }

  @Test
  void uneAdresseIpDoitPouvoirEtreComparee() {
    AdresseIp ip1 = new AdresseIp("192.168.0.2");
    AdresseIp ip2 = new AdresseIp("192.168.0.10");

    assertTrue(ip1.compareTo(ip2) < 0);
    assertTrue(ip2.compareTo(ip1) > 0);
  }
}
