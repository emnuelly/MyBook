package br.com.senaijandira.mybook.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.mybook.R;
import br.com.senaijandira.mybook.Utils;
import br.com.senaijandira.mybook.db.MyBooksDatabase;
import br.com.senaijandira.mybook.model.Livro;

public class LivroAdapter extends ArrayAdapter<Livro>{

    //chamando o banco de dados
    private MyBooksDatabase myBooksDB;

    //criando a lista de livros
    public LivroAdapter(Context ctx, MyBooksDatabase myBooksDB){
        super(ctx, 0, new ArrayList<Livro>());

        this.myBooksDB = myBooksDB;
    }

    //metodo de deletar
    private void deletarLivro(Livro livro){

        myBooksDB.daoLivro().deletar(livro);

        remove(livro);
    }

    private void editarLivro(Livro livro){

        myBooksDB.daoLivro().atualizar(livro);

        add(livro);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.livro_layout, parent, false);

        }

        final Livro livro = getItem(position);

        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtTitulo = v.findViewById(R.id.txtLivroTitulo);
        TextView txtAutor = v.findViewById(R.id.txtNomeAutor);
        TextView txtDescricao = v.findViewById(R.id.txtLivroSinopse);

        ImageView imgDelete = v.findViewById(R.id.imgDelete);
        ImageView imgEditar = v.findViewById(R.id.editarLivro);

        //FAZENDO O OUVINTE DO ICONE DE DELETAR
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletarLivro(livro);
            }
        });

        imgEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarLivro(livro);
            }
        });

        //SETANDO A IMAGEM
        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
        //SETANDO O TITULO
        txtTitulo.setText(livro.getTitulo());
        //SETANDO O AUTOR
        txtAutor.setText(livro.getAutor());
        //SETANDO A DESCRIÇÃO
        txtDescricao.setText(livro.getDescricao());


        return v;
    }
}
