package polytext.vue.regex;

import java.awt.Dimension;

import javax.swing.JFrame;

import polytext.controleur.Controleur;

public class FrameSelectionnerRegex extends JFrame
{
	public FrameSelectionnerRegex( Controleur controleur )
	{
		super();
		super.add( new PanelSelectionnerRegex( controleur ) );
		super.setSize( new Dimension(500,200) );
		super.setLocationRelativeTo(null);
		super.setVisible(true);
	}
}