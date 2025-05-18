package polytext.vue.regex;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import polytext.controleur.Controleur;
import polytext.vue.Outils;

public class PanelSelectionnerRegex extends JPanel implements ISelectionRegex, ActionListener
{
	private JComboBox<String> lstRegex;
	private JButton btnModifier;
	private JButton btnSupprimer;
	private JButton btnSelectionner;

	private Controleur ctrl;

	public PanelSelectionnerRegex( Controleur controleur )
	{
		super();
		this.ctrl = controleur;
		this.ctrl.setISelectionRegex( this );
		this.initPanel();
		this.initComposants();
		this.placerComposants();
	}

	public void initPanel()
	{
		super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		super.setBorder(new EmptyBorder(10, 10, 10, 10));
	}

	public void initComposants()
	{
		this.lstRegex = new JComboBox<>();

		this.btnModifier = new JButton("Modifier");
		this.btnModifier.addActionListener( this );

		this.btnSupprimer = new JButton("Supprimer");
		this.btnSupprimer.addActionListener( this );

		this.btnSelectionner = new JButton("Sélectionner");
		this.btnSelectionner.addActionListener( this );
	}

	public void placerComposants()
	{
		super.add( this.lstRegex );

		JPanel panel;
		panel = new JPanel();
		panel.setLayout( new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.add( this.btnSelectionner );
		panel.add( this.btnModifier );
		panel.add( this.btnSupprimer );

		super.add( panel );
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		int index = this.lstRegex.getSelectedIndex();

		if( e.getSource() == this.btnSelectionner )
		{
			this.ctrl.selectionnerRegex(index);
		}
		else if( e.getSource() == this.btnModifier )
		{
			//this.ctrl.modifierRegex();
			System.out.println("TODO:");
		}
		else if( e.getSource() == this.btnSupprimer )
		{
			this.ctrl.supprimerRegex(index);
		}

		Outils.fermerFenetreParent( this );
	}

	@Override
	public void setListeRegex(List<String> liste)
	{
		this.lstRegex.removeAllItems();

		for (String item : liste)
			this.lstRegex.addItem(item);

		if (!liste.isEmpty())
			this.lstRegex.setSelectedIndex(0);
	}

}
