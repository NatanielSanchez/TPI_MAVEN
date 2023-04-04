import java.io.File;
import java.nio.file.Path;

public class TPI
{
    public static void main(String[] args)
    {
        String f1 = "src\\main\\resources\\resultados.csv";
        String f2 = "src\\main\\resources\\pronosticos.csv";
        Torneo torneo = new Torneo(f1, f2);

        System.out.println("\n\t*** TRABAJO PRACTICO INTEGRADOR - Grupo Atenea ***\n");
        System.out.println("--- Listado de participantes, con sus puntos y pronosticos ---");
        System.out.print(torneo.listadoParticipantes());
        System.out.println("! DONE !");
    }
}