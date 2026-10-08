package fr.uvsq.saclay.m1.info;


public class RechercheNom implements Commande {

    private final Dns dns;
    private final AdresseIP adresseIP;

    /**
     * Construit une commande de recherche de nom.
     *
     * @param dns la base DNS
     * @param adresseIP l'adresse IP recherchée
     */
    public RechercheNom(Dns dns, AdresseIP adresseIP) {
        this.dns = dns;
        this.adresseIP = adresseIP;
    }

    /**
     * Recherche le nom correspondant à l'adresse IP.
     *
     * @return l'association DNS trouvée
     */
    @Override
    public Object execute() {
        return dns.getItem(adresseIP);
    }
}