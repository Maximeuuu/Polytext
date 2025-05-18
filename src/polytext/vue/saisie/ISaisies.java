package polytext.vue.saisie;

import java.util.List;

public interface ISaisies
{
	public void setTexteAppercu( String texte );

	public void applyStyleTexteAppercu( List<int[]> positions );

	public void setTexteOutput( String texte );

	public void setCible( String cible );
	
	public void setRemplacement( String remplacement );

	public void setEstRegex( boolean estRegex );

	public String getCible();
	
	public String getRemplacement();

	public boolean getEstRegex();
}
