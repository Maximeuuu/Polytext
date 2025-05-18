package polytext.modele.stockage;

import java.util.Map.Entry;
import java.io.File;
import java.util.*;

import polytext.modele.RegexData;

public class RepositoryRegexData
{
	private static Map<RegexData, RegexMetadata> mapRegexData;

	/*public static void update()
	{
		if( mapRegexData == null ) mapRegexData = new HashMap<>();
		Stockeur stockeur = new Stockeur( new File("data/ConfigRegex.tsv") );
		//parcourir la map et appeler les methodes necessaires
	}*/

	public static void update()
	{
		if (mapRegexData == null) initLocalement();
		else appliquerPermanent();
	}

	private static void initLocalement()
	{
		mapRegexData = new HashMap<>();

		Stockeur stockeur = new Stockeur(new File("data/ConfigRegex.tsv"));
		List<Stockeur.LigneStockee> lignes = stockeur.lire();

		for (Stockeur.LigneStockee ligne : lignes)
		{
			RegexData data = ligne.data;

			RegexMetadata metadata = new RegexMetadata();
			metadata.id = ligne.id;
			metadata.etat = RegexMetadata.Etat.SIMPLE;

			mapRegexData.put(data, metadata);
		}
	}

	private static void appliquerPermanent()
	{
		Stockeur stockeur = new Stockeur(new File("data/ConfigRegex.tsv"));
		List<Stockeur.LigneStockee> lignes = stockeur.lire();

		// Création d'une map temporaire avec les ID pour modifications rapides
		Map<Integer, Stockeur.LigneStockee> mapTemp = new HashMap<>();
		for (Stockeur.LigneStockee l : lignes)
			mapTemp.put(l.id, l);

		int maxId = lignes.stream().mapToInt(l -> l.id).max().orElse(-1);
		List<Stockeur.LigneStockee> nouvellesLignes = new ArrayList<>();

		for (Entry<RegexData, RegexMetadata> entry : mapRegexData.entrySet())
		{
			RegexData data = entry.getKey();
			RegexMetadata meta = entry.getValue();

			switch (meta.etat)
			{
				case SUPPRIME:
					if (meta.id != null)
					{
						Stockeur.LigneStockee l = mapTemp.get(meta.id);
						if (l != null )
							mapTemp.remove(meta.id);
					}
					break;

				case MODIFIE:
					if (meta.id != null)
					{
						Stockeur.LigneStockee l = mapTemp.get(meta.id);
						if (l != null)
							mapTemp.put(meta.id, new Stockeur.LigneStockee(meta.id, data));
					}
					break;

				case AJOUT:
					++maxId;
					mapTemp.put(maxId, new Stockeur.LigneStockee(maxId, data));
					break;

				default:
					break;
			}
		}

		// On régénère la liste proprement triée
		List<Stockeur.LigneStockee> lignesFinales = new ArrayList<>(mapTemp.values());
		lignesFinales.sort(Comparator.comparingInt(l -> l.id));

		stockeur.ecrire(lignesFinales);
	}

	public static void ajouter( RegexData regexData )
	{
		RegexMetadata metadata = new RegexMetadata();
		metadata.id = null;
		metadata.etat = RegexMetadata.Etat.AJOUT;

		RepositoryRegexData.mapRegexData.put(regexData, metadata);
	}

	public static void supprimer( int id )
	{
		for( Entry<RegexData, RegexMetadata> entry : mapRegexData.entrySet() )
		{
			RegexMetadata metadata = entry.getValue();
			if( metadata.id.equals( id ) )
			{
				metadata.etat = RegexMetadata.Etat.SUPPRIME;
			}
		}
	}

	public static void modifier( int id, RegexData regexData )
	{
		for( Entry<RegexData, RegexMetadata> entry : mapRegexData.entrySet() )
		{
			RegexMetadata metadata = entry.getValue();
			RegexData regexDataOrig = entry.getKey();
			if( metadata.id.equals( id ) )
			{
				metadata.etat = RegexMetadata.Etat.MODIFIE;
				regexDataOrig = regexData;
			}
		}
	}

	public static List<RegexData> getAll()
	{
		List<RegexData> list = new ArrayList<>();
		list.addAll(mapRegexData.keySet());
		return list;
	}

	public static List<String> getTousLesNomsDisponibles()
	{
		List<String> ensNom = new ArrayList<>();

		for( Entry<RegexData, RegexMetadata> entry : mapRegexData.entrySet() )
		{
			RegexMetadata metadata = entry.getValue();
			RegexData regexData = entry.getKey();
			if( !metadata.etat.equals( RegexMetadata.Etat.SUPPRIME ) )
			{
				ensNom.add(regexData.nom);
			}
		}

		return ensNom;
	}

	public static Integer getIdByIndex( int index )
	{
		return mapRegexData.get( getByIndex(index) ).id;
	}

	public static RegexData getByIndex( int index )
	{
		return getAll().get(index);
	}

	private static class RegexMetadata
	{
		public Integer id;
		public Etat etat;

		public static enum Etat
		{
			SIMPLE(),
			SUPPRIME(),
			MODIFIE(),
			AJOUT();
		}
	}
}
