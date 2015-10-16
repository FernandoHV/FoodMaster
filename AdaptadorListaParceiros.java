package com.foodmaster.fernandoh.foodmaster;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class AdaptadorListaParceiros extends ArrayAdapter<String>{

        String[] nomes_parceiros;
        String[] nomes_parceiros2;
        String[] image_url;
        String[] idParceiro;
        Context context;
        Bitmap resultado;

public AdaptadorListaParceiros(Activity context, String[] urlsImagens, String[] nomeParceiro, String[] idParceiro){
        super(context, R.layout.linha_item_parceiro, nomeParceiro);
        // TODO Auto-generated constructor stub
        this.nomes_parceiros = nomeParceiro;
        this.idParceiro = idParceiro;
        this.image_url = urlsImagens;
        this.context = context;
        }
@Override
public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.linha_item_parceiro, null,
                        true);
                viewHolder.nomeParceiro = (TextView) convertView.findViewById(R.id.nomeParceiro);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                viewHolder.idParceiro = (TextView) convertView.findViewById(R.id.idParceiro);
                convertView.setTag(viewHolder);
        } else{
                viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nomeParceiro.setText(nomes_parceiros[position]);
        viewHolder.idParceiro.setText(idParceiro[position]);
        //viewHolder.imageView.setImageResource(R.drawable.sem_imagem);
        Glide.with(getContext()).load(image_url[position])
                .placeholder(R.drawable.loading)
                .crossFade()
                .thumbnail((float) 0.1)
                .error(R.drawable.sem_imagem)
                .into(viewHolder.imageView);



        return convertView;

        }

        static class ViewHolder{
                TextView nomeParceiro;
                TextView idParceiro;
                ImageView imageView ;
        }

}

