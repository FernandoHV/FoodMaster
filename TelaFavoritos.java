package com.foodmaster.fernandoh.foodmaster;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class TelaFavoritos extends Fragment {

    public TelaFavoritos() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tela_favoritos, container, false);

        return rootView;
    }




}