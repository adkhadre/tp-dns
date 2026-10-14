package fr.uvsq.saclay.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Tests de la commande RechercheIP. */
class RechercheIpTest {

  @TempDir Path dossierTemporaire;

  @Test
  void rechercheIpDoitRetournerLaBonneAdresseIp() {
    Path fichierDns = dossierTemporaire.resolve("dns.txt");
    Dns dns = new Dns(fichierDns);

    DnsItem item =
        new DnsItem(new AdresseIp("192.168.0.1"), new NomMachine("machine.domaine.local"));

    dns.addItem(item);

    Commande commande = new RechercheIp(dns, new NomMachine("machine.domaine.local"));

    assertEquals(new AdresseIp("192.168.0.1"), commande.execute());
  }
}
