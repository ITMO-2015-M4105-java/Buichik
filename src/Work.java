import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Work 
{
	private TreeMap<String,ArrayList<Integer>> products = new TreeMap<String,ArrayList<Integer>>();
	private TreeMap<String,Integer> colors = new TreeMap<String,Integer>();
	
	public void readColors(String path) 
	{
		Pattern p = Pattern.compile("^([^\\s]+)\\s([\\d]+)$");
		try
		{
			File F = new File(path);
			if(F.isFile())
			{
				if(getFileExtention(F.getName()).equals("txt"))
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
			if(fList.length==0) throw new IOException("Папка с продуктами пуста");
			boolean isEmpty = true;
			for(int i=0; i<fList.length; i++)           
			{
				if(fList[i].isFile())
				{
					if(getFileExtention(fList[i].getName()).equals("txt"))
					{
						isEmpty = false;
						Scanner sc = new Scanner(new File(fList[i].getAbsolutePath()));
						String line;
						TreeMap<String,Integer> temp_map = new TreeMap<String,Integer>();
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
			if(isEmpty) throw new IOException("Папка с продуктами пуста");
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
		for(Map.Entry<String,ArrayList<Integer>> p : products.entrySet())
		{
			for(Map.Entry<String,Integer> c : colors.entrySet())
			{
				Double sum = 0.0;
				for(int i = 0;i<p.getValue().size();i++) sum+=p.getValue().get(i)+c.getValue();
				info(p.getKey()+"_"+c.getKey()+" "+(sum/p.getValue().size()));
			}
		}
	}
	
	private String getFileExtention(String filename)
	{
		int dotPos = filename.lastIndexOf(".") + 1;
		return filename.substring(dotPos);
	}
	
	private void addToGlobal(TreeMap<String,Integer> map)
	{
		for(Map.Entry<String,Integer> entry : map.entrySet())
		{
			if(products.containsKey(entry.getKey())) products.get(entry.getKey()).add(entry.getValue());
			else 
			{
				ArrayList<Integer> a = new ArrayList<Integer>();
				a.add(entry.getValue());
				products.put(entry.getKey(), a);
			}
		}
	}
	
	private void info(String str)
	{
		System.out.println(str);
	}
}
