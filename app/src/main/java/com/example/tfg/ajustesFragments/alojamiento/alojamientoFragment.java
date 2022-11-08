package com.example.tfg.ajustesFragments.alojamiento;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfg.R;

public class alojamientoFragment extends Fragment {

    public alojamientoFragment() {
        // Required empty public constructor
    }

    public static alojamientoFragment newInstance() {
        alojamientoFragment fragment = new alojamientoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alojamiento, container, false);
    }
}