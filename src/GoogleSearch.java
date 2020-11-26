import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.json.Json;



/*
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agogs.languagelayer.api.APIConsumer;
import com.github.agogs.languagelayer.api.LanguageLayerAPIConsumer;
import com.github.agogs.languagelayer.model.APIResult;
import com.github.agogs.languagelayer.model.Batch;
import com.github.agogs.languagelayer.model.QueryParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import org.json.simple.JSONObject;



public class GoogleSearch {
	
    public static void writeToCsv_OutLink(String url) throws IOException{
    	FileWriter pw = new FileWriter("report_out.csv",true); 
    		pw.append(url+","+"\n");  
            pw.flush();
            pw.close();
    }
    
    public static void writeToCsv_InLink(Map<String, List<String>> web_record) throws IOException{
    	FileWriter pw = new FileWriter("report_In.csv",true);
    	
    	for(Map.Entry<String, List<String>> me : web_record.entrySet()) {
    		
    		String key = me.getKey();
    		
    		List<String> valueList = me.getValue();
    		
    		//pw.append(key + ",");
    		System.out.println("Seed" + "--> " + key);
    		//System.out.println("\n");
    		
    		for(String s : valueList) {
    			
    			//pw.append(s +",");
    			System.out.println(s);
    			//System.out.println("\n");
    		}
    		
    		
    		pw.append("\n");
    		
    		
    	}

            pw.flush();
            pw.close();
    }
    
    
    public static boolean check_pagerank(HashMap<String,Double> a, HashMap<String,Double> b) throws IOException{
    	
    	int i = 0;
    	
    	for(HashMap.Entry<String, Double> me :a.entrySet()) {
    		
    		String key_ = me.getKey();
    		i++;
    		
    		if(a.get(key_) == b.get(key_)) {
    			
    			System.out.println("i = " + i);
    			
    			return false;
    		}
    	}
    	
    	return true;
    	
    }
    
    public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm) { 
        // Create a list from elements of HashMap 
        List<Map.Entry<String, Double> > list = new LinkedList<Map.Entry<String, Double> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() { 
            public int compare(Map.Entry<String, Double> o1,  
                               Map.Entry<String, Double> o2) 
            { 
                return (o1.getValue()).compareTo(o2.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>(); 
        for (Map.Entry<String, Double> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 
    
    public static void PRcalculator(Map<String, List<String>> inCome,HashMap<String,  Integer> outPut) throws IOException {
    	
    	HashMap<String,Double> pagerank_table = new HashMap<String, Double>();
    	HashMap<String,Double> pagerank_table_update = new HashMap<String, Double>();
    	
    	
    	double size = outPut.size();
    	
    	double ini_pr = 1/size;
    	int counter = 0;
    	
    	System.out.println(size);

    	
    	
    	for(HashMap.Entry<String, Integer> me :outPut.entrySet()) {
    		
    		String key = me.getKey();
    		pagerank_table.put(key, ini_pr);
    		//System.out.println(key + " **** " + ini_pr);
    	}
    	
    	
    	while(check_pagerank(pagerank_table,pagerank_table_update)) {

    		pagerank_table_update = new HashMap<String,Double>(pagerank_table);
        	
    		
        	for(HashMap.Entry<String, Double> me :pagerank_table.entrySet()) {
        		
        		double temSum = 0;
        		
        		try {
        			
            		String key_ = me.getKey();
            		List<String> outList = inCome.get(key_);
            		//System.out.println("RCHDBG---key_ " + key_);
            		
            		for(String s : outList) {
            			
            			temSum = temSum + pagerank_table.get(s)/outPut.get(s);
            		}
            		
            		pagerank_table.put(key_, temSum);
            		
            		
        		}
        		
        		catch(Exception e) {
    				System.out.println("EXCEPT");
    			}
        		
        	}
        	

        	
        	counter++;
    	}

    	
    	
    	//print page rank table
    	/*
    	for(HashMap.Entry<String, Double> me :pagerank_table.entrySet()) {
    		
    		//String key = me.getKey();
    		//pagerank_table.put(key, ini_pr);
    		System.out.println("key===>  " + me.getKey() + "   value   " + me.getValue());
    		
    	}
    	*/
    	
    	Map<String, Double> hm1 = sortByValue(pagerank_table);
    	
    	for (Map.Entry<String, Double> en : hm1.entrySet()) { 
            System.out.println("Key = " + en.getKey() +  ", Value = " + en.getValue()); 
        } 
    	
    	
    	
    	
    }

	public static void main(String[] args) throws IOException {
		
		
		//File file = new File("C:\\Selenium\\chromedriver.exe");
		
		HashMap<String, Integer> web_index = new HashMap<String, Integer>();
		Map<String, List<String>> web_map = new HashMap<String, List<String>>();

		//System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\HAPPYYYY\\git\\webcrawling\\chrome_DR\\chromedriver.exe");
		
		
		WebDriver driver = new ChromeDriver();
		
		var url = "https://www.cpp.edu/";
		
		Queue<String> queue = new LinkedList<>();
		var count = 0;
		
		
		queue.add(url);

		//System.out.println(queue.element());		
		

		while(!queue.isEmpty() && count <= 5) {
			
			//System.out.println("51561561561");
			
			
			var size = queue.size();
			var RCH_flag = 0;
			
			while(size-- != 0 && RCH_flag <= 8) {
				
				String temp_url = queue.element();
				
				
				
				queue.remove();
				
				driver.get(temp_url);
				
				//System.out.println("Current Crawling Website : " + temp_url);
				
				List<WebElement> elementNames = driver.findElements(By.xpath("//a"));
				
				for(int i=0; i< elementNames.size(); i++) {
					
					try {
						String tmp = elementNames.get(i).getAttribute("href");
						
						if(tmp.startsWith("http://") || tmp.startsWith("https://")) {
							queue.add(tmp);
							//System.out.println("Crawling Result :"+ i + "--" + tmp);
						

							if(!web_map.containsKey(tmp)) {
							
								web_map.put(tmp, new ArrayList<String>());
							}
						
							web_map.get(tmp).add(temp_url);
						
						}
						
					}
					catch(Exception e) {
						System.out.println("EXCEPT");
					}

				}
				
				RCH_flag++;
				
				//System.out.println(elementNames.size());
				web_index.put(temp_url, elementNames.size());
				
				writeToCsv_OutLink(temp_url+","+elementNames.size());
			}
			
			count++;

		}
		
		writeToCsv_InLink(web_map);
		
		PRcalculator(web_map,web_index);
		

		//driver.close();
		//driver.quit();

	}
	
	

}



