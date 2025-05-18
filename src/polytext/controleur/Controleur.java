package polytext.controleur;

import polytext.modele.Analyseur;
import polytext.modele.RegexData;
import polytext.modele.Remplaceur;
import polytext.modele.StockageRegex;
import polytext.modele.Analyseur.ResultatAnalyseur;
import polytext.modele.Remplaceur.ResultatRemplaceur;
import polytext.modele.stockage.RepositoryRegexData;
import polytext.vue.regex.FrameCreerRegex;
import polytext.vue.regex.FrameSelectionnerRegex;
import polytext.vue.regex.IRegexCreation;
import polytext.vue.regex.ISelectionRegex;
import polytext.vue.saisie.ISaisies;
import polytext.vue.saisie.IStats;
import polytext.vue.saisie.IUpdates;
import polytext.vue.saisie.MenuPolytext;
import polytext.vue.saisie.OptionPaneAbout;

public class Controleur
{
	private IUpdates updates;
	private ISaisies saisies;
	private IStats stats;
	private IRegexCreation regexCreation;
	private ISelectionRegex selectionRegex;

	public Controleur()
	{
		RepositoryRegexData.update();
	}

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

	public void setIRegexCreation( IRegexCreation regexCreation )
	{
		this.regexCreation = regexCreation;
	}

	public void setISelectionRegex( ISelectionRegex selectionRegex )
	{
		this.selectionRegex = selectionRegex;
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
			case MenuPolytext.I_MENU_PREFERENCES:
				System.out.println("TODO:");
			case MenuPolytext.I_MENU_EXIT:
				this.sauvegarder();
				System.exit(0); break;
			case MenuPolytext.I_REGEX_NEW:
				new FrameCreerRegex( this ); //TODO: probleme pour empecher plusieurs fenetres d'etre creer (car on ne connait pas la fenetre initiale pour faire un setRelativeLocation() )
				this.regexCreation.setCible( this.saisies.getCible() );
				this.regexCreation.setRemplacement( this.saisies.getRemplacement() );
				this.regexCreation.setMode( this.saisies.getEstRegex() );
				break;
			case MenuPolytext.I_REGEX_SELECT:
				new FrameSelectionnerRegex( this ); //TODO: probleme pour empecher plusieurs fenetres d'etre creer (car on ne connait pas la fenetre initiale pour faire un setRelativeLocation() )
				this.selectionRegex.setListeRegex( RepositoryRegexData.getTousLesNomsDisponibles() );
				break;
			default:
				break;
		}
	}

	public void creerRegex( String nom, String cible, String remplacement, boolean estRegex )
	{
		RegexData rd = new RegexData();
		rd.nom = nom;
		rd.cible = cible;
		rd.remplacement = remplacement;
		rd.estRegex = estRegex;
		RepositoryRegexData.ajouter(rd);
	}

	public void selectionnerRegex( int index )
	{
		RegexData regexData = RepositoryRegexData.getByIndex(index);
		this.saisies.setCible( regexData.cible );
		this.saisies.setRemplacement( regexData.remplacement );
		this.saisies.setEstRegex( regexData.estRegex );
	}

	public void supprimerRegex( int index )
	{
		Integer id = RepositoryRegexData.getIdByIndex(index);
		if( id != null ) //TODO: probleme quand on veut supprimer un objet qui vient d'etre créé car il n'a pas d'id pour l'identifier
			RepositoryRegexData.supprimer( id );
	}

	public void sauvegarder()
	{
		RepositoryRegexData.update();
	}
}
