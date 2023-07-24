package MainClass;

import GUI.StockGUI;
import SystemClasses.CompanySys;
import SystemClasses.WebSiteSys;

public class main {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		WebSiteSys.stockDataOutput("",1);	
		StockGUI stockGui = new StockGUI();
		stockGui.setVisible(true);
		CompanySys.displayWatchListTable("", 1);
	}
	
}
