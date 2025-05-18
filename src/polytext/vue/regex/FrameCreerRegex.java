package polytext.vue.regex;

import java.awt.Dimension;

import javax.swing.JFrame;

import polytext.controleur.Controleur;

public class FrameCreerRegex extends JFrame
{
	public FrameCreerRegex( Controleur controleur )
	{
		super();
		super.add( new PanelCreerRegex( controleur ) );
		super.setSize( new Dimension(500,200) );
		super.setLocationRelativeTo(null);
		super.setVisible(true);
	}
}