import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by РђРЅС‚РѕРЅ on 16.10.2015.
 */
public class Work {

    private TreeMap<String,ArrayList<Integer>> products = new TreeMap<>();
    private TreeMap<String,Integer> colors = new TreeMap<>();

    public void readColors(String path)
    {
        Pattern p = Pattern.compile("^([^\\s]+)\\s([\\d]+)$");
        try
        {
            File F = new File(path);
            if(F.isFile())
            {
                if(Utils.getFileExtention(F.getName()).equals("txt"))
                {
                    Scanner sc = new Scanner(new File(F.getAbsolutePath()));
                    String line;
                    while(sc.hasNextLine())
                    {
                        line = sc.nextLine();
                        Matcher m = p.matcher(line);
                        if(m.matches())
                        {
                            String color = m.group(1);
                            Integer price = Integer.parseInt(m.group(2));
                            if(!colors.containsKey(color))
                            {
                                colors.put(color, price);
                            }
                            else new Exception("BUG");
                        }
                    }
                    sc.close();
                }
            }
            else throw new FileNotFoundException("Файл с цветами не найден");
        }
        catch(IOException e)
        {
            System.err.println(e.getClass()+" "+e.getMessage());
        }
        catch(Exception e)
        {
            System.err.println(e.getClass()+" "+e.getMessage());
            System.exit(0);
        }
    }

    public void readProducts(String path)
    {
        File F = new File(path);
        File[] fList = F.listFiles();

        Pattern p = Pattern.compile("^([^\\s]+)\\s([\\d]+)$");
        try
        {
            if(fList.length==0) throw new IOException("Директория с продуктами пуста");
            boolean isEmpty = true;
            for(int i=0; i<fList.length; i++)
            {
                if(fList[i].isFile())
                {
                    if(Utils.getFileExtention(fList[i].getName()).equals("txt"))
                    {
                        isEmpty = false;
                        Scanner sc = new Scanner(new File(fList[i].getAbsolutePath()));
                        String line;
                        TreeMap<String,Integer> temp_map = new TreeMap<>();
                        while(sc.hasNextLine())
                        {
                            line = sc.nextLine();
                            Matcher m = p.matcher(line);
                            if(m.matches())
                            {
                                String product = m.group(1);
                                Integer price = Integer.parseInt(m.group(2));
                                if(!temp_map.containsKey(product))
                                {
                                    temp_map.put(product, price);
                                }
                            }
                        }
                        sc.close();
                        addToGlobal(temp_map);
                    }
                }
            }
            if(isEmpty) throw new IOException("Директория с продуктами пуста");
        }
        catch(IOException e)
        {
            System.err.println(e.getClass()+" "+e.getMessage());
        }
        catch(Exception e)
        {
            System.err.println(e.getClass()+" "+e.getMessage());
            System.exit(0);
        }
    }

    public void printPrices()
    {
        if(colors.size()==0) colors.put("",0);
        File result = new File("result.txt");
        FileWriter writer = null;
        try {
            if (!result.isFile()) result.createNewFile();
            writer = new FileWriter(result);
            for (Map.Entry<String, ArrayList<Integer>> p : products.entrySet()) {
                for (Map.Entry<String, Integer> c : colors.entrySet()) {
                    Double sum = 0.0;
                    for (int i = 0; i < p.getValue().size(); i++) sum += p.getValue().get(i) + c.getValue();
                    writer.write(p.getKey() + "_" + c.getKey() + " " + (sum / p.getValue().size()) + System.getProperty("line.separator"));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void addToGlobal(TreeMap<String,Integer> map)
    {
        for(Map.Entry<String,Integer> entry : map.entrySet())
        {
            if(products.containsKey(entry.getKey())) products.get(entry.getKey()).add(entry.getValue());
            else
            {
                ArrayList<Integer> a = new ArrayList<>();
                a.add(entry.getValue());
                products.put(entry.getKey(), a);
            }
        }
    }
}
