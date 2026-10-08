package fr.uvsq.saclay.m1.info;

/**
 * Commande permettant de rechercher une adresse IP
 * à partir du nom d'une machine.
 */
public class RechercheIP implements Commande {

    private final Dns dns;
    private final NomMachine nomMachine;

    /**
     * Construit une commande de recherche d'adresse IP.
     *
     * @param dns la base DNS
     * @param nomMachine le nom de machine recherché
     */
    public RechercheIP(Dns dns, NomMachine nomMachine) {
        this.dns = dns;
        this.nomMachine = nomMachine;
    }

    /**
     * Recherche l'adresse IP correspondant au nom.
     *
     * @return l'association DNS trouvée
     */
    @Override
    public Object execute() {
        return dns.getItem(nomMachine);
    }
}