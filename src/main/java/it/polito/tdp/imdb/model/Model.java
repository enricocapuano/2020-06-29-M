package it.polito.tdp.imdb.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private Graph<Director, DefaultWeightedEdge> grafo;
	private Map<Integer, Director> idMap;
	private ImdbDAO dao;
	
	public Model() {
		dao = new ImdbDAO();
		idMap = new HashMap<>();
	}
	
	public void creaGrafo(int anno) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.getDirettoriAnno(idMap, anno);
		//aggiungo i vertici
		Graphs.addAllVertices(grafo, idMap.values());
		
		//System.out.println(grafo.vertexSet().size());
		
		
	}

}
