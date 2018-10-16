package br.com.senaijandira.mybook.adapter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.senaijandira.mybook.R;
import br.com.senaijandira.mybook.Utils;
import br.com.senaijandira.mybook.db.MyBooksDatabase;
import br.com.senaijandira.mybook.model.Livro;

public class LivroLidoAdapter extends ArrayAdapter<Livro> {

    MyBooksDatabase myBooksDB;

    public LivroLidoAdapter(Context cont){

        super(cont, 0, new ArrayList<Livro>());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        //Banco
        myBooksDB = Room.databaseBuilder(getContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        if(v == null){

            v = LayoutInflater.from(getContext()).inflate(R.layout.ler_layout, parent, false);
        }

        final Livro livro = getItem(position);

        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtTitulo = v.findViewById(R.id.txtLivroTitulo);
        TextView txtAutor = v.findViewById(R.id.txtNomeAutor);
        TextView txtDescricao = v.findViewById(R.id.txtLivroSinopse);

        ImageView imgRemover = v.findViewById(R.id.imgRemover);
        ImageView imgLer = v.findViewById(R.id.imgQueroLer);

        //CRIANDO O LISTENER PARA REMOVER O LIVRO DE QUERO LER
        imgRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //cria novo livro, mas muda o status
                Livro livroLista = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getAutor(), livro.getDescricao(), 0);

                //atualizando o banco
                myBooksDB.daoLivro().atualizar(livroLista);

                //limpando o list view
                clear();

                //select no banco
                Livro[] livros = myBooksDB.daoLivro().selecionarLivrosParaLer();

                //adicionando no listview
                addAll(livros);

                Toast.makeText(getContext(), "O livro foi removido de lidos!", Toast.LENGTH_SHORT).show();
            }
        });

        //CRIANDO O LISTENER DE LIVRO LIDO
        imgLer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cria novo livro, mas muda o status
                Livro livroLido = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getAutor(), livro.getDescricao(), 1);

                //atualizando o banco
                myBooksDB.daoLivro().atualizar(livroLido);

                //limpando o list view
                clear();

                //select no banco
                Livro[] livros = myBooksDB.daoLivro().selecionarLivrosParaLer();

                //adicionando no listview
                addAll(livros);

                Toast.makeText(getContext(), "O livro foi adicionado a Quero Lwe", Toast.LENGTH_SHORT).show();
            }
        });

        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));

        txtTitulo.setText(livro.getTitulo());

        txtAutor.setText(livro.getAutor());

        txtDescricao.setText(livro.getDescricao());


        return v;
    }
}