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
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz11','fr', 'Il n''y a pas de bonne ligne droite dans les rues de La Alberca. Les routes sont sinueuses, les lignes des façades sont brisées et il est facile de se perdre dans ses rues, ses chemins et ses ruelles qui montent, descendent ou s''entrecroisent. De temps en temps, ils offrent la surprise d''une fontaine où l''on peut s''asseoir, boire un bon verre d''eau et admirer la beauté des maisons environnantes.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz12','fr', 'Certains disent que sa structure urbaine est celle d''un quartier juif, en raison du caractère labyrinthique et secret de ses rues, mais d''autres, en se promenant dans la ville, l''ont associée à la banlieue de Damas. Ce n''est pas pour rien que ses rues pavées et ses bâtiments en bois et en pierre en ont fait le premier village d''Espagne à être déclaré Monument historique-artistique national en 1940.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz21','fr', 'Les façades des maisons de La Alberca sont à la fois étranges et audacieuses. Tout d''abord, le rez-de-chaussée : un mur de granit moisi est créé avec des pierres de taille de granit ou des pierres irrégulières jointes avec du mortier, où s''ouvrent deux portes : une étroite qui donne accès aux étages supérieurs de la maison, et une plus large, destinée à fermer l''écurie. ');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz22','fr', 'Il y a des maisons dont l''accès se fait au niveau de la rue, mais il y a des résidences dont la porte est surélevée, accessible par des escaliers en granit, appelés \"conventinos\". La porte de l''écurie, connue sous le nom de \"vallipuerta\", s''ouvre toujours vers l''intérieur et vers la droite, car elle était utilisée pour que le cavalier, lorsqu''il descendait de cheval, puisse accéder à l''écurie sans problème.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz23','fr', 'Le reste de la maison est construit au rez-de-chaussée : un premier et un deuxième étage, et une dépendance. Ces planchers sont soutenus par d''épaisses poutres en châtaignier, entourées de cadres géométriques en bois remplis de pierre ou d''adobe. Chacune des maisons est en saillie par rapport à la précédente, ce qui signifie qu''en s''élevant, la maison est en saillie, donnant la sensation que les étages supérieurs sont soutenus dans l''air.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz11','fr', 'S''il y a une chose qui fait que La Alberca se distingue, outre son architecture, c''est son savoir-faire.  Les vacances sont le moment idéal pour admirer les magnifiques costumes traditionnels portés par les hommes et les femmes et les rares broderies qui ornent les rues.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz12','fr', 'Outre les costumes traditionnels, la poterie de la région a permis de créer d''intéressants bijoux et autres pièces d''or, d''argent et de corail, qui sont utilisés comme amulettes depuis des générations, pour la fertilité, pour le soin des enfants, contre le mauvais œil...');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz13','fr', 'En revanche, la broderie serrano se distingue, avec des images de bouquets de fleurs ou d''animaux mythologiques, de poissons et d''oiseaux, ainsi que d''autres motifs religieux, qui la différencient de celles réalisées dans d''autres régions.');");
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
