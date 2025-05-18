package polytext.vue.regex;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import polytext.controleur.Controleur;
import polytext.vue.Outils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelCreerRegex extends JPanel implements IRegexCreation, ActionListener
{
	private JTextField txtNom;
	private JTextField txtCible;
	private JTextField txtRemplacement;
	private JRadioButton rbRegex;
	private JRadioButton rbEtendu;
	private JButton btnCreer;

	private Controleur ctrl;

	public PanelCreerRegex( Controleur controleur )
	{
		super();
		this.ctrl = controleur;
		this.ctrl.setIRegexCreation( this );
		this.initPanel();
		this.initComposants();
		this.placerComposants();
	}

	private void initPanel()
	{
		this.setLayout( new BorderLayout() );
	}

	private void initComposants()
	{
		this.txtNom = new JTextField();
		this.txtCible = new JTextField();
		this.txtRemplacement = new JTextField();

		this.rbEtendu = new JRadioButton("Etendu");
		this.rbEtendu.setToolTipText("Active le mode expression étendue.\nPermet d’utiliser les caracteres d'echapement ('\\n', '\\t', ...).");

		this.rbRegex = new JRadioButton("Regex");
		this.rbRegex.setToolTipText("Active le mode expression régulière.\nPermet d’utiliser des motifs (regex).");
		
		ButtonGroup rbGroupe = new ButtonGroup();
		rbGroupe.add(rbEtendu);
		rbGroupe.add(rbRegex);
		rbGroupe.setSelected(rbEtendu.getModel(), true);

		this.btnCreer = new JButton("Enregistrer");
		this.btnCreer.addActionListener( this );
	}

	private void placerComposants()
	{
		this.add( this.creerPanelEdition(), BorderLayout.CENTER );
		this.add( this.btnCreer, BorderLayout.SOUTH );
	}

	private JPanel creerPanelEdition()
	{
		JPanel panelEdition = new JPanel();
		panelEdition.setLayout(new BoxLayout(panelEdition, BoxLayout.Y_AXIS));
		panelEdition.setBorder(new EmptyBorder(10, 10, 10, 10));

		// partie TextField
		Dimension fieldSize = new Dimension(Integer.MAX_VALUE, txtCible.getPreferredSize().height);
		this.txtNom.setMaximumSize(fieldSize);
		this.txtCible.setMaximumSize(fieldSize);
		this.txtRemplacement.setMaximumSize(fieldSize);

		JPanel ligneNom = Outils.creerPanelLabelComposant( "Nom :", this.txtNom );
		JPanel ligneCible = Outils.creerPanelLabelComposant( "Texte à chercher :", this.txtCible );
		JPanel ligneRemplacement = Outils.creerPanelLabelComposant( "Texte de remplacement :", this.txtRemplacement );

		// partie RadioButton
		this.rbEtendu.setMaximumSize(rbEtendu.getPreferredSize());
		this.rbRegex.setMaximumSize(rbRegex.getPreferredSize());

		JPanel ligneModes = Outils.creerPanelLabelComposant( "Mode :", this.rbEtendu, this.rbRegex );

		// placement dans le panel
		panelEdition.add(ligneNom);
		panelEdition.add(Box.createRigidArea(new Dimension(0, 5)));
		panelEdition.add(ligneCible);
		panelEdition.add(Box.createRigidArea(new Dimension(0, 5)));
		panelEdition.add(ligneRemplacement);
		panelEdition.add(Box.createRigidArea(new Dimension(0, 5)));
		panelEdition.add(ligneModes);

		return panelEdition;
	}

	@Override
	public void setCible(String cible)
	{
		this.txtCible.setText(cible);
	}

	@Override
	public void setRemplacement(String remplacement)
	{
		this.txtRemplacement.setText(remplacement);
	}

	@Override
	public void setMode(boolean estRegex)
	{
		if( estRegex )
			this.rbRegex.setSelected( true );
		else
			this.rbEtendu.setSelected( true );
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.ctrl.creerRegex( this.txtNom.getText(), this.txtCible.getText(), this.txtRemplacement.getText(), this.rbRegex.isSelected());
		Outils.fermerFenetreParent( this );
	}
}
