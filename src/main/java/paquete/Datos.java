package paquete;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Crea y almacena todos los datos de los equipos, partidos, rondas y participantes
 */
public class Datos
{
    private ArrayList<Persona> lista_personas = new ArrayList<>(); // lista de participantes
    private ArrayList<Equipo> lista_equipos = new ArrayList<>(); // lista de equipos
    private ArrayList<Fase> lista_fases = new ArrayList<>(); // lista de fases
    private File resultados; // formato: fase,ronda,equipo1,descripcion1,goles1,equipo2,descripcion2,goles2

    /**
     * Procesa la información del archivo de resultados y base de datos.
     * @param resultados La ruta al archivo de resultados.
     * @param db_data Contiene la url, usuario y contraseña necesaria para el acceso a la DB.
     */
    public Datos(String resultados, String[] db_data)
    {
        this.resultados = new File(resultados);
        System.out.println("Archivo de partidos         --> " + this.resultados.getAbsolutePath());
        generatePartidos();
        generateParticipantes(db_data);
    }

    /**
     * Procesa el archivo de texto para crear las instancias de equipo, partido, ronda y fase.
     * Estas instancias se almacenan en sus listas correspondientes.
     * Cada linea del archivo corresponde a un partido.
     * <p>
     * El contador id_partidos sirve para contar las lineas Y TAMBIEN como id de los partidos,
     * así cada partido tiene un numero de identificación UNICO Y DIFERENTE.
     */
    private void generatePartidos()
    {
        // RegEx de verificación de lineas del archivo resultados.
        Pattern p = Pattern.compile("\\d+,\\d+(,[^,;_:&#]+,[^,;_:&#]+,\\d+){2}");

        int id_partidos = 1;
        try (Scanner sc = new Scanner(resultados))
        {
            while (sc.hasNextLine())
            {
                String linea = sc.nextLine();
                Matcher m = p.matcher(linea);

                // Si la linea de datos tiene errores se salta a la siguiente.
                if ( ! m.matches() )
                {
                    id_partidos++;
                    continue;
                }
                String[] datos = linea.split(",");

                Fase fase = buscarFase( Integer.parseInt(datos[0]) );
                if (fase == null)
                {
                    fase = new Fase( Integer.parseInt(datos[0]) );
                    lista_fases.add(fase);
                }

                Ronda ronda = buscarRonda( Integer.parseInt(datos[1]) );
                if (ronda == null)
                {
                    ronda = new Ronda( Integer.parseInt(datos[1]) );
                    fase.addRonda(ronda);
                }

                Equipo equipo1 = buscarEquipo(datos[2]);
                if (equipo1 == null)
                {
                    equipo1 = new Equipo(datos[2], datos[3]);
                    lista_equipos.add(equipo1);
                }

                Equipo equipo2 = buscarEquipo(datos[5]);
                if (equipo2 == null)
                {
                    equipo2 = new Equipo(datos[5], datos[6]);
                    lista_equipos.add(equipo2);
                }

                int goles1 = Integer.parseInt(datos[4]);
                int goles2 = Integer.parseInt(datos[7]);
                Partido partido = new Partido(id_partidos, equipo1, equipo2, goles1, goles2);
                id_partidos++;
                ronda.addPartido(partido);
            }
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Crea las instancias de Persona y sus Pronosticos
     * @param db_data Datos necesarios para acceder a la DB.
     */
    private void generateParticipantes(String[] db_data)
    {
        try
        {
            Connection conn = DriverManager.getConnection(db_data[0], db_data[1], db_data[2]);
            Statement stmt = conn.createStatement();
            String sql = "select * from pronostico";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                Persona persona = buscarPersona(rs.getString("nombre"));
                if (persona == null)
                {
                    persona = new Persona(rs.getString("nombre"));
                    lista_personas.add(persona);
                }

                ResultadoEnum res = null;
                switch (rs.getString("resultado").toLowerCase())
                {
                    case "ganador":
                        res = ResultadoEnum.GANADOR;
                        break;
                    case "perdedor":
                        res = ResultadoEnum.PERDEDOR;
                        break;
                    case "empate":
                        res = ResultadoEnum.EMPATE;
                        break;
                }
                // Si res es null, se saltea el pronostico
                if (res == null) continue;

                Partido partido = buscarPartido( rs.getInt("idPartido") );
                // Si el partido no está registrado, se saltea el pronostico
                if (partido == null)
                    continue;
                // Si ya se hizo un pronostico con el mismo id de partido, se saltea el pronostico
                // Parece tonto permitir varios pronosticos de una persona al mismo partido.
                if ( pronosticoRepetido( rs.getInt("idPartido"), persona ) )
                    continue;

                Equipo equipo = buscarEquipo(rs.getString("equipo"));
                /*Si el equipo es null o no coincide con los equipos que
                 *jugaron el partido, se saltea el pronostico.
                 */
                if ( equipo == null || !( equipo.equals(partido.getEquipo1()) || equipo.equals(partido.getEquipo2()) ) )
                    continue;

                Pronostico pron = new Pronostico(partido, equipo, res);
                persona.addPronostico(pron);
            }
            conn.close();
            stmt.close();
            rs.close();
        }
        catch(SQLException ex)
        {
            System.out.println("ERROR AL ACCEDER A LA BASE DE DATOS: " + ex.getMessage());
        }

    }

    /**
     * Busca una instancia de fase segun su numero y la devuelve.
     * @param num Numero de fase a buscar
     * @return La instancia de fase si se encuentra registrada, o null de lo contrario.
     */
    private Fase buscarFase(int num)
    {
        Fase x = null;
        for (Fase y : lista_fases)
        {
            if (y.getNumero() == num)
            {
                x = y;
                break;
            }
        }
        return x;
    }

    /**
     * Busca una instancia de partido segun su numero y la devuelve.
     * @param num Numero id de partido a buscar
     * @return La instancia de partido si se encuentra registrado, o null de lo contrario.
     */
    private Partido buscarPartido(int num)
    {
        Partido p = null;
        for (Fase x : lista_fases)
        {
            ArrayList<Ronda> rondas = x.getRondas();
            for (Ronda y : rondas)
            {
                p = y.getPartido(num); // ver getPartido() en Ronda
                if (p != null) return p;
            }
        }
        return p;
    }

    /**
     * Devuelve un puntero de Persona si se encuentra una persona (participante) ya
     * registrada con el nombre. De lo contrario, devuelve null.
     * @param nombre Nombre de la persona
     * @return Puntero a una persona, o null si no se encuentra una persona con el mismo nombre.
     */
    private Persona buscarPersona(String nombre)
    {
        Persona x = null;
        for (Persona y : lista_personas)
        {
            if (nombre.equals(y.getNombre()))
            {
                x = y;
                break;
            }
        }
        return x;
    }

    /**
     * Devuelve un puntero de Equipo si se encuentra un equipo ya registrado con el nombre.
     * De lo contrario, devuelve null.
     * @param nombre Nombre del equipo a buscar
     * @return Un Equipo si se encuentra registrado, o null de lo contrario
     */
    private Equipo buscarEquipo(String nombre)
    {
        // Devuelve un puntero de Equipo si se encuentra un equipo ya registrada con el nombre
        // De lo contrario, devuelve null.
        Equipo x = null;
        for (Equipo y : lista_equipos)
        {
            if (nombre.equals(y.getNombre()))
            {
                x = y;
                break;
            }
        }
        return x;
    }

    /**
     * Devuelve un puntero de Ronda si se encuentra una ronda ya registrada con el mismo numero.
     * De lo contrario, devuelve null.
     * @param num Numero de ronda a buscar
     * @return Una Ronda si se encuentra registrada, o null de lo contrario.
     */
    private Ronda buscarRonda(int num)
    {
        // Devuelve un puntero de Ronda si se encuentra una ronda ya registrada con el mismo numero
        // De lo contrario, devuelve null.
        Ronda r = null;
        for (Fase x : lista_fases)
        {
            ArrayList<Ronda> rondas = x.getRondas();
            for (Ronda y : rondas)
            {
                if (y.getNumero() == num)
                {
                    r = y;
                    return r;
                }
            }
        }
        return r;
    }

    public ArrayList<Persona> getLista_personas()
    {
        return lista_personas;
    }

    public ArrayList<Fase> getLista_fases()
    {
        return lista_fases;
    }

    /**
     * Verifica si ya existe un pronostico con la id de partido pasada por parámetro, para una persona.
     * @param id ID de partido de pronostico.
     * @param x Persona cuyos pronosticos se revisan.
     * @return true si se encuentra un pronostico con la id pasada por parámetro, false de lo contrario.
     */
    private boolean pronosticoRepetido( int id, Persona x )
    {
        for (Pronostico y : x.getPronosticos())
        {
            if (y.getIdPartido() == id)
                return true;
        }
        return false;
    }
}
