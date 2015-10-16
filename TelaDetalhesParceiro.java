package com.foodmaster.fernandoh.foodmaster;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TelaDetalhesParceiro extends Fragment {

    ArrayList<String> campos = new ArrayList<String>();
    ArrayList<String> valores = new ArrayList<String>();
    View rootView;
    TextView txtJsonDetalhes;
    TextView txtNomeParceiro;

    public TelaDetalhesParceiro() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_tela_detalhe_parceiro, container, false);
        Bundle args = getArguments();
        String id_selecionado = args.getString("id_parceiro");

        TextView txt_id = (TextView) rootView.findViewById(R.id.txtId );
        txtJsonDetalhes = (TextView) rootView.findViewById(R.id.txtResultadoDetalhes );
        txtNomeParceiro = (TextView) rootView.findViewById(R.id.txtNomeParceiro );
        //TextView txt_nome = (TextView) rootView.findViewById(R.id.txtNomeParceiro );

        txt_id.setText(id_selecionado);
        buscaDetalhesParceiro(id_selecionado);

        txtJsonDetalhes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //preencheCampos(txtJsonDetalhes.getText().toString());
                txtJsonDetalhes.setVisibility(View.GONE);
            }
        });



        //código para voltar
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().getFragmentManager().popBackStack();
                    return true;
                } else {
                    return false;
                }
            }
        });





        return rootView;
    }
    public void buscaDetalhesParceiro(String id_parceiro) {

        campos.add("id_parceiro");
        valores.add(id_parceiro);

        WebServicePost.ParamentrosPost p = new WebServicePost.ParamentrosPost(Util.url_detalheParceiro,campos,valores);
        WebServicePost wb = new WebServicePost();
        String resultado = null;
        try {
            resultado = wb.execute(p).get();
            preencheCampos(resultado);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void preencheCampos(String s){
        ImageView imagem = (ImageView) rootView.findViewById(R.id.img_Parceiro);
        try {

            ArrayList detalhes =  leJsonParceiroDetalhes(s);
            for (int i = 0; i < detalhes.size();i++){
                ParceiroDetalhes valores = (ParceiroDetalhes) detalhes.get(i);

                Glide.with(getActivity()).load(valores.urlImagem)
                        .placeholder(R.drawable.loading)
                        .crossFade()
                        .error(R.drawable.sem_imagem)
                        .into(imagem);
                txtNomeParceiro.setText(valores.nome);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        public ArrayList<ParceiroDetalhes> leJsonParceiroDetalhes(String str){
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();

            JsonArray jArray = null;
            Type collectionType = null;
            jArray = parser.parse(str).getAsJsonArray();
            collectionType = new TypeToken<ArrayList<ParceiroDetalhes>>() {
                }.getType();
            ArrayList<ParceiroDetalhes> enums = gson.fromJson(jArray, collectionType);
            return enums;
     }

public class ParceiroDetalhes{
       String nome;
       String urlImagem;
       String uf;
       String cidade;
       String bairro;
       String endereco;
       String numero;
       String ddd;
       String telefone;
    }
}