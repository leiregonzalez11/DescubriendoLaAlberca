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
        String query2 = "CREATE TABLE IF NOT EXISTS artesania (idArte INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "namePag TEXT NOT NULL, idioma VARCHAR(2) NOT NULL, nombreTraje VARCHAR, descr VARCHAR NOT NULL UNIQUE)";
        Log.d("Tabla artesania", query2);
        sqLiteDatabase.execSQL(query2);

        //Esquema de la tabla gastronomia
        String query3 = "CREATE TABLE IF NOT EXISTS gastronomia (idGastro INTEGER PRIMARY KEY AUTOINCREMENT, " +
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

        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz31','en','vistas','The vistas suit is perhaps the most interesting and richest in Spain, as well as being the oldest. It is difficult to determine the date of its origin, as even in the village there is no one who knows its origins, although some old women claim that it is of Jewish origin. It is of absolute chastity, as it hides the woman''s anatomy and conceals her forms. It was originally a wedding dress, but over time it has lost its bridal character to become a festive garment linked to the celebration of processions and offertories.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz33','en','vistas','This suit from La Alberca has inspired artists, painters such as Sorolla, sculptors and photographers such as Ortiz Echagüe, who portrays the women of La Alberca in all their splendour. The great masters such as Givenchy and Galiano have also drunk from this source of the mountain village, because the richness of its forms, the superimposition of fabrics and jewels and the singular way in which it sits on the woman''s body suggest a multitude of ideas.');");


        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz11','ca', 'No hi ha una bona recta pels carrers de La Alberca. Es corben els camins, es trenquen les línies de les façanes i és fàcil perdre''s pels seus carrers, camins i carrerons que pugen, baixen o s''entrecreuen. De tant en tant, ofereixen la sorpresa d''una font on poder asseure''t, beure un bon glop d''aigua i admirar la bellesa de les cases que l''envolten.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz12','ca', 'Hi ha qui diu que la seva estructura urbana és la d''una jueria, pel laberíntic i secret dels seus carrers, però altres, en recórrer el poble, l''han associat amb els ravals de Damasc. No en va, els seus carrers empedrats i els seus edificis de fusta i pedra, li van servir per a convertir-se en el primer poble d''Espanya declarat Monument Historicoartístic Nacional l''any 1940.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz21','ca', 'Les façanes de les cases en La Alberca són estranyes, alhora que atrevides. Primerament, la planta baixa: es crea un mur de granit rovellat amb carreus de granit o pedres irregulars unides amb argamassa, on s''obren dues portes grans: un estret que dona accés a les plantes superiors de l''habitatge, i un més ample, destinat a tancar la quadra.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz22','ca', 'Existeixen cases l''accés de les quals està a peu de carrer, però existeixen residències la porta de les quals està elevada, accedint a ella mitjançant unes escales de granit, denominades “conventinos”. La porta gran de la quadra, denominat “vallipuerta”, s''obre sempre cap a dins i a la dreta, ja que servia perquè quan el genet desmuntés el cavall, pogués accedir a la quadra sense cap problema.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz23','ca', 'Sobre la planta baixa s''alça la resta de la casa: una primera i una segona planta, i un sobrat. Aquestes plantes se sustenten mitjançant gruixudes bigues de castanyer, tancat amb geomètrics entramats de fusta farcits de pedra o de tova. Cadascuna de les cases sobresurt de l''anterior, la qual cosa provoca que, a mesura que la casa va ascendint, la casa vagi sobresortint, donant la sensació que les plantes superiors se sustenten en l''aire.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz31','ca', 'En la quadra és on habitaven els animals del dia a dia: muls, cabres, gallines, porcs, etc. Els animals tenien un paper fonamental en aquella vida: no sols servien d''aliment, també eren una font indispensable de calor gràcies a la qual la casa es mantenia calenta en una societat en la qual no existien l''electricitat, la calefacció ni l''aigua corrent.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz32','ca', 'Sota l''escala, s''emmagatzemava la palla i els farratges per als animals i el fons de la quadra podia usar-se com a magatzem de fem o com a celler per a guardar el vi i l''oli, en època de recollida de raïm i olives. També aprofitaven aquest espai per a resguardar les seves pertinences de valor en la tinaja de l''or, una petita ''caixa forta'' que tenien totes les cases en la quadra.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz33','ca', 'En el primer pis es trobaven les habitacions més íntimes i molt importants a la casa albercana: la sala de diari i la sala bona. Ambdues eren sales grans on podia haver-hi una o diverses alcoves. La sala de diari era més humil, on la família dormia. La sala bona era una estada més especial, ja que en ella succeïen els esdeveniments que marcaven la vida dels albercanos: el naixement, la celebració del convit del bateig, el banquet de les noces, la mort i la vetlla de la persona morta…');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz34','ca', 'En el segon pis es desenvolupava la vida diària de la casa. En ella es trobava la cuina, el camp casa, una habitació espaiosa que s''utilitzava com a replanell després de pujar els empinats esglaons de fusta desiguals fins a la planta alta de la casa i per a guardar tot el fruit recollit del camp, el quart’l salaero, on es preparaven els xoriços i els lloms i se salaven els pernils, el rebost i un parell de sales amb una alcova cadascuna.');");
        sqLiteDatabase.execSQL("INSERT INTO arquitectura (namePag, idioma, descr) VALUES ('interfaz35','ca', 'Finalment, el sobrao. Aquesta planta solia tenir diversos usos: algunes cases que no disposaven de xemeneia en altres plantes, utilitzaven aquesta estada per a posar el forn on fer pa, precisament aprofitant la teulada per a la sortida de fum. El sobrao també servia com a assecador per a assecar les castanyes o els pernils, o com a magatzem per a guardar la llenya o altres utensilis i productes menys utilitzats en el dia a dia.');");

        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz11','ca', 'Si hi ha una cosa en la qual destaca La Alberca, a part de per l''arquitectura, és per la seva artesania. Els dies festius són el moment adequat per a admirar els preciosos vestits típics d''homes i dones i els rars brodats serrans amb què engalanen els carrers.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz12','ca', 'Acompanyant la vestimenta tradicional, la terrisseria de la zona ha creat interessants joies i altres joies d''or, plata i coral, que s''usen com a amulets des de fa generacions, per a la fertilitat, la cura dels fills, contra el mal d''ull…');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz13','ca', 'D''altra banda, destaca el brodat serrà, amb imatges de rams florals o d''animals mitològics, peixos i ocells, juntament amb altres motius religiosos, que els diferencien dels realitzats en altres comarques.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz21','ca','Els brodats serrans s''aplicaven en teixits per a la llar, en la vestimenta popular o en draps rituals i estovalles per a altars, entre altres. Els animals, mitològics o reals (aixetes i dracs i algun gall, papallona, senglar, mussol o gos), s''entrellacen amb el vegetal, també l''arbre de la vida.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz22','ca','Cada animal representat té un significat, per exemple, l''àguila bicèfala representa el matrimoni; el lleó, la virilitat; l''ocell d''un cap, la dona; el cor del qual sali la flor, l''amor; l''arbre de la vida, la salut i la longevitat, etc.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz23','ca','Altres trets del brodat serrà són l''absència de figures humanes i de motius geomètrics, deixant entreveure la influència de civilitzacions orientals antigues, renaixentistes i morisques. Una altra dada interessant és el llenguatge dels colors emprats: el blau s''emprava per a amortallar als difunts, mentre que els vermells es brodaven en la roba de llit dels jovençans. A mitjan segle XX, s''incorporen nous colors: malves, morats, verda herba, blau cobalt, salmó, entre altres.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz24','ca','Els materials bàsics per als brodats eren el lli i la llana. Amb el pas dels anys, aquests van ser substituïts pel bri de seda, i més tard va arribar el cotó (l''anomenat moliné) que juntament amb la seda han arribat als nostres dies. Per a realitzar els teixits, fins al segle XIX s''empraven telers manuals, utilitzant la tècnica mixta, a fils comptats i a dibuix. Una vegada desapareguts, es van començar a usar teixits industrials amb altres fibres.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz41','ca', 'El vestit masculí és completament diferent al de dona. El primer que es posen els albercanos és un camisón ample de blanc llenç. El coll alt del camisón llueix un dibuix marcat a punt de creu, al qual les labranderas diuen ullet groc, tancant-ho per davant amb un bessó format per dos inflats botons d''or.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz42','ca', 'Sobre els calçotets llargs, fets també de lli, va els calçons de vellut blau o negre. Es tanca en la cintura, per davant, amb el arzapón, que sosté un botó de plata, i amb una prima corretja de pell de cabra, que arrenca dels ronyons, es nua en el melic. Els calçons s''estreny cames a baix, i pot cenyir-se sota els genolls amb botonadura de plata o quedar obert, deixant solts els botons i penjolls de plata, anomenats fullatges, perquè campanillee en caminar. Hi ha un altre tipus de calçons, anomenat bombacho, que no s''estreny en els genolls, sinó que està sempre obert i s''emporta amb botes altes.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz43','ca', 'Per a cobrir les cames, utilitzen les calces, que solen ser de fil blanc, sense peu. Sobre les sabates van les polaines de drap en color cru. No pot faltar l''armilla, que és de vellut (blau o negre) amb doble filera de botons de plata, una per a tancar el pit, encara que solament es botona fins a la meitat, i una altra per a adornar. La jaqueta, tala de rínxol o de vellut, va sobre l''armilla, amb fermalls de plata.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, descr) VALUES ('interfaz44','ca', 'Finalment el cap se cenyeix amb un mocador de seda, a l''estil moro, podent afegir un barret damunt d''aquest. I la cintura, amb una faixa vermella acabada en serrells que cauen per davant.');");


        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz31','ca','manteo','El vestit albercano de manteo o mantita,recorda al de Candelario, encara que el albercano és més majestuós i típic. Les seves principals peces són: el gipó, de vellut negre o llaurat en color; una faldilla, anomenada refeix, tallada en forma de manteo, oberta per darrere i després una altra sobreposada en tons foscos. Porta la saya una franja de vellut llaurat, que es coneix en El Safareig amb el nom de \"tirana\" i acaba amb un fistó calat. El brodat és a base de mostacilla o trencilla.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz32','ca','manteo','La mandila, brodada en mostacilla o trencilla també, està feta de tela forta i en la part baixa porta un volant en seda o ras. Diuen que la mandila continua amb la tradició del bernio, no com a peça protectora contra la brutícia, sinó com a garant de la fertilitat i virginitat.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz33','ca','manteo','A continuació es col·loca el mantó de Manila, i, sobre aquest, es posen més o menys collarets d''or o plata daurada, dels quals pengen creus, sants, veneres (medalles esmaltades), tortugues, reliquiaris… Les mitjanes són, generalment blanques i calades. Les sabates, de vellut o xarol amb sivella de plata.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz31','ca','zagalejo','El vestit de zagalejo és una modalitat del vestit de manteo. Està format pel gipó de vellut negre amb granadures en els punys; la corbata de tela blanca brodada i la faldilla de drap vermell, brodada amb granadures i lluentons i adornada amb caragols i sobreposats.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz32','ca','zagalejo','El mocador d''arrebosso es col·loca sobre el pit, i, sobre ell, els fils o collarets d''or que la família, dins de les seves possibilitats, posseeix. Estan formats per comptes petits d''arboços o aceitunillas, dels quals pengen tortugues, creus...');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz33','ca','zagalejo','Sobre la faldilla es posa el següent: per la part davantera la mandila, de drap negre que tapa pràcticament el farraco de vellut, i, per la part posterior, l''anomenada cinta gran de darrere de vellut negre i brodada. A diferència del manteo, el zagalejo no porta mantilla, i sí una cinta de trossa que es col·loca sobre el rodete, pentinat característic d''aquest vestit.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz31','ca','vistas','El vestit de Vistas potser és el més interessant i ric d''Espanya, a més de ser el més antic. És difícil poder arribar a concretar la data del seu origen, ja que, ni fins i tot al poble hi ha qui sàpiga els orígens, encara que algunes ancianes afirmen que és d''origen jueu. És d''una castedat absoluta, ja que amaga l''anatomia de la dona i oculta les seves formes. Originalment era un vestit de núvia que amb el temps ha perdut el seu caràcter nupcial per a convertir-se en una indumentària festiva vinculada a la celebració de processons i ofertorios.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz32','ca','vistas','El més singular del Vestit de Vistas és, sense cap dubte, la seva joieria. El conjunt està format principalment per corals i plata, en el qual s''inclouen reliquiaris, patenas, medalles, crucifixos, i tot tipus de dijeros, aconseguint un pes de prop de 10 quilos. Aquesta joieria està carregada de simbologies religioses i d''elements de protecció enfront del mal, les desgràcies o la malaltia.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz33','ca','vistas','Aquest vestit albercano ha inspirat a artistes, pintors com Sorolla, escultors, fotògrafs, com Ortiz Echagüe, que retrata a les dones del Safareig en tota la seva esplendor. També han begut d''aquesta font del poble serrà els grans mestres com Givenchy o Galiano, perquè la riquesa de les seves formes, la superposició de les teles i joies i el singular de la seva manera d''assentar-se en el cos de la dona donen per a suggerir multitud d''idees.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz31','ca','ventioseno','El de ventioseno és l''autèntic vestit de respecte, propi de cerimònies de gravetat, per al dol i per a anar al temple. Es compon de manteo negre, amb nosa blava; mitjanes de cotó blanques o de color, preferentment encarnades; pañuelito de pit i damunt mantita severa. En ell tot és auster i greu.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz32','ca','ventioseno','El que dona to i solemnitat al vestit consisteix en un ampli mantell que, cobrint el cap, caient sobre les espatlles i tota la part posterior, fixa sobre el front una graciosa i impressionant borla de seda, de bellesa clàssica, per la seva originalitat.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (namePag, idioma, nombreTraje, descr) VALUES ('interfaz33','ca','ventioseno','El manteo de dol, denominat manteo de vores, és de negre drap fi, sense adorns, ni sobreposats. S''usava amb el ventioseno i constituïa un conjunt imponent, propi dels duels.');");


        sqLiteDatabase.execSQL("INSERT INTO gastronomia (namePag, idioma, descr) VALUES ('interfaz11','ca','Puede decirse que la gastronomía de la Sierra de Francia, y por tanto de La Alberca, descansa en las carnes y embutidos, curados al aire serrano, así como en las muy famosas patatas meneás o el cabrito cuchifrito, sin olvidarnos de sus inigualables turrones o sus deliciosas almendras garrapiñadas.');");
        sqLiteDatabase.execSQL("INSERT INTO gastronomia (namePag, idioma, descr) VALUES ('interfaz12','ca','Así mismo, son muy apreciados los hornazos, empanada a base de embutidos, o su limón serrano, hecho con limón, naranja, huevo duro y chorizo. Sus vinos, cosechas de la comarca y los dulces de extraordinaria calidad realizados con productos naturales, obleas, perrunillas, miel y polen tienen también mucho protagonismo en la gastronomía de este entrañable pueblo.');");

    }

    public String[] obtenerDatosInterfaz(String idioma, String interfaz, String tabla, int numTV){

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

    public String[] obtenerDatosInterfazTrajes(String idioma, String interfaz, String tabla, int numTV, String nombreTraje){

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

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS arquitectura");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS artesania");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS gastronomia");
        onCreate(sqLiteDatabase);

    }
}
