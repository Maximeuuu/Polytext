package polytext.controleur;

import polytext.modele.Remplaceur;
import polytext.modele.Remplaceur.ResultatRemplaceur;
import polytext.vue.ISaisies;

public class Controleur
{
	public void updateTextes( ISaisies saisies )
	{
		String cible = saisies.getCible();
		String remplacement = saisies.getRemplacement();
		String texte = saisies.getTexteSaisie();
		boolean estRegex = saisies.isRegex();

		saisies.setTexteAppercu(texte);

		Remplaceur remplaceur = new Remplaceur(cible, remplacement, estRegex);
		ResultatRemplaceur rr = remplaceur.remplacer(texte);

		// Définir les couleurs après coup, en dehors du cycle d’événement
		javax.swing.SwingUtilities.invokeLater(() -> {
			saisies.applyStyleTexteAppercu( rr.getPositions() );
		});

		saisies.setTexteOutput( rr.getTexte() );
	}
}
