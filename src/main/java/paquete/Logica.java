package paquete;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

public class Logica
{
    private int puntos_acierto;
    private int puntos_ronda;
    private int puntos_fase;
    private File config; //formato: url,usuario,password,puntos_acierto,puntos_ronda,puntos_fase
    private Datos datos;
    private ArrayList<ArrayList> tabla_torneo; //tabla de partidos, organizada por fase y ronda

    /**
     * Recibe los archivos de texto, crea una instancia de Datos,
     * y calcula los puntos de cada participante.
     * @param resultados Ruta al archivo de partidos.
     * @param config Ruta al archivo de configuracion (acceso a la DB y puntaje por aciertos)
     */
    public Logica(String resultados, String config)
    {
        this.config = new File(config);
        System.out.println("Archivo de configuracion    --> " + this.config.getAbsolutePath());
        String[] db = readConfigFile();
        datos = new Datos(resultados, db);
        tabla_torneo = createTable(0);
    }

    /**
     * Calcula los puntos de cada participante, usando dos tablas (matrices):
     * <p>
     * -tabla_torneo, que representa el total de partidos del torneo, organizado por fase y ronda;
     * <p>
     * -tabla_participante: idéntica a la tabla del torneo, pero con valores inicializados en cero,
     * que se van llenando segun los aciertos de los pronosticos.
     * @param participante Persona a calcular sus puntos
     * @return Puntos obtenidos por el participante
     */
    private int calcularPuntos(Persona participante)
    {
        ArrayList<ArrayList> tabla_participantes = createTable(1);
        for (Pronostico x : participante.getPronosticos())
        {
            if (x.acierto())
            {
                int[] fase_ronda = getFaseRonda(x.getIdPartido());
                ArrayList<Integer> z = tabla_participantes.get(fase_ronda[0]);
                int num = (int) tabla_participantes.get(fase_ronda[0]).get(fase_ronda[1])  + 1;
                tabla_participantes.get(fase_ronda[0]).set(fase_ronda[1], num);
            }
        }

        int puntos = 0;
        for (int i = 0; i < tabla_torneo.size(); i++)
        {
            int partidos_fase = 0;
            int acierto_fase_participante = 0;

            for (int j = 0; j < tabla_torneo.get(i).size(); j++)
            {
                partidos_fase += (int) tabla_torneo.get(i).get(j);
                acierto_fase_participante += (int) tabla_participantes.get(i).get(j);
                puntos += (int) tabla_participantes.get(i).get(j) * puntos_acierto;
                if (tabla_participantes.get(i).get(j) == tabla_torneo.get(i).get(j))
                    puntos += puntos_ronda;
            }
            if (partidos_fase == acierto_fase_participante)
                puntos += puntos_fase;
        }

        return puntos;
    }

    /**
     * Devuelve la fase y ronda de un partido.
     * @param idPartido Partido a buscar
     * @return La fase y ronda del partido, en un vector.
     */
    private int[] getFaseRonda(int idPartido)
    {
        ArrayList<Fase> fases = datos.getLista_fases();
        int[] fase_ronda = new int[2];
        for (int i = 0; i < fases.size(); i++)
        {
            fase_ronda[0] = i;
            for (int j = 0; j < fases.get(i).getRondas().size(); j++)
            {
                Partido x = fases.get(i).getRondas().get(j).getPartido(idPartido);
                if (x != null)
                {
                    fase_ronda[1] = j;
                    return fase_ronda;
                }
            }
        }
        throw new InvalidParameterException("Algo salió mal en getFaseRonda()... ");
    }

    /**
     * Crea las tablas para calcular los puntos de los participantes.
     * @param opc Opcion 0 para crear la tabla del torneo, donde cada casilla es la cantidad de partidos.
     *         Opcion 1 para crear la tabla del participante, donde cada casilla se inicializa en cero.
     * @return Una matriz (cuadrada o irregular) formada por ArrayLists
     */
    private ArrayList createTable(int opc)
    {
        ArrayList<ArrayList> tabla = new ArrayList<>();
        for (Fase fase : datos.getLista_fases())
        {
            ArrayList<Integer> y = new ArrayList<>();
            for (Ronda ronda : fase.getRondas())
            {
                if (opc == 0)
                    y.add(ronda.getSize());
                if (opc == 1)
                    y.add(0);
            }
            tabla.add(y);
        }
        return tabla;
    }

    /**
     * Lee y verifica el archivo de configuracion.
     * @return Un vector con los datos necesarios para acceder a la DB.
     */
    private String[] readConfigFile()
    {
        try (Scanner sc = new Scanner(config))
        {
            String[] cfg = sc.nextLine().split(",");
            puntos_acierto = Integer.parseInt(cfg[3]); // tira una exception si no es un numero.
            puntos_ronda = Integer.parseInt(cfg[4]); // tira una exception si no es un numero.
            puntos_fase = Integer.parseInt(cfg[5]); // tira una exception si no es un numero.
            String [] y = new String[3];
            for (int i = 0; i < y.length; i++)
            {
                y[i] = cfg[i];
            }
            return y;
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage() + ". ! CERRANDO PROGRAMA !");
            System.exit(1);
        }
        catch(NumberFormatException ex)
        {
            System.out.println("Dato numerico erróneo. ! VERIFIQUE EL ARCHIVO ! ");
            throw ex;
        }
        return null;
    }

    /**
     * Devuelve un string con el nombre de cada participante y sus puntos.
     * @return String que representa un listado de participantes y sus puntos.
     */
    public String listadoParticipantes()
    {
        StringBuilder stb = new StringBuilder();
        for (Persona x : datos.getLista_personas())
        {
            stb.append(x.getNombre() + " - PUNTOS: " + calcularPuntos(x) + "\n");
        }
        return stb.toString();
    }
}
