package fr.uvsq.saclay.m1.info;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une base de données DNS.
 */
public class Dns {

    private final List<DnsItem> items;


    public Dns() {
        items = new ArrayList<>();
    }

    public int taille() {
        return items.size();
    }
    /**
     * Ajoute une association DNS.
     *
     * @param item l'association à ajouter
     * @throws IllegalArgumentException si l'association existe déjà
     */
    public void addItem(DnsItem item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "L'association DNS ne peut pas être null.");
        }

        for (DnsItem element : items) {
            if (element.getAdresseIP().equals(item.getAdresseIP())) {
                throw new IllegalArgumentException(
                        "Cette adresse IP existe déjà.");
            }

            if (element.getNomMachine().equals(item.getNomMachine())) {
                throw new IllegalArgumentException(
                        "Ce nom de machine existe déjà.");
            }
        }

        items.add(item);
    }
}