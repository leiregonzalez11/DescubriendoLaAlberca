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
    private static final int DB_VERSION = 2;
    private final Context context;
    private boolean seguir = true;

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
            cargarDatosCatalan(sqLiteDatabase);
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
        String query2 = "CREATE TABLE IF NOT EXISTS artesania (idArqui INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "namePag TEXT NOT NULL, idioma VARCHAR(2) NOT NULL, nombreTraje VARCHAR, descr VARCHAR NOT NULL UNIQUE)";
        Log.d("Tabla artesania", query2);
        sqLiteDatabase.execSQL(query2);

        //Esquema de la tabla gastronomia
        String query3 = "CREATE TABLE IF NOT EXISTS gastronomia (idArqui INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "namePag TEXT NOT NULL, idioma VARCHAR(2) NOT NULL, descr VARCHAR NOT NULL)";
        Log.d("Tabla gastronomia", query3);
        sqLiteDatabase.execSQL(query3);

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

    private void cargarDatosCatalan(SQLiteDatabase sqLiteDatabase) throws IOException{

        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz11','ca', 'No hi ha una bona recta pels carrers de La Alberca. Es corben els camins, es trenquen les línies de les façanes i és fàcil perdre''s pels seus carrers, camins i carrerons que pugen, baixen o s''entrecreuen. De tant en tant, ofereixen la sorpresa d''una font on poder asseure''t, beure un bon glop d''aigua i admirar la bellesa de les cases que l''envolten.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz12','ca', 'Hi ha qui diu que la seva estructura urbana és la d''una jueria, pel laberíntic i secret dels seus carrers, però altres, en recórrer el poble, l''han associat amb els ravals de Damasc. No en va, els seus carrers empedrats i els seus edificis de fusta i pedra, li van servir per a convertir-se en el primer poble d''Espanya declarat Monument Historicoartístic Nacional l''any 1940.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz21','ca', 'Les façanes de les cases en La Alberca són estranyes, alhora que atrevides. Primerament, la planta baixa: es crea un mur de granit rovellat amb carreus de granit o pedres irregulars unides amb argamassa, on s''obren dues portes grans: un estret que dona accés a les plantes superiors de l''habitatge, i un més ample, destinat a tancar la quadra.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz22','ca', 'Existeixen cases l''accés de les quals està a peu de carrer, però existeixen residències la porta de les quals està elevada, accedint a ella mitjançant unes escales de granit, denominades “conventinos”. La porta gran de la quadra, denominat “vallipuerta”, s''obre sempre cap a dins i a la dreta, ja que servia perquè quan el genet desmuntés el cavall, pogués accedir a la quadra sense cap problema.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz23','ca', 'Sobre la planta baixa s''alça la resta de la casa: una primera i una segona planta, i un sobrat. Aquestes plantes se sustenten mitjançant gruixudes bigues de castanyer, tancat amb geomètrics entramats de fusta farcits de pedra o de tova. Cadascuna de les cases sobresurt de l''anterior, la qual cosa provoca que, a mesura que la casa va ascendint, la casa vagi sobresortint, donant la sensació que les plantes superiors se sustenten en l''aire.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz11','ca', 'Si hi ha una cosa en la qual destaca La Alberca, a part de per l''arquitectura, és per la seva artesania. Els dies festius són el moment adequat per a admirar els preciosos vestits típics d''homes i dones i els rars brodats serrans amb què engalanen els carrers.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz12','ca', 'Acompanyant la vestimenta tradicional, la terrisseria de la zona ha creat interessants joies i altres joies d''or, plata i coral, que s''usen com a amulets des de fa generacions, per a la fertilitat, la cura dels fills, contra el mal d''ull…');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz13','ca', 'D''altra banda, destaca el brodat serrà, amb imatges de rams florals o d''animals mitològics, peixos i ocells, juntament amb altres motius religiosos, que els diferencien dels realitzats en altres comarques.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz21','ca','Els brodats serrans s''aplicaven en teixits per a la llar, en la vestimenta popular o en draps rituals i estovalles per a altars, entre altres. Els animals, mitològics o reals (aixetes i dracs i algun gall, papallona, senglar, mussol o gos), s''entrellacen amb el vegetal, també l''arbre de la vida.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz22','ca','Cada animal representat té un significat, per exemple, l''àguila bicèfala representa el matrimoni; el lleó, la virilitat; l''ocell d''un cap, la dona; el cor del qual sali la flor, l''amor; l''arbre de la vida, la salut i la longevitat, etc.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz23','ca','Altres trets del brodat serrà són l''absència de figures humanes i de motius geomètrics, deixant entreveure la influència de civilitzacions orientals antigues, renaixentistes i morisques. Una altra dada interessant és el llenguatge dels colors emprats: el blau s''emprava per a amortallar als difunts, mentre que els vermells es brodaven en la roba de llit dels jovençans. A mitjan segle XX, s''incorporen nous colors: malves, morats, verda herba, blau cobalt, salmó, entre altres.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz24','ca','Els materials bàsics per als brodats eren el lli i la llana. Amb el pas dels anys, aquests van ser substituïts pel bri de seda, i més tard va arribar el cotó (l''anomenat moliné) que juntament amb la seda han arribat als nostres dies. Per a realitzar els teixits, fins al segle XIX s''empraven telers manuals, utilitzant la tècnica mixta, a fils comptats i a dibuix. Una vegada desapareguts, es van començar a usar teixits industrials amb altres fibres.');");
        sqLiteDatabase.execSQL("INSERT INTO gastronomia (namePag, idioma, descr) VALUES ('interfaz11','ca','Puede decirse que la gastronomía de la Sierra de Francia, y por tanto de La Alberca, descansa en las carnes y embutidos, curados al aire serrano, así como en las muy famosas patatas meneás o el cabrito cuchifrito, sin olvidarnos de sus inigualables turrones o sus deliciosas almendras garrapiñadas.');");
        sqLiteDatabase.execSQL("INSERT INTO gastronomia (namePag, idioma, descr) VALUES ('interfaz12','ca','Así mismo, son muy apreciados los hornazos, empanada a base de embutidos, o su limón serrano, hecho con limón, naranja, huevo duro y chorizo. Sus vinos, cosechas de la comarca y los dulces de extraordinaria calidad realizados con productos naturales, obleas, perrunillas, miel y polen tienen también mucho protagonismo en la gastronomía de este entrañable pueblo.');");

    }

    public String[] obtenerDatosInterfazSencilla(String idioma, String interfaz, String tabla, int numTV){

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

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS arquitectura");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS artesania");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS gastronomia");
        onCreate(sqLiteDatabase);

    }
}
