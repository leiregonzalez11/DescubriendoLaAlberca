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

public class GestorDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "laAlbercaDB";
    private static final int DB_VERSION = 1;
    private final Context context;
    private boolean seguir = true;

    public GestorDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        if (sqLiteDatabase != null){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS arquitectura");
        }
        assert sqLiteDatabase != null;
        crearTablas(sqLiteDatabase);
        try {
            cargarDatosArquitectura(sqLiteDatabase);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("NO CARGAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }

    }

    private void crearTablas(SQLiteDatabase sqLiteDatabase){

        //TABLA ARQUITECTURA
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS arquitectura (idArqui INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "namePag TEXT NOT NULL, idioma TEXT NOT NULL, descr TEXT NOT NULL)");

        //TABLA ARTESANIA
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS artesania (idArte INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "namePag TEXT NOT NULL, idioma TEXT NOT NULL, descr TEXT NOT NULL)");
    }

    private void cargarDatosArquitectura(SQLiteDatabase sqLiteDatabase) throws IOException {

        //Carga de datos desde un archivo .txt usando res/raw
        assert context != null;
        InputStream file = context.getResources().openRawResource(R.raw.datos);
        BufferedReader buffer = new BufferedReader((new InputStreamReader(file)));

        while (seguir){
            try{
                String query = buffer.readLine();
                Log.d("Query: ", query);
                sqLiteDatabase.execSQL(query);
            } catch (Exception e){
                seguir = false;
                buffer.close();            }
        }
    }

    public String[] obtenerDatosInterfazSencilla(String idioma, String interfaz, String tabla){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        int i = 0;
        String [] descr = new String[4];

        Cursor c = sqLiteDatabase.rawQuery("SELECT descr FROM " + tabla + " WHERE namePag LIKE '" + interfaz + "%' AND idioma = '" + idioma + "';", null);
        while (c.moveToNext()){
            descrip = c.getString(0);
            descr[i] = descrip;
            i++;
        }
        c.close();
        return descr;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
