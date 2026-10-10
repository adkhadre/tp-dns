package fr.uvsq.saclay.m1.info;

/**
 * Application principale du DNS.
 */
public class DnsApp {

    private final DnsTUI tui;

    /**
     * Construit l'application.
     *
     * @param tui interface utilisateur
     */
    public DnsApp(DnsTUI tui) {
        this.tui = tui;
    }

    /**
     * Lance l'application.
     */
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

    /**
     * Lance l'application.
     *
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        Dns dns = new Dns();
        DnsTUI tui = new DnsTUI(dns);
        DnsApp app = new DnsApp(tui);

        app.run();
    }
}