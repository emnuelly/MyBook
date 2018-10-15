package br.com.senaijandira.mybook.Fragments;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.senaijandira.mybook.CadastroActivity;
import br.com.senaijandira.mybook.R;
import br.com.senaijandira.mybook.Utils;
import br.com.senaijandira.mybook.adapter.LivroAdapter;
import br.com.senaijandira.mybook.db.MyBooksDatabase;
import br.com.senaijandira.mybook.model.Livro;

public class LivroActivity extends Fragment{

    //declarando o list view que vai ser eesponsável por criar a lista de livros
    ListView lstViewLivros;

    //declarando o adapter
    LivroAdapter adapter;

    //função estática
    public static Livro[] livros;

    //clase já criada que dá acesso ao banco
    private MyBooksDatabase myBooksDB;


    FloatingActionButton btnCadastro;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v;
        //instanciando e criando o layout
        v = inflater.inflate(R.layout.livros_fragment, container, false);

        //instaciando a classe do banco
        myBooksDB = Room.databaseBuilder(getContext(),MyBooksDatabase.class, Utils.DATABASE_NAME ).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        //pegando a listview
        lstViewLivros = v.findViewById(R.id.lstViewLivros);

        //cria o adapter
        adapter = new LivroAdapter(getContext(), myBooksDB);

        lstViewLivros.setAdapter(adapter);


        btnCadastro = v.findViewById(R.id.btnCadastro);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCadastro(v);
            }
        });

        //retornando um valor
        return v;
    }


    public void abrirCadastro(View v){
        //chamando uma activity
        startActivity(new Intent(getContext(), CadastroActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();

        //fazendo o select
        livros = myBooksDB.daoLivro().selecionarTodos();

        adapter.clear();//limpando

        adapter.addAll(livros); //adcionando

    }


}
