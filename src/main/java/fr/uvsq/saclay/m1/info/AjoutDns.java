package fr.uvsq.saclay.m1.info;

/**
 * Commande permettant d'ajouter une association dans la base DNS.
 */
public class AjoutDns implements Commande {

    private final Dns dns;
    private final DnsItem item;

    /**
     * Construit une commande d'ajout.
     *
     * @param dns la base DNS
     * @param item l'association à ajouter
     */
    public AjoutDns(Dns dns, DnsItem item) {
        this.dns = dns;
        this.item = item;
    }

    /**
     * Ajoute l'association dans la base DNS.
     *
     * @return l'association ajoutée
     */
    @Override
    public Object execute() {
        dns.addItem(item);
        return item;
    }
}