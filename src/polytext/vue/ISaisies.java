package polytext.vue;

import java.util.List;

public interface ISaisies
{
	public String getTexteSaisie();

	public String getCible();

	public String getRemplacement();

	public boolean isRegex();

	public void setTexteAppercu( String texte );

	public void applyStyleTexteAppercu( List<int[]> positions );

	public void setTexteOutput( String texte );
}
