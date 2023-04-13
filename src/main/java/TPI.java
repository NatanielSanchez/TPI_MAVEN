import paquete.Datos;
import paquete.Logica;

import java.io.File;

public class TPI
{
    public static void main(String args[])
    {
        System.out.println("\n\t\t*** TRABAJO PRACTICO INTEGRADOR - Grupo Atenea ***");
        System.out.println("* INTEGRANTES: *");
        System.out.println("-Nataniel Sanchez");
        System.out.print("-Katy ----> ");
        File mi_gatita = new File("src\\main\\resources\\Katy.jpg");
        System.out.println(mi_gatita.getAbsolutePath() + "\n");

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