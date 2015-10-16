package com.foodmaster.fernandoh.foodmaster;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class CadastroActivity extends Fragment {

    public CadastroActivity(){}
    Button btCad;
    TextView txtNome;
    TextView txtEmail;
    TextView txtCpf ;
    TextView txtSenha ;
    String urlServidor = "http://foodmastercm.pe.hu/webservice/addusuario";
    String respostaServidor;
    ProgressBar pb;
    ArrayList<String> campos = new ArrayList<String>();
    ArrayList<String> valores = new ArrayList<String>();
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.activity_cadastro, container, false);
            btCad = (Button) rootView.findViewById(R.id.btCadCadastrar);
            txtNome = (TextView) rootView.findViewById(R.id.txtLoginEmail);
            txtEmail = (TextView) rootView.findViewById(R.id.txtLoginSenha);
            txtCpf = (TextView) rootView.findViewById(R.id.txtCadCpf);
            txtSenha = (TextView) rootView.findViewById(R.id.txtCadSenha);
            //pb = (ProgressBar) rootView.findViewById(R.id.progressBar1);


        btCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaUsuario();
            }
        });


        return rootView;
    }


        public void salvaUsuario() {
            try{

                campos.add("nome") ;
                campos.add("email");
                campos.add("cpf");
                campos.add("senha");

                valores.add(txtNome.getText().toString());
                valores.add(txtEmail.getText().toString());
                valores.add(txtCpf.getText().toString());
                valores.add(txtSenha.getText().toString());

                WebServicePost.ParamentrosPost p = new WebServicePost.ParamentrosPost(Util.url_cadastrar,campos,valores);
                WebServicePost wb = new WebServicePost();
                String resultado = null;
                try {
                    resultado = wb.execute(p).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if(resultado.contains("Sucesso")) {
                    Toast.makeText(getActivity(), "Usuario Cadastrado", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Erro no cadastro", Toast.LENGTH_SHORT).show();
                }

            }
            catch(Exception ex)
            {

            }
        }

    }
