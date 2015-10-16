/**
 * Created by Антон on 16.10.2015.
 */
public class Utils {

    public static String getFileExtention(String filename)
    {
        int dotPos = filename.lastIndexOf(".") + 1;
        return filename.substring(dotPos);
    }
}
