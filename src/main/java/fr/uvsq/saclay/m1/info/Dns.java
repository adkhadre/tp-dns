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
}