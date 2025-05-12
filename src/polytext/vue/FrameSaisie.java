
package polytext.vue;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import polytext.controleur.Controleur;

public class FrameSaisie extends JFrame
{
	public FrameSaisie( Controleur ctrl )
	{
		super.setTitle( "Editeur" );
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLayout( new BorderLayout() );
		super.add( new PanelSaisie( ctrl ), BorderLayout.CENTER );
		super.add( new PanelStatistiques(ctrl), BorderLayout.SOUTH );
		super.setSize(1500, 700);
		super.setLocationRelativeTo( null );
		super.setJMenuBar( new MenuPolytext(ctrl) );
		super.setVisible(true);
	}
}
