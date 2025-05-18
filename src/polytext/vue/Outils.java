package polytext.vue;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Outils
{
	public static JPanel creerPanelLabelComposant( String libelle, Component... composants )
	{
		JPanel ligne = new JPanel();
		ligne.setLayout(new BoxLayout(ligne, BoxLayout.X_AXIS));
		ligne.add( new JLabel( libelle ) );
		for( Component composant : composants )
		{
			ligne.add(Box.createRigidArea(new Dimension(5, 0)));
			ligne.add( composant );
		}
		return ligne;
	}

	public static boolean fermerFenetreParent( Component composant )
	{
		Component parent = composant.getParent();
		while( !(parent instanceof JFrame) || parent == null )
		{
			parent = parent.getParent();
		}

		try
		{
			((JFrame)parent).dispose();
			return true;
		}
		catch( Exception exception )
		{
			return false;
		}
	}
}
