import java.io.File;
import java.nio.file.Path;

public class TPI
{
    public static void main(String[] args)
    {
        Torneo torneo = new Torneo("resultados.csv", "pronosticos.csv");

        System.out.println("\n\t*** TRABAJO PRACTICO INTEGRADOR - Grupo Atenea ***\n");
        System.out.println("--- Listado de participantes, con sus puntos y pronosticos ---");
        System.out.print(torneo.listadoParticipantes());
        System.out.println("DONE !");
    }
}