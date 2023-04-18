package paquete;

import javax.naming.ConfigurationException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logica
{
    private int puntos_acierto;
    private int puntos_ronda;
    private int puntos_fase;
    private File config; //formato: url,usuario,password,puntos_acierto,puntos_ronda,puntos_fase
    private Datos datos;

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
    }

    /**
     * Calcula los puntos de un participante.
     * @param participante Persona a calcular sus puntos
     * @return Cantidad de puntos obtenidos.
     */
    private int calcularPuntos(Persona participante)
    {
        int puntos = 0;
        for (Fase f : datos.getLista_fases())
        {
            boolean fase_completa = true;
            for (Ronda r : f.getRondas())
            {
                boolean ronda_completa = true;
                for (Partido p : r.getPartidos())
                {
                    Pronostico x = participante.getPronostico(p.getId());
                    if ( x != null && x.acierto() ) puntos += puntos_acierto;
                    else
                    {
                        fase_completa = false;
                        ronda_completa = false;
                    }
                }
                if (ronda_completa) puntos += puntos_ronda;
            }
            if (fase_completa) puntos += puntos_fase;
        }
        return puntos;
    }

    /**
     * Lee y verifica el archivo de configuracion.
     * @return Un vector con los datos necesarios para acceder a la DB.
     */
    private String[] readConfigFile()
    {
        Pattern p = Pattern.compile("jdbc:mysql://[^,;\\s]+:\\d+/([^,;\\s]+,){4}(\\d+,){2}\\d+");
        try (Scanner sc = new Scanner(config))
        {
            String linea = sc.nextLine();
            Matcher m = p.matcher(linea);
            if ( ! m.matches() ) throw new ConfigFileErrorException();

            String[] cfg = linea.split(",");
            puntos_acierto = Integer.parseInt(cfg[4]);
            puntos_ronda = Integer.parseInt(cfg[5]);
            puntos_fase = Integer.parseInt(cfg[6]);
            String [] y = new String[4];
            System.arraycopy(cfg, 0, y, 0, 4);
            return y;
        }
        catch(IOException ex)
        {
            Color.println(ex.getMessage() + ". ! CERRANDO PROGRAMA !", "red");
            System.exit(1);
        }
        catch(ConfigFileErrorException ex)
        {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        return null;
    }

    /**
     * Devuelve un string con el nombre de cada participante y sus puntos.
     * @return String que representa un listado de participantes y sus puntos.
     */
    public String listadoPuntos()
    {
        StringBuilder stb = new StringBuilder();
        for (Persona x : datos.getLista_personas())
        {
            stb.append(x.getNombre() + " - PUNTOS: " + calcularPuntos(x) + "\n");
        }
        return stb.toString();
    }

    public String listadoPronosticos()
    {
        StringBuilder stb = new StringBuilder();
        for (Persona x : datos.getLista_personas())
        {
            stb.append(x.toString() + "\n");
        }
        return stb.toString();
    }
}
