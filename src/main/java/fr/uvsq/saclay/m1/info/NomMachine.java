package fr.uvsq.saclay.m1.info;

/**
 * Représente le nom d'une machine.
 */
public class NomMachine {

    private final String nom;

    public NomMachine(String nom) {
        if (nom == null || nom.isEmpty()) {
            throw new IllegalArgumentException(
                    "Le nom de machine ne peut pas être vide.");
        }
        this.nom = nom;
    }
    @Override
    public String toString() {
        return nom;
    }
}