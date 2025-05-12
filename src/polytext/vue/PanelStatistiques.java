package polytext.vue;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import polytext.controleur.Controleur;

public class PanelStatistiques extends JPanel implements IStats
{
	private JLabel lblNbCara;
	private JLabel lblNbMots;
	private JLabel lblNbMatchs;

	public PanelStatistiques(Controleur ctrl)
	{
		super();
		ctrl.setIStats( this );
		this.setLayout( new BorderLayout() );
		this.add( this.creerPanelStats(), BorderLayout.CENTER );
	}

	private JPanel creerPanelStats()
	{
		this.lblNbCara = new JLabel(" ");
		this.lblNbMots = new JLabel(" ");
		this.lblNbMatchs = new JLabel(" ");

		JPanel panelStats = new JPanel();
		panelStats.setBorder( new EmptyBorder(new Insets(15, 5, 15, 5)));

		panelStats.setLayout(new BoxLayout(panelStats, BoxLayout.Y_AXIS));
		panelStats.add(this.lblNbCara);
		panelStats.add(this.lblNbMots);
		panelStats.add(this.lblNbMatchs);
		
		return panelStats;
	}

	@Override
	public void setNbCaracteres(long nb)
	{
		this.lblNbCara.setText("Nb caracteres : " + nb);
	}

	@Override
	public void setNbMots(long nb)
	{
		this.lblNbMots.setText("Nb mots : " + nb);
	}

	@Override
	public void setNbMatchs(long nb)
	{
		this.lblNbMatchs.setText("Nb matchs : " + nb);
	}
}
