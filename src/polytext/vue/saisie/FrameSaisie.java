
package polytext.vue.saisie;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import polytext.controleur.Controleur;

public class FrameSaisie extends JFrame implements WindowListener
{
	private Controleur ctrl;

	public FrameSaisie( Controleur ctrl )
	{
		super();
		this.ctrl = ctrl;
		super.setTitle( "Editeur" );
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLayout( new BorderLayout() );
		super.add( new PanelSaisie( ctrl ), BorderLayout.CENTER );
		super.add( new PanelStatistiques(ctrl), BorderLayout.SOUTH );
		super.setSize(1500, 700);
		super.setLocationRelativeTo( null );
		super.setJMenuBar( new MenuPolytext(ctrl) );
		super.setVisible(true);
		super.addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e)
	{
		this.ctrl.sauvegarder();
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		this.ctrl.sauvegarder();
	}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
}
