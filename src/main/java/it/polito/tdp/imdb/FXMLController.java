/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.DirectorAdiacente;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	int anno = boxAnno.getValue();
    	model.creaGrafo(anno);
    	
    	txtResult.appendText("Grafo creato!\n");
    	txtResult.appendText("# Vertici : "+model.getGrafo().vertexSet().size()+"\n# Archi : "+model.getGrafo().edgeSet().size());
    	
    	boxRegista.getItems().addAll(model.getGrafo().vertexSet());
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	txtResult.clear();
    	Director partenza = boxRegista.getValue();
    	List<DirectorAdiacente> adiacenti = model.getDirectorAdiacenti(partenza);
    	for(DirectorAdiacente d : adiacenti) {
    		txtResult.appendText(d.toString()+"\n");
    	}
    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	txtResult.clear();
    	Director partenza = boxRegista.getValue();
    	String massimo = txtAttoriCondivisi.getText();
    	if(massimo == "") {
    		txtResult.appendText("Inserire un numero massimo di attori condivisi");
    		return;
    	}
    	int max = 0;
    	try {
    		max = Integer.parseInt(massimo);
    	} catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un numero");
    		return;
    	}
    	List<DirectorAdiacente> adiacenti = model.getPercorso(partenza, max);
    	for(DirectorAdiacente d : adiacenti) {
    		txtResult.appendText(d.toString()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	this.model = model;
    	for(int i = 2004; i <= 2006; i++) {
    		boxAnno.getItems().addAll(i);
    	}
    }
    
}
