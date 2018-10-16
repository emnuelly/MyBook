package br.com.senaijandira.mybook.adapter;

import android.content.Context;
import android.content.Intent;
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

import br.com.senaijandira.mybook.EditarActivity;
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

        Toast.makeText(getContext(), "O livro foi apagado!", Toast.LENGTH_SHORT).show();
    }

   /* private void editarLivro(Livro livro){

        myBooksDB.daoLivro().atualizar(livro);

        add(livro);
    }*/

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
        final ImageView imgQueroLer = v.findViewById(R.id.imgQueroLer);
        final ImageView imgLido = v.findViewById(R.id.imgLido);
        ImageView imgEditar = v.findViewById(R.id.editarLivro);


        //FAZENDO O OUVINTE DO ICONE DE DELETAR
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletarLivro(livro);
            }
        });

        //OUVINTE DO EDITAR
        imgEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditarActivity.class);
                intent.putExtra("livro", livro.getId());
                getContext().startActivity(intent);

            }
        });

        //OUVINTE DO PARA LER
        imgQueroLer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Livro livroLer = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getAutor(), livro.getDescricao(), 1);

                myBooksDB.daoLivro().atualizar(livroLer);

                clear();

                Livro[] livros = myBooksDB.daoLivro().selecionarTodos();

                addAll(livros);

                if(livro.getStatus() != 1){
                    Toast.makeText(getContext(), "Adicionado a Quero Ler!", Toast.LENGTH_SHORT).show();
                    imgQueroLer.setEnabled(false);
                }
            }
        });

        //OUVINTE DO LIDO
        imgLido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Livro livroLido = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getAutor(), livro.getDescricao(), 2);

                myBooksDB.daoLivro().atualizar(livroLido);

                clear();

                Livro[] livros = myBooksDB.daoLivro().selecionarTodos();

                addAll(livros);

                if(livro.getStatus() != 2){
                    Toast.makeText(getContext(), "Adicionado a Lido!", Toast.LENGTH_SHORT).show();
                    imgLido.setEnabled(false);
                }

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
