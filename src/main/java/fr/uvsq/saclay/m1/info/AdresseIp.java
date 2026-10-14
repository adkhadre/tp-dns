package fr.uvsq.saclay.m1.info;

/**
 * Représente une adresse IPv4 (quatre entiers entre 0 et 255 séparés par des points, par exemple
 * "192.168.0.1").
 *
 * <p>La classe est immuable : l'adresse est validée à la construction et ne peut plus être modifiée
 * ensuite.
 */
public class AdresseIp {

  private final String adresse;

  /**
   * Construit une adresse IP après avoir vérifié sa validité.
   *
   * @param adresse l'adresse IPv4 sous forme de texte (ex. "10.0.0.1")
   * @throws IllegalArgumentException si l'adresse est nulle ou mal formée
   */
  public AdresseIp(String adresse) {
    // On refuse de créer l'objet si l'adresse est invalide :
    // ainsi, toute instance de AdresseIp est garantie valide.
    if (!estValide(adresse)) {
      throw new IllegalArgumentException("Adresse IP invalide : " + adresse);
    }
    this.adresse = adresse;
  }

  /**
   * Vérifie qu'une chaîne est une adresse IPv4 valide, c'est-à-dire composée de exactement 4
   * nombres entiers compris entre 0 et 255, séparés par des points.
   *
   * @param adresse la chaîne à vérifier
   * @return {@code true} si l'adresse est valide, {@code false} sinon (y compris si elle est {@code
   *     null})
   */
  private boolean estValide(String adresse) {
    // Une adresse null n'est pas valide
    if (adresse == null) {
      return false;
    }

    // Découpage selon le point ("\\." car "." est un caractère spécial en regex).
    // Le paramètre -1 conserve les parties vides finales :
    // "1.2.3.4." donne 5 parties (dont une vide) et sera donc rejetée.
    String[] parties = adresse.split("\\.", -1);

    // Une IPv4 comporte exactement 4 parties
    if (parties.length != 4) {
      return false;
    }

    for (String partie : parties) {
      try {
        int nombre = Integer.parseInt(partie);

        // Chaque partie doit être comprise entre 0 et 255
        if (nombre < 0 || nombre > 255) {
          return false;
        }
      } catch (NumberFormatException e) {
        // La partie n'est pas un entier (lettres, chaîne vide, etc.)
        return false;
      }
    }

    return true;
  }

  /**
   * Compare cette adresse IP avec une autre, partie par partie (de gauche à droite) selon l'ordre
   * numérique.
   *
   * @param autre l'autre adresse IP à comparer
   * @return un nombre négatif si cette adresse est inférieure à {@code autre}, zéro si elles sont
   *     égales, un nombre positif si elle est supérieure
   */
  public int compareTo(AdresseIp autre) {
    String[] partiesCetteAdresse = adresse.split("\\.");
    String[] partiesAutreAdresse = autre.adresse.split("\\.");

    for (int i = 0; i < 4; i++) {
      // Conversion en entier pour comparer numériquement
      // (en texte, "9" serait considéré comme supérieur à "10")
      int partieCetteAdresse = Integer.parseInt(partiesCetteAdresse[i]);
      int partieAutreAdresse = Integer.parseInt(partiesAutreAdresse[i]);

      // Dès que deux parties diffèrent, le résultat est déterminé
      if (partieCetteAdresse != partieAutreAdresse) {
        return Integer.compare(partieCetteAdresse, partieAutreAdresse);
      }
    }

    // Les 4 parties sont identiques
    return 0;
  }

  /**
   * Retourne l'adresse IP sous forme de texte.
   *
   * @return l'adresse au format "a.b.c.d"
   */
  @Override
  public String toString() {
    return adresse;
  }

  /**
   * Deux adresses IP sont égales si leurs représentations textuelles sont identiques.
   *
   * @param obj l'objet à comparer
   * @return {@code true} si {@code obj} est une AdresseIp de même valeur
   */
  @Override
  public boolean equals(Object obj) {
    // Même référence : forcément égales
    if (this == obj) {
      return true;
    }

    // Un objet d'un autre type (ou null) ne peut pas être égal
    if (!(obj instanceof AdresseIp)) {
      return false;
    }

    AdresseIp autre = (AdresseIp) obj;

    return adresse.equals(autre.adresse);
  }

  /**
   * Retourne un code de hachage cohérent : deux adresses égales ont le même hashCode.
   *
   * @return le code de hachage de l'adresse
   */
  @Override
  public int hashCode() {
    return adresse.hashCode();
  }
}