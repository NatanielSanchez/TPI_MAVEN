import paquete.Color;
import paquete.Datos;
import paquete.Logica;

import java.io.File;

public class TPI
{
    public static void main(String args[])
    {
        Color.println("\n\t\t*** TRABAJO PRACTICO INTEGRADOR - Grupo Atenea ***", "blue");
        Color.println("* INTEGRANTES: *", "blue");
        System.out.println("-Nataniel Sanchez");
        System.out.println("-Mi gata Katy");

        Color.println("\n! ATENCION ! : El programa debe recibir dos argumentos al ejecutar: " +
                "\n1) La ruta a un archivo con los partidos, donde cada linea representa " +
                "un partido, con el siguiente formato:" +
                "\n  <fase>,<ronda>,<nombreEquipo1>,<descripcion>,<golesEquipo1>," +
                "<nombreEquipo2>,<descripcion>,<golesEquipo2>" +
                "\n  La ID de partido se auto-genera en el programa, según el numero de línea." +
                "\n2) La ruta a un archivo de configuración, con UNA SOLA LINEA," +
                " con el siguiente formato:" +
                "\n  <url de la DB>,<usuario>,<contraseña>,<puntos por acierto>," +
                "<puntos por ronda completa>,<puntos por fase completa>" +
                "\nLa tabla con los pronosticos debe tener las siguientes columnas: " +
                "\n   nombre(string),idPartido(int),equipo(string),resultado(string)\n", "yellow");

        //String f1 = "C:\\Users\\crabs\\Documents\\GitHub\\TPI_MAVEN\\target\\classes\\resultados.csv";
        //String f2 = "C:\\Users\\crabs\\Documents\\GitHub\\TPI_MAVEN\\target\\classes\\config.csv";
        if (args.length < 2)
        {
            Color.println("! FALTAN UNA O MAS RUTAS DE ARCHIVOS !", "red");
            return;
        }
        String f1 = args[0];
        String f2 = args[1];
        Logica x = new Logica(f1, f2);

        Color.println("\n--- Listado de participantes con sus puntos ---", "green");
        System.out.println(x.listadoParticipantes());
        Color.println("! DONE !", "green");
    }
}