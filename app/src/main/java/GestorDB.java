import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tfg.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GestorDB extends SQLiteOpenHelper{

    @SuppressLint("StaticFieldLeak")
    private static GestorDB sInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static final String DATABASE_NAME = "laAlbercaDB";
    private static final int DATABASE_VERSION = 1;


    /**
     * Constructor should be private to prevent direct instantiation.
     */
    private GestorDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        GestorDB.context = context;

    }

    public static synchronized GestorDB getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new GestorDB(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLitedatabase){
        try {
            crearTablas(sqLitedatabase);
            insertarDatos(sqLitedatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public void crearTablas(SQLiteDatabase sqLiteDatabase) throws IOException{

        //Carga de datos desde un archivo .txt usando res/raw
        InputStream file = context.getResources().openRawResource(R.raw.tablas);
        BufferedReader buffer = new BufferedReader((new InputStreamReader(file)));
        boolean seguir = true;

        while (seguir){
            try{
                String query = buffer.readLine();
                Log.d("Query: ", query);
                sqLiteDatabase.execSQL(query);
            }catch (Exception e){
                seguir = false;
                buffer.close();
            }
        }

    }

    public void insertarDatos(SQLiteDatabase sqLiteDatabase) throws IOException {

        //Carga de datos desde un archivo .txt usando res/raw
        InputStream file = context.getResources().openRawResource(R.raw.datos);
        BufferedReader buffer = new BufferedReader((new InputStreamReader(file)));
        boolean seguir = true;

        while (seguir){
            try{
                String query = buffer.readLine();
                Log.d("Query: ", query);
                sqLiteDatabase.execSQL(query);
            }catch (Exception e){
                seguir = false;
                buffer.close();
            }
        }

    }

    public String [] obtenerdatosCategoria (String categoria){

        SQLiteDatabase sqLiteDatabase = sInstance.getWritableDatabase();

        //Obtenemos el número de filas de la categoria seleccionada
        int numfilas = 0;
        Cursor c1 = sqLiteDatabase.rawQuery("SELECT COUNT(id) FROM \'" + categoria + "\' );", null);
        while (c1.moveToNext()) {
            numfilas = c1.getInt(0);
        }
        c1.close();

        String name = "";
        int i = 0;
        String [] nombresCat = new String[numfilas];

        Cursor c2 = sqLiteDatabase.rawQuery("SELECT nombre FROM  \'" + categoria + "\' ORDER BY nombre ASC;", null);
        while (c2.moveToNext()){
            name = c2.getString(0);
            nombresCat[i] = name;
            i++;
        }

        return nombresCat;
    }

}