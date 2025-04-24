
package polytext.vue;

import java.awt.MenuBar;

import javax.swing.JFrame;

import polytext.controleur.Controleur;

public class FrameSaisie extends JFrame
{
	public FrameSaisie( Controleur ctrl )
	{
		super.setTitle( "Editeur" );
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.add( new PanelSaisie( ctrl ) );
		super.setSize(1500, 700);
		super.setLocationRelativeTo( null );
		super.setJMenuBar( new MenuPolytext(ctrl) );
		super.setVisible(true);
	}
}
