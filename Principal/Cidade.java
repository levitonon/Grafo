package Grafo.Principal;

public class Cidade {
    private int codigo;
    private String nome;

    public Cidade(int codigo, String nome){
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo(){
        return codigo;
    }

    public String getNome(){
        return nome;
    }

    public void setCodigo(int codigo){
        this.codigo = codigo;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String toString(){
        return (this.getNome()+ "\nCódigo da cidade: "+ this.getCodigo()+"\n");
    }
}
