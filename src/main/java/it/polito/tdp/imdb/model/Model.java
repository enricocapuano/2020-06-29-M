package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private Graph<Director, DefaultWeightedEdge> grafo;
	private Map<Integer, Director> idMap;
	private ImdbDAO dao;
	private int somma = 0;
	
	public Model() {
		dao = new ImdbDAO();
		
	}
	
	public void creaGrafo(int anno) {
		idMap = new HashMap<>();
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.getDirettoriAnno(idMap, anno);
		//aggiungo i vertici
		Graphs.addAllVertices(grafo, idMap.values());
		
		System.out.println(grafo.vertexSet().size());
		
		List<Adiacenza> archi = dao.getArchi(idMap, anno);
		
		for(Adiacenza a : archi) {
			Graphs.addEdge(grafo, a.getD1(), a.getD2(), a.getPeso());
		}
		
		System.out.println(grafo.edgeSet().size());
	}

	public Graph<Director, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public List <DirectorAdiacente> getDirectorAdiacenti(Director partenza){
		
		 List <DirectorAdiacente> result = new LinkedList<>();
		 for(DefaultWeightedEdge e: this.grafo.edgesOf(partenza)) {
				
				int peso = (int) this.grafo.getEdgeWeight(e);
				
				Director dir = Graphs.getOppositeVertex(this.grafo, e, partenza);
				
				result.add(new DirectorAdiacente(dir, peso));
				}
		    	
		    	Collections.sort(result);
		    	return result;
	}
	
	public List<DirectorAdiacente> getPercorso(Director d, int max){
		List<DirectorAdiacente> soluzione = new ArrayList<>();
		cerca(d, soluzione, max);
		return soluzione;
	}
	
	private void cerca(Director d, List<DirectorAdiacente> soluzione, int max) {
		if(somma == max) {
			return;
		}
		DefaultWeightedEdge minimo = new DefaultWeightedEdge();
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if(minimo == null) {
				minimo = e;
			}
			else {
				if(grafo.getEdgeWeight(e) < grafo.getEdgeWeight(minimo)) {
					minimo = e;
				}
			}
		}
		int peso = (int) this.grafo.getEdgeWeight(minimo);	
		Director dir = Graphs.getOppositeVertex(this.grafo, minimo, d);		
		DirectorAdiacente ad = new DirectorAdiacente(dir, peso);
		somma += peso;
		soluzione.add(ad);		
		cerca(dir, soluzione, max);
		soluzione.remove(ad);
	}
}
