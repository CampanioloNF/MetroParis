package it.polito.tdp.metroparis.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	private Graph<Fermata, DefaultEdge> grafo;
	private List<Fermata> fermate;
	private Map<Integer, Fermata> fermateIdMap ;
	
	public void creaGrafo() {
		
		//crea l'oggetto grafo
		this.grafo = new SimpleDirectedGraph<Fermata, DefaultEdge>(DefaultEdge.class);
		
		//aggiungere i vertici
		
		MetroDAO dao = new MetroDAO();
		this.fermate=dao.getAllFermate();
	    
		//creo la mappa
		
		this.fermateIdMap = new HashMap<>() ;
	 	for(Fermata f: this.fermate)
			fermateIdMap.put(f.getIdFermata(), f) ;
		
		
		Graphs.addAllVertices(grafo, fermate);	
		
	    
	    
	    //Aggiungere gli archi 3 approcci
	    
	    //Approccio 1 
	   
	    /*
	    for(Fermata partenza: this.grafo.vertexSet()) {
	    	
	    	for(Fermata arrivo : this.grafo.vertexSet()) {
	    		if(dao.esisteConnessione(partenza, arrivo)) {
	    			this.grafo.addEdge(partenza, arrivo);
	    		}
	    	}
	    	
	    }
	    */
	    
	   // Approccio 2 delego al DB il lavoro
	    
         /*for(Fermata partenza: this.grafo.vertexSet()) {
	    	
	    	List<Fermata> arrivi = dao.stazioneArrivo(partenza);
	    	
	    	for(Fermata arrivo : arrivi) {
	    		
	    		int pos = this.fermate.indexOf(arrivo);
	    		
	    		this.grafo.addEdge(partenza, this.fermate.get(pos));
	    	}
	    	
	    }*/
         
        //Approccio 3 utilizzo la classe connessione 
        
        
        for(Connessione conn: dao.getConnessioni(this.fermateIdMap)) {
 
        	this.grafo.addEdge(conn.getStazP(), conn.getStazA());
        }
	    
	    
	    
		
	}

	public Graph<Fermata, DefaultEdge> getGrafo() {
		return grafo;
	}

	public List<Fermata> getFermate() {
		return fermate;
	}

}
