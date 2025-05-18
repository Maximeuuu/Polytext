package polytext.vue.saisie;

import polytext.controleur.Controleur;

public class MenuPolytext extends AbstractMenuBarre
{
	private Controleur ctrl;

	public static final String I_MENU_PREFERENCES = "Préférences";
	public static final String I_MENU_ABOUT = "À propos";
	public static final String I_MENU_RESET = "Réinitialiser";
	public static final String I_MENU_EXIT = "Quitter";
	public static final String I_REGEX_NEW = "Nouveau";
	public static final String I_REGEX_SELECT = "Sélectionner";

	public MenuPolytext( Controleur ctrl )
	{
		super();
		this.ctrl = ctrl;
	}

	@Override
	protected void allerVersPage(String nom)
	{
		this.ctrl.elementMenuActive( nom );
	}

	@Override
	public String[][] getModeleBar ( )
	{
		return new String[][] {
			{	MENU, 			"Menu",				"",		"M", "CTRL+M"	},
			{		ITEM, 		I_MENU_PREFERENCES,	"",	"", 				},
			{		ITEM, 		I_MENU_ABOUT,		"",	"", 				},
			{		SEPARATEUR												},
			{		ITEM, 		I_MENU_RESET,		"",	"", 				},
			{		ITEM, 		I_MENU_EXIT,		"",	"", "ALT+F4"		},
			/*{	MENU, 			"Fichiers",			"",		"F", "CTRL+F"	},
			{		ITEM, 		"Importer",			"",	""					},
			{		ITEM, 		"Exporter",			"",	""					},*/
			{	MENU, 			"Regex",			"",		"R", "CTRL+R"	},
			{		ITEM, 		I_REGEX_NEW,		"",	""					},
			{		ITEM, 		I_REGEX_SELECT,		"",	""					}
		};
	}
}
