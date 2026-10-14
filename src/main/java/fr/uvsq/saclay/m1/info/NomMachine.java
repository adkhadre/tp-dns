package fr.uvsq.saclay.m1.info;

/**
 * Représente le nom qualifié d'une machine, c'est-à-dire un nom contenant au moins un point, par
 * exemple "www.uvsq.fr". Le nom se décompose en deux parties : le nom de la machine proprement dit
 * (avant le premier point, ici "www") et le domaine (après le premier point, ici "uvsq.fr"). La
 * classe est immuable : le nom est validé à la construction et ne peut plus être modifié ensuite.
 */
public class NomMachine {

  /** Le nom qualifié complet (ex. "www.uvsq.fr"), toujours valide. */
  private final String nom;

  /**
   * Construit un nom de machine à partir d'un nom qualifié.
   *
   * @param nom le nom qualifié de la machine (ex. "www.uvsq.fr")
   * @throws IllegalArgumentException si le nom est null ou ne contient pas de point (nom non
   *     qualifié)
   */
  public NomMachine(String nom) {
    // Un nom sans point n'est pas qualifié : impossible d'en extraire un domaine
    if (nom == null || !nom.contains(".")) {
      throw new IllegalArgumentException("Le nom de machine doit être qualifié.");
    }
    this.nom = nom;
  }

  /**
   * Retourne le nom de la machine sans son domaine, c'est-à-dire la partie située avant le premier
   * point.
   *
   * <p>Exemple : pour "www.uvsq.fr", retourne "www".
   *
   * @return le nom de la machine (sans le domaine)
   */
  public String getNomMachine() {
    // Position du premier point : il sépare la machine du domaine
    int position = nom.indexOf('.');
    return nom.substring(0, position);
  }

  /**
   * Retourne le nom du domaine, c'est-à-dire la partie située après le premier point.
   *
   * <p>Exemple : pour "www.uvsq.fr", retourne "uvsq.fr".
   *
   * @return le nom du domaine
   */
  public String getDomaine() {
    int position = nom.indexOf('.');
    // +1 pour ne pas inclure le point lui-même
    return nom.substring(position + 1);
  }

  /**
   * Retourne le nom qualifié complet de la machine.
   *
   * @return le nom qualifié (ex. "www.uvsq.fr")
   */
  @Override
  public String toString() {
    return nom;
  }

  /**
   * Compare ce nom de machine avec un autre objet. Deux noms sont égaux si leurs noms qualifiés
   * sont identiques (la comparaison est sensible à la casse).
   *
   * @param obj l'objet à comparer
   * @return true si obj est un NomMachine de même valeur
   */
  @Override
  public boolean equals(Object obj) {
    // Même référence : forcément égaux
    if (this == obj) {
      return true;
    }

    // Un objet d'un autre type (ou null) ne peut pas être égal
    if (!(obj instanceof NomMachine)) {
      return false;
    }

    NomMachine autre = (NomMachine) obj;

    return nom.equals(autre.nom);
  }

  /**
   * Retourne un code de hachage cohérent: deux noms égaux ont le même hashCode.
   *
   * @return le code de hachage du nom
   */
  @Override
  public int hashCode() {
    return nom.hashCode();
  }
}
