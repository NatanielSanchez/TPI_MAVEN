package paquete;

public class ConfigFileErrorException extends Exception
{
    private static final String ANSI_RED = "\u001B[31m";
    private String msg = ANSI_RED + "! ERROR EN EL ARCHIVO DE CONFIGURACION ! Verifique el archivo!" +
    "\n - El archivo debe contener UNA SOLA LINEA, campos separados por comas, con el siguiente formato: \n" +
    "jdbc:mysql://<host>:<port>/<nombre de la DB>,<usuario>,<contraseña>,<puntos por acierto>," +
    "<puntos por ronda completa>,<puntos por fase completa>\n" +
    " - Carácteres no permitidos en los campos: , ; white_space";

    public ConfigFileErrorException(){}

    public ConfigFileErrorException(String msg)
    {
        this.msg = msg;
    }

    @Override
    public String getMessage()
    {
        return msg;
    }
}
