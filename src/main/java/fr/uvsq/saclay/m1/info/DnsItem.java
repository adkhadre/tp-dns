package fr.uvsq.saclay.m1.info;

/**
 * Représente une association entre une adresse IP et un nom de machine,
 * c'est-à-dire une ligne de la base DNS.
 *
 * La classe est immuable : les deux champs sont renseignés à la construction
 * et ne peuvent plus être modifiés ensuite.
 */
public class DnsItem {

    /** L'adresse IP de la machine. */
    private final AdresseIP adresseIP;

    /** Le nom qualifié de la machine (ex. "www.uvsq.fr"). */
    private final NomMachine nomMachine;

    /**
     * Construit une association DNS.
     *
     * @param adresseIP  l'adresse IP de la machine
     * @param nomMachine le nom de la machine
     * @throws IllegalArgumentException si l'adresse IP ou le nom de machine est {@code null}
     */
    public DnsItem(AdresseIP adresseIP, NomMachine nomMachine) {
        // Les deux valeurs sont obligatoires : un DnsItem n'a pas de sens
        // sans adresse ou sans nom
        if (adresseIP == null || nomMachine == null) {
            throw new IllegalArgumentException(
                    "L'adresse IP et le nom de machine ne peuvent pas être null.");
        }

        this.adresseIP = adresseIP;
        this.nomMachine = nomMachine;
    }

    /**
     * Retourne l'adresse IP de l'association.
     *
     * @return l'adresse IP
     */
    public AdresseIP getAdresseIP() {
        return adresseIP;
    }

    /**
     * Retourne le nom de machine de l'association.
     *
     * @return le nom de machine
     */
    public NomMachine getNomMachine() {
        return nomMachine;
    }

    /**
     * Retourne l'association sous la forme {@code "nomMachine adresseIP"},
     * qui est le format d'une ligne du fichier de la base DNS.
     *
     * @return la représentation textuelle de l'association
     */
    @Override
    public String toString() {
        return nomMachine + " " + adresseIP;
    }

    /**
     * Deux associations sont égales si elles ont la même adresse IP
     * et le même nom de machine.
     *
     * @param obj l'objet à comparer
     * @return {@code true} si {@code obj} est un DnsItem de même valeur
     */
    @Override
    public boolean equals(Object obj) {
        // Même référence : forcément égaux
        if (this == obj) {
            return true;
        }

        // Un objet d'un autre type (ou null) ne peut pas être égal
        if (!(obj instanceof DnsItem)) {
            return false;
        }

        DnsItem autre = (DnsItem) obj;

        // Égalité sur les deux champs
        return adresseIP.equals(autre.adresseIP)
                && nomMachine.equals(autre.nomMachine);
    }

    /**
     * Retourne un code de hachage cohérent:
     * il est calculé à partir des deux mêmes champs.
     *
     * @return le code de hachage de l'association
     */
    @Override
    public int hashCode() {
        // 31 est un multiplicateur classique pour combiner les hash des champs
        return 31 * adresseIP.hashCode() + nomMachine.hashCode();
    }
}