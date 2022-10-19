package com.example.tfg;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GestorDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "laAlbercaDB";
    private static final int DB_VERSION = 4;
    private final Context context;
    private boolean seguir = true;
    int numAloj;

    public GestorDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        assert sqLiteDatabase != null;
        crearTablas(sqLiteDatabase);
        try {
            cargarDatos(sqLiteDatabase);
            cargarDatosConComillas(sqLiteDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void crearTablas(SQLiteDatabase sqLiteDatabase){


        //Esquema de la tabla arquitectura
        String query1 = "CREATE TABLE IF NOT EXISTS arquitectura (idArqui INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "namePag TEXT NOT NULL, idioma VARCHAR(2) NOT NULL, descr VARCHAR NOT NULL UNIQUE)";
        Log.d("Tabla arquitectura", query1);
        sqLiteDatabase.execSQL(query1);

        //Esquema de la tabla artesania
        String query2 = "CREATE TABLE IF NOT EXISTS artesania (idArte INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "namePag TEXT NOT NULL, idioma VARCHAR(2) NOT NULL, nombreTraje VARCHAR, descr VARCHAR NOT NULL UNIQUE)";
        Log.d("Tabla artesania", query2);
        sqLiteDatabase.execSQL(query2);

        //Esquema de la tabla gastronomia
        String query3 = "CREATE TABLE IF NOT EXISTS gastronomia (idGastro INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "namePag VARCHAR NOT NULL, idioma VARCHAR(2) NOT NULL, descr VARCHAR NOT NULL)";
        Log.d("Tabla gastronomia", query3);
        sqLiteDatabase.execSQL(query3);

        //Esquema de la tabla rutas
        String query4 = "CREATE TABLE IF NOT EXISTS rutas (idRuta INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idioma VARCHAR(2) NOT NULL, nombreRuta VARCHAR NOT NULL, descRuta VARCHAR NOT NULL, " +
                "distancia VARCHAR NOT NULL, desnivel VARCHAR NOT NULL, dificultad VARCHAR NOT NULL, " +
                "tiempo VARCHAR NOT NULL)";
        Log.d("Tabla rutas", query4);
        sqLiteDatabase.execSQL(query4);

        //Esquema de la tabla otros lugares
        String query5 = "CREATE TABLE IF NOT EXISTS otroslugares (idOtros INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idioma VARCHAR(2) NOT NULL, nombreOtro VARCHAR NOT NULL, descrOtro VARCHAR NOT NULL, " +
                "kmdesdeLa VARCHAR NOT NULL, fiestamayor VARCHAR, latLugar VARCHAR, lonLugar VARCHAR)";
        Log.d("Tabla otros lugares", query5);
        sqLiteDatabase.execSQL(query5);

        //Esquema de la tabla alojamiento
        String query6 = "CREATE TABLE IF NOT EXISTS alojamiento (idAloj INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "categoriaAloj VARCHAR NOT NULL, nombreAloj VARCHAR NOT NULL, ubiAloj VARCHAR NOT NULL, " +
                "latAloj VARCHAR NOT NULL, lonAloj VARCHAR NOT NULL)";
        Log.d("Tabla alojamiento", query6);
        sqLiteDatabase.execSQL(query6);

        //Esquema de la tabla restaurantes
        String query7 = "CREATE TABLE IF NOT EXISTS restaurante (idRest INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idioma VARCHAR NOT NULL, categoriaRest VARCHAR NOT NULL, nombreRest VARCHAR NOT NULL, descRest VARCHAR NOT NULL, " +
                "ubiRest VARCHAR NOT NULL, latRest VARCHAR NOT NULL, lonRest VARCHAR NOT NULL)";
        Log.d("Tabla Restaurantes", query7);
        sqLiteDatabase.execSQL(query7);

        //Esquema de la tabla comercio
        String query8 = "CREATE TABLE IF NOT EXISTS comercio (idCom INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "categoriaCom VARCHAR NOT NULL, nombreCom VARCHAR NOT NULL," +
                "ubiCom VARCHAR NOT NULL, latCom VARCHAR NOT NULL, lonCom VARCHAR NOT NULL)";
        Log.d("Tabla Comercio", query8);
        sqLiteDatabase.execSQL(query8);

    }

    private void cargarDatos(SQLiteDatabase sqLiteDatabase) throws IOException {

        //Carga de datos desde un archivo .txt usando res/raw
        assert context != null;
        InputStream file = context.getResources().openRawResource(R.raw.datos);
        BufferedReader buffer = new BufferedReader((new InputStreamReader(file)));

        while (seguir){
            try{
                String query = buffer.readLine();
                Log.d("Query: ", query);
                if (!query.contains("/")) {
                    sqLiteDatabase.execSQL(query);
                }
            } catch (Exception e){
                seguir = false;
                buffer.close();            }
        }
    }

    private void cargarDatosConComillas(SQLiteDatabase sqLiteDatabase) throws IOException{

        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz31','en','vistas','The vistas suit is perhaps the most interesting and richest in Spain, as well as being the oldest. It is difficult to determine the date of its origin, as even in the village there is no one who knows its origins, although some old women claim that it is of Jewish origin. It is of absolute chastity, as it hides the woman''s anatomy and conceals her forms. It was originally a wedding dress, but over time it has lost its bridal character to become a festive garment linked to the celebration of processions and offertories.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz33','en','vistas','This suit from La Alberca has inspired artists, painters such as Sorolla, sculptors and photographers such as Ortiz Echagüe, who portrays the women of La Alberca in all their splendour. The great masters such as Givenchy and Galiano have also drunk from this source of the mountain village, because the richness of its forms, the superimposition of fabrics and jewels and the singular way in which it sits on the woman''s body suggest a multitude of ideas.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz31','en','manteo','The Albercan suit of manteo or mantita is reminiscent of Candelario''s suit, although the Albercan suit is more majestic and typical. Its main garments are: the doublet, made of black or coloured velvet; a skirt, called refajo, cut in the shape of a manteo, open at the back and then another one over it in dark colours. The skirt has a strip of carved velvet, which is known in La Alberca as \"tirana\" and ends with an openwork scallop. The embroidery is based on mustacilla or braid.');");
    }

    public String[] obtenerDescrInterfaz(String idioma, String interfaz, String tabla, int numTV){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        int i = 0;
        String [] descr = new String[numTV];

        Cursor c = sqLiteDatabase.rawQuery("SELECT descr FROM " + tabla + " WHERE namePag LIKE '" + interfaz + "%' AND idioma = '" + idioma + "';", null);
        while (c.moveToNext()){
            descrip = c.getString(0);
            descr[i] = descrip;
            i++;
        }
        c.close();
        return descr;
    }

    public String[] obtenerDatosTrajes(String idioma, String interfaz, String tabla, int numTV, String nombreTraje){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        int i = 0;
        String [] descr = new String[numTV];

        Cursor c = sqLiteDatabase.rawQuery("SELECT descr FROM " + tabla + " WHERE namePag LIKE '" + interfaz + "%' " +
                "AND idioma = '" + idioma + "' AND nombreTraje = '" + nombreTraje + "';", null);
        while (c.moveToNext()){
            descrip = c.getString(0);
            descr[i] = descrip;
            i++;
        }
        c.close();
        return descr;
    }

    public String[] obtenerDatosRutas(String idioma, String nombreRuta, String tabla){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        int i = 0;
        String [] descr = new String[5];

        Cursor c = sqLiteDatabase.rawQuery("SELECT descRuta, distancia, desnivel, dificultad, tiempo FROM " + tabla + "" +
                " WHERE idioma = '" + idioma + "' AND nombreRuta = '" + nombreRuta + "';", null);
        while (c.moveToNext()){
            for (int j = 0; j < 5; j++){
                descrip = c.getString(j);
                System.out.println("DESCRIIIIIIIP" + descrip);
                descr[i] = descrip;
                i++;
            }
        }
        c.close();
        return descr;
    }

    public ArrayList<String> obtenerlistaAlojamientos(String tabla, String categoriaAloj){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        ArrayList<String> descr = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("SELECT nombreAloj FROM " + tabla + "" +
                " WHERE categoriaAloj = '" + categoriaAloj + "';", null);
        while (c.moveToNext()){
            descrip = c.getString(0);
            System.out.println("DESCRIIIIIIIP" + descrip);
            descr.add(descrip);
        }
        c.close();
        return descr;
    }

    public String[] obtenerDatosAloj(String tabla, String nombreAloj){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        String [] descr = new String[3];

        Cursor c = sqLiteDatabase.rawQuery("SELECT latAloj, lonAloj, ubiAloj FROM " + tabla + "" +
                " WHERE nombreAloj = '" + nombreAloj + "';", null);
        while (c.moveToNext()){
            for (int j = 0; j < 3; j++){
                descrip = c.getString(j);
                System.out.println("DESCRIIIIIIIP" + descrip);
                descr[j] = descrip;
            }
        }
        c.close();
        return descr;
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS arquitectura");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS artesania");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS gastronomia");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS otroslugares");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS rutas");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS alojamiento");
        onCreate(sqLiteDatabase);

    }
}
