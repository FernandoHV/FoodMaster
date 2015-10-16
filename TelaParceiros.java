package com.foodmaster.fernandoh.foodmaster;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TelaParceiros extends Fragment {

    static View rootView = null;
    List<String> nomes_produtos_temp = new ArrayList<String>();
    List<String> id_parceiro_temp = new ArrayList<String>();
    List<String> url_img_temp = new ArrayList<String>();
    TextView txtJasonParceiros;
    GridView grid;


    public TelaParceiros(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       rootView = inflater.inflate(R.layout.fragment_tela_parceiros, container, false);
        txtJasonParceiros = (TextView) rootView.findViewById(R.id.txtJasonParceiros);
        grid = (GridView) rootView.findViewById(R.id.listaPromocoes);

        WebServiceGet wb = new WebServiceGet();
        String resultado = null;

        try {
            resultado = wb.execute(Util.url_todosParceiros).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        montaGrid(resultado);

        return rootView;
    }

    private void montaGrid(String s) {
        try {
            ArrayList parceiros =  leJsonParceiro(s);
           for (int i = 0; i < parceiros.size();i++){
               Parceiro parceiro = (Parceiro) parceiros.get(i);
               nomes_produtos_temp.add(parceiro.nome);
               url_img_temp.add(parceiro.urlImagem);
               id_parceiro_temp.add(parceiro.id);

           }
            usaAdaptador();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Parceiro> leJsonParceiro(String str){
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray jArray = parser.parse(str).getAsJsonArray();
        Type collectionType = new TypeToken<ArrayList<Parceiro>>(){}.getType();
        ArrayList<Parceiro> enums = gson.fromJson(jArray, collectionType);
        return enums;
    }

    private void usaAdaptador(){
        String[] urlsImgs = converteArray(url_img_temp);
        String[] nomeProdutos = converteArray(nomes_produtos_temp);
        String[] idParceiro = converteArray(id_parceiro_temp);

        AdaptadorListaParceiros adapter = new AdaptadorListaParceiros((Activity) rootView.getContext(), urlsImgs, nomeProdutos,idParceiro);
        grid.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    TextView id_clicado = (TextView) view.findViewById(R.id.idParceiro);
                    TextView nome = (TextView) view.findViewById(R.id.nomeParceiro);
                    //ImageView imagem = (ImageView) view.findViewById(R.id.imageView);

                    TelaDetalhesParceiro fragmentDetalheParceiro = null;
                    fragmentDetalheParceiro = new TelaDetalhesParceiro();
                    android.app.FragmentManager fragmentManager;

                    Bundle arguments = new Bundle();
                    arguments.putString("id_parceiro", id_clicado.getText().toString());
                    //arguments.putString("urlImagem);

                    fragmentDetalheParceiro.setArguments(arguments);
                    FragmentTransaction transaction = getFragmentManager()
                            .beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.fragmentTelaPrincipal, fragmentDetalheParceiro);
                    transaction.commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

   // Toast.makeText(rootView.getContext(), id_clicado.getText().toString(), Toast.LENGTH_SHORT).show();

  public class Parceiro {

        public String id;
        public String nome;
        public String urlImagem;


        public Parceiro() {

        }
    }

    public String[] converteArray(List<String> ar){
        String[] convertido = new String[ar.size()];
        try {
            convertido = ar.toArray(convertido);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertido;
    }

}


