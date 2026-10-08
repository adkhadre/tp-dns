package fr.uvsq.saclay.m1.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.nio.file.Path;
import java.nio.file.Files;



/**
 Représente une base de données DNS.
 */
public class Dns {


    private final List<DnsItem> items;
    private final Path fichierDns;


    public Dns() {
        items = new ArrayList<>();
        fichierDns = chargerConfiguration();
        chargerBase();
    }
    public Dns(Path fichierDns) {
        items = new ArrayList<>();
        this.fichierDns = fichierDns;
        chargerBase();
    }
    private Path chargerConfiguration() {
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                        "Le fichier config.properties est introuvable.");
            }

            properties.load(input);

            String nomFichier = properties.getProperty("dns.file");

            if (nomFichier == null || nomFichier.isEmpty()) {
                throw new IllegalStateException(
                        "La propriété dns.file est absente.");
            }

            return Path.of(nomFichier);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Impossible de lire la configuration.", e);
        }
    }
    /**
     * Charge la base DNS depuis le fichier.
     */
    private void chargerBase() {
        try {
            if (!Files.exists(fichierDns)) {
                Files.createFile(fichierDns);
                return;
            }

            List<String> lignes = Files.readAllLines(fichierDns);

            for (String ligne : lignes) {
                if (ligne.isBlank()) {
                    continue;
                }

                String[] parties = ligne.trim().split("\\s+");

                if (parties.length != 2) {
                    throw new IllegalStateException(
                            "Ligne invalide dans la base DNS : " + ligne);
                }

                AdresseIP adresseIP = new AdresseIP(parties[0]);
                NomMachine nomMachine = new NomMachine(parties[1]);

                items.add(new DnsItem(adresseIP, nomMachine));
            }

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Impossible de lire la base DNS.", e);
        }
    }


    public int taille() {
        return items.size();
    }
    /**
      Ajoute une association DNS.
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

    /**
     * Recherche par adresse Ip
     * @param adresseIP
     * @return
     */
    public DnsItem getItem(AdresseIP adresseIP) {
        for (DnsItem item : items) {
            if (item.getAdresseIP().equals(adresseIP)) {
                return item;
            }
        }

        return null;
    }

    /**
     * Recherche par nom Machine
     * @param nomMachine
     * @return
     */
    public DnsItem getItem(NomMachine nomMachine) {
        for (DnsItem item : items) {
            if (item.getNomMachine().equals(nomMachine)) {
                return item;
            }
        }

        return null;
    }
    /**
     * Recherche les associations appartenant à un domaine.
     *
     * @param domaine le domaine recherché
     * @return la liste des associations du domaine, triées par nom de machine
     */
    public List<DnsItem> getItems(String domaine) {
        List<DnsItem> resultat = new ArrayList<>();

        for (DnsItem item : items) {
            if (item.getNomMachine().getDomaine().equals(domaine)) {
                resultat.add(item);
            }
        }

        resultat.sort(Comparator.comparing(
                item -> item.getNomMachine().getNomMachine()));

        return resultat;
    }
}