package it.polito.tdp.imdb.model;

public class DirectorAdiacente implements Comparable<DirectorAdiacente>{

	private Director d;
	private double peso;
	public DirectorAdiacente(Director d, double peso) {
		super();
		this.d = d;
		this.peso = peso;
	}
	public Director getD() {
		return d;
	}
	public void setD(Director d) {
		this.d = d;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(DirectorAdiacente o) {
		return (int) (o.peso- this.peso);
	}
	@Override
	public String toString() {
		return d+" ---> Attori condivisi : "+(int)peso;
	}
	
	
	
}
