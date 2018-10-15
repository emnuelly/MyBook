package br.com.senaijandira.mybook;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;

import br.com.senaijandira.mybook.R;
import br.com.senaijandira.mybook.db.MyBooksDatabase;
import br.com.senaijandira.mybook.model.Livro;
import br.com.senaijandira.mybook.Utils;

public class CadastroActivity extends AppCompatActivity {


    //declarando em escopo de classe
    ImageView imgLivroCapa; // recebe a imagem
    Bitmap livroCapa; //carrega o objeto e joga dentro do banco de dados, faz a conversão
    private final int COD_REQ_GALERIA = 101; //variável final nunca será alterado o valor.
    EditText txtTitulo, txtDescricao, txtAutor;

    private MyBooksDatabase myBooksDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //instaciando o banco de dados
        myBooksDB = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();


        //chamando as propriedades dela
        imgLivroCapa = findViewById(R.id.imgLivroCapa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtAutor = findViewById(R.id.txtAutor);
    }
    //metodo para alterar a foto quando for cadastrar um livro
    public void abrirGaleria(View v) {

        //essa intent apenas busca algo, ele se comunica com o sistema operacional
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        // está dizendo o tipo de arquivo que vão pedir
        intent.setType("image/*");

        //recebe uum resltado, que nesse caso é uma imagem
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"),COD_REQ_GALERIA);

    }

    //PEGANDO A REQUISIÇÃO PEDIDA
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // ESTRUTURA DE DECISÃO PARA SABER SE O QUE TEM DENTRO DO ESCOLHIDO É O QUE SE PEDE
        if(requestCode == COD_REQ_GALERIA && resultCode == Activity.RESULT_OK){
            //USA A VARIÁVEL COD_REQ PARA SER MAIS FÁCIL A COMPARAÇÃO/ RESULT OK É PARA QUANDO O SELECIONADO FOR O CERTO
            try{
                //pega o arquivo e transforma em binário
                InputStream input  = getContentResolver().openInputStream(data.getData());

                //converte em bitmap
                livroCapa = BitmapFactory.decodeStream(input);

                //exibe a conversão
                imgLivroCapa.setImageBitmap(livroCapa);

            }catch (Exception erro){

                //exibindo o erro
                erro.printStackTrace();

            }
        }
    }

    public void salvarLivro(View view) {

        //para pegar a capa vai usar um array de bytes
        byte[] capa = Utils.toByteArray(livroCapa);

        String titulo = txtTitulo.getText().toString();
        String descricao = txtDescricao.getText().toString();
        String autor = txtAutor.getText().toString();

        //verificando se os componentes estão nulos
        if(titulo.equals("") && descricao.equals("")){
            alert("Caixas Vazias", "Preencha os campos e carregue a foto");
        }else{
            alert("Sucesso!", "Livro cadastrado com sucesso");
        }

        //chamando o metodo livro para criar um novo obejto livro, estanciando o titulo, descricao pego de dentro das edit text
        Livro livro = new Livro(0, capa, titulo, autor, descricao );

        /*
        //inserindo a variável estática
        int tamanhoArray = MainActivity.livros.length;

        //dando espaço para a criação de um novo livro, cria uma copia e colocar outro dentro
        MainActivity.livros = Arrays.copyOf(MainActivity.livros,tamanhoArray+1);

        //chama o main, seta dentro da variavel quue cria os a lista de livros, chama
        //o array para criado em cima e seta o novo livro que foi cadastrado.

        MainActivity.livros[tamanhoArray] = livro;
        */

        //inserir no banco de dados
        myBooksDB.daoLivro().inserir(livro);



    }

    //criando um alert

    public void alert(String titulo, String msg){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setMessage(msg);
        alert.setCancelable(false);

        //botão OK
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alert.create().show();
    }
}
