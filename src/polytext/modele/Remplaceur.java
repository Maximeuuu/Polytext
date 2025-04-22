package polytext.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Remplaceur
{
	private String motif;
	private String remplacement;

	public Remplaceur( String motif, String remplacement, boolean estRegex )
	{
		this.motif = motif;

		if( estRegex )
		{
			this.remplacement = remplacement;
		}
		else
		{
			this.remplacement = Matcher.quoteReplacement( remplacement );
		}
	}

	public ResultatRemplaceur remplacer( String texte )
	{
		String resultat = "";
		List<int[]> positions = new ArrayList<int[]>();

		try
		{
			Pattern pattern = Pattern.compile( this.motif );
			Matcher matcher = pattern.matcher( texte );

			StringBuffer sb = new StringBuffer();

			while( matcher.find() )
			{
				positions.add( new int[]{matcher.start(), matcher.end()} );
				matcher.appendReplacement( sb, this.remplacement );
			}
			matcher.appendTail( sb );

			resultat = sb.toString();
		}
		catch( PatternSyntaxException e )
		{
			positions.clear();
			resultat = texte;
		}

		return new ResultatRemplaceur( resultat, positions );
	}

	public static class ResultatRemplaceur
	{
		private String texteModif;
		private List<int[]> positionsModif;

		public ResultatRemplaceur( String texteModif, List<int[]> positionsModif )
		{
			this.texteModif = texteModif;
			this.positionsModif = positionsModif;
		}

		public String getTexte()
		{
			return this.texteModif;
		}

		public List<int[]> getPositions()
		{
			return this.positionsModif;
		}
	}
}
