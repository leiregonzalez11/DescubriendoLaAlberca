package com.example.tfg;

import android.annotation.SuppressLint;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.io.BufferedReader;
import android.content.Context;
import android.database.Cursor;
import java.io.InputStreamReader;
import androidx.annotation.Nullable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.tfg.mapOptions.otrosLugares.pueblos.Pueblo;
import com.example.tfg.categorias.secundarias.gastronomia.Receta;

public class GestorDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "BBDDprueba1";
    private static final int DB_VERSION = 24;
    private final Context context;
    @SuppressLint("StaticFieldLeak")
    private static GestorDB sInstance;
    private boolean seguir = true;

    private GestorDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public static synchronized GestorDB getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new GestorDB(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        assert sqLiteDatabase != null;
        //Establecemos el encoding a UTF-8
        sqLiteDatabase.execSQL("PRAGMA encoding=\"UTF-8\";");
        try {
            //Creamos las tablas de la BBDD
            crearTablas(sqLiteDatabase);
            //Cargamos los datos en la BBDD
            cargarDatos(sqLiteDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS arquitectura");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS artesania");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS gastronomia");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS otroslugares");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS rutas");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tradiciones");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS historia");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS monumento");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS fiesta");
        onCreate(sqLiteDatabase);

    }

    public void crearTablas(SQLiteDatabase sqLiteDatabase) {

        //Esquema de la tabla arquitectura
        String query = "CREATE TABLE IF NOT EXISTS arquitectura (idArqui INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "catArqui VARCHAR NOT NULL, idioma VARCHAR(2) NOT NULL, descrArqui VARCHAR NOT NULL)";
        Log.d("Tabla Arquitectura: ", query);
        sqLiteDatabase.execSQL(query);

        //Esquema de la tabla artesania
        query = "CREATE TABLE IF NOT EXISTS artesania (idArte INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "catArte VARCHAR NOT NULL, idioma VARCHAR(2) NOT NULL, nombreTraje VARCHAR, descrArte VARCHAR NOT NULL)";
        Log.d("Tabla Artesania: ", query);
        sqLiteDatabase.execSQL(query);

        //Esquema de la tabla gastronomia
        query = "CREATE TABLE IF NOT EXISTS gastronomia (idGastro INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "catGastro VARCHAR NOT NULL, idioma VARCHAR(2) NOT NULL, descrGastro VARCHAR NOT NULL, " +
                "nombreReceta VARCHAR, ingrReceta VARCHAR, pasosReceta VARCHAR)";
        Log.d("Tabla Gastronomia: ", query);
        sqLiteDatabase.execSQL(query);

        //Esquema de la tabla rutas
        query = "CREATE TABLE IF NOT EXISTS rutas (idRuta INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idioma VARCHAR(2) NOT NULL, nombreRuta VARCHAR NOT NULL, descRuta VARCHAR NOT NULL, " +
                "distancia VARCHAR NOT NULL, desnivel VARCHAR NOT NULL, dificultad VARCHAR NOT NULL, " +
                "tiempo VARCHAR NOT NULL)";
        Log.d("Tabla Rutas: ", query);
        sqLiteDatabase.execSQL(query);

        //Esquema de la tabla otros lugares
        query = "CREATE TABLE IF NOT EXISTS otroslugares (idOtros INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "categoriaOtros VARCHAR NOT NULL, idioma VARCHAR(2) NOT NULL, nombreOtro VARCHAR NOT NULL, descrOtro VARCHAR NOT NULL, " +
                "kmdesdeLa VARCHAR, fiestamayor VARCHAR, latLugar VARCHAR, lonLugar VARCHAR)";
        Log.d("Tabla Otros Lugares: ", query);
        sqLiteDatabase.execSQL(query);

        //Esquema de la tabla tradiciones
        query = "CREATE TABLE IF NOT EXISTS tradiciones (idTrad INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombreTrad VARCHAR NOT NULL, idioma VARCHAR(2) NOT NULL, descr VARCHAR NOT NULL)";
        Log.i("Tabla Tradiciones: ", query);
        sqLiteDatabase.execSQL(query);

        //Esquema de la tabla historia
        query = "CREATE TABLE IF NOT EXISTS historia (idHist INTEGER PRIMARY KEY AUTOINCREMENT, catHistoria VARCHAR NOT NULL," +
                "idioma VARCHAR(2) NOT NULL, descHistoria VARCHAR NOT NULL)";
        Log.i("Tabla Historia: ", query);
        sqLiteDatabase.execSQL(query);

        //Esquema de la tabla monumento
        query = "CREATE TABLE IF NOT EXISTS monumento (idMon INTEGER PRIMARY KEY AUTOINCREMENT, categoriaMon VARCHAR, nombreMon VARCHAR NOT NULL," +
                "idioma VARCHAR(2) NOT NULL, descMon VARCHAR NOT NULL)";
        Log.i("Tabla monumento: ", query);
        sqLiteDatabase.execSQL(query);

        //Esquema de la tabla fiesta
        query = "CREATE TABLE IF NOT EXISTS fiesta (idFiesta INTEGER PRIMARY KEY AUTOINCREMENT, idioma VARCHAR NOT NULL, nombreFiesta VARCHAR NOT NULL," +
                "descrFiesta VARCHAR NOT NULL)";
        Log.i("Tabla Historia: ", query);
        sqLiteDatabase.execSQL(query);

    }

    private void cargarDatos(SQLiteDatabase sqLiteDatabase) throws IOException {

        //Carga de datos desde un archivo .txt usando res/raw
        assert context != null;
        InputStream file = context.getResources().openRawResource(R.raw.inserts);
        BufferedReader buffer = new BufferedReader((new InputStreamReader(file)));

        while (seguir){
            try{
                String query = buffer.readLine();
                System.out.println("Query: " + query);
                Log.d("Query: ", query);

                if (!query.contains("//")) {
                    sqLiteDatabase.execSQL(query);
                }
            } catch (Exception e){
                seguir = false;
                buffer.close();
            }
        }
    }

    //Tabla Arquitectura
    public String[] obtenerDatosArqui(String idioma, String categoria, int numTV){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        int i = 0;
        String [] descr = new String[numTV];

        Cursor c = sqLiteDatabase.rawQuery("SELECT descrArqui FROM arquitectura WHERE catArqui LIKE '" + categoria + "%' AND idioma = ?;", new String[] {idioma});
        while (c.moveToNext()){
            descrip = c.getString(0);
            descr[i] = descrip;
            i++;
        }
        c.close();
        return descr;
    }

    //Tabla Artesania
    public String[] obtenerDatosArte(String idioma, String categoria, int numTV){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        int i = 0;
        String [] descr = new String[numTV];

        Cursor c = sqLiteDatabase.rawQuery("SELECT descrArte FROM artesania WHERE catArte LIKE '" + categoria + "%' AND idioma = '" + idioma + "';", null);
        while (c.moveToNext()){
            descrip = c.getString(0);
            descr[i] = descrip;
            i++;
        }
        c.close();
        return descr;
    }

    public String[] obtenerDatosTrajes(String idioma, String categoria, int numTV, String nombreTraje){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        int i = 0;
        String [] descr = new String[numTV];

        Cursor c = sqLiteDatabase.rawQuery("SELECT descrArte FROM artesania WHERE catArte LIKE '" + categoria + "%' " +
                "AND idioma = ? AND nombreTraje = ?;", new String[]{idioma, nombreTraje});
        while (c.moveToNext()){
            descrip = c.getString(0);
            descr[i] = descrip;
            i++;
        }
        c.close();
        return descr;
    }

    //Tabla Rutas
    public String[] obtenerDatosRutas(String idioma, String nombreRuta){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        int i = 0;
        String [] descr = new String[5];

        Cursor c = sqLiteDatabase.rawQuery("SELECT descRuta, distancia, desnivel, dificultad, tiempo FROM rutas WHERE idioma = ? AND nombreRuta = ?;", new String[]{idioma, nombreRuta});
        while (c.moveToNext()){
            for (int j = 0; j < 5; j++){
                descrip = c.getString(j);
                descr[i] = descrip;
                i++;
            }
        }
        c.close();
        return descr;
    }

    //Tabla Gastronomia
    public String[] obtenerDescrGastro(String idioma, String categoria, int numTV){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        int i = 0;
        String [] descr = new String[numTV];

        Cursor c = sqLiteDatabase.rawQuery("SELECT descrGastro FROM gastronomia WHERE catGastro LIKE '" + categoria + "%' " +
                "AND idioma = '" + idioma + "';", null);
        while (c.moveToNext()){
            descrip = c.getString(0);
            descr[i] = descrip;
            i++;
        }
        c.close();
        return descr;
    }

    public LinkedList<Receta> obtenerRecetas(String idioma){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        LinkedList<Receta> recetas = new LinkedList<>();
        Cursor c = sqLiteDatabase.rawQuery("SELECT nombreReceta, descrGastro, ingrReceta, pasosReceta FROM gastronomia WHERE idioma = ? AND nombreReceta IS NOT NULL", new String[]{idioma});
        while (c.moveToNext()){
            Receta receta = new Receta();
            receta.setNombreReceta(c.getString(0));
            receta.setDescrReceta(c.getString(1));
            receta.setIngredientes(c.getString(2));
            receta.setPasos(c.getString(3));
            recetas.add(receta);
        }
        c.close();
        return recetas;
    }

    //Tabla Tradiciones
    public String[] obtenerInfoTrad(String idioma, String nombreTrad, int numTV){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        int i = 0;
        String [] descr = new String[numTV];

        Cursor c = sqLiteDatabase.rawQuery("SELECT descr FROM tradiciones WHERE nombreTrad LIKE '" + nombreTrad + "%'" +
                "AND idioma = ?;", new String [] {idioma});
        while (c.moveToNext()){
            descrip = c.getString(0);
            descr[i] = descrip;
            i++;
        }
        c.close();
        return descr;
    }

    //Tabla Otros lugares
    public LinkedList<Pueblo> obtenerPueblos (String idioma){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        LinkedList<Pueblo> pueblos = new LinkedList<>();

        Cursor c = sqLiteDatabase.rawQuery("SELECT nombreOtro, descrOtro, kmdesdeLa, fiestamayor, latLugar, lonLugar FROM otrosLugares" +
                " WHERE categoriaOtros = 'pueblo' AND idioma = ?;", new String[]{idioma});
        while (c.moveToNext()){
            Pueblo pueblo = new Pueblo();
            pueblo.setNombrePueblo(c.getString(0));
            pueblo.setDescrPueblo(c.getString(1));
            pueblo.setKmDesdeLA(c.getString(2));
            pueblo.setFiestamayor(c.getString(3));
            pueblo.setLatitud(Double.parseDouble(c.getString(4)));
            pueblo.setLongitud(Double.parseDouble(c.getString(5)));
            pueblos.add(pueblo);
        }
        c.close();
        return pueblos;
    }

    public Double [] obtenerUbiLugares (String categoria){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Double [] descr = new Double[2];

        Cursor c = sqLiteDatabase.rawQuery("SELECT latLugar, lonLugar FROM otrosLugares WHERE categoriaOtros = '" + categoria + "' AND nombreOtro = 'principal';", null);
        while (c.moveToNext()){
            for (int j = 0; j < 2; j++){
                c.getString(j);
                descr[j] = Double.parseDouble(c.getString(j));
            }
        }

        c.close();
        return descr;
    }

    public String [] obtenerInfoLugares (String idioma, String lugar, String categoria, Integer numTV){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        String [] descr = new String[numTV];
        int i = 0;

        Cursor c = sqLiteDatabase.rawQuery("SELECT descrOtro FROM otrosLugares WHERE categoriaOtros LIKE '" + categoria + "' AND idioma = '" + idioma + "' AND nombreOtro = '" + lugar + "';", null);
        while (c.moveToNext()){
            descrip = c.getString(0);
            descr[i] = descrip;
            i++;
        }
        c.close();
        return descr;
    }

    //Tabla Historia
    public String[] obtenerInfoHist(String catHist, String idioma, int numTV) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        String [] descr = new String[numTV];
        int i=0;

        Cursor c = sqLiteDatabase.rawQuery("SELECT descHistoria FROM historia " +
                "WHERE catHistoria = ? AND idioma = ?;", new String[]{catHist, idioma});
        while (c.moveToNext()){
            descrip = c.getString(0);
            System.out.println(descrip);
            System.out.println(i);
            descr[i] = descrip;
            i++;
            System.out.println(i);
        }
        c.close();
        return descr;
    }

    //Tabla Monumentos
    public String[] obtenerInfoMonumentos(String idioma, String nombreMonumento, int numTV) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        String [] descr = new String[numTV];
        int i=0;

        Cursor c = sqLiteDatabase.rawQuery("SELECT descMon FROM monumento " +
                "WHERE nombreMon = ? AND idioma = ?;", new String[]{nombreMonumento, idioma});
        while (c.moveToNext()){
            descrip = c.getString(0);
            descr[i] = descrip;
            i++;
        }
        c.close();
        return descr;
    }

    public String[] obtenerDatosFiestas(String idioma, String nombreFiesta, int numTV) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        String [] descr = new String[numTV];
        int i=0;

        Cursor c = sqLiteDatabase.rawQuery("SELECT descrFiesta FROM fiesta " +
                "WHERE nombreFiesta = ? AND idioma = ?;", new String[]{nombreFiesta, idioma});
        while (c.moveToNext()){
            descrip = c.getString(0);
            descr[i] = descrip;
            i++;
        }
        c.close();
        return descr;

    }
}
