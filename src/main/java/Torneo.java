import paquete.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;


public class Torneo// Crea y almacena todos los datos de los equipos, partidos, rondas y participantes
{
    private ArrayList<Persona> lista_personas = new ArrayList<>(); // lista de participantes
    private ArrayList<Equipo> lista_equipos = new ArrayList<>(); // lista de equipos (para no repetir equipos)
    private ArrayList<Ronda> lista_rondas = new ArrayList<>(); // lista de rondas

    // LOS ARCHIVOS ESTAN ESCRITOS DE LA SIGUIENTE FORMA:
    //- resultados.csv : una linea por partido.
    //- pronosticos.csv : una linea por participante
    private File resultados; // formato: "ronda,equipo1,descripcion1,goles1,equipo2,descripcion2,goles2"
    private File pronosticos; // formato: "nombre,id,equipo,resultado"

    public Torneo() // NO SE USA ESTO
    {
    }

    public Torneo(String resultados, String pronosticos)
    // Recibe los paths de los archivos.
    {
        this.resultados = new File(resultados);
        this.pronosticos = new File(pronosticos);
        generatePartidos();
        generateParticipantes();
    }

    private void generatePartidos()
    {
        // Procesa el archivo de texto para crear las instancias de equipo, partido y ronda.
        // Estas instancias se almacenan en sus listas correspondientes.
        // Cada linea del archivo corresponde a un partido.
        // El contador id_partidos sirve para contar las lineas Y TAMBIEN como id de los partidos...
        // ... así cada partido tiene un numero de identificación UNICO Y DIFERENTE

        int id_partidos = 1;
        try (Scanner sc = new Scanner(resultados))
        {
            System.out.println("Archivo de partidos    --> " + this.resultados.getAbsolutePath());

            while (sc.hasNextLine())
            {
                String linea = sc.nextLine();
                String[] datos = linea.split(",");
                // Si la linea de datos tiene errores se salta a la siguiente.
                if ( dataErrorResultados(datos) )
                {
                    id_partidos++;
                    continue;
                }

                Ronda ronda = buscarRonda( Integer.parseInt(datos[0]) );
                if (ronda == null)
                {
                    ronda = new Ronda( Integer.parseInt(datos[0]) );
                    lista_rondas.add(ronda);
                }

                Equipo equipo1 = buscarEquipo(datos[1]);
                if (equipo1 == null)
                {
                    equipo1 = new Equipo(datos[1], datos[2]);
                    lista_equipos.add(equipo1);
                }

                Equipo equipo2 = buscarEquipo(datos[4]);
                if (equipo2 == null)
                {
                    equipo2 = new Equipo(datos[4], datos[5]);
                    lista_equipos.add(equipo2);
                }

                int goles1 = Integer.parseInt(datos[3]);
                int goles2 = Integer.parseInt(datos[6]);
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

    private void generateParticipantes()
    {
        // Procesa el archivo de texto para crear las instancias de Persona
        // Estas instancias se almacenan en su lista correspondiente.

        try (Scanner sc = new Scanner(pronosticos))
        {
            System.out.println("Archivo de pronosticos --> " + this.pronosticos.getAbsolutePath());

            while (sc.hasNextLine())
            {
                String linea = sc.nextLine();
                String[] datos = linea.split(",");
                if ( dataErrorPronosticos(datos) )
                    continue;

                Persona persona = buscarPersona(datos[0]);
                if (persona == null)
                {
                    persona = new Persona(datos[0]);
                    lista_personas.add(persona);
                }

                ResultadoEnum res = null;
                switch (datos[3])
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

                Partido partido = buscarPartido( Integer.parseInt(datos[1]) );
                // Si el partido no está registrado, se saltea el pronostico
                if (partido == null)
                    continue;

                Equipo equipo = buscarEquipo(datos[2]);
                // Si el equipo es null o no coincide con los equipos que jugaron el partido, se saltea el pronostico
                if ( equipo == null || !( equipo.equals(partido.getEquipo1()) || equipo.equals(partido.getEquipo2()) ) )
                    continue;

                Pronostico pron = new Pronostico(partido, equipo, res);
                persona.addPronostico(pron);
            }
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /**
    Verifica los datos de resultados.csv
     @return false si los datos son correctos, true si hubo errores.
    */
    private boolean dataErrorResultados(String[] datos)
    {
        boolean error = false;
        if ( datos.length != 7 ) return error = true; // cantidad de datos erronea
        try
        {
            int num1 = Integer.parseInt(datos[0]); //ronda
            int num2 = Integer.parseInt(datos[3]); //goles equipo1
            int num3 = Integer.parseInt(datos[6]); //goles equipo2
            if ( num1 < 0 || num2 < 0 || num3 < 0 ) // Datos numericos negativos
                error = true;
        }
        catch (NumberFormatException ex) // Datos numericos erroneos
        {
            System.out.println(ex);
            error = true;
        }
        return error;
    }

    /**
     Verifica los datos de pronosticos.csv
     @return false si los datos son correctos, true si hubo errores.
     */
    public boolean dataErrorPronosticos(String[] datos)
    {
        boolean error = false;
        if ( datos.length != 4 ) return error = true; // cantidad de datos erronea
        try
        {
            int num = Integer.parseInt(datos[1]); // id de Partido
            if ( num < 0 ) // Dato numerico negativos
                error = true;
        }
        catch (NumberFormatException ex) // Dato numerico erroneo
        {
            System.out.println(ex.getMessage());
            error = true;
        }
        return error;
    }

    private Partido buscarPartido(int num)
    {
        // Devuelve la instancia de Partido segun el numero id.
        Partido x = null;
        for (int i = 0; i < lista_rondas.size(); i++) // Recorre cada ronda del torneo
        {
            x = lista_rondas.get(i).getPartido(num); // Ver getPartido() en Ronda
            if (x != null) break;
        }
        return x;
    }

    private Persona buscarPersona(String nombre)
    {
        // Devuelve un puntero de Persona si se encuentra una persona (participante) ya registrada con el nombre
        // De lo contrario, devuelve null.
        Persona x = null;
        for (int i = 0; i < lista_personas.size(); i++)
        {
            if ( nombre.equals(lista_personas.get(i).getNombre()) )
            {
                x = lista_personas.get(i);
                break;
            }
        }
        return x;
    }

    private Equipo buscarEquipo(String nombre)
    {
        // Devuelve un puntero de Equipo si se encuentra un equipo ya registrada con el nombre
        // De lo contrario, devuelve null.
        Equipo x = null;
        for (int i = 0; i < lista_equipos.size(); i++)
        {
            if ( nombre.equals(lista_equipos.get(i).getNombre()) )
            {
                x = lista_equipos.get(i);
                break;
            }
        }
        return x;
    }

    private Ronda buscarRonda(int num)
    {
        // Devuelve un puntero de Ronda si se encuentra una ronda ya registrada con el mismo numero
        // De lo contrario, devuelve null.
        Ronda x = null;
        for (int i = 0; i < lista_rondas.size(); i++)
        {
            if (lista_rondas.get(i).getNumero() == num)
                return lista_rondas.get(i);
        }
        return x;
    }

    public String listadoParticipantes()
    {
        StringBuilder stb = new StringBuilder();
        for (int i=0; i < lista_personas.size(); i++)
        {
            stb.append(lista_personas.get(i).toString());
            stb.append("\n");
        }
        return stb.toString();
    }
}
