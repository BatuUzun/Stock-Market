package SystemClasses;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import Companies.Company;

public class CompanySys {
	private static ArrayList<Company> companies = new ArrayList<Company>();
	private static ArrayList<Company> allCompanies = new ArrayList<Company>();
	private static ArrayList<Company> watchListAllCompanies =  new ArrayList<Company>();
	private static ArrayList<Company> watchListCompanies =  new ArrayList<Company>();
	
	public static void addCompany(String companyName, double companyPrice, double companyPercentage, String priceDate ,String type) {
		Company a = new Company(companyName, companyPrice, companyPercentage, priceDate);
		if(type.equalsIgnoreCase("displayed companies")) {
			companies.add(a);
		}		
		else
			allCompanies.add(a);		
	}
	
	public static ArrayList<Company> getCompanies() {
		return companies;
	}
	public static ArrayList<Company> getAllCompanies() {
		return allCompanies;
	}
	public static ArrayList<Company> getWatchListCompanies() {
		return watchListCompanies;
	}
	public static ArrayList<Company> getWatchListAllCompanies() {
		return watchListAllCompanies;
	}
	public static Company findCompany(String ticker) {
		for(int i = 0;i<allCompanies.size();i++) {
			if(allCompanies.get(i).isThisCompany(ticker)) {
				Company c = allCompanies.get(i);
				return c;
			}
		}		
		return null;
	}
	
	public static void setCompanies(ArrayList<Company> a){
		companies = a;
	}
	
	public static void setAllCompanies(ArrayList<Company> a){
		allCompanies = a;
	}
	
	
	public static JTable displayCompaniesAsATable() {
		String columns[] = {"STOCK TICKER", "OPENING PRICE","CURRENT PRICE", "PERCENTAGE","DATE","CEILING PRICE", "FLOOR PRICE"};
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		JTable table = new JTable(tableModel);
		Locale turkishLocale = new Locale("tr", "TR");
        NumberFormat turkishFormat = NumberFormat.getCurrencyInstance(turkishLocale);
        
        
		for(int i = 0;i<companies.size();i++) {
			
			Object[] a = {companies.get(i).getCompanyName(), turkishFormat.format(companies.get(i).getDayStartPrice()), turkishFormat.format(companies.get(i).getCompanyPrice()),
					companies.get(i).getCompanyPercentage()+"%",companies.get(i).getPriceDate(), turkishFormat.format(companies.get(i).getMaxPriceDay()), 
					turkishFormat.format(companies.get(i).getMinPriceDay())};
			
			tableModel.addRow(a);
			
		}
		
		
		return table;
	}
	
	
	
	//////////////////////////////////////
	public static void displayWatchListTable(String sub, int a) {
		watchListCompanies = new ArrayList<Company>();
		watchListAllCompanies =  new ArrayList<Company>();
		final String url = "jdbc:mysql://localhost:3306/BIST100";
		final String username = "root";
		final String password = "Ayhan1989";
		int checkPosition = 0;
		try {
			Connection con = DriverManager.getConnection(url, username, password);
			
			
			String query ="";
			if(sub.equalsIgnoreCase(""))
				query ="select * from watch_list;";
			else
				query ="select * from watch_list where ticker like '"+sub+"%';";
			
			Statement statement = con.prepareStatement(query);
			ResultSet result = statement.executeQuery(query);
			String name = "",companyPrice="", percentage="", priceDate="";
			
			
			while(result.next()) {
				
				for(int i = 1;i<8;i++) {
					switch(i) {
					case 1:name = result.getString(i);
						break;
					case 2:
						break;
					case 3:companyPrice = result.getString(i);
						break;
					case 4:percentage = result.getString(i);
						break;
					case 5:priceDate = result.getString(i);
						break;
					case 6:
						break;
					case 7:
						break;									
					
					}
					
				}
				Company b = new Company(name, Double.parseDouble(companyPrice), Double.parseDouble(percentage), priceDate);
				watchListAllCompanies.add(b);
				if(checkPosition>=(a-1)*WebSiteSys.getOnePage() && checkPosition <(a)*WebSiteSys.getOnePage()) {
					watchListCompanies.add(b);
				}
				checkPosition++;
			}
			
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	////////////////////////////////////////////
	
	public static boolean addToWatchList(String ticker) {
		final String url = "jdbc:mysql://localhost:3306/BIST100";
		final String username = "root";
		final String password = "Ayhan1989";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, password);
			Company c = findCompany(ticker);
			if(c!=null)
			{
				String query ="insert into Watch_List values("+"'"+c.getCompanyName()+"',"+c.getDayStartPrice()+","+c.getCompanyPrice()+","+
						c.getCompanyPercentage()+",'"+ c.getPriceDate()+"',"+c.getMaxPriceDay()+","+c.getMinPriceDay()+");";
		
				PreparedStatement insert = con.prepareStatement(query);
				insert.executeUpdate();
				return true;
			}
			else
				return false;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static boolean deleteFromWatchList(String ticker) {
		final String url = "jdbc:mysql://localhost:3306/BIST100";
		final String username = "root";
		final String password = "Ayhan1989";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, password);
			Company c = findCompany(ticker);
			if(c != null)
			{
				String query ="DELETE FROM watch_list WHERE ticker = '"+ ticker +"';";
				Statement delete = con.prepareStatement(query);
				delete.execute(query);
				return true;
			}
			else
				return false;
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static boolean isWatchListTableEmpty(){
		final String url = "jdbc:mysql://localhost:3306/BIST100";
		final String username = "root";
		final String password = "Ayhan1989";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, password);
			
			
				String query ="select count(*) from watch_list;";
				Statement statement = con.prepareStatement(query);
				ResultSet result = statement.executeQuery(query);
				int rowCount = 0;
				while(result.next()) {
					rowCount = result.getInt(1);
				}
				if(rowCount != 0)
					return false;
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
		
		return true;
	}
	
	public static boolean searchFromWatchListWatchList(String ticker) {
		final String url = "jdbc:mysql://localhost:3306/BIST100";
		final String username = "root";
		final String password = "Ayhan1989";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, password);
			
			
				String query ="select * from watch_list where ticker ='"+ticker+"';";
				Statement statement = con.prepareStatement(query);
				ResultSet result = statement.executeQuery(query);
				String name = "";
				while(result.next()) {
					name = result.getString(1);
				}
				if(name.equalsIgnoreCase(ticker))
					return true;
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
		
		return false;
	}
	
	public static void updateWatchList() {
		String query = "";
		Company comp = null;
		final String url = "jdbc:mysql://localhost:3306/BIST100";
		final String username = "root";
		final String password = "Ayhan1989";
		HashSet<String> companyNames = new HashSet<String>();
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, password);
			query ="select ticker from watch_list;";
			Statement statement = con.prepareStatement(query);
			ResultSet result = statement.executeQuery(query);
			
			while(result.next()) {
				companyNames.add(result.getString(1));
			}
			
			for(String s : companyNames) {
				comp = CompanySys.findCompany(s);
				query ="update watch_list set current_price="+comp.getCompanyPrice()+", opening_price ="+comp.getDayStartPrice()+", percentage="+comp.getCompanyPercentage()+
						", hour ='"+comp.getPriceDate()+"', ceiling_price="+comp.getMaxPriceDay()+", floor_price="+comp.getMinPriceDay()+
						" where ticker = '"+comp.getCompanyName()+"';";
				statement = con.createStatement();
				statement.executeUpdate(query);
			}
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		
	}
	
}
