package com.example.tfg.navigationmenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tfg.R;
import com.example.tfg.adapters.SliderAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;


public class FragmentInicio extends Fragment {

    TextView texto;

    public FragmentInicio() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        texto = requireView().findViewById(R.id.bienvenidatextPrueba);

        //SLIDER
        SliderView sliderView = requireView().findViewById(R.id.imageSliderPrueba);
        int[] images = new int[]{R.drawable.laalberca1, R.drawable.laalberca2, R.drawable.laalberca3, R.drawable.laalberca4};
        SliderAdapter adapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(adapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SCALE_DOWN);
        sliderView.setScrollTimeInSec(3);
        sliderView.startAutoCycle();

    }

    public String determinarIdioma() {

        String idioma = null;
        String text = texto.getText().toString();

        if (text.contains("¡Bienvenid@s")){
            idioma = "es";
        } else if (text.contains("Ongi")){
            idioma = "eu";
        }else if (text.contains("Welcome")){
            idioma="en";
        }else if (text.contains("Benvinguts")){
            idioma="ca";
        }

        return idioma;
    }


}