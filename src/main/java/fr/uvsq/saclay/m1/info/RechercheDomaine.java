package fr.uvsq.saclay.m1.info;

import java.util.List;

/**
 * Commande permettant de rechercher les machines d'un domaine.
 */
public class RechercheDomaine implements Commande {

    private final Dns dns;
    private final String domaine;
    private final boolean triParAdresseIP;

    /**
     * Construit une commande de recherche par domaine.
     *
     * @param dns la base DNS
     * @param domaine le domaine recherché
     * @param triParAdresseIP indique si le tri se fait par IP
     */
    public RechercheDomaine(
            Dns dns, String domaine, boolean triParAdresseIP) {
        this.dns = dns;
        this.domaine = domaine;
        this.triParAdresseIP = triParAdresseIP;
    }

    /**
     * Recherche les machines du domaine.
     *
     * @return la liste des associations du domaine
     */
    @Override
    public Object execute() {
        List<DnsItem> resultats =
                dns.getItems(domaine, triParAdresseIP);

        return resultats;
    }
}