package Grafo.Grafo;

import java.util.ArrayList;

public class Vertice<T> {
    
    private T valor;

    private ArrayList<Aresta<T>> destinos = new ArrayList<Aresta<T>>();

    public Vertice(T valor){
        this.valor=valor;
    }

    public T getValor(){
        return valor;
    }

    public void adicionarDestino(Aresta<T> aresta){ //Adiciona destino na lista de destinos do vertice
        destinos.add(aresta);
    }

    public ArrayList<Aresta<T>> getDestinos(){
        return destinos;
    }

    public String toString(){
        return "Cidade origem: "+ this.getValor().toString();
    }

    public boolean equals(Vertice<T> v) {
      boolean result = false;
      if (this.getValor() == v.getValor()) {
        result = true;
      }
      return result;
   }   
}
