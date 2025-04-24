package polytext.vue;

import java.util.List;

public interface ISaisies
{
	public void setTexteAppercu( String texte );

	public void applyStyleTexteAppercu( List<int[]> positions );

	public void setTexteOutput( String texte );
}
