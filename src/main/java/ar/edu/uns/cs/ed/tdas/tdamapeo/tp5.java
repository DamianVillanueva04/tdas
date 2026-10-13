package ar.edu.uns.cs.ed.tdas.tdamapeo;

import javax.swing.text.Position;

public class tp5 {

    
    public Map<Character,Integer> ejd(PositionList<Character> lista) {
	Map<Character,Integer> mapa = new MapHash<Character,Integer>();
	for (Position<Character> p: lista.positions())                          {
		if (mapa.get(p.element()) == null)
			mapa.put(p.element(),1);
		else
			mapa.put(p.element(), mapa.get(p.element())+1);     	}
	return mapa;
}
}
