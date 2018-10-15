package br.com.senaijandira.mybook.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Livro {
    //declarando em escopo de classe
    @PrimaryKey(autoGenerate = true)
    private int id;


    //cria um array de bits para carregar a imagem e ser enviada para o banco
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) //dizendo que nessa coluna vai receber um tipo BLob que é binário
    private byte[] capa;

    private String titulo;
    private String autor;
    private String descricao;
    

    //consultor para livro
    public Livro(){
    }

    //consultor para livro passando as informações
    public Livro(int id, byte [] capa ,String titulo, String autor, String descricao ){
        this.id = id;
        this.capa = capa;
        this.titulo = titulo;
        this.autor = autor;
        this.descricao = descricao;
    }


    //GETS E SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getCapa() {
        return capa;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


}
