package SystemClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;

import java.util.HashSet;

import java.util.Locale;

import Companies.Company;

public class MyStocksSys {
	private final static int onePage = 2;
	
	public static int getOnePage() {
		return onePage;
	}
	
	public static void addToMyStocksTable(String ticker, double cost, int amount) {
		
		Company comp = CompanySys.findCompany(ticker);
		final String url = "jdbc:mysql://localhost:3306/BIST100";
		final String username = "root";
		final String password = "Ayhan1989";
		String query;
		Connection con = null;
		double[] pcs;
		try {
			con = DriverManager.getConnection(url, username, password);
			addToAllBuyOperations(comp, con, ticker, amount, cost);
			
			
			if(isTickerExist(con, ticker)) {
				pcs = calculateStockProfitAndAverageCost(con, ticker);
				if(pcs[2] > 0 )
				{
					query = "update my_stocks set current_price ="+comp.getCompanyPrice()+",cost = "+pcs[1]+",stock_amount = "+pcs[2]+",profit = "+pcs[0]+" where ticker='"+ticker+"';";
					Statement statement = con.createStatement();
					statement.executeUpdate(query);
					
				}
				else {
					
					
				}
				
			}else {
				if(amount >0)
				{
					pcs = calculateStockProfitAndAverageCost(con, ticker);
					query ="insert into my_stocks values('"+ticker+"',"+comp.getCompanyPrice()+","+pcs[1]+","+amount+","+pcs[0]+");";
					PreparedStatement insert;
					try {
						insert = con.prepareStatement(query);
						insert.executeUpdate();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
				
				
		}catch(Exception e) {
		
			
		}
 }
	
	public static double[] calculateStockProfitAndAverageCost(Connection con, String ticker) {
		
		//updateBuyOperationsAndMyStocks();
		
		
		double[] profitAndAverageCost = new double[3];
		int amnt = 0;
		double wastedMoney = 0, curr = 0;
		String query = "SELECT amount, cost, current_price FROM all_buy_operations where ticker = '"+ticker+"';";
		Statement statement;
		
		try {
			statement = con.prepareStatement(query);
			ResultSet result = statement.executeQuery(query);
			
			while(result.next()) {
				wastedMoney += result.getInt(1) * result.getDouble(2);
				amnt += result.getInt(1);
				curr = result.getDouble(3);
				if(amnt == 0) {
					wastedMoney = 0;
					
					deleteFromTables(con, ticker);
					
					
				}
			}
			profitAndAverageCost[0] = amnt*curr - wastedMoney;
			profitAndAverageCost[1] = wastedMoney / amnt;
			profitAndAverageCost[2] = amnt;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return profitAndAverageCost;
	}
	
	public static boolean isTickerExist(Connection con, String ticker) {
		String query ="select * from my_stocks where ticker ='"+ticker+"';";
		try {
			Statement statement = con.prepareStatement(query);
			ResultSet result = statement.executeQuery(query);
			String name = "";
			while(result.next()) {
				name = result.getString(1);
			}
			if(name.equalsIgnoreCase(ticker)) {
				return true;
				
			}else {
				return false;
		}
		
		
	}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	

	}
	
	
	
	public static void addToAllBuyOperations(Company a,Connection con, String ticker, int amount, double cost) {
		
		double currentPrice = 0;
		currentPrice = a.getCompanyPrice();
		String query ="insert into all_buy_operations values('"+ticker+"',"+amount+","+cost+","+currentPrice+")";

		PreparedStatement insert;
		try {
			insert = con.prepareStatement(query);
			insert.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String[] displayStocks(String ticker, int a) {
		final String url = "jdbc:mysql://localhost:3306/BIST100";
		final String username = "root";
		final String password = "Ayhan1989";
		Locale turkishLocale = new Locale("tr", "TR");
        NumberFormat turkishFormat = NumberFormat.getCurrencyInstance(turkishLocale);
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String query = "select * from my_stocks;";
		Statement statement;
		String[] res = new String[5];
		res[0] = "";
		res[1] = "";
		double balance = 0, totalCost = 0;
		try {
			statement = con.prepareStatement(query);
			ResultSet result = statement.executeQuery(query);
			int checkPosition = 0;
			while(result.next()) {
				checkPosition++;
				if(checkPosition>(a-1)*onePage && checkPosition <=(a)*onePage) {
					res[0]+=" "+result.getString(1)+"\n Current Price                "+  turkishFormat.format( result.getDouble(2))+"\n Total                               "+ turkishFormat.format(result.getDouble(2)*result.getInt(4)) +
							"\n Average Cost                "+ turkishFormat.format(result.getDouble(3)) +"\n Amount                          "+result.getInt(4)+"\n Profit/Loss                    "+ turkishFormat.format(result.getDouble(5))+"\n---------------------------------------------------------------------------------------------------\n"
							;
				}
				if(result.getString(1).equalsIgnoreCase(ticker)) {
					res[2] = String.valueOf(result.getInt(4));
				}
				
				balance += result.getDouble(2)*result.getInt(4);
				totalCost += result.getDouble(3)*result.getInt(4);
				
				
			}
			if(res[0].contains("---------------------------------------------------------------------------------------------------")) {
				res[0] = res[0].substring(0, res[0].lastIndexOf("---------------------------------------------------------------------------------------------------")) ;
				res[1] = turkishFormat.format(balance);
				res[3] = turkishFormat.format(balance - totalCost);
				res[4] = String.valueOf(checkPosition);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static void deleteFromTables(Connection con, String ticker) {
		String query = "delete from my_stocks where ticker = '"+ticker.toUpperCase()+"';";
		PreparedStatement stat;
		try {
			stat = con.prepareStatement(query);
			stat.executeUpdate(query);
			
			query = "delete from all_buy_operations where ticker = '"+ticker+"';";
			stat = con.prepareStatement(query);
			stat.executeUpdate(query);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void updateBuyOperationsAndMyStocks() {
		
		final String url = "jdbc:mysql://localhost:3306/BIST100";
		final String username = "root";
		final String password = "Ayhan1989";
		String query = "";
		Company comp = null;
		
		HashSet<String> companyNames= new HashSet<String>();
		
		try {
			Connection con = DriverManager.getConnection(url, username, password);
			query ="select ticker from all_buy_operations;";
			Statement statement = con.prepareStatement(query);
			ResultSet result = statement.executeQuery(query);
			
			while(result.next()) {
				companyNames.add(result.getString(1));
			}
			for(String s : companyNames) {
				comp = CompanySys.findCompany(s);
				query ="update all_buy_operations set current_price="+comp.getCompanyPrice()+" where ticker = '"+comp.getCompanyName()+"';";
				statement = con.createStatement();
				statement.executeUpdate(query);
				
				double[] pcs = calculateStockProfitAndAverageCost(con, s);
				query = "update my_stocks set current_price ="+comp.getCompanyPrice()+",profit = "+pcs[0]+" where ticker='"+s+"';";
				statement = con.createStatement();
				statement.executeUpdate(query);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
}