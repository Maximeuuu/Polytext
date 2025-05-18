package polytext.vue.saisie;

import java.util.ArrayList;
import java.util.List;

import java.awt.Image;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/** Barre de menu
  * @author : Maximeuuu
  * @version : 3.1 - 24/04/25
  * @date : 06/12/2023
  */
public abstract class AbstractMenuBarre extends JMenuBar implements ActionListener
{
	public static final String REPERTOIRE_IMAGES = "./images/";

	// Identifiants des éléments du menu
	protected static final String MENU			= "M";
	protected static final String ITEM			= "I";
	protected static final String SEPARATEUR	= "S";
	protected static final String SOUS_MENU		= "m";
	protected static final String ITEM_SM		= "i";
	protected static final String SEPARATEUR_SM	= "s";

	// Position des éléments du menu dans le "modeleBar"
	protected static final int TYPE = 0;
	protected static final int NAME = 1;
	protected static final int ICON = 2;
	protected static final int CHAR = 3;
	protected static final int KEYS = 4;

	/*---------------------------------------*/
	/*             CONSTRUCTEUR              */
	/*---------------------------------------*/ 

	public AbstractMenuBarre( )
	{
		//Initialisation
		super ( );
		this.initComposants ( );
	}

	/**
	 * Initialisation des composants de la barre
	 */
	private final void initComposants ( )
	{
		JMenu  menuEnCreation     = null;
		JMenu  sousMenuEnCreation = null;
		String hotkey;

		// Format du MenuBar
		String[][] modeleBar = this.getModeleBar ( );

		// Générer les composants
		for ( int cptLig = 0; cptLig < modeleBar.length; cptLig++ )
		{
			String[] ligne = modeleBar[cptLig];

			switch ( ligne[TYPE] )
			{
				case MENU:
					menuEnCreation = this.creerMenu ( ligne[NAME], ligne[ICON], ligne[CHAR] );
					this.add ( menuEnCreation );
					break;

				case SOUS_MENU:
					sousMenuEnCreation = this.creerMenu ( ligne[NAME], ligne[ICON], ligne[CHAR] );
					menuEnCreation.add ( sousMenuEnCreation );
					break;

				case ITEM:
					if ( ligne.length-1 == KEYS ) { hotkey = ligne[KEYS]; }
					else { hotkey = null; }
					menuEnCreation.add ( this.creerMenui ( ligne[NAME], ligne[ICON], ligne[CHAR], hotkey ) );
					break;

				case ITEM_SM:
					if ( ligne.length-1 == KEYS ) { hotkey = ligne[KEYS]; }
					else { hotkey = null; }
					sousMenuEnCreation.add ( this.creerMenui ( ligne[NAME], ligne[ICON], ligne[CHAR], hotkey ) );
					break;

				case SEPARATEUR:
					menuEnCreation.addSeparator ( );
					break;

				case SEPARATEUR_SM:
					sousMenuEnCreation.addSeparator ( );
					break;
			}
		}
	}


	/*---------------------------------------*/
	/*         Creation des composants       */
	/*---------------------------------------*/ 

	/**
	 * Simplification de la création d'un élément Menu (correspond au premier niveau)
	 */
	private final JMenu creerMenu ( String nom, String image, String mnemo )
	{
		JMenu menuTmp = new JMenu ( nom );

		if ( !image.equals ( "" ) )
		{
			menuTmp.setIcon ( genererIcone( image, 20 ) );
		}

		menuTmp.setMnemonic ( mnemo.charAt( 0 ) );
		return menuTmp;
	}

	/**
	 * Simplification de la création d'un élément MenuItem (correspond au sous-niveau)
	 */
	private final JMenuItem creerMenui ( String nom, String image, String mnemo, String hotkey )
	{
		JMenuItem menui = new JMenuItem ( nom );

		if ( !image.equals ( "" ) )
		{
			menui.setIcon ( genererIcone ( image, 20 ) );
		}

		if ( !mnemo.equals( "" ) )
		{
			menui.setMnemonic ( mnemo.charAt ( 0 ) );
		}

		if ( hotkey != null )
		{
			menui.setAccelerator( getEquivalentKeyStroke ( hotkey ) );
		}

		menui.addActionListener ( this );
		return menui;
	}


	/*---------------------------------------*/
	/*        GESTION DES EVENEMENTS         */
	/*---------------------------------------*/ 

	/**
	 * Détection des évènements sur le JMenuBar
	 * @see allerVersPage( String nom )
	 */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) instanceof JMenuItem )
		{
			String nom = ( ( JMenuItem ) e.getSource () ).getText ( );
			this.allerVersPage ( nom );
		}
	}

	/**
	 * Actions à réaliser lors des selections
	 * @see actionPerformed( ActionEvent e )
	 */
	protected abstract void allerVersPage ( String nom );


	/*---------------------------------------*/
	/*         METHODES UTILITAIRES          */
	/*---------------------------------------*/ 

	/**
	 * Méthode utilitaire permettant de convertir une chaine de caractère en KeyStroke pour les raccourcis
	 * @see creerMenui( String nom, String image, String mnemo, String hotkey )
	 * @see getModeleBar( )
	 */
	private final static KeyStroke getEquivalentKeyStroke ( String hotkey )
	{
		String[] setTmp = hotkey.split ( "\\+" );
		String sTmp="";

		for ( int cpt=0; cpt<setTmp.length-1; cpt++ )
		{
			sTmp += setTmp[cpt].toLowerCase ( ) + " ";
		}
		sTmp += setTmp[ setTmp.length-1 ];

		return KeyStroke.getKeyStroke ( sTmp );
	}

	/**
	 * Méthode utilitaire permettant de générer une image redimensionnée
	 * @see creerMenu( String nom, String image, String mnemo )
	 * @see creerMenui( String nom, String image, String mnemo, String hotkey )
	 */
	private final static ImageIcon genererIcone ( String image, int taille )
	{
		ImageIcon icone = new ImageIcon ( REPERTOIRE_IMAGES + image );
		Image imgDim = icone.getImage ( ).getScaledInstance ( taille, taille, Image.SCALE_SMOOTH );
		return new ImageIcon ( imgDim );
	}


	/*---------------------------------------*/
	/*       AJOUT DES MENUS ET ITEMS        */
	/*---------------------------------------*/ 

	/**
	 * Méthode qui permet de récupérer toutes les options du MenuBar
	 * @see getModeleBar( )
	 */
	public final String[] getOptionsBarre ( )
	{
		List<String> options = new ArrayList<> ( );

		for ( String[] ligne : this.getModeleBar ( ) )
		{
			if ( ligne[TYPE].equals ( ITEM ) || ligne[TYPE].equals ( ITEM_SM ) )
			{
				options.add ( ligne[NAME] );
			}
		}

		return options.toArray ( new String[options.size ( )] );
	}

	/**
	 * Méthodes abstraite pour ajuster les éléments du MenuBar ;
	 * Format : TYPE, NAME, ICON, CHAR, KEYS ;
	 * Types : MENU ITEM SEPARATEUR SOUS_MENU ITEM_SM SEPARATEUR_SM
	 * @see getOptionsBarre( )
	 * @see getModeleBar( )
	 */
	public abstract String[][] getModeleBar ( );

	/**
	 * Exemple de format pour la configuration du menu barre
	 * @see getModeleBar()
	 */
	public static final String[][] getExempleModeleBar ( )
	{
		return new String[][] {
			{	MENU, 				"Fichier",			"",					"F"				},
			{		ITEM, 			"Accueil",			"",					"A", "CTRL+H"	},
			{		SEPARATEUR																},
			{		ITEM, 			"Quitter",			"",					"Q", "ALT+F4"	},
			{	MENU, 				"Edition",			"",					"E", "CTRL+E"	},
			{		ITEM, 			"Paramètres",		"",					"P"				},
			{		SEPARATEUR																},
			{		SOUS_MENU, 		"Sous menu",		"",					"P"				},
			{			ITEM_SM,	"item 1",			"",					"1"				},
			{			ITEM_SM,	"item 2",			"",					"2" 			},
			{			ITEM_SM,	"item 3",			"",					"3" 			},
			{	MENU, 				"Affichage",		"",					"A"				},
		};
	}
}