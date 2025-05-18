package polytext.modele.stockage;

import java.io.*;
import java.util.*;
import polytext.modele.RegexData;

public class Stockeur
{
	private File fichier;

	public Stockeur(File fichier)
	{
		this.fichier = fichier;
	}

	// Structure interne pour stocker une ligne
	public static class LigneStockee
	{
		public int id;
		public RegexData data;

		public LigneStockee(int id, RegexData data)
		{
			this.id = id;
			this.data = data;
		}
	}

	// Lecture complète du fichier
	public List<LigneStockee> lire()
	{
		List<LigneStockee> lignes = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(fichier)))
		{
			String ligne;
			while ((ligne = br.readLine()) != null)
			{
				String[] parts = ligne.split("\t", -1);
				if (parts.length != 5) continue;

				int id = Integer.parseInt(parts[0]);
				RegexData data = fromTSV(ligne);

				lignes.add(new LigneStockee(id, data));
			}
		}
		catch (IOException e) { e.printStackTrace(); }

		return lignes;
	}

	// Écriture complète (remplace le contenu)
	public void ecrire(List<LigneStockee> lignes)
	{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichier)))
		{
			for (LigneStockee l : lignes)
			{
				bw.write(toTSV(l.id, l.data));
				bw.newLine();
			}
		}
		catch (IOException e) { e.printStackTrace(); }
	}

	private static String escape(String s)
	{
		if (s == null) return "";
		//return s.replace("\\", "\\\\").replace("\t", "\\t").replace("\n", "\\n").replace("\r", "\\r");
		return s;
	}

	private static String unescape(String s)
	{
		if (s == null) return "";
		//return s.replace("\\r", "\r").replace("\\n", "\n").replace("\\t", "\t").replace("\\\\", "\\");
		return s;
	}

	public static String toTSV(int id, RegexData r)
	{
		return String.join("\t",
			Integer.toString(id),
			escape(r.nom),
			escape(r.cible),
			escape(r.remplacement),
			Boolean.toString(r.estRegex)
		);
	}

	public static RegexData fromTSV(String ligne)
	{
		String[] parts = ligne.split("\t", -1);
		if (parts.length != 5) return null;

		RegexData r = new RegexData();
		r.nom = unescape(parts[1]);
		r.cible = unescape(parts[2]);
		r.remplacement = unescape(parts[3]);
		r.estRegex = Boolean.parseBoolean(parts[4]);
		return r;
	}
}
