package br.com.senaijandira.mybook;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.senaijandira.mybook.Fragments.LerActivity;
import br.com.senaijandira.mybook.Fragments.LidosActivity;
import br.com.senaijandira.mybook.Fragments.LivroActivity;

public class MainActivity extends AppCompatActivity {



    //Declarando a variavel que manipula o fragment
    FragmentManager fm;

    //declarando o menu
    TabLayout tbMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //instanciando o fragment manager
        fm = getSupportFragmentManager();

        tbMenu = findViewById(R.id.tbMenu);
        //listener especifico para aquele tipo de menu

        tbMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    openTodosLivros();
                }

                if(tab.getPosition() == 1){
                    openLivrosLidos();
                }

                if(tab.getPosition() == 2){
                    openLivrosParaLer();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });

        openTodosLivros();
    }



    public void openTodosLivros(){

        //fazendo a transição
        FragmentTransaction ft = fm.beginTransaction();

        //trocando um layout por outro
        ft.replace(R.id.frame_layout, new LivroActivity());

        //mandando as alterações

        ft.commit();

    }
    //tela de livros lidos
    public void openLivrosLidos(){

        //fazendo a transição
        FragmentTransaction ft = fm.beginTransaction();

        //trocando um layout por outro
        ft.replace(R.id.frame_layout, new LidosActivity());

        //mandando as alterações

        ft.commit();
    }

    //tela de livros para ler
    public void openLivrosParaLer(){

        //fazendo a transição
        FragmentTransaction ft = fm.beginTransaction();

        //trocando um layout por outro
        ft.replace(R.id.frame_layout, new LerActivity());

        //mandando as alterações

        ft.commit();
    }
}
