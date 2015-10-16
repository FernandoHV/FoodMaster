package com.foodmaster.fernandoh.foodmaster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.foodmaster.fernandoh.foodmaster.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends Activity {
    ArrayList<String> campos = new ArrayList<String>();
    ArrayList<String> valores = new ArrayList<String>();
    TextView txtEmail;
    TextView txtSenha;
    TextView txtResultado;
    Button btLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //remover
        /*Intent intent = new Intent();
        intent.setClass(LoginActivity.this, telaPrincipal.class);
        startActivity(intent);*/

        btLogin = (Button) findViewById(R.id.btLogin);
        txtEmail = (TextView) findViewById(R.id.txtLoginEmail);
        txtSenha = (TextView) findViewById(R.id.txtLoginSenha);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progress = new ProgressDialog(LoginActivity.this);
                progress.setMessage("Fazendo Login...");
                progress.show();
                fazLogin();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


        public void fazLogin() {
            try{

                campos.add("email") ;
                campos.add("senha");

                valores.add(txtEmail.getText().toString());
                valores.add(txtSenha.getText().toString());

                WebServicePost.ParamentrosPost p = new WebServicePost.ParamentrosPost(Util.url_login,campos,valores);
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
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, telaPrincipal.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Erro no login",Toast.LENGTH_SHORT).show();
                }

            }
            catch(Exception ex)
            {

            }

        }
}
