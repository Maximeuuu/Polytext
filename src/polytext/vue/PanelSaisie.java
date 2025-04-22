package polytext.vue;

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

public class PanelSaisie extends JPanel implements DocumentListener, ActionListener, ISaisies
{
	private Controleur ctrl;

	private StyledDocument docInput;
	private StyledDocument docOutput;
	private StyledDocument docAppercu;

	private JTextField txtCible;
	private JTextField txtRemplacement;
	private JCheckBox cbRegex;

	/* ============================= */
	/* Initialisation des composants */
	/* ============================= */

	public PanelSaisie(Controleur ctrl)
	{
		super();
		this.ctrl = ctrl;

		final int NB_LIG = 1;
		final int NB_COL = 3;
		this.setLayout(new GridLayout(NB_LIG, NB_COL));

		this.add( this.creerPanelInput() );
		this.add( this.creerPanelCentre() );
		this.add( this.creerPanelOutput() );

		this.addListener();
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

		JLabel lblCible = new JLabel("Texte à chercher :");
		JLabel lblRemplacement = new JLabel("Texte de remplacement :");

		JPanel ligneCible = new JPanel();
		ligneCible.setLayout(new BoxLayout(ligneCible, BoxLayout.X_AXIS));
		ligneCible.add(lblCible);
		ligneCible.add(Box.createRigidArea(new Dimension(5, 0)));
		ligneCible.add(txtCible);

		JPanel ligneRemplacement = new JPanel();
		ligneRemplacement.setLayout(new BoxLayout(ligneRemplacement, BoxLayout.X_AXIS));
		ligneRemplacement.add(lblRemplacement);
		ligneRemplacement.add(Box.createRigidArea(new Dimension(5, 0)));
		ligneRemplacement.add(txtRemplacement);

		// partie "checkbox Regex"
		this.cbRegex = new JCheckBox("Regex");
		this.cbRegex.setMaximumSize(cbRegex.getPreferredSize());
		this.cbRegex.setToolTipText("Active le mode expression régulière.\nPermet d’utiliser des motifs (regex) dans la recherche.");

		// partie "panneau central"
		JPanel panelCentre = new JPanel();
		panelCentre.setLayout(new BoxLayout(panelCentre, BoxLayout.Y_AXIS));
		panelCentre.setBorder(new EmptyBorder(10, 10, 10, 10));

		panelCentre.add(ligneCible);
		panelCentre.add(Box.createRigidArea(new Dimension(0, 5)));

		panelCentre.add(ligneRemplacement);
		panelCentre.add(Box.createRigidArea(new Dimension(0, 5)));

		panelCentre.add(cbRegex);
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

	private void addListener()
	{
		this.docInput.addDocumentListener(this);
		this.txtCible.getDocument().addDocumentListener(this);
		this.txtRemplacement.getDocument().addDocumentListener(this);
		this.cbRegex.addActionListener(this);
	}

	/* ========================================= */
	/* Méthodes pour interractions sur interface */
	/* ========================================= */

	@Override
	public String getTexteSaisie()
	{
		try
		{
			return this.docInput.getText( 0, this.docInput.getLength() );
		}
		catch( BadLocationException ble ){ return ""; }
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
	public boolean isRegex()
	{
		return this.cbRegex.isSelected();
	}

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

		// Application du style rouge aux matches
		final SimpleAttributeSet RED_ATTR = new SimpleAttributeSet();
		StyleConstants.setForeground(RED_ATTR, Color.RED);
		for( int[] pos : positions )
		{
			int start = pos[0];
			int length = pos[1] - pos[0];
			this.docAppercu.setCharacterAttributes(start, length, RED_ATTR, false);
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
		this.ctrl.updateTextes( this );
	}

	@Override
	public void removeUpdate(DocumentEvent e)
	{
		this.ctrl.updateTextes( this );
	}

	@Override
	public void changedUpdate(DocumentEvent e){ }

	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.ctrl.updateTextes( this );
	}
}
