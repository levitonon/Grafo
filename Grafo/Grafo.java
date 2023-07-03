//Autores: Fabio Henrique e Levi Tonon
//Árvore geradora mínima utilizando algoritmo de Prim

package Grafo.Grafo;


import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class Grafo<T> {
    private Comparator<T> comparador;
    private ArrayList<Vertice<T>> vertices = new ArrayList<Vertice<T>>();
    
    public Grafo(Comparator<T> comparador){
        this.comparador = comparador;
    }

    public ArrayList<Aresta<T>> getCidadeVizinhas(T cidade){ //Cria um vertice com o valor passado e chama a função getVertice que irá retornar um vertice, 
        Vertice<T> vertice = obterVertice(cidade);            //com isso utiliza-se o método getDestinos para retornar uma lista de arestas(que contém destinos e peso).
        return vertice.getDestinos();
    }

    public Vertice<T> adicionarVertice(T valor){ 
        Vertice<T> novo = new Vertice<T>(valor);
        vertices.add(novo);
        return novo;
    }

    public Vertice<T> obterVertice(T valor){
        Vertice<T> v;
        for(int i = 0; i<this.vertices.size();i++){
            v = this.vertices.get(i);
            if(comparador.compare(v.getValor(),valor)==0){
                return v;
            }
        }
        return null;
    }

    public void adicionarAresta(T origem, T destino, float peso){
        Vertice<T> verticeOrigem, verticeDestino;
        
        verticeOrigem = obterVertice(origem); //Verifica se o vertice ja existe na lista de vertices, se for null é por que não está
        
        if(verticeOrigem==null){
            verticeOrigem=adicionarVertice(origem); //adiciona vertice na lista caso não esteja
        }

        verticeDestino = obterVertice(destino); //Verifica se o vertice ja existe na lista de vertices, se for null é por que não está

        if (verticeDestino==null){
            verticeDestino = adicionarVertice(destino); //adiciona vertice na lista caso não esteja
        }

        verticeOrigem.adicionarDestino(new Aresta<T>(verticeDestino,peso)); //adiciona o destino do vertice origem passando um objeto aresta com parâmetros sendo o vertice destino e o peso
    }

    public ArrayList<T> buscaEmLargura(int codigo){
        ArrayList<Vertice<T>> marcados = new ArrayList<Vertice<T>>();
        ArrayList<Vertice<T>> fila = new ArrayList<Vertice<T>>();
        ArrayList<T> chegaCidade = new ArrayList<T>(); //Quais cidades é possível chegar
        Vertice<T> atual = this.vertices.get(codigo-1); //Cria um vertice para ser o atual e é passado "codigo-1" pois assim ficará de acordo com o index da lista
        fila.add(atual);
        while(fila.size()>0){ //Acontece enquanto o tamanho da fila é maior que 0
            atual = fila.get(0); //Passa o vertice do primeiro da fila pro atual
            fila.remove(0); //Remove o primeiro da fila
            marcados.add(atual); //Adiciona o atual para o marcados
            chegaCidade.add(atual.getValor()); //Adiciona à lista de quais cidades é possível chegar
            ArrayList<Aresta<T>> destinos = atual.getDestinos();
            Vertice<T> proximo;
            for (int i=0; i<destinos.size(); i++){ //Passa por toda lista de destinos
                proximo=destinos.get(i).getDestino(); 
                if(!marcados.contains(proximo) && !fila.contains(proximo)){ //Se proximo nao estiver na lista de marcados e nao estiver na fila, será adicionado na fila
                    fila.add(proximo);
                }
            }
        }
        return chegaCidade;
    }

    public ArrayList<T> getValores(){
        ArrayList<T> valores = new ArrayList<T>();
        for(int i = 0; i<this.vertices.size();i++){
            valores.add(this.vertices.get(i).getValor());
        }
        return valores;
    }

    public ArrayList<Vertice<T>> getVertices(){
        return this.vertices;
    }

    public Grafo<T> arvGerMin(Comparator<T> comparador) { 
        Grafo<T> grafoMin = new Grafo<T>(comparador); //Cria o grafo onde irá ficar a árvore geradora mínima
        ArrayList<Vertice<T>> marcados = new ArrayList<Vertice<T>>(); //Lista de maracdos
        Vertice<T> origem = this.vertices.get(0); //Cria-se um vertice de origem
        grafoMin.adicionarVertice(origem.getValor()); //adiciona vertice origem
        marcados.add(origem); //Adiciona a origem na lista de marcados
        Aresta<T> menorPeso = null; 
        float pesoTotal = 0;

    while (grafoMin.vertices.size() < this.vertices.size()) {
        menorPeso = null;
        for (Vertice<T> vertice : marcados) { //verifica cada item em marcados (for each)
            for (Aresta<T> aresta : vertice.getDestinos()) { //verifica cada item em vertice.getDestinos() (for each)
                if (!marcados.contains(aresta.getDestino())) { //verifica se não tem esse item na lista de marcados
                    if (menorPeso == null || aresta.getPeso() < menorPeso.getPeso()) { //Verifica se menorPeso está nulo ou se a aresta verificada tem peso menor que o peso da aresta menorPeso
                        menorPeso = aresta;
                        origem = vertice;
                    }
                }
            }
        }

        if (menorPeso != null) { //Verifica se o menor peso é nulo e adiciona o vertice origem e destino além da aresta que conecta os dois (com o peso incluso) e marca o vertice destino
            Vertice<T> destino = menorPeso.getDestino();
            grafoMin.adicionarVertice(destino.getValor());
            grafoMin.adicionarAresta(origem.getValor(), destino.getValor(), menorPeso.getPeso());
            grafoMin.adicionarAresta(destino.getValor(), origem.getValor(), menorPeso.getPeso());
            marcados.add(destino);
            System.out.println(origem.toString()); 
            System.out.println(menorPeso.toString());
            marcados.add(menorPeso.getDestino());
            pesoTotal += menorPeso.getPeso();            
            
        }
    }
    System.out.println("Peso total: "+pesoTotal+"\n");
    return grafoMin;
    }

    public void caminhoMinimo(Vertice<T> verticeOrigem, Vertice<T> verticeDestino) {
        LinkedList<Vertice<T>> fila = new LinkedList<Vertice<T>>();
        LinkedList<Float> distancias = new LinkedList<Float>();
        LinkedList<Vertice<T>> antecessores = new LinkedList<Vertice<T>>();
        ArrayList<Vertice<T>> visitados = new ArrayList<Vertice<T>>();

        for (Vertice<T> vertice : vertices) {
            if (vertice.equals(verticeOrigem)) {
                distancias.add(0.0f);
            } else {
                distancias.add(Float.POSITIVE_INFINITY);
            }

            antecessores.add(null);
        }

        fila.add(verticeOrigem);

        while (!fila.isEmpty()) {
            Vertice<T> vertice = fila.remove();

            if (!visitados.contains(vertice)) {
                visitados.add(vertice);

                for (Aresta<T> aresta : vertice.getDestinos()) {
                    Vertice<T> destino = aresta.getDestino();
                    float pesoAresta = aresta.getPeso();
                    int indexDestino = vertices.indexOf(destino);

                    if (!visitados.contains(destino)) {
                        float distanciaAtual = distancias.get(indexDestino);
                        float novaDistancia = distancias.get(vertices.indexOf(vertice)) + pesoAresta;

                        if (novaDistancia < distanciaAtual) {
                            distancias.set(indexDestino, novaDistancia);
                            antecessores.set(indexDestino, vertice);
                        }

                        fila.add(destino);
                    }
                }
            }
        }

        if (!visitados.contains(verticeDestino)) {
            System.out.println("Não foi possível encontrar um caminho do vértice origem ao vértice destino.");
            return;
        }

        LinkedList<Vertice<T>> caminho = new LinkedList<Vertice<T>>();
        Vertice<T> vertice = verticeDestino;

        while (vertice != null) {
            caminho.addFirst(vertice);
            vertice = antecessores.get(vertices.indexOf(vertice));
        }

        System.out.println("Caminho mínimo:");
        for (Vertice<T> v : caminho) {
            System.out.println(v.toString());
        }

        System.out.println("Distância total: " + distancias.get(vertices.indexOf(verticeDestino)));
    }

private boolean bfs(float[][] residuo, int inicio, int fim, int[] pai) {
    int tamanho = vertices.size();
    boolean[] visitado = new boolean[tamanho];
    Arrays.fill(visitado, false);

    Queue<Integer> fila = new LinkedList<>();
    fila.add(inicio);
    visitado[inicio] = true;
    pai[inicio] = -1;

    while (!fila.isEmpty()) {
        int atual = fila.poll();

        ArrayList<Aresta<T>> destinos = vertices.get(atual).getDestinos();
        for (Aresta<T> aresta : destinos) {
            int proximo = vertices.indexOf(aresta.getDestino());
            float capacidade = aresta.getPeso();

            if (!visitado[proximo] && residuo[atual][proximo] > 0) {
                fila.add(proximo);
                pai[proximo] = atual;
                visitado[proximo] = true;
            }
        }
    }

    return visitado[fim];
}

public float fluxoMaximo(Vertice<T> verticeOrigem, Vertice<T> verticeDestino) {
    int tamanho = vertices.size();

    // Criar uma matriz de capacidades residuais
    float[][] residuo = new float[tamanho][tamanho];
    for (int i = 0; i < tamanho; i++) {
        ArrayList<Aresta<T>> destinos = vertices.get(i).getDestinos();
        for (Aresta<T> aresta : destinos) {
            int j = vertices.indexOf(aresta.getDestino());
            residuo[i][j] = aresta.getPeso();
        }
    }

    float fluxoMaximo = 0;
    int[] pai = new int[tamanho]; // Array para armazenar o caminho de aumento
    while (bfs(residuo, vertices.indexOf(verticeOrigem), vertices.indexOf(verticeDestino), pai)) {
        float caminhoAumento = Float.MAX_VALUE;

        // Encontrar a capacidade residual mínima ao longo do caminho de aumento
        for (Vertice<T> v = verticeDestino; v != verticeOrigem; v = vertices.get(pai[vertices.indexOf(v)])) {
            Vertice<T> u = vertices.get(pai[vertices.indexOf(v)]);
            float capacidadeResidual = residuo[vertices.indexOf(u)][vertices.indexOf(v)];
            caminhoAumento = Math.min(caminhoAumento, capacidadeResidual);
        }

        // Atualizar o fluxo ao longo do caminho de aumento
        for (Vertice<T> v = verticeDestino; v != verticeOrigem; v = vertices.get(pai[vertices.indexOf(v)])) {
            Vertice<T> u = vertices.get(pai[vertices.indexOf(v)]);
            residuo[vertices.indexOf(u)][vertices.indexOf(v)] -= caminhoAumento;
            residuo[vertices.indexOf(v)][vertices.indexOf(u)] += caminhoAumento;
        }

        fluxoMaximo += caminhoAumento;
    }

    return fluxoMaximo;
}

}