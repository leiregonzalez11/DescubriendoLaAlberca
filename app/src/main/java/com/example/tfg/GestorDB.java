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
            cargarDatos(sqLiteDatabase);
            cargarDatosCatalanyFrances(sqLiteDatabase);
        } catch (IOException e) {
            e.printStackTrace();
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

    private void cargarDatos(SQLiteDatabase sqLiteDatabase) throws IOException {

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

    private void cargarDatosCatalanyFrances(SQLiteDatabase sqLiteDatabase) throws IOException {

        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz11','ca', 'No hi ha una bona recta pels carrers de La Alberca. Es corben els camins, es trenquen les línies de les façanes i és fàcil perdre''s pels seus carrers, camins i carrerons que pugen, baixen o s''entrecreuen. De tant en tant, ofereixen la sorpresa d''una font on poder asseure''t, beure un bon glop d''aigua i admirar la bellesa de les cases que l''envolten.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz12','ca', 'Hi ha qui diu que la seva estructura urbana és la d''una jueria, pel laberíntic i secret dels seus carrers, però altres, en recórrer el poble, l''han associat amb els ravals de Damasc. No en va, els seus carrers empedrats i els seus edificis de fusta i pedra, li van servir per a convertir-se en el primer poble d''Espanya declarat Monument Historicoartístic Nacional l''any 1940.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz21','ca', 'Les façanes de les cases en La Alberca són estranyes, alhora que atrevides. Primerament, la planta baixa: es crea un mur de granit rovellat amb carreus de granit o pedres irregulars unides amb argamassa, on s''obren dues portes grans: un estret que dona accés a les plantes superiors de l''habitatge, i un més ample, destinat a tancar la quadra.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz22','ca', 'Existeixen cases l''accés de les quals està a peu de carrer, però existeixen residències la porta de les quals està elevada, accedint a ella mitjançant unes escales de granit, denominades “conventinos”. La porta gran de la quadra, denominat “vallipuerta”, s''obre sempre cap a dins i a la dreta, ja que servia perquè quan el genet desmuntés el cavall, pogués accedir a la quadra sense cap problema.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz23','ca', 'Sobre la planta baixa s''alça la resta de la casa: una primera i una segona planta, i un sobrat. Aquestes plantes se sustenten mitjançant gruixudes bigues de castanyer, tancat amb geomètrics entramats de fusta farcits de pedra o de tova. Cadascuna de les cases sobresurt de l''anterior, la qual cosa provoca que, a mesura que la casa va ascendint, la casa vagi sobresortint, donant la sensació que les plantes superiors se sustenten en l''aire.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz11','ca', 'Si hi ha una cosa en la qual destaca La Alberca, a part de per l''arquitectura, és per la seva artesania. Els dies festius són el moment adequat per a admirar els preciosos vestits típics d''homes i dones i els rars brodats serrans amb què engalanen els carrers.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz12','ca', 'Acompanyant la vestimenta tradicional, la terrisseria de la zona ha creat interessants joies i altres joies d''or, plata i coral, que s''usen com a amulets des de fa generacions, per a la fertilitat, la cura dels fills, contra el mal d''ull…');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz13','ca', 'D''altra banda, destaca el brodat serrà, amb imatges de rams florals o d''animals mitològics, peixos i ocells, juntament amb altres motius religiosos, que els diferencien dels realitzats en altres comarques.');");
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
