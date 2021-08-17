package com.senac.projectvitor.model.vo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Produto {
   @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

   @DatabaseField(canBeNull = false, width = 100)
    private String nome;

   @DatabaseField(canBeNull = false)
    private Double valor;

    @DatabaseField(canBeNull = false)
   private Integer estoque;

    @DatabaseField
    private Double valorDolar;

    // GetNome e SetNome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    //GetValor e SetValor
    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setValor(String valor) {
        try {
            this.valor = Double.parseDouble(valor);
        } catch(Exception e){
            this.valor = null;
        }
    }

    //GetEstoque e SetEstoque
    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public void setEstoque(String estoque) {
        try {
            this.estoque = Integer.parseInt(estoque);
        } catch(Exception e){
            this.estoque = null;
        }
    }

    //GetValorDolar e SetValorDolar
   public Double getValorDolar() {
        return valorDolar;
   }

    public void setValorDolar(Double valorDolar) {
        this.valorDolar = valorDolar;
    }

   public void setValorDolar(String valorDolar) {
        try {
           this.valorDolar = Double.parseDouble(valorDolar);
        } catch(Exception e){
           this.valorDolar = null;
       }
    }

    //get e sed ID
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //Construtores
    public Produto(Integer id, String nome, Double valor, Integer estoque, Double valorDolar) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.estoque = estoque;
        this.valorDolar = valorDolar;
    }

    public Produto() {
    }

    //toString
    @Override
    public String toString() {
        return
                "ID: " + id +
                "\nNome: " + nome +
                "\nValor (R$): " + valor +
                "\nEstoque: " + estoque +
                "\nValue (USD): " + valorDolar;
    }
}
