package polytext;

import polytext.controleur.Controleur;
import polytext.vue.FrameSaisie;

public class Main
{
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Controleur ctrl = new Controleur();
		FrameSaisie vue = new FrameSaisie( ctrl );
	}
}
