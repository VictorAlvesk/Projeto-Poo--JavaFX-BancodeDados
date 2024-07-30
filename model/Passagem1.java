package model;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;

public class Passagem1{
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String nome;
    @DatabaseField
    private String descricao;
    @DatabaseField
    private float preco;
    
    /*
   *  Classe: Servico de Passagens Class
   * 
   * @author Matheus Teles
   * @author João Vitor
   * @author Victor Alves
   * @author Yan Santos
   * @author Gabriel Eduardo
   */
  
    public Passagem1(){};
    public Passagem1(String nome, String descricao, float preco){
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }
    
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getNome(){
        return nome;
    }
    
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    public String getDescricao(){
        return descricao;
    }
    
    public void setPreco(float preco){
        this.preco = preco;
    }
    public float getPreco(){
        return preco;
    }
    
}
