package com.example.tfg.categoriasFragments.principal;

import static java.lang.Thread.sleep;

import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.tfg.GestorDB;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.widget.VideoView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.navigationmenu.Categorias;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class tradicionesInicio extends Fragment {

    private Bundle args;
    private String idioma, categoria, nombreTrad;
    private StorageReference storageRef;
    VideoView videoView, videoView2;
    Button btnPlay1, btnPlay2;

    public tradicionesInicio() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
            categoria = getArguments().getString("categoria");
        }

        args = new Bundle();
        args.putString("idioma", idioma);
        args.putString("categoria", categoria);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tradiciones, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(v -> {

            myToolbar.setNavigationIcon(null);
            Fragment fragment = new Categorias();

            // Obtenemos el administrador de fragmentos a través de la actividad
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

            // Definimos una transacción
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Remplazamos el contenido principal por el fragmento
            fragmentTransaction.replace(R.id.relativelayout, fragment);
            fragmentTransaction.addToBackStack(null);

            // Cambiamos el fragment en la interfaz
            fragmentTransaction.commit();
        });

        TextView titulo = requireView().findViewById(R.id.titulotrad);
        TextView text1 = requireView().findViewById(R.id.trad11);
        TextView text2 = requireView().findViewById(R.id.trad12);
        TextView text3 = requireView().findViewById(R.id.trad13);
        TextView text4 = requireView().findViewById(R.id.trad14);
        TextView text5 = requireView().findViewById(R.id.trad15);
        TextView text6 = requireView().findViewById(R.id.trad16);
        TextView text7 = requireView().findViewById(R.id.trad17);
        TextView text8 = requireView().findViewById(R.id.trad18);
        videoView = requireView().findViewById(R.id.videoView1);
        videoView2 = requireView().findViewById(R.id.videoView2);
        btnPlay1 = requireView().findViewById(R.id.play1);
        btnPlay2 = requireView().findViewById(R.id.play2);

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerInfoTrad(idioma, "inicio", categoria, 1);

        text1.setText(datos[0]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        Spinner spinner = requireView().findViewById(R.id.spinnerTrad);
        String [] tradiciones = getResources().getStringArray(R.array.tradiciones);
        spinner.setAdapter(new SpinnerAdapter(requireContext(), R.layout.dropdownitemtradiciones, tradiciones));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                nombreTrad = (String) adapterView.getItemAtPosition(position);
                titulo.setText(nombreTrad);
                if (nombreTrad.equalsIgnoreCase("la moza de ánimas")) {

                    videoView.setVisibility(View.VISIBLE);
                    videoView2.setVisibility(View.VISIBLE);
                    btnPlay1.setVisibility(View.VISIBLE);
                    btnPlay2.setVisibility(View.VISIBLE);

                    String nombreTradBBDD = nombreTrad.toLowerCase().replaceAll(" ", "");
                    String[] textoTrad = dbHelper.obtenerInfoTrad(idioma, nombreTradBBDD, categoria, 7);
                    text2.setText(textoTrad[0]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text3.setText(textoTrad[1]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text4.setText(textoTrad[2]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text5.setText(textoTrad[3]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text6.setText(textoTrad[4]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text7.setText(textoTrad[5]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text8.setText(textoTrad[6]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

                    Uri uri = Uri.parse("android.resource://"+ requireActivity().getPackageName()+"/"+R.raw.mozadeanimasprimeraparte);
                    Uri uri2 = Uri.parse("android.resource://"+ requireActivity().getPackageName()+"/"+R.raw.mozadeanimassegundaparte);
                    videoView.setVideoURI(uri);
                    videoView2.setVideoURI(uri2);

                    btnPlay1.setOnClickListener(view1 -> {
                        videoView.start();
                        videoView2.pause();
                        videoView2.seekTo(0);

                    });
                    btnPlay2.setOnClickListener(view12 -> {
                        videoView.pause();
                        videoView.seekTo(0);
                        videoView2.start();
                    });
                }
                else{
                    videoView.setVisibility(View.GONE);
                    videoView2.setVisibility(View.GONE);
                    btnPlay1.setVisibility(View.GONE);
                    btnPlay2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        storageRef = FirebaseStorage.getInstance().getReference();

    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, VideoView video) {
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(video::setVideoURI);

    }

}