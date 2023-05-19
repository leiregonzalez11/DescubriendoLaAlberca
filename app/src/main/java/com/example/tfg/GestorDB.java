package com.example.tfg;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import com.example.tfg.EspacioDelViajero.alojamiento.Alojamiento;
import com.example.tfg.EspacioDelViajero.comercio.Comercio;
import com.example.tfg.EspacioDelViajero.restauracion.Establecimiento;
import com.example.tfg.Categorias.secundarias.gastronomia.Receta;
import com.example.tfg.Maps.otrosLugares.pueblos.Pueblo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class GestorDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "BBDDprueba1";
    private static final int DB_VERSION = 14;
    private final Context context;
    private boolean seguir = true;

    public GestorDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        assert sqLiteDatabase != null;
        //Establecemos el encoding a UTF-8
        sqLiteDatabase.execSQL("PRAGMA encoding=\"UTF-8\";");
        //Creamos las tablas de la BBDD
        crearTablas(sqLiteDatabase);
        //Cargamos los datos en la BBDD
        try {
            cargarDatos(sqLiteDatabase);
            cargarDatosConComillas(sqLiteDatabase);
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS restaurante");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS rutas");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tradiciones");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS alojamiento");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS comercio");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS historia");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS monumento");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS fiesta");
        onCreate(sqLiteDatabase);

    }

    private void crearTablas(SQLiteDatabase sqLiteDatabase){


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

        //Esquema de la tabla alojamiento
        query = "CREATE TABLE IF NOT EXISTS alojamiento (idAloj INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "categoriaAloj VARCHAR NOT NULL, nombreAloj VARCHAR NOT NULL UNIQUE, ubiAloj VARCHAR NOT NULL, " +
                "puntuacion DOUBLE NOT NULL, numTel VARCHAR NOT NULL)";
        Log.i("Tabla Alojamiento: ", query);
        sqLiteDatabase.execSQL(query);

        //Esquema de la tabla restaurantes
        query = "CREATE TABLE IF NOT EXISTS restaurante (idRest INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "categoriaRest VARCHAR NOT NULL, nombreRest VARCHAR UNIQUE NOT NULL, numTel VARCHAR NOT NULL, " +
                "ubiRest VARCHAR NOT NULL, puntuacion DOUBLE NOT NULL)";
        Log.i("Tabla Restaurantes: ", query);
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
                "fechaFiesta VARCHAR, descrFiesta VARCHAR NOT NULL)";
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

    private void cargarDatosConComillas(SQLiteDatabase sqLiteDatabase) throws IOException{
        sqLiteDatabase.execSQL("INSERT INTO historia (catHistoria, idioma, descHistoria) VALUES ('cat2', 'en', 'In the Modern Age, La Alberca played an important role in the Spanish War of Independence, during which the inhabitants of the town joined the resistance forces against the French invader. In 1812, French troops invaded La Alberca, sacking and burning several houses and public buildings as they passed, and stealing a large number of valuable objects, such as silverware and paintings, which belonged to the church and the town''s wealthiest families.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (catArte, idioma, nombreTraje, descrArte) VALUES ('trajes1','en','vistas','The vistas suit is perhaps the most interesting and richest in Spain, as well as being the oldest. It is difficult to determine the date of its origin, as even in the village there is no one who knows its origins, although some old women claim that it is of Jewish origin. It is of absolute chastity, as it hides the woman''s anatomy and conceals her forms. It was originally a wedding dress, but over time it has lost its bridal character to become a festive garment linked to the celebration of processions and offertories.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (catArte, idioma, nombreTraje, descrArte) VALUES ('trajes3','en','vistas','This suit from La Alberca has inspired artists, painters such as Sorolla, sculptors and photographers such as Ortiz Echagüe, who portrays the women of La Alberca in all their splendour. The great masters such as Givenchy and Galiano have also drunk from this source of the mountain village, because the richness of its forms, the superimposition of fabrics and jewels and the singular way in which it sits on the woman''s body suggest a multitude of ideas.');");
        sqLiteDatabase.execSQL("INSERT INTO artesania (catArte, idioma, nombreTraje, descrArte) VALUES ('trajes1','en','manteo','The Albercan suit of manteo or mantita is reminiscent of Candelario''s suit, although the Albercan suit is more majestic and typical. Its main garments are: the doublet, made of black or coloured velvet; a skirt, called refajo, cut in the shape of a manteo, open at the back and then another one over it in dark colours. The skirt has a strip of carved velvet, which is known in La Alberca as \"tirana\" and ends with an openwork scallop. The embroidery is based on mustacilla or braid.');");
        sqLiteDatabase.execSQL("INSERT INTO otroslugares (categoriaOtros, idioma, nombreOtro, descrOtro, kmdesdeLa, fiestamayor, latLugar, lonLugar) VALUES ('pueblo','en','sanmartindelcastañar','Declared a Historic-Artistic Site in 1982, this typically mountain village and its natural surroundings are part of the Biosphere Reserve of the Sierra de Francia and the Sierra de Las Quilamas. Its 15th-century castle has been restored and houses a museum and the Sierra de Francia Visitor Reception and Interpretation Centre. The old parade ground of the castle has been converted into the municipality''s traditional bullring, built with rough granite \"burladeros\" dating from the 17th century, making it the second oldest bullring in Spain.','11 km','August 10th', 40.5222349, -6.0646428);");
        sqLiteDatabase.execSQL("INSERT INTO otroslugares (categoriaOtros, idioma, nombreOtro, descrOtro) VALUES ('peñadefrancia','en','balcóndesantiago','Another very popular spot in the Sanctuary and no less curious is the Balcón de Santiago (Santiago''s Balcony). Hanging over the so-called Salto del Niño, this is an exceptional viewpoint over the Sierra de Francia and the town of La Alberca, where the Spanish apostle is remembered.');");
        sqLiteDatabase.execSQL("INSERT INTO otroslugares (categoriaOtros, idioma, nombreOtro, descrOtro) VALUES ('peñadefrancia','en','balcóndesantiago','The history of this place tells us that, on 20 July 1439, stonemasons extracting rocks for the construction of the Sanctuary found an image of St. James at this spot, which can now be visited in the chapel dedicated to the apostle');");
        sqLiteDatabase.execSQL("INSERT INTO otroslugares (categoriaOtros, idioma, nombreOtro, descrOtro) VALUES ('peñadefrancia','en','capillaexteriordelsantocristo','The chapel of Santo Cristo is the furthest to the west, on the edge of a promontory of large rocks overlooked by the pilgrim''s cross. Its structure is in keeping with the rugged environment that surrounds it. Inside, a cleft in the rock indicates the place where the image was found.');");
        sqLiteDatabase.execSQL("INSERT INTO otroslugares (categoriaOtros, idioma, nombreOtro, descrOtro) VALUES ('peñadefrancia','en','navederecha','In the right-hand nave of the church are the chapel of Santiago, the access to the Virgin''s main chapel and the sacristy. In the chapel of Santiago is the image of Santiago, a beautiful Romanesque carving 110 cm high, wearing a long tunic over which he wears a short cloak with an opening on the sides, which was found by some stonemasons in 1439.');");
        sqLiteDatabase.execSQL("INSERT INTO otroslugares (categoriaOtros, idioma, nombreOtro, descrOtro) VALUES ('peñadefrancia','en','convento','From the cloister, and on two floors, all the convent''s rooms were built: the dining room, the library, dormitories, etc. The left side of the cloister leads to the convent dining room built in 1740, which is now an exhibition hall in the Peña de Francia, and the right side leads to one of the naves of the church.');");
        sqLiteDatabase.execSQL("INSERT INTO otroslugares (categoriaOtros, idioma, nombreOtro, descrOtro) VALUES ('peñadefrancia','en','historia','The number of religious of the community established in La Peña grew rapidly and took root in the area. Already in 1516, the year in which the foundation stone of La Casa Baja was laid, the community had 22 religious. However, the number of Dominican monks in La Peña dwindled at the beginning of the 19th century and the community disappeared in 1835, when all the monastery''s assets were seized by Mendizábal''s confiscation and the monks were dispersed.');");
        sqLiteDatabase.execSQL("INSERT INTO otroslugares (categoriaOtros, idioma, nombreOtro, descrOtro) VALUES ('peñadefrancia','en','capilladelablanca','Inside, with a ribbed vault, there was a remarkable gilded altarpiece in the 18th century, which disappeared, like many other valuable works, at the time of the disentailment of Mendizabal. In its place there is now a stone relief, quite deteriorated, which represents the unveiling of the image of the Virgin with the clothes and adornments with which she was adorned at that time. In addition to the altarpiece, there is also the entrance to the Virgin''s grotto, a small chapel with a classical altar and, to the left, the grotto where Simón Vela found the image.');");
        sqLiteDatabase.execSQL("INSERT INTO otroslugares (categoriaOtros, idioma, nombreOtro, descrOtro) VALUES ('peñadefrancia','en','elrollo','On the Rollo of Peña de Francia, to which the cross was later added, the coat of arms of John II and the coat of arms of the Dominican order and emblem of the sanctuary can be seen in bas-relief: a vase of lilies. In addition, there is also the figure of a man imprisoned by the neck and feet, symbolising the Lord of La Peña''s power of jurisdiction.');\n");
        sqLiteDatabase.execSQL("INSERT INTO tradiciones (nombreTrad, idioma, descr) VALUES ('lasalboradas','en', 'The profane alborada, which is sung, above all, on the occasion of a wedding or marriage, the night before the celebration, when the guests sing it in front of the bride''s and groom''s houses.');");
        sqLiteDatabase.execSQL("INSERT INTO tradiciones (nombreTrad, idioma, descr) VALUES ('elpendón','en', 'To this day, the original banner is preserved in the village''s parish archive, and it is said that the original banner still retains blood from the battle, which tradition attributes to the women cutting off the testicles of some of the soldiers of the Portuguese troops.');");
        sqLiteDatabase.execSQL("INSERT INTO gastronomia (catGastro, idioma, nombreReceta, descrGastro, ingrReceta, pasosReceta) VALUES ('receta', 'en', 'waggishpotatoes', 'This is the funny name of this dish whose main ingredient is potatoes, and although it has spread throughout much of Spain, patatas meneás is a little gem of Salamanca''s gastronomy. These potatoes are a very simple dish, both in terms of ingredients and preparation, and can be served as an excellent tapa or even as a garnish for meat or fish, replacing the classic homemade mashed potato and giving it a special touch with paprika. The crispy and particularly tasty counterpoint is provided by the fried bacon, which traditionally crowns this delicious dish.', '400gr of potatoes,120gr of bacon,Salt,1 bay leaf,4 cloves of garlic,Olive oil,De la Vera Paprika', '1. Peel the potatoes, wash them and cut them into pieces. Boil them in water with a spoonful of salt and the bay leaf for approximately 20 minutes. Reserve a glass of the cooking water.--2. Cut the bacon into thick strips or cubes and fry until golden brown and crispy. Set aside.--3. Cut the garlic into slices and fry in the same oil as the bacon until lightly browned. Remove from the heat, add the paprika to the garlic and mix.--4. Return the garlic and paprika to the heat and pour in half a glass of the potato cooking liquid. Stir and bring to the boil for just one minute. Set aside.--5. Mash the cooked potatoes and add them to the sauce to \"stir\" all the ingredients in the same frying pan. Serve the potatoes piping hot with the bacon torreznos on top.');");
        sqLiteDatabase.execSQL("INSERT INTO gastronomia (catGastro, idioma, nombreReceta, descrGastro, ingrReceta, pasosReceta) VALUES ('receta', 'en', 'hornazo', 'Hornazo is one of the most famous and representative products of Salamanca''s gastronomy. There are several varieties, but its preparation is quite similar. It is a pie made with a simple homemade dough filled with local products: pork fillets and good sausage.  Although it is a product that can be eaten all year round, it is a tradition in La Alberca to eat it on Easter Monday, on the day of the \"Pendón\".', '300gr flour,30gr lard,60ml water,10ml olive oil,2 eggs,1 salt teaspoon,200gr chorizo,300gr pork loin, 40ml white wine,25gr fresh yeast,½ paprika teaspoon', '1. Place the flour in a large bowl, make a hole in the middle and add the egg, oil, salt and paprika.--2. Heat the wine with the water and add the yeast and lard. Mix everything well and add to the large bowl. Knead everything well until you get a homogeneous dough.--3. Transfer the dough to another bowl, cover it with a damp cloth and leave it to rise for about 30 minutes.--4. After 30 minutes, sprinkle a flat surface with flour, divide the dough into two parts and roll them out with a rolling pin, giving them a rounded shape. Place 1 part on the baking tray and set the other part aside.--5. Place the pork loin and the chorizo sausage on the dough on the tray, and place the other part of the dough on top. Cut off the leftovers and join the two doughs well. Knead the leftovers and place them as decoration on top of the hornazo dough.--6. Beat the remaining egg and paint the hornazo dough. Once painted, bake in the oven at 200ºC for approximately 30 minutes, until golden brown. Leave to cool and serve.--');");
        sqLiteDatabase.execSQL("INSERT INTO gastronomia (catGastro, idioma, nombreReceta, descrGastro, ingrReceta, pasosReceta) VALUES ('receta', 'en', 'perrunillas', 'Perrunillas are biscuits or pastries typical of the Sierra de Francia and Extremadura, very easy to make, with few ingredients and with all the flavour of tradition. Depending on the region, the recipe for making homemade perrunillas varies, but they all have in common the simplicity of their preparation and the ability to evoke aromas and flavours of yesteryear. It is also very typical to add a dash of aniseed. Because tasty and simple don''t have to be at odds.', '125g sweet aniseed,1 sachet baking powder,150g sugar,1 egg white,125g lard,½ lemon zest,4 eggs,500g flour', '1. Put the butter in a bowl, add the aniseed and mix a little. Add the sugar, 2 egg yolks, 2 whole eggs and the lemon zest to the mixture. Whisk again.--2. Add the flour mixed with the yeast and mix until the dough is smooth and manageable. Add more flour if necessary.--3. Mix the egg whites until stiff and set aside.--4. Take portions of dough and make a kind of croquette. Flatten slightly and press with a finger to make an imprint.--5. Beat another egg white without stiffening it and brush the perrunillas with it.--6. On the marked mark, add a \"bow\" with the reserved egg white. Carefully roll the perrunillas in sugar on the side of the \"bun\" without letting the perrunilla out of your hands.--7. Preheat the oven to 250ºC. Once hot, bake the perrunillas for approximately 20 minutes or until golden brown.--');");
        sqLiteDatabase.execSQL("INSERT INTO gastronomia (catGastro, idioma, nombreReceta, descrGastro, ingrReceta, pasosReceta) VALUES ('receta', 'en', 'maimonbun', 'The \"bollo maimón\", also called \"Rosco de Bodas\", is a typical sponge cake from the capital and the province of Salamanca. It dates back to the time when the Arabs lived in Spain alongside Jews and Christians. This sponge cake was a common wedding gift, and specifically in La Alberca, according to the protocol of Serrano love, the \"maimona\" sponge cake was one of the gifts that the groom had to give to the bride''s family at certain times of the year.  There are also references to the fact that the sponge cake or bollo maimón was also used to settle disputes and coexistence between different religions.', '100g sugar,4 eggs,Icing sugar,Butter,Brandy,150g flour (starch),1 lemon zest', '1. Add the eggs, sugar, one tablespoon of brandy and lemon zest to a bowl. Mix with a whisk until the mixture is frothy and white.--2. In the same bowl, gradually add the sifted flour and mix until it is completely incorporated.--3. Grease a cake mould with a hole in the centre with butter and pour the mixture into the mould.--4. Preheat the oven to 180ºC. Once hot, place the mould in the oven and bake the cake for about 25 minutes, until the cake is golden brown.--5. Once cooked, remove the sponge cake from the oven and cover it with icing sugar. Leave the cake to cool, remove from the mould and serve.--');");
        sqLiteDatabase.execSQL("INSERT INTO gastronomia (catGastro, idioma, nombreReceta, descrGastro, ingrReceta, pasosReceta) VALUES ('receta', 'en', 'florets', 'Florets or floretas are another of Salamanca''s most delicious typical sweets and are easier to make than you might think. Traditionally, these sweets were typical of Carnival, Lent and Easter, but nowadays they can be found in bakeries at any time of the year. The mould used to make them, the \"florero\", is a tool that used to be passed down from mother to daughter along with the recipe.', '250ml milk,2 tablespoons sugar,1 egg,½ orange zest,30ml aniseed,200gr wheat flour,1 pinch of salt,Oil for frying,Cinnamon,Sugar for breading', '1. Start by beating the eggs and sugar in a bowl. When they are well mixed, add the milk, aniseed and orange zest.--2. Gradually add the flour and stir until it has a smooth, lump-free consistency. Strain the dough through a sieve if necessary. Transfer the dough to another bowl and leave to rest, covered, for about 20 minutes.--3. Heat plenty of oil in a deep frying pan over medium heat. Put the flower moulds into the oil so that they heat up at the same time. This step is very important so that the dough can adhere well to the mould.--4. When the oil is hot, uncover the batter and stir it a little until it has a light consistency. We introduce the mould into the batter up to the middle of the drawing without covering the whole flower and we put it immediately into the frying pan, taking care that it does not touch the bottom, and we shake it gently until the flower comes off.--5. Fry the flower until golden brown on both sides for a few seconds and transfer to absorbent paper before coating them generously in a mixture of cinnamon and sugar.--6. Reheat the mould and dip it in the batter again, repeating the previous operation until all the batter has been coated.--7. Dredge the portions in sugar and cinnamon. Serve the florets.--');");
        sqLiteDatabase.execSQL("INSERT INTO monumento (categoriaMon, nombreMon, idioma, descMon) VALUES ('plaza', 'plaza', 'en', 'It is a square with an irregular rectangular ground plan, open on the lower part towards Tablado street. It is surrounded by buildings of two, three and even four storeys, built on stone columns, forming an asphyxiated enclosure of great character. The buildings include the town hall, the former ducal house, now converted into a theatre, the former schools, the former pilgrims'' hospital, now a library, and the large transept that presides over the square.');");
        sqLiteDatabase.execSQL("INSERT INTO monumento (categoriaMon, nombreMon, idioma, descMon) VALUES ('plaza', 'crucero', 'en', 'On the opposite side is the Virgin Mary. On the shaft of the column, the signs of the Passion are sculpted in low relief: the coins of Judas'' betrayal, the whip, the nails and the lance. In addition, in the lower part of the cross, creating a double height in the square, there is a small fountain, very useful for hot days.');");
        sqLiteDatabase.execSQL("INSERT INTO monumento (categoriaMon, nombreMon, idioma, descMon) VALUES ('plaza', 'ayuntamiento', 'en', 'Located on one side of the square, opposite the Casa Ducal, is the town''s consistory, the town hall. Although it is not known for sure, it is said that the building where it is located was owned by the Holy Inquisition. It has three floors, and the arcades on which it stands are formed by granite columns with basins on high plinths and 18th century Tuscan capitals with a medallion on the shaft of the central column with an inscription dated 1774.');");
        sqLiteDatabase.execSQL("INSERT INTO monumento (categoriaMon, nombreMon, idioma, descMon) VALUES ('plaza', 'ayuntamiento', 'en', 'On the ground floor was the old prison, nowadays converted into a tourist office. The grille that covers the door and a tile placed on the lintel of the door with the inscription \"Cárcel Pública\" (Public Prison) remain from those times. The two upper floors have iron balconies on the first floor and wooden balconies on the second floor, with wooden railings on both. On the façade of the first floor balcony, there is a stone plaque commemorating King Alfonso XIII''s trip to La Alberca in 1922.');");
        sqLiteDatabase.execSQL("INSERT INTO monumento (categoriaMon, nombreMon, idioma, descMon) VALUES ('plaza', 'teatro-cat1', 'en', 'The building on one of the corners of the square is perhaps one of the most remarkable buildings in the area. Formerly, and for many years, it was the Ducal house where the servant-administrator of the revenues of the Duchy of Alba in that area resided. Years later, the current theatre of La Alberca served as the barracks of the Civil Guard, as an old butcher''s shop and as a stable. Today it is used for theatrical performances, informative talks, book presentations, children''s and folklore festivals and even as a cinema.');");
        sqLiteDatabase.execSQL("INSERT INTO monumento (nombreMon, idioma, descMon) VALUES ('ermitadelhumilladero', 'en', 'Most of the villages in this region of Salamanca have a hermitage of the Humilladero. This used to be the site of the boundary cross and was a place for visitors to say goodbye and pray when leaving on their journey or on their arrival. In other times it was a place of entry for those who came from other distant lands, for life''s pursuits and the affairs of cities.');");
        sqLiteDatabase.execSQL("INSERT INTO monumento (categoriaMon, nombreMon, idioma, descMon) VALUES ('plaza', 'retratomigueldeunamuno', 'en', 'Miguel de Unamuno y Jugo was a Spanish philosopher and writer who was born in Bilbao in 1864 and died in Salamanca in 1936. He was part of the well-known Generation of ''98 and excelled in various literary genres, as well as holding different positions at the University of Salamanca, most notably as rector in three different periods.');");
        sqLiteDatabase.execSQL("INSERT INTO monumento (categoriaMon, nombreMon, idioma, descMon) VALUES ('plazaiglesia', 'predios', 'en', 'Over the years, the collapse of the church''s cemetery and its surroundings and the idea of creating new enclosures outside the village led to the opening of the cemetery of the Ermita de San Blas and later, as it was too small and so close to inhabited areas, what is now the village cemetery was built in the area of La Somá, on the outskirts of the village.');");
        sqLiteDatabase.execSQL("INSERT INTO monumento (categoriaMon, nombreMon, idioma, descMon) VALUES ('iglesia', 'pulpito', 'en', 'The railing or tribune is located in the upper part of the pulpit, three different panels can be distinguished. In the first one, from left to right, we can see an image of St. Peter the Apostle together with a text of his. In the centre, at the top, the symbolic dove, representative of the Holy Spirit. Immediately below, in gold lettering, we find a phrase from St. John''s Gospel:');");
        sqLiteDatabase.execSQL("INSERT INTO otroslugares (categoriaOtros, idioma, nombreOtro, descrOtro) VALUES ('peñadefrancia','en','lacasabaja','The friars lived in the lower house from November to May, specifically from All Souls'' Day (1st November) until the Wednesday before Corpus Christi (end of May-beginning of June) to prepare for the feast of Pentecost, one of the four most important feasts in La Peña. However, a small group of religious remained at the Risco to attend to the pilgrims and the worship of the Sanctuary.');");
        sqLiteDatabase.execSQL("INSERT INTO otroslugares (categoriaOtros, idioma, nombreOtro, descrOtro) VALUES ('peñadefrancia','en','lacasabaja','In the 19th century, the War of Independence left all the buildings with traces of looting and destruction. Despite this, in 1816 the monks began the restoration of the convent. This tenacity in building and rebuilding the convent was put an end to in 1835 by Mendizábal''s Disentailment.');");
        sqLiteDatabase.execSQL("INSERT INTO otroslugares (categoriaOtros, idioma, nombreOtro, descrOtro) VALUES ('peñadefrancia','en','lacasabaja','When the monks were expelled and the convent was sold, the destruction of the convent began. Today, there are no more roofs, the porter''s lodge, the ashlar cloister, the refectory, the kitchen, the cells, the bakery, the offices... everything is full of brambles and weeds that grow luxuriantly on the floor and walls. There are still traces of what was once a royal staircase, of a large and beautiful neoclassical church that serves as a hayloft and stable, and the only remaining vaults of the monastery, in what were once the sacristy and the chapter house, threaten to fall down.');");
        sqLiteDatabase.execSQL("INSERT INTO otroslugares (categoriaOtros, idioma, nombreOtro, descrOtro) VALUES ('batuecas','en','monasterio','The enclosure is made up of two fences, within which the convent and its outbuildings are located, and the church, built in 1602 and enlarged in 1686, is the central building of the complex, and is surrounded by a street paved with slate and large gardens in the form of a cloister. To the south, there are several monks'' oratory cells, and on the west wall, a door opens onto the refectory, kitchen, workshops, servants'' quarters, bakery, laundry... The definitive structure of the monastery took shape during the 17th and 18th centuries.');");
        sqLiteDatabase.execSQL("INSERT INTO fiesta (idioma, nombreFiesta, fechaFiesta, descrFiesta) VALUES ('en', 'sananton', 'enero', 'The feast of San Antón (Saint Anthony, Abbot), which is celebrated on 17 January, is a feast to bless the village''s livestock, to protect them from plagues and diseases. The day before, in the afternoon, the feast is announced by a parade with the tambourine and bagpipes. On the feast day, a mass and procession with the image of the saint is held in the morning around the church.');");
        sqLiteDatabase.execSQL("INSERT INTO fiesta (idioma, nombreFiesta, fechaFiesta, descrFiesta) VALUES ('en', 'sananton', 'enero', 'The saint has stewards, who play a special role on this day. In the afternoon, the rosary is recited and the animals are blessed by the priest in front of the church. In the old days, the cattle were given three laps around the church and surrounding streets, a good complement to the blessing with holy water. However, perhaps the most striking aspect of this fiesta is the so-called Marrano San Antón, something that had been lost and which has been recovered in recent years.');");
        sqLiteDatabase.execSQL("INSERT INTO fiesta (idioma, nombreFiesta, fechaFiesta, descrFiesta) VALUES ('en', 'sansebastian', 'enero', 'The festivity of San Sebastián consists of a mass with a procession around the church, with the image of the saint. The most significant part of this celebration is the \"alborada\" or \"alborá\" which is sung to the saint in the streets of the village on the night of the eve, i.e. the night of 19 January, sung to the town''s mayordomos and authorities, with each person having their own personalised verse.');");
        sqLiteDatabase.execSQL("INSERT INTO fiesta (idioma, nombreFiesta, fechaFiesta, descrFiesta) VALUES ('en', 'sansebastian', 'enero', 'There is great devotion to this saint in La Alberca, as the people, in their collective memory, credit him with having saved them from the plague of 1918, an event to which the \"alborá\" alludes.');");

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

    //Tabla Alojamientos
    public LinkedList<Alojamiento> obteneralojamientos(String query) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        LinkedList<Alojamiento> alojamientos = new LinkedList<>();

        Cursor c = sqLiteDatabase.rawQuery(query, null);
        while (c.moveToNext()){
            Alojamiento alojamiento = new Alojamiento();
            //alojamiento.setCatAloj(c.getString(1));
            alojamiento.setNombreAloj(c.getString(2));
            alojamiento.setTelAloj(c.getString(5));
            alojamiento.setLocationAloj(c.getString(3));
            alojamiento.setPuntAloj(c.getDouble(4));
            alojamientos.add(alojamiento);
        }
        c.close();
        return alojamientos;
    }

    //Tabla Restaurantes
    public LinkedList<Establecimiento> obtenerestablecimientos(String query) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        LinkedList<Establecimiento> establecimientos = new LinkedList<>();

        Cursor c = sqLiteDatabase.rawQuery(query, null);
        while (c.moveToNext()){
            Establecimiento establecimiento = new Establecimiento();
            //establecimiento.setCatEstabl(c.getString(1));
            establecimiento.setNombreEstabl(c.getString(2));
            establecimiento.setTelEstabl(c.getString(3));
            establecimiento.setLocationEstabl(c.getString(4));
            establecimiento.setPuntEstabl(c.getDouble(5));
            establecimientos.add(establecimiento);
        }
        c.close();
        return establecimientos;
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
    public String[] obtenerInfoTrad(String idioma, String interfaz, int numTV){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String descrip;
        int i = 0;
        String [] descr = new String[numTV];

        Cursor c = sqLiteDatabase.rawQuery("SELECT descr FROM tradiciones WHERE nombreTrad LIKE '" + interfaz + "%'" +
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

}
