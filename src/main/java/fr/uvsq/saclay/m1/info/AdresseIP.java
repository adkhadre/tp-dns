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

        if (parties.length != 4) {
            return false;
        }

        for (String partie : parties) {
            try {
                int nombre = Integer.parseInt(partie);

                if (nombre < 0 || nombre > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return adresse;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof AdresseIP)) {
            return false;
        }

        AdresseIP autre = (AdresseIP) obj;

        return adresse.equals(autre.adresse);
    }

    @Override
    public int hashCode() {
        return adresse.hashCode();
    }
}