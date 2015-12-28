package sample;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Conexio a la API per captar dades
 */
public class Connect {

    protected static int cantidadPokemons = 10;
    protected static ArrayList<String> nombresPokAL = new ArrayList<>();

    public Connect(){
        conexioApi();
    }

    public static int getCantidadPokemons() {
        return cantidadPokemons;
    }

    public static void setCantidadPokemons(int cantidadPokemons) {
        Connect.cantidadPokemons = cantidadPokemons;
    }

    /**
     * Conexio primaria a la api que es dona
     * nomes iniciar-se l'aplicacio
     */
    public static void conexioApi(){
        nombresPokAL.clear();
        int indice = 1;
        for (int i = 0; i < cantidadPokemons; i++) {//controla kuants pokemons
            String pokemon;
            String peticioPokemon = "http://pokeapi.co/api/v1/pokemon/" + indice + "/";

            try {
                pokemon = getHTML(peticioPokemon);//peticioPokemons
                String str = nomPoke(pokemon);
                nombresPokAL.add(str);
                pokemonPrintJson(pokemon);
                indice++;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//conexioApi

    /**
     * Conexio primaria a la api que es dona
     * quan es fan peticions
     * @param indice id del Pokemon
     */
    public static void conexioApi(int indice){
            String pokemon;
            String peticioPokemon = "http://pokeapi.co/api/v1/pokemon/" + indice + "/";

            try {
                pokemon = getHTML(peticioPokemon);//peticioPokemons
                String str = nomPoke(pokemon);
                nombresPokAL.add(str);
                pokemonPrintJson(pokemon);

            } catch (Exception e) {
                e.printStackTrace();
            }
    }//conexioApi


    /**
     * Retorna el JSON dels pokemons
     * LLibreria que sencarrega de gestionar les peticions GET
     * @param urlToRead
     * @return
     * @throws Exception
     */
    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();

        return result.toString();//retorna un JSON
    }

    /**
     * Parseja un Json per trobar el nom del pokemon
     * @param cadena JSON del pokemon
     * @return Nom del pokemon
     */
    public static String nomPoke (String cadena){
        Object objPoke = JSONValue.parse(cadena);
        JSONObject JsonObjPoke = (JSONObject)objPoke;
        String  name, id;

        id = String.valueOf(JsonObjPoke.get("national_id"));
        name = String.valueOf(JsonObjPoke.get("name"));
        name = name.concat("     Id: " + id);

        return name;
    }


    /**
     * Escriu el pokemon per terminal
     * @param cadena
     */
    public static void pokemonPrintJson (String cadena){//para pokemons INSERTS

        Object objPoke = JSONValue.parse(cadena);
        JSONObject JsonObjPoke = (JSONObject)objPoke;

        System.out.println("\nnational_id: " + JsonObjPoke.get("national_id"));
        System.out.println("Name: " + JsonObjPoke.get("name"));
        System.out.println("Attack: " + JsonObjPoke.get("attack"));
        System.out.println("Defense: " + JsonObjPoke.get("defense"));
        System.out.println("Exp: " + JsonObjPoke.get("exp"));
        System.out.println("Height: " + JsonObjPoke.get("height"));
        System.out.println("Speed: " + JsonObjPoke.get("speed"));

    }

    /**
     * Detalla les dades del pokemon que corresponen
     * a la descripcio
     * @param cadena JSON del pokemon
     * @return Descripcio del pokemon
     */
    public static String returnDescription (String cadena){
        Object objPoke = JSONValue.parse(cadena);
        JSONObject JsonObjPoke = (JSONObject)objPoke;
        String id, name, attack, defense, exp, height, speed;
        String description;

        id = String.valueOf(JsonObjPoke.get("national_id"));
        name = String.valueOf(JsonObjPoke.get("name"));
        attack = String.valueOf(JsonObjPoke.get("attack"));
        defense = String.valueOf(JsonObjPoke.get("defense"));
        exp = String.valueOf(JsonObjPoke.get("exp"));
        height = String.valueOf(JsonObjPoke.get("height"));
        speed = String.valueOf(JsonObjPoke.get("speed"));

        description = "Name: " + name.toUpperCase() + "\n";
        description = description.concat("Id: " + id + "\n");
        description = description.concat("Attack: " + attack + "\n");
        description = description.concat("Defense: " + defense + "\n");
        description = description.concat("Experience: " + exp + "\n");
        description = description.concat("Height: " + height + "\n");
        description = description.concat("Speed: " + speed + "\n");

        return description;
    }

}


/*JSON pokemon

{
   "abilities":[
      {
         "name":"sand-veil",
         "resource_uri":"/api/v1/ability/8/"
      },
      {
         "name":"arena-trap",
         "resource_uri":"/api/v1/ability/71/"
      },
      {
         "name":"sand-force",
         "resource_uri":"/api/v1/ability/159/"
      }
   ],
   "attack":55,
   "catch_rate":0,
   "created":"2013-11-03T15:05:41.365518",
   "defense":25,
   "descriptions":[
      {
         "name":"diglett_gen_1",
         "resource_uri":"/api/v1/description/801/"
      },
      {
         "name":"diglett_gen_1",
         "resource_uri":"/api/v1/description/798/"
      },
      {
         "name":"diglett_gen_1",
         "resource_uri":"/api/v1/description/799/"
      },
      {
         "name":"diglett_gen_1",
         "resource_uri":"/api/v1/description/800/"
      },
      {
         "name":"diglett_gen_1",
         "resource_uri":"/api/v1/description/802/"
      },
      {
         "name":"diglett_gen_2",
         "resource_uri":"/api/v1/description/804/"
      },
      {
         "name":"diglett_gen_3",
         "resource_uri":"/api/v1/description/805/"
      },
      {
         "name":"diglett_gen_3",
         "resource_uri":"/api/v1/description/806/"
      },
      {
         "name":"diglett_gen_3",
         "resource_uri":"/api/v1/description/807/"
      },
      {
         "name":"diglett_gen_6",
         "resource_uri":"/api/v1/description/812/"
      },
      {
         "name":"diglett_gen_6",
         "resource_uri":"/api/v1/description/813/"
      },
      {
         "name":"diglett_gen_4",
         "resource_uri":"/api/v1/description/808/"
      },
      {
         "name":"diglett_gen_5",
         "resource_uri":"/api/v1/description/811/"
      },
      {
         "name":"diglett_gen_4",
         "resource_uri":"/api/v1/description/809/"
      },
      {
         "name":"diglett_gen_2",
         "resource_uri":"/api/v1/description/803/"
      },
      {
         "name":"diglett_gen_4",
         "resource_uri":"/api/v1/description/810/"
      }
   ],
   "egg_cycles":0,
   "egg_groups":[
      {
         "name":"Ground",
         "resource_uri":"/api/v1/egg/5/"
      }
   ],
   "ev_yield":"",
   "evolutions":[
      {
         "level":26,
         "method":"level_up",
         "resource_uri":"/api/v1/pokemon/51/",
         "to":"Dugtrio"
      }
   ],
   "exp":53,
   "growth_rate":"",
   "happiness":0,
   "height":"2",
   "hp":10,
   "male_female_ratio":"",
   "modified":"2013-11-23T13:13:25.213302",
   "moves":[
      {
         "learn_type":"level up",
         "level":18,
         "name":"Bulldoze",
         "resource_uri":"/api/v1/move/523/"
      },
      {
         "learn_type":"egg move",
         "name":"Final-gambit",
         "resource_uri":"/api/v1/move/515/"
      },
      {
         "learn_type":"machine",
         "name":"Echoed-voice",
         "resource_uri":"/api/v1/move/497/"
      },
      {
         "learn_type":"machine",
         "name":"Round",
         "resource_uri":"/api/v1/move/496/"
      },
      {
         "learn_type":"machine",
         "name":"Hone-claws",
         "resource_uri":"/api/v1/move/468/"
      },
      {
         "learn_type":"egg move",
         "name":"Memento",
         "resource_uri":"/api/v1/move/262/"
      },
      {
         "learn_type":"egg move",
         "name":"Headbutt",
         "resource_uri":"/api/v1/move/29/"
      },
      {
         "learn_type":"egg move",
         "name":"Reversal",
         "resource_uri":"/api/v1/move/179/"
      },
      {
         "learn_type":"machine",
         "name":"Stealth-rock",
         "resource_uri":"/api/v1/move/446/"
      },
      {
         "learn_type":"machine",
         "name":"Captivate",
         "resource_uri":"/api/v1/move/445/"
      },
      {
         "learn_type":"level up",
         "level":29,
         "name":"Mud-bomb",
         "resource_uri":"/api/v1/move/426/"
      },
      {
         "learn_type":"machine",
         "name":"Shadow-claw",
         "resource_uri":"/api/v1/move/421/"
      },
      {
         "learn_type":"level up",
         "level":26,
         "name":"Earth-power",
         "resource_uri":"/api/v1/move/414/"
      },
      {
         "learn_type":"level up",
         "level":23,
         "name":"Sucker-punch",
         "resource_uri":"/api/v1/move/389/"
      },
      {
         "learn_type":"machine",
         "name":"Natural-gift",
         "resource_uri":"/api/v1/move/363/"
      },
      {
         "learn_type":"level up",
         "level":7,
         "name":"Astonish",
         "resource_uri":"/api/v1/move/310/"
      },
      {
         "learn_type":"machine",
         "name":"Sandstorm",
         "resource_uri":"/api/v1/move/201/"
      },
      {
         "learn_type":"level up",
         "level":21,
         "name":"Fury-swipes",
         "resource_uri":"/api/v1/move/154/"
      },
      {
         "learn_type":"machine",
         "name":"Aerial-ace",
         "resource_uri":"/api/v1/move/332/"
      },
      {
         "learn_type":"machine",
         "name":"Rock-tomb",
         "resource_uri":"/api/v1/move/317/"
      },
      {
         "learn_type":"machine",
         "name":"Secret-power",
         "resource_uri":"/api/v1/move/290/"
      },
      {
         "learn_type":"machine",
         "name":"Facade",
         "resource_uri":"/api/v1/move/263/"
      },
      {
         "learn_type":"egg move",
         "name":"Uproar",
         "resource_uri":"/api/v1/move/253/"
      },
      {
         "learn_type":"egg move",
         "name":"Beat-up",
         "resource_uri":"/api/v1/move/251/"
      },
      {
         "learn_type":"machine",
         "name":"Rock-smash",
         "resource_uri":"/api/v1/move/249/"
      },
      {
         "learn_type":"egg move",
         "name":"Ancientpower",
         "resource_uri":"/api/v1/move/246/"
      },
      {
         "learn_type":"machine",
         "name":"Sunny-day",
         "resource_uri":"/api/v1/move/241/"
      },
      {
         "learn_type":"machine",
         "name":"Hidden-power",
         "resource_uri":"/api/v1/move/237/"
      },
      {
         "learn_type":"egg move",
         "name":"Pursuit",
         "resource_uri":"/api/v1/move/228/"
      },
      {
         "learn_type":"level up",
         "level":9,
         "name":"Magnitude",
         "resource_uri":"/api/v1/move/222/"
      },
      {
         "learn_type":"machine",
         "name":"Frustration",
         "resource_uri":"/api/v1/move/218/"
      },
      {
         "learn_type":"machine",
         "name":"Return",
         "resource_uri":"/api/v1/move/216/"
      },
      {
         "learn_type":"machine",
         "name":"Sleep-talk",
         "resource_uri":"/api/v1/move/214/"
      },
      {
         "learn_type":"machine",
         "name":"Attract",
         "resource_uri":"/api/v1/move/213/"
      },
      {
         "learn_type":"machine",
         "name":"Swagger",
         "resource_uri":"/api/v1/move/207/"
      },
      {
         "learn_type":"machine",
         "name":"Endure",
         "resource_uri":"/api/v1/move/203/"
      },
      {
         "learn_type":"machine",
         "name":"Mud-slap",
         "resource_uri":"/api/v1/move/189/"
      },
      {
         "learn_type":"machine",
         "name":"Sludge-bomb",
         "resource_uri":"/api/v1/move/188/"
      },
      {
         "learn_type":"egg move",
         "name":"Faint-attack",
         "resource_uri":"/api/v1/move/185/"
      },
      {
         "learn_type":"machine",
         "name":"Protect",
         "resource_uri":"/api/v1/move/182/"
      },
      {
         "learn_type":"machine",
         "name":"Curse",
         "resource_uri":"/api/v1/move/174/"
      },
      {
         "learn_type":"machine",
         "name":"Snore",
         "resource_uri":"/api/v1/move/173/"
      },
      {
         "learn_type":"machine",
         "name":"Thief",
         "resource_uri":"/api/v1/move/168/"
      },
      {
         "learn_type":"egg move",
         "name":"Screech",
         "resource_uri":"/api/v1/move/103/"
      },
      {
         "learn_type":"machine",
         "name":"Cut",
         "resource_uri":"/api/v1/move/15/"
      },
      {
         "learn_type":"machine",
         "name":"Substitute",
         "resource_uri":"/api/v1/move/164/"
      },
      {
         "learn_type":"level up",
         "level":31,
         "name":"Slash",
         "resource_uri":"/api/v1/move/163/"
      },
      {
         "learn_type":"machine",
         "name":"Rock-slide",
         "resource_uri":"/api/v1/move/157/"
      },
      {
         "learn_type":"machine",
         "name":"Rest",
         "resource_uri":"/api/v1/move/156/"
      },
      {
         "learn_type":"machine",
         "name":"Bide",
         "resource_uri":"/api/v1/move/117/"
      },
      {
         "learn_type":"machine",
         "name":"Double-team",
         "resource_uri":"/api/v1/move/104/"
      },
      {
         "learn_type":"machine",
         "name":"Mimic",
         "resource_uri":"/api/v1/move/102/"
      },
      {
         "learn_type":"machine",
         "name":"Rage",
         "resource_uri":"/api/v1/move/99/"
      },
      {
         "learn_type":"machine",
         "name":"Toxic",
         "resource_uri":"/api/v1/move/92/"
      },
      {
         "learn_type":"level up",
         "level":19,
         "name":"Dig",
         "resource_uri":"/api/v1/move/91/"
      },
      {
         "learn_type":"machine",
         "name":"Fissure",
         "resource_uri":"/api/v1/move/90/"
      },
      {
         "learn_type":"level up",
         "level":40,
         "name":"Earthquake",
         "resource_uri":"/api/v1/move/89/"
      },
      {
         "learn_type":"level up",
         "level":15,
         "name":"Growl",
         "resource_uri":"/api/v1/move/45/"
      },
      {
         "learn_type":"machine",
         "name":"Double-edge",
         "resource_uri":"/api/v1/move/38/"
      },
      {
         "learn_type":"machine",
         "name":"Take-down",
         "resource_uri":"/api/v1/move/36/"
      },
      {
         "learn_type":"machine",
         "name":"Body-slam",
         "resource_uri":"/api/v1/move/34/"
      },
      {
         "learn_type":"level up",
         "level":24,
         "name":"Sand-attack",
         "resource_uri":"/api/v1/move/28/"
      },
      {
         "learn_type":"level up",
         "level":1,
         "name":"Scratch",
         "resource_uri":"/api/v1/move/10/"
      }
   ],
   "name":"Diglett",
   "national_id":50,
   "pkdx_id":50,
   "resource_uri":"/api/v1/pokemon/50/",
   "sp_atk":35,
   "sp_def":45,
   "species":"",
   "speed":95,
   "sprites":[
      {
         "name":"diglett",
         "resource_uri":"/api/v1/sprite/51/"
      }
   ],
   "total":0,
   "types":[
      {
         "name":"ground",
         "resource_uri":"/api/v1/type/5/"
      }
   ],
   "weight":"8"
}

 */