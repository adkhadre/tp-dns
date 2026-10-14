package fr.uvsq.saclay.m1.info;

/**
 * Commande de recherche du nom de machine associé à une adresse IP.
 * Le résultat de l'exécution est le NomMachine correspondant, ou  null si
 * l'adresse n'existe pas dans la base DNS.
 */

public class RechercheNom implements Commande {

  private final Dns dns;
  private final AdresseIp adresseIp;

  /**
   * Construit une commande de recherche de nom.
   *
   * @param dns la base DNS
   * @param adresseIp l'adresse IP recherchée
   */
  public RechercheNom(Dns dns, AdresseIp adresseIp) {
    this.dns = dns;
    this.adresseIp = adresseIp;
  }

  /**
   * Recherche le nom correspondant à l'adresse IP.
   *
   * @return l'association DNS trouvée
   */
  @Override
  public Object execute() {
    DnsItem item = dns.getItem(adresseIp);

    if (item == null) {
      return null;
    }

    return item.getNomMachine();
  }
}
