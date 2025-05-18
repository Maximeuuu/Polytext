package polytext.modele;

public class RegexData
{
	public String nom;
	public String cible;
	public String remplacement;
	public boolean estRegex;

	public String toString()
	{
		return nom + ", " + cible + ", " + remplacement + ", " + estRegex;
	}
}
