import java.io.IOException;

/**
 * Created by Антон on 16.10.2015.
 */
public class Main {

    public static void main(String... args) throws IOException
    {
        if(args.length!= 2 || args[0].isEmpty() || args[1].isEmpty()) throw new IOException("Неверные аргументы");
        String path = args[0];
        String colors = args[1];
        Work lab1 = new Work();
        lab1.readProducts(path);
        lab1.readColors(colors);
        lab1.printPrices();
    }
}
