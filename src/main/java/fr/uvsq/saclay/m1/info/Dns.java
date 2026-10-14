package fr.uvsq.saclay.m1.info;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

/**
 * Représente une base de données DNS : un ensemble d'associations (adresse IP, nom de machine)
 * stockées en mémoire et persistées dans un fichier texte.
 *
 * <p>Chaque ligne du fichier a la forme {@code nomMachine adresseIp}. Une même adresse IP ou un
 * même nom de machine ne peut apparaître qu'une seule fois dans la base.
 */
public class Dns {

  /** Les associations DNS chargées en mémoire. */
  private final List<DnsItem> items;

  /** Le fichier dans lequel la base est lue et sauvegardée. */
  private final Path fichierDns;

  /**
   * Construit une base DNS en lisant le chemin du fichier dans le fichier de configuration {@code
   * config.properties} (propriété {@code dns.file}).
   *
   * @throws IllegalStateException si la configuration ou la base est illisible
   */
  public Dns() {
    items = new ArrayList<>();
    fichierDns = chargerConfiguration();
    chargerBase();
  }

  /**
   * Construit une base DNS à partir d'un fichier donné. Utile notamment pour les tests (fichier
   * temporaire).
   *
   * @param fichierDns le chemin du fichier contenant la base
   * @throws IllegalStateException si le fichier est illisible ou mal formé
   */
  public Dns(Path fichierDns) {
    items = new ArrayList<>();
    this.fichierDns = fichierDns;
    chargerBase();
  }

  /**
   * Lit le fichier {@code config.properties} (placé dans les ressources) et retourne le chemin du
   * fichier DNS indiqué par la propriété {@code dns.file}.
   *
   * @return le chemin du fichier de la base DNS
   * @throws IllegalStateException si le fichier de configuration est introuvable, illisible, ou si
   *     la propriété {@code dns.file} est absente ou vide
   */
  private Path chargerConfiguration() {
    Properties properties = new Properties();

    // try-with-resources : le flux est fermé automatiquement
    try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {

      // getResourceAsStream renvoie null si la ressource n'existe pas
      if (input == null) {
        throw new IllegalStateException("Le fichier config.properties est introuvable.");
      }

      properties.load(input);

      String nomFichier = properties.getProperty("dns.file");
      if (nomFichier == null || nomFichier.isEmpty()) {
        throw new IllegalStateException("La propriété dns.file est absente.");
      }

      return Path.of(nomFichier);

    } catch (IOException e) {
      throw new IllegalStateException("Impossible de lire la configuration.", e);
    }
  }

  /**
   * Charge la base DNS depuis le fichier. Si le fichier n'existe pas, il est créé vide.
   *
   * @throws IllegalStateException si le fichier est illisible ou contient une ligne invalide
   */
  private void chargerBase() {
    try {
      // Première utilisation : on crée un fichier vide, rien à charger
      if (!Files.exists(fichierDns)) {
        Files.createFile(fichierDns);
        return;
      }

      List<String> lignes = Files.readAllLines(fichierDns);

      for (String ligne : lignes) {
        // On ignore les lignes vides
        if (ligne.isBlank()) {
          continue;
        }

        // Découpage sur un ou plusieurs espaces : [nomMachine, adresseIp]
        String[] parties = ligne.trim().split("\\s+");

        if (parties.length != 2) {
          throw new IllegalStateException("Ligne invalide dans la base DNS : " + ligne);
        }

        // Les constructeurs valident les valeurs
        // et lèvent une exception si elles sont incorrectes
        NomMachine nomMachine = new NomMachine(parties[0]);
        AdresseIp adresseIp = new AdresseIp(parties[1]);

        items.add(new DnsItem(adresseIp, nomMachine));
      }

    } catch (IOException e) {
      throw new IllegalStateException("Impossible de lire la base DNS.", e);
    }
  }

  /**
   * Retourne le nombre d'associations contenues dans la base.
   *
   * @return le nombre d'associations
   */
  public int taille() {
    return items.size();
  }

  /**
   * Ajoute une association DNS et la sauvegarde dans le fichier.
   *
   * @param item l'association à ajouter
   * @throws IllegalArgumentException si l'association est null, ou si son adresse IP ou son nom de
   *     machine existe déjà
   * @throws IllegalStateException si la sauvegarde dans le fichier échoue
   */
  public void addItem(DnsItem item) {
    if (item == null) {
      throw new IllegalArgumentException("L'association DNS ne peut pas être null.");
    }

    // Vérification d'unicité : ni l'IP ni le nom ne doivent déjà exister
    for (DnsItem element : items) {
      if (element.getAdresseIp().equals(item.getAdresseIp())) {
        throw new IllegalArgumentException("Cette adresse IP existe déjà !");
      }

      if (element.getNomMachine().equals(item.getNomMachine())) {
        throw new IllegalArgumentException("Ce nom de machine existe déjà !");
      }
    }

    items.add(item);

    // Sauvegarde : on réécrit tout le fichier avec le contenu de la liste
    try {
      List<String> lignes = new ArrayList<>();

      for (DnsItem element : items) {
        lignes.add(element.toString());
      }

      Files.write(fichierDns, lignes);
    } catch (IOException e) {
      throw new IllegalStateException("Impossible de sauvegarder la base DNS.", e);
    }
  }

  /**
   * Recherche une association à partir de son adresse IP.
   *
   * @param adresseIp l'adresse IP recherchée
   * @return l'association correspondante, ou {@code null} si aucune n'est trouvée
   */
  public DnsItem getItem(AdresseIp adresseIp) {
    // Parcours linéaire de la liste
    for (DnsItem item : items) {
      if (item.getAdresseIp().equals(adresseIp)) {
        return item;
      }
    }

    return null;
  }

  /**
   * Recherche une association à partir de son nom de machine.
   *
   * @param nomMachine le nom de machine recherché
   * @return l'association correspondante, ou {@code null} si aucune n'est trouvée
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
   * Recherche les associations appartenant à un domaine, triées par nom de machine.
   *
   * @param domaine le domaine recherché
   * @return les associations du domaine, triées par nom de machine
   */
  public List<DnsItem> getItems(String domaine) {
    // Par défaut : tri par nom de machine
    return getItems(domaine, false);
  }

  /**
   * Recherche les associations appartenant à un domaine.
   *
   * @param domaine le domaine recherché
   * @param triParAdresseIp {@code true} pour trier par adresse IP, {@code false} pour trier par nom
   *     de machine
   * @return les associations du domaine, triées selon le critère choisi
   */
  public List<DnsItem> getItems(String domaine, boolean triParAdresseIp) {

    List<DnsItem> resultat = new ArrayList<>();

    // Filtrage : on ne garde que les machines du domaine demandé
    for (DnsItem item : items) {
      if (item.getNomMachine().getDomaine().equals(domaine)) {
        resultat.add(item);
      }
    }

    if (triParAdresseIp) {
      // Tri selon l'ordre numérique des adresses IP
      resultat.sort((item1, item2) -> item1.getAdresseIp().compareTo(item2.getAdresseIp()));
    } else {
      // Tri alphabétique sur le nom de la machine
      resultat.sort(Comparator.comparing(item -> item.getNomMachine().getNomMachine()));
    }

    return resultat;
  }
}