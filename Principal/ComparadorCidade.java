package Grafo.Principal;
import java.util.Comparator;

public class ComparadorCidade implements Comparator<Cidade>{

    public int compare(Cidade city1, Cidade city2){
        return Integer.compare(city1.getCodigo(),city2.getCodigo());
    }
}
