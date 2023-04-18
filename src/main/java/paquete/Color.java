package paquete;

/**
 * Clase utilidad para dar color a textos.
 * <p>Se puede usar los colores directamente, o usar el método print() / println() para imprimir un
 * String con el color indicado.</p>
 * <p> - Para usar los colores directamente, simplemente llame al atributo (color) correspondiente,
 * seguido del texto, seguido del atributo RESET (para terminar de "pintar"). Si no se llama
 * al atributo RESET, todo texto siguiente que se imprima se mostrará en color hasta que se
 * invoque a RESET.</p>
 * <p>- Para usar el método print() / println(), se debe invocar con dos parámetros: el String a imprimir,
 * y el color (en inglés y en String) a utilizar.  Si el color está mal escrito, se imprime sin color.</p>
 * <p>- Colores disponibles: BLACK, RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, WHITE</p>
 *
 */
public abstract class Color
{
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    /**
     * Imprime un texto en color, sin "enter" (\n) al final.
     * @param txt Texto a imprimir.
     * @param color Color a usar. Si el color está mal escrito, se imprime sin color.
     */
    public static void print(Object txt, String color)
    {
        switch (color.toUpperCase())
        {
            case "BLACK":
                System.out.print(BLACK + txt + RESET);
                break;
            case "RED":
                System.out.print(RED + txt + RESET);
                break;
            case "GREEN":
                System.out.print(GREEN + txt + RESET);
                break;
            case "YELLOW":
                System.out.print(YELLOW + txt + RESET);
                break;
            case "BLUE":
                System.out.print(BLUE + txt + RESET);
                break;
            case "PURPLE":
                System.out.print(PURPLE + txt + RESET);
                break;
            case "CYAN":
                System.out.print(CYAN + txt + RESET);
                break;
            case "WHITE":
                System.out.print(WHITE + txt + RESET);
                break;
            default:
                System.out.print(txt);
        }
    }

    /**
     * Imprime un texto en color, con "enter" (\n) al final.
     * @param txt Texto a imprimir.
     * @param color Color a usar. Si el color está mal escrito, se imprime sin color.
     */
    public static void println(Object txt, String color)
    {
        switch (color.toUpperCase())
        {
            case "BLACK":
                System.out.println(BLACK + txt + RESET);
                break;
            case "RED":
                System.out.println(RED + txt + RESET);
                break;
            case "GREEN":
                System.out.println(GREEN + txt + RESET);
                break;
            case "YELLOW":
                System.out.println(YELLOW + txt + RESET);
                break;
            case "BLUE":
                System.out.println(BLUE + txt + RESET);
                break;
            case "PURPLE":
                System.out.println(PURPLE + txt + RESET);
                break;
            case "CYAN":
                System.out.println(CYAN + txt + RESET);
                break;
            case "WHITE":
                System.out.println(WHITE + txt + RESET);
                break;
            default:
                System.out.println(txt);
        }
    }
}
