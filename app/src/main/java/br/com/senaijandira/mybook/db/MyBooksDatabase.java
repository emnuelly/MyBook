package br.com.senaijandira.mybook.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.senaijandira.mybook.dao.LivroDao;
import br.com.senaijandira.mybook.model.Livro;

//chamando a classe que vai para o banco. na versao altera o numero da versao se voce alterar alguma coisa
@Database(entities = {Livro.class}, version = 1)
public abstract class MyBooksDatabase extends RoomDatabase{

    public abstract LivroDao daoLivro();


}
