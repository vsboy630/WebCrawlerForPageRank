import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

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
    		
    		pw.append(key + ",");
    		
    		for(String s : valueList) {
    			
    			pw.append(s +",");
    		}
    		
    		
    		pw.append("\n");
    		
    		
    	}

            pw.flush();
            pw.close();
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
			
			while(size-- != 0 && RCH_flag <= 20) {
				
				String temp_url = queue.element();
				
				
				
				queue.remove();
				
				driver.get(temp_url);
				
				System.out.println("Current Crawling Website : " + temp_url);
				
				List<WebElement> elementNames = driver.findElements(By.xpath("//a"));
				
				for(int i=0; i< elementNames.size(); i++) {
					
					try {
						String tmp = elementNames.get(i).getAttribute("href");
						
						if(tmp.startsWith("http://") || tmp.startsWith("https://")) {
							queue.add(tmp);
							System.out.println("Crawling Result :"+ i + "--" + tmp);
						

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
		
		
		

		//driver.close();
		//driver.quit();

	}
	
	

}



