package polytext.modele;

import polytext.modele.Remplaceur.ResultatRemplaceur;

public class Analyseur
{
	private String texteOrig;
	private ResultatRemplaceur resultatRemplaceur;

	public Analyseur( String texteOrig, ResultatRemplaceur resultatRemplaceur )
	{
		this.texteOrig = texteOrig;
		this.resultatRemplaceur = resultatRemplaceur;
	}

	public ResultatAnalyseur analyser()
	{
		long nbCaracteres = this.texteOrig.length();
		long nbMots = compterMots(this.texteOrig);
		long nbMatchs = this.resultatRemplaceur.getPositions().size();

		ResultatAnalyseur ra = new ResultatAnalyseur(nbCaracteres, nbMots, nbMatchs);
		return ra;
	}

	private long compterMots(String texte)
	{
		if (texte == null || texte.trim().isEmpty())
		{
			return 0;
		}
		String[] mots = texte.trim().split("\\s+");
		return mots.length;
	}
	

	public static class ResultatAnalyseur
	{
		private long nbCara;
		private long nbMots;
		private long nbMatchs;

		public ResultatAnalyseur( long nbCara, long nbMots, long nbMatchs )
		{
			this.nbCara = nbCara;
			this.nbMots = nbMots;
			this.nbMatchs = nbMatchs;
		}

		public long getNbCaracteres()
		{
			return this.nbCara;
		}

		public long getNbMots()
		{
			return this.nbMots;
		}

		public long getNbMatchs()
		{
			return this.nbMatchs;
		}
	}
}
