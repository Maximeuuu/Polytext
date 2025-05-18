package polytext.vue.saisie;

import javax.swing.JOptionPane;

public class OptionPaneAbout
{
	public static void afficher()
	{
		String message = String.format(
			"<html><body style='width: 300px;'>"
			+ "<h2>PolyText</h2>"
			+ "Développé par : <b><a href=\"https://github.com/Maximeuuu\">Maximeuuu</a></b><br>"
			+ "Version Java : <b>%s</b><br>"
			+ "Version de l'application : <b>1.0</b><br>"
			+ "© 2025 - Tous droits réservés.<br><br>"
			+ "<i>Petit outil de manipulation de texte avec expressions régulières.</i>"
			+ "</body></html>",
			System.getProperty("java.version")
		);

		JOptionPane.showMessageDialog(
			null, message, "À propos", JOptionPane.INFORMATION_MESSAGE
		);
	}
}
