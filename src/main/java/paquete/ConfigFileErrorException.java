package paquete;

/**
 * Representa un error en el contenido del archivo de configuración, el cual
 * puede darse por:
 * <p>
 *     -Cantidad de campos incorrectos.
 * </p>
 * <p>
 *     -Contenido incorrecto en los campos.
 * </p>
 */
public class ConfigFileErrorException extends Exception
{
    private String msg = Color.RED + "! ERROR EN EL ARCHIVO DE CONFIGURACION ! Verifique el archivo!" +
    "\n - El archivo debe contener UNA SOLA LINEA, campos separados por comas, con el siguiente formato: \n" +
    "jdbc:mysql://<host>:<port>/<nombre de la DB>,<nombre de tabla>, <usuario>,<contraseña>,<puntos por acierto>," +
    "<puntos por ronda completa>,<puntos por fase completa>\n" +
    " - Carácteres no permitidos en los campos: , ; white_space" + Color.RESET;

    public ConfigFileErrorException(){}

    public ConfigFileErrorException(String msg)
    {
        this.msg = Color.RED + msg + Color.RESET;
    }

    @Override
    public String getMessage()
    {
        return msg;
    }
}
