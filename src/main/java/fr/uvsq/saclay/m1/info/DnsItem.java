
package fr.uvsq.saclay.m1.info;

/**
 * Représente une association entre une adresse IP
 * et un nom de machine.
 */
public class DnsItem {

    private final AdresseIP adresseIP;
    private final NomMachine nomMachine;

    /**
     * Construit une association DNS.
     */
    public DnsItem(AdresseIP adresseIP, NomMachine nomMachine) {
        if (adresseIP == null || nomMachine == null) {
            throw new IllegalArgumentException(
                    "L'adresse IP et le nom de machine ne peuvent pas être null.");
        }

        this.adresseIP = adresseIP;
        this.nomMachine = nomMachine;
    }

    public AdresseIP getAdresseIP() {
        return adresseIP;
    }

    public NomMachine getNomMachine() {
        return nomMachine;
    }

    @Override
    public String toString() {
        return nomMachine + " " + adresseIP;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof DnsItem)) {
            return false;
        }

        DnsItem autre = (DnsItem) obj;

        return adresseIP.equals(autre.adresseIP)
                && nomMachine.equals(autre.nomMachine);
    }

    @Override
    public int hashCode() {
        return 31 * adresseIP.hashCode() + nomMachine.hashCode();
    }
}