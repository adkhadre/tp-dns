package fr.uvsq.saclay.m1.info;

/** Application principale du DNS. */
public class DnsApp {

  private final DnsTui tui;

  /**
   * Construit l'application.
   *
   * @param tui interface utilisateur
   */
  public DnsApp(DnsTui tui) {
    this.tui = tui;
  }

  /**
   * Lance l'application.
   *
   * @param args arguments de la ligne de commande
   */
  public static void main(String[] args) {
    Dns dns = new Dns();
    DnsTui tui = new DnsTui(dns);
    DnsApp app = new DnsApp(tui);

    app.run();
  }

  /** Lance l'application. */
  public void run() {
    tui.afficheAide();
    boolean continuer = true;

    while (continuer) {
      Commande commande = tui.nextCommande();

      if (commande == null) {
        continue;
      }

      try {
        Object resultat = commande.execute();
        tui.affiche(resultat);
      } catch (IllegalArgumentException e) {
        System.out.println("ERREUR : " + e.getMessage());
      }

      if (commande instanceof Quitter) {
        continuer = false;
      }
    }
  }
}
