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
	Director minimo;
	int peso;
	
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
		minimo = null;
		peso = 0;
		List<DirectorAdiacente> soluzione = new ArrayList<>();
		cerca(d, soluzione, max);
		return soluzione;
	}
	
	private void cerca(Director d, List<DirectorAdiacente> soluzione, int max) {
		if(somma == max) {
			return;
		}
		
		for(Director e : Graphs.neighborListOf(grafo, d)) {
			if(minimo == null) {
				minimo = e;
			}
			else {
				if(grafo.getEdgeWeight(grafo.getEdge(d, e)) < peso) {
					minimo = e;
				}
			}
		}
		peso = (int) this.grafo.getEdgeWeight(grafo.getEdge(d, minimo));			
		DirectorAdiacente ad = new DirectorAdiacente(minimo, peso);
		somma += peso;
		soluzione.add(ad);		
		cerca(minimo, soluzione, max);
		soluzione.remove(ad);
	}
}
