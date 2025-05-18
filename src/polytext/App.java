package polytext;

import polytext.controleur.Controleur;
import polytext.vue.saisie.FrameSaisie;

public class App
{
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Controleur ctrl = new Controleur();
		FrameSaisie vue = new FrameSaisie( ctrl );
	}
}
