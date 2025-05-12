package polytext.controleur;

import polytext.modele.Analyseur;
import polytext.modele.Remplaceur;
import polytext.modele.Analyseur.ResultatAnalyseur;
import polytext.modele.Remplaceur.ResultatRemplaceur;
import polytext.vue.ISaisies;
import polytext.vue.IStats;
import polytext.vue.IUpdates;
import polytext.vue.MenuPolytext;
import polytext.vue.OptionPaneAbout;

public class Controleur
{
	private IUpdates updates;
	private ISaisies saisies;
	private IStats stats;

	public void setIUpdates( IUpdates updates )
	{
		this.updates = updates;
	}

	public void setISaisies( ISaisies saisies )
	{
		this.saisies = saisies;
	}

	public void setIStats( IStats stats )
	{
		this.stats = stats;
	}

	public void appliquerRemplacement( String cible, String remplacement, String texte, boolean estRegex )
	{
		this.saisies.setTexteAppercu(texte);

		Remplaceur remplaceur = new Remplaceur(cible, remplacement, estRegex);
		ResultatRemplaceur rr = remplaceur.remplacer(texte);

		// Définir les couleurs après coup, en dehors du cycle d’événement
		javax.swing.SwingUtilities.invokeLater(() -> {
			this.saisies.applyStyleTexteAppercu( rr.getPositions() );
		});

		this.saisies.setTexteOutput( rr.getTexte() );
		this.updatesStatistiques( texte, rr );
	}

	private void updatesStatistiques( String texte, ResultatRemplaceur rr )
	{
		Analyseur analyseur = new Analyseur(texte, rr);
		ResultatAnalyseur ra = analyseur.analyser();
		this.stats.setNbCaracteres( ra.getNbCaracteres() );
		this.stats.setNbMots( ra.getNbMots() );
		this.stats.setNbMatchs( ra.getNbMatchs() );
	}

	public void elementMenuActive( String element )
	{
		switch( element )
		{
			case MenuPolytext.I_MENU_ABOUT:
				OptionPaneAbout.afficher(); break;
			case MenuPolytext.I_MENU_RESET:
				this.updates.reset(); break;
			case MenuPolytext.I_MENU_EXIT:
				System.exit(0); break;
			default:
				break;
		}
	}
}
