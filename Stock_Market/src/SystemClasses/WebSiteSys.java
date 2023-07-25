package SystemClasses;


import java.io.IOException;

import java.util.ArrayList;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import Companies.Company;


public class WebSiteSys {
	private final static String stockURL = "LINK1";
	private final static String bistURL = "LINK2";
	private final static int onePage = 20;
	private final static int bistIndexesPerPage = 15;
	private static ArrayList<Object[]> bistIndexes = new ArrayList<Object[]>();
	private static ArrayList<Object[]> bistAllIndexes = new ArrayList<Object[]>();
	
	public static int getOnePage() {
		return onePage;
	}
	public static int getOnePageForIndexes() {
		return bistIndexesPerPage;
	}
	
	public static ArrayList<Object[]> getBistIndexes() {
		return bistIndexes;
	}
	public static ArrayList<Object[]> getBistAllIndexes() {
		return bistAllIndexes;
	}
	public static void stockDataOutput(String sub, int a) {
		ArrayList<Company> comp = new ArrayList<Company>();
		ArrayList<Company> allComp = new ArrayList<Company>();
		CompanySys.setCompanies(comp);
		CompanySys.setAllCompanies(allComp);
		int checkPosition = 0;
		try {
			String name, id, date;
			double price, percentage;
			
			final Document document = Jsoup.connect(stockURL).get();
			
			for(Element data : document.select("table.table3 tr")) {
				if(data.select("td.currency").text().equals("")) {
					continue;
				}
				else {
					name = data.select("td.currency").text();
					if(!name.contains(sub)) {
						continue;		
					}
					else {
						checkPosition++;
						id= "td#h_td_fiyat_id_"+name;
						price = Double.parseDouble(data.select(id).text().replace(".", "").replace(",", "."));
						id = "td#h_td_yuzde_id_"+name;
						percentage = Double.parseDouble(data.select(id).text().replace(",", "."));
						id ="td#h_td_zaman_id_"+name;
						date = data.select(id).text();
						CompanySys.addCompany(name, price, percentage, date, "all");
						if(checkPosition>(a-1)*onePage && checkPosition <=(a)*onePage) {
							CompanySys.addCompany(name, price, percentage, date, "displayed companies");
						}
						
						
					}
					
				}
				
				
			}
			//
			CompanySys.updateWatchList();
			MyStocksSys.updateBuyOperationsAndMyStocks();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void bistDataOutput(int a) {
		
		bistIndexes = new ArrayList<Object[]>();
		bistAllIndexes = new ArrayList<Object[]>();
        String index ="";
        String last = "";
        String dailyChange = "";
        String weeklyChange = "";
        
		try {
			final Document doc = Jsoup.connect(bistURL).get();
			boolean isBist100 = false;
			int i = 0, checkPosition = 0;
			for(Element data : doc.select("td")) {
				if(data.select("td").text().equals("BIST 100")) isBist100 = true;
				if(data.select("td").text().equals("")) {
					continue;
				}
				else if(isBist100){
					switch(i) {
					case 0: index = data.select("td").text(); break;
					case 1: last = data.select("td").text().replace(".", ""); break;
					case 2: dailyChange = data.select("td").text().replace(",", "."); break;
					case 3: weeklyChange = data.select("td").text().replace(",", "."); Object[] b = {index, last, dailyChange, weeklyChange};bistAllIndexes.add(b); 
					checkPosition++;
					if(checkPosition>(a-1)*getOnePageForIndexes() && checkPosition <=(a)*getOnePageForIndexes()) {
						bistIndexes.add(b);
					}
					break;	
					case 4:
						break;
					case 5:i = -1;break;
					}
					i++;					
				}			
			}			
			} catch (IOException e) {
			
			e.printStackTrace();
		}		
	}
	
	
	
	
	
}
