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
    private File config;
    private Datos datos;
    private ArrayList<ArrayList> tabla_torneo;

    public Logica(String resultados, String config)
    {
        this.config = new File(config);
        String[] db = readConfigFile();
        datos = new Datos(resultados, db);
        tabla_torneo = createTable(0);
    }

    public int calcularPuntos(Persona participante)
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

        /*for (int i = 0; i < tabla_participantes.size(); i++)
        {
            System.out.println(tabla_participantes.get(i));
        }*/

        return puntos;
    }

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

    public ArrayList createTable(int opc)
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

    public String[] readConfigFile()
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

    public String mostrarListado()
    {
        StringBuilder stb = new StringBuilder();
        for (Persona x : datos.getLista_personas())
        {
            stb.append(x.getNombre() + " - PUNTOS: " + calcularPuntos(x) + "\n");
        }
        return stb.toString();
    }
}
