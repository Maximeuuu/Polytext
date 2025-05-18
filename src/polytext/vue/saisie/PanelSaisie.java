package polytext.vue.saisie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.text.*;

import polytext.controleur.Controleur;
import polytext.vue.Outils;

public class PanelSaisie extends JPanel implements DocumentListener, ActionListener, ISaisies, IUpdates
{
	private Controleur ctrl;

	private StyledDocument docInput;
	private StyledDocument docOutput;
	private StyledDocument docAppercu;

	private JTextField txtCible;
	private JTextField txtRemplacement;
	private JRadioButton rbRegex;
	private JRadioButton rbEtendu;

	/* ============================= */
	/* Initialisation des composants */
	/* ============================= */

	public PanelSaisie(Controleur ctrl)
	{
		super();
		this.ctrl = ctrl;
		this.ctrl.setIUpdates( this );
		this.ctrl.setISaisies( this );

		this.initPanel();
		this.placerComposants();
		this.addListeners();
	}

	private void initPanel()
	{
		final int NB_LIG = 1;
		final int NB_COL = 3;
		this.setLayout(new GridLayout(NB_LIG, NB_COL));
	}

	private void placerComposants()
	{
		this.add( this.creerPanelInput() );
		this.add( this.creerPanelCentre() );
		this.add( this.creerPanelOutput() );
	}

	private JPanel creerPanelInput()
	{
		JTextPane textPaneInput = new JTextPane();
		this.docInput = textPaneInput.getStyledDocument();
		JScrollPane scrollInput = new JScrollPane(textPaneInput);

		JPanel panelInput = new JPanel(new BorderLayout());
		panelInput.setBorder(BorderFactory.createTitledBorder("Entrée"));
		panelInput.add(scrollInput, BorderLayout.CENTER);

		return panelInput;
	}

	private JPanel creerPanelCentre()
	{
		// partie "appercu"
		JTextPane textPaneAppercu = new JTextPane();
		textPaneAppercu.setEditable(false);
		this.docAppercu = textPaneAppercu.getStyledDocument();
		JScrollPane scrollAppercu = new JScrollPane(textPaneAppercu);
		scrollAppercu.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		scrollAppercu.setPreferredSize(new Dimension(10, 100));
		scrollAppercu.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		scrollAppercu.setMinimumSize(new Dimension(10, 50));

		JPanel panelAppercu = new JPanel(new BorderLayout());
		panelAppercu.setBorder(BorderFactory.createTitledBorder("Aperçu"));
		panelAppercu.add(scrollAppercu, BorderLayout.CENTER);

		// partie "champs de saisie"
		this.txtCible = new JTextField();
		this.txtRemplacement = new JTextField();
		
		Dimension fieldSize = new Dimension(Integer.MAX_VALUE, txtCible.getPreferredSize().height);
		txtCible.setMaximumSize(fieldSize);
		txtRemplacement.setMaximumSize(fieldSize);

		JPanel ligneCible = Outils.creerPanelLabelComposant( "Texte à chercher :", this.txtCible );
		JPanel ligneRemplacement = Outils.creerPanelLabelComposant( "Texte de remplacement :", this.txtRemplacement );

		// partie "radio button"
		ButtonGroup rbGroupe = new ButtonGroup();

		this.rbEtendu = new JRadioButton("Etendu");
		this.rbEtendu.setMaximumSize(rbEtendu.getPreferredSize());
		this.rbEtendu.setToolTipText("Active le mode expression étendue.\nPermet d’utiliser les caracteres d'echapement ('\\n', '\\t', ...).");

		this.rbRegex = new JRadioButton("Regex");
		this.rbRegex.setMaximumSize(rbRegex.getPreferredSize());
		this.rbRegex.setToolTipText("Active le mode expression régulière.\nPermet d’utiliser des motifs (regex).");

		rbGroupe.add(rbEtendu);
		rbGroupe.add(rbRegex);
		rbGroupe.setSelected(rbEtendu.getModel(), true);

		JPanel ligneRb = new JPanel();
		ligneRb.setLayout(new BoxLayout(ligneRb, BoxLayout.X_AXIS));
		ligneRb.add(rbEtendu);
		ligneRb.add(Box.createRigidArea(new Dimension(5, 0)));
		ligneRb.add(rbRegex);

		// partie "panneau central"
		JPanel panelCentre = new JPanel();
		panelCentre.setLayout(new BoxLayout(panelCentre, BoxLayout.Y_AXIS));
		panelCentre.setBorder(new EmptyBorder(10, 10, 10, 10));

		panelCentre.add(ligneCible);
		panelCentre.add(Box.createRigidArea(new Dimension(0, 5)));

		panelCentre.add(ligneRemplacement);
		panelCentre.add(Box.createRigidArea(new Dimension(0, 5)));

		panelCentre.add(ligneRb);
		panelCentre.add(Box.createVerticalGlue());
		panelCentre.add(Box.createRigidArea(new Dimension(0, 10)));
		panelCentre.add(panelAppercu);

		return panelCentre;
	}

	private JPanel creerPanelOutput()
	{
		JTextPane textPaneOutput = new JTextPane();
		textPaneOutput.setEditable(false);
		this.docOutput = textPaneOutput.getStyledDocument();
		JScrollPane scrollOutput = new JScrollPane(textPaneOutput);

		JPanel panelOutput = new JPanel(new BorderLayout());
		panelOutput.setBorder(BorderFactory.createTitledBorder("Sortie"));
		panelOutput.add(scrollOutput, BorderLayout.CENTER);

		return panelOutput;
	}

	private void addListeners()
	{
		this.docInput.addDocumentListener(this);
		this.txtCible.getDocument().addDocumentListener(this);
		this.txtRemplacement.getDocument().addDocumentListener(this);
		this.rbEtendu.addActionListener(this);
		this.rbRegex.addActionListener(this);
	}

	private String getTexteSaisie()
	{
		try
		{
			return this.docInput.getText( 0, this.docInput.getLength() );
		}
		catch( BadLocationException ble ){ return ""; }
	}

	/* ========================================= */
	/* Méthodes pour interractions sur interface */
	/* ========================================= */

	@Override
	public void setTexteAppercu( String texte )
	{
		try
		{
			this.docAppercu.remove(0, this.docAppercu.getLength());
			this.docAppercu.insertString(0, texte, null);
		}
		catch( BadLocationException ble ){ }
	}

	@Override
	public void applyStyleTexteAppercu( List<int[]> positions )
	{
		// Reset du style
		final SimpleAttributeSet NORMAL_ATTR = new SimpleAttributeSet();
		this.docAppercu.setCharacterAttributes(0, docAppercu.getLength(), NORMAL_ATTR, true);

		// Application du style aux matches
		final SimpleAttributeSet MATCH_ATTR = new SimpleAttributeSet();
		StyleConstants.setForeground(MATCH_ATTR, Color.WHITE);
		StyleConstants.setBackground( MATCH_ATTR, Color.DARK_GRAY);

		for( int[] pos : positions )
		{
			int start = pos[0];
			int length = pos[1] - pos[0];
			this.docAppercu.setCharacterAttributes(start, length, MATCH_ATTR, false);
		}
	}

	@Override
	public void setTexteOutput( String texte )
	{
		try
		{
			this.docOutput.remove(0, this.docOutput.getLength());
			this.docOutput.insertString(0, texte, null);
		}
		catch( BadLocationException ble ){ }
	}

	/* ================================ */
	/* Méthodes d'écoute des composants */
	/* ================================ */

	@Override
	public void insertUpdate(DocumentEvent e)
	{
		this.updateTextes( );
	}

	@Override
	public void removeUpdate(DocumentEvent e)
	{
		this.updateTextes();
	}

	@Override
	public void changedUpdate(DocumentEvent e){ }

	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.updateTextes();
	}

	private void updateTextes()
	{
		this.ctrl.appliquerRemplacement( this.txtCible.getText(), this.txtRemplacement.getText(), this.getTexteSaisie(), this.rbRegex.isSelected() );
	}

	@Override
	public void reset()
	{
		try
		{
			this.txtCible.setText("");
			this.txtRemplacement.setText("");
			
			this.docOutput.remove(0, this.docOutput.getLength());
			this.docAppercu.remove(0, this.docAppercu.getLength());
			this.docInput.remove(0, this.docInput.getLength());
		}
		catch( BadLocationException e ){}
	}

	@Override
	public void setCible(String cible)
	{
		this.txtCible.setText( cible );
	}

	@Override
	public void setRemplacement(String remplacement)
	{
		this.txtRemplacement.setText( remplacement );
	}

	@Override
	public void setEstRegex(boolean estRegex)
	{
		if( estRegex ) this.rbRegex.setSelected(true);
		else this.rbEtendu.setSelected(true);
	}

	@Override
	public String getCible()
	{
		return this.txtCible.getText();
	}

	@Override
	public String getRemplacement()
	{
		return this.txtRemplacement.getText();
	}

	@Override
	public boolean getEstRegex()
	{
		return this.rbRegex.isSelected();
	}
}
