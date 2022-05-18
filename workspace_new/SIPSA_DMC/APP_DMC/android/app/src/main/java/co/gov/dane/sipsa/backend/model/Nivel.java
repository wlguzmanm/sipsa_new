package co.gov.dane.sipsa.backend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreslopera on 4/23/16.
 */
public class Nivel implements Serializable {

    private String nombre;
    private ArrayList<String> segundoNivel;

    public Nivel(){
        this.segundoNivel = new ArrayList<String>();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addSegundoNivel(String segundoNivel) {
        this.segundoNivel.add(segundoNivel);
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getSegundoNivel() {
        return segundoNivel;
    }


    @Override
    public String toString() {
        return nombre;
    }
}
