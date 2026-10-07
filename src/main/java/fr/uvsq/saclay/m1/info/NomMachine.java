package fr.uvsq.saclay.m1.info;

/**
 * Représente le nom qualifié d'une machine.
 */
public class NomMachine {

    private final String nom;


    public NomMachine(String nom) {
        if (nom == null || !nom.contains(".")) {
            throw new IllegalArgumentException(
                    "Le nom de machine doit être qualifié.");
        }
        this.nom = nom;
    }

    /**
     * Retourne le nom de la machine.
     *
     * @return le nom de la machine
     */
    public String getNomMachine() {
        int position = nom.indexOf('.');
        return nom.substring(0, position);
    }

    /**
     * Retourne le nom du domaine.
     *
     * @return le nom du domaine
     */
    public String getDomaine() {
        int position = nom.indexOf('.');
        return nom.substring(position + 1);
    }
    @Override
    public String toString() {
        return nom;
    }
    /**
      Compare ce nom de machine avec un autre objet.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof NomMachine)) {
            return false;
        }

        NomMachine autre = (NomMachine) obj;

        return nom.equals(autre.nom);
    }
    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}