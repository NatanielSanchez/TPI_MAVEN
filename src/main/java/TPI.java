import paquete.Datos;
import paquete.Logica;

import java.util.ArrayList;

public class TPI
{
    public static void main(String args[])
    {
        System.out.println("\n\t\t*** TRABAJO PRACTICO INTEGRADOR - Grupo Atenea ***\n");
        String f1 = "src\\main\\resources\\resultados.csv";
        String f2 = "src\\main\\resources\\config.csv";
        //String f1 = args[0];
        //String f2 = args[1];
        Logica x = new Logica(f1, f2);

        System.out.println("\n--- Listado de participantes con sus puntos ---");
        System.out.println(x.listadoParticipantes());
        System.out.println("! DONE !");
    }
}