package polytext.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Remplaceur
{
	private String motif;
	private String remplacement;

	public Remplaceur( String motif, String remplacement, boolean estRegex )
	{
		if( estRegex )
		{
			this.motif = motif;
			this.remplacement = Remplaceur.desechapper(remplacement);
		}
		else
		{
			this.motif = Pattern.quote(motif);
			this.remplacement = Matcher.quoteReplacement( remplacement );
		}
	}

	public static String desechapper(String texte)
	{
		StringBuilder sb = new StringBuilder();
		for( int i = 0; i < texte.length(); i++ )
		{
			char c = texte.charAt(i);
			if( c == '\\' && i + 1 < texte.length() )
			{
				char suivant = texte.charAt(i + 1);
				switch (suivant)
				{
					case 'n': sb.append('\n'); i++; break;
					case 't': sb.append('\t'); i++; break;
					case 'r': sb.append('\r'); i++; break;
					//case '\\': sb.append('\\'); i++; break; //cas à ne pas mettre
					case '"': sb.append('\"'); i++; break;
					default: sb.append(c); break;
				}
			}
			else
			{
				sb.append(c);
			}
		}
		return sb.toString();
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
		catch( IndexOutOfBoundsException | IllegalArgumentException e )
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
