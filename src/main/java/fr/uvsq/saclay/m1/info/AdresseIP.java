package fr.uvsq.saclay.m1.info;

/**
 * Représente une adresse IPv4.
 */
public class AdresseIP {

    private final String adresse;

    /**
     * Construit une adresse IP.
     *
     * @param adresse l'adresse IPv4
     */
    public AdresseIP(String adresse) {
        if (!estValide(adresse)) {
            throw new IllegalArgumentException("Adresse IP invalide : " + adresse);
        }
        this.adresse = adresse;
    }
    private boolean estValide(String adresse) {
        if (adresse == null) {
            return false;
        }

        String[] parties = adresse.split("\\.", -1);

        return parties.length == 4;
    }
}