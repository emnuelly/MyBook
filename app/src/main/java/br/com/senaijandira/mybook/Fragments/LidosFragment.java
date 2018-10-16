package br.com.senaijandira.mybook.Fragments;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.senaijandira.mybook.R;
import br.com.senaijandira.mybook.Utils;
import br.com.senaijandira.mybook.adapter.LivroAdapter;
import br.com.senaijandira.mybook.db.MyBooksDatabase;
import br.com.senaijandira.mybook.model.Livro;

public class LidosFragment extends Fragment {

    //declarando o listview
    ListView lstLivrosLidos;

    LivroAdapter adapter;

    MyBooksDatabase myBooksDatabase;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //erro
        try{
            atualizar();
        }catch (Exception e){
            e.printStackTrace();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public LidosFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //declarando e criando um inflate
        View v = inflater.inflate(R.layout.lidos_layout, container, false);

        //banco
        myBooksDatabase = Room.databaseBuilder(getContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        //list view
        lstLivrosLidos = v.findViewById(R.id.lstLivrosLidos);

        //livro adapter

        adapter = new LivroAdapter(getContext(), myBooksDatabase);

        lstLivrosLidos.setAdapter(adapter);

        //retonando o valor
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        atualizar();
    }

    public void atualizar(){

        adapter.clear();

        Livro[] livros = myBooksDatabase.daoLivro().selecionarLivrosLidos();

        adapter.addAll(livros);

    }
}
