package Grafo.Principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;

import Grafo.Grafo.*;

public class APLGrafos {

    public static void main(String[] args) throws FileNotFoundException{
    
    Grafo<Cidade> grafo = new Grafo<Cidade>(new ComparadorCidade());
    Grafo<Cidade> grafoMin = new Grafo<Cidade>(new ComparadorCidade());

    Scanner scan = new Scanner(new FileReader("C:\\Users\\Levi\\OneDrive\\Zoom\\Grafo\\Principal\\entrada.txt")); //Coloque o path do arquivo aqui
    Scanner lerTeclado = new Scanner(System.in);
    String partes1[] = new String[2]; //Primeira parte do arquivo (Codigo e nome da cidade)    
    int qtdNos = scan.nextInt();
    String partes2[] = new String[qtdNos]; //Segunda parte do arquivo (peso entre cidades)
    ArrayList<Cidade> cidades = new ArrayList<>(); //cria uma lista de cidades para poder adicionar as arestas (tanto destinos quanto custos) de cada vertice
    scan.nextLine(); // Limpa buffer
        
        for(int i = 0; i<qtdNos;i++) {
            String ler = scan.nextLine();
            partes1 = ler.split(","); //separa a linha em partes utilizando a "," como separador
            Cidade cidade = new Cidade(Integer.parseInt(partes1[0]), partes1[1]); //cria objeto cidade
            cidades.add(cidade); //adiciona na lista de cidades
            grafo.adicionarVertice(cidade); //adiciona o vertice ao grafo
        }

        for(int origem = 0; origem<qtdNos;origem++) { //esse for é utilizado para saber as linhas da leitura do arquivo, por exemplo, todos os pesos da linha 1 será ligado à cidade 1, assim por diante
            String ler = scan.nextLine();
            partes2 = ler.split(",");
            for(int dest  = 0; dest<qtdNos;dest++) {         //esse for é utilizado para saber as colunas de cada linha do arquivo.
                if (Float.parseFloat(partes2[dest]) >0){    //por exemplo, a primeira coluna será o custo dos vertice para cidade 1 independente da cidade (no caso, linha)
                    grafo.adicionarAresta(cidades.get(origem), cidades.get(dest), Float.parseFloat(partes2[dest]));
                }
        }
       }    
       
       boolean sair = false;

       while(!sair){
       System.out.println("Escolha uma ação:\n1- Obter cidades vizinhas\n2- Obter todos os caminhos a partir de uma cidade\n3- Calcular árvore geradora mínima\n4- Caminho mínimo\n5- Calcular fluxo mínimo\n6- Sair");
       int escolha = lerTeclado.nextInt();
       if(escolha == 1){
        System.out.println("Escreva o código da cidade para obter suas cidades vizinhas:");
        int codigo = lerTeclado.nextInt();
        ArrayList<Aresta<Cidade>> vizinhas = new ArrayList<Aresta<Cidade>>(); //lista de cidades vizinhas
        if(codigo<=qtdNos && codigo>0){ // evita erros de colocar codigos invalidos
        vizinhas = grafo.getCidadeVizinhas(new Cidade(codigo,null)); 
        for(int j = 0; j<vizinhas.size();j++){ //percorre a lista de cidades vizinhas
            Aresta<Cidade> aresta = vizinhas.get(j); //Pega a aresta da lista
            Vertice<Cidade> vertice = aresta.getDestino(); //Pega o vertice destino da aresta
            Cidade cidadeVizinha = vertice.getValor(); //Pega o valor do vertice (no caso, a cidade)
        System.out.println("Codigo da cidade: "+cidadeVizinha.getCodigo()+"\nNome da cidade: "+cidadeVizinha.getNome()+"\nDistância: "+aresta.getPeso()+"\n");
        }
    }
    else{
        System.out.println("Esse codigo de cidade não existe.\n");
    }
       }

       else if(escolha==2){
        ArrayList<Cidade> caminhoCidade = new ArrayList<Cidade>(); //Lista de caminhos nos quais a cidade chega
        System.out.println("Escreva o código da cidade de origem para saber seus caminhos: ");
        int codigo = lerTeclado.nextInt();
        if(codigo<=qtdNos && codigo>0){ //evita erros de colocar codigos invalidos
        caminhoCidade = grafo.buscaEmLargura(codigo); //busca em largura as cidades nas quais é possivel chegar
            if(caminhoCidade!=null){
            for(int i = 0; i<caminhoCidade.size();i++){
            System.out.println("Codigo da cidade: "+ caminhoCidade.get(i).getCodigo()+"\nNome da cidade: "+caminhoCidade.get(i).getNome()+"\n");
                }
            }
        }
        else{
        System.out.println("Esse codigo de cidade não existe.\n");
        }
    }

    else if(escolha == 3){
        grafoMin = grafo.arvGerMin(new ComparadorCidade());
    }

    else if(escolha == 4){
        System.out.println(grafoMin.getVertices().size());
        int escolha2 = 0;
        int codigo1 = 0;
        int codigo2 = 0;
        Vertice<Cidade> vertice1 = null;
        Vertice<Cidade> vertice2 = null;
        System.out.println("Escolha uma opção:\n1-Cacular caminho mínimo entre duas cidades\n2-Calcular caminho mínimo entre duas cidades considerando a AGM");
        escolha2 = lerTeclado.nextInt();
        if (escolha2==1){
            System.out.println("Escreva o código da primeira cidade: ");
            codigo1=lerTeclado.nextInt();
            System.out.println("Escreva o código da segunda cidade: ");
            codigo2=lerTeclado.nextInt();
            vertice1 = grafo.obterVertice(new Cidade(codigo1,null));
            vertice2 = grafo.obterVertice(new Cidade(codigo2,null));
            grafo.caminhoMinimo(vertice1, vertice2);
        }
        else if(escolha2 == 2){
            if(grafoMin.getVertices().size()>0){
            System.out.println("Escreva o código da primeira cidade: ");
            codigo1=lerTeclado.nextInt();
            System.out.println("Escreva o código da segunda cidade: ");
            codigo2=lerTeclado.nextInt();
            vertice1 = grafoMin.obterVertice(new Cidade(codigo1,null));
            vertice2 = grafoMin.obterVertice(new Cidade(codigo2,null));
            grafoMin.caminhoMinimo(vertice1, vertice2);
            }
            else{
                grafoMin = grafo.arvGerMin(new ComparadorCidade());    
            }

        }
        else{
            System.out.println("Escolha uma opção válida");
        }
    }   
       else if(escolha == 5){
            int codigo1 = 0;
            int codigo2 = 0;
            Vertice<Cidade> vertice1 = null;
            Vertice<Cidade> vertice2 = null;
            System.out.println("Escreva o código da primeira cidade: ");
            codigo1=lerTeclado.nextInt();
            System.out.println("Escreva o código da segunda cidade: ");
            codigo2=lerTeclado.nextInt();
            vertice1 = grafo.obterVertice(new Cidade(codigo1,null));
            vertice2 = grafo.obterVertice(new Cidade(codigo2,null));
            System.out.println("Fluxo maximo: "+grafo.calcularFluxoMaximo(vertice1, vertice2));
       }
       else if(escolha == 6){
        sair = true;
       }

       else{
        System.out.println("Escolha uma opção válida");
    }
    }
    lerTeclado.close(); //fecha scanner
}
}
