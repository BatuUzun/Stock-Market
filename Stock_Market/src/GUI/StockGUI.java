package GUI;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



import Companies.Company;
import SystemClasses.CompanySys;
import SystemClasses.MyStocksSys;
import SystemClasses.WebSiteSys;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;


import javax.swing.border.CompoundBorder;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;



public class StockGUI extends JFrame {

	private JPanel contentPane;
	
	private JTextField companyTickerTextField;
	private JTextField addWatchListTextField;
	private JTextField deleteWatchListTextField = new JTextField();
	private JPanel panelDeleteWatchList;
	private JTextField stockTickerTf;
	private JTextField costTf;
	private JTextField amountTf;
	private JTextArea myStocksTextArea;
	private JLabel balanceLabel;
	private JLabel totalPLLbl;
	
	private SimpleDateFormat formatter;
	private Date date;
	private JLabel hourLbl;
	
	private JLabel[][] bist100data = new JLabel[WebSiteSys.getOnePage()][7];
	private JLabel[][] watchListData = new JLabel[WebSiteSys.getOnePage()][7];
	private JLabel[][] bistIndexesData = new JLabel[WebSiteSys.getOnePageForIndexes()][4];
	private ArrayList<Company> companies = CompanySys.getCompanies();
	
	private JLabel pageInfoLbl;
	private int pageBist100 = 1;
	private int pagewatchList = 1;
	private int pageMyStocks = 1;
	private JButton oneLeftButton;
	private JButton firstButton;
	private JButton oneRightButton;
	private JButton lastButton;
	private int result;
	private ArrayList<Company> watchListCompanies;
	private ArrayList<Object[]> bistIndexes;
	private Locale turkishLocale = new Locale("tr", "TR");
    private NumberFormat turkishFormat = NumberFormat.getCurrencyInstance(turkishLocale);
    
	private int resultWatchList;
	private JButton firstPageWLButton;
	private JButton oneLeftPageWLButton;
	private JLabel pageInfoLblWL;
	private JButton oneRightWLButton;
	private JButton lastWLButton;
	private int redG = 128;
    private int greenG = 255;
    private int blueG = 128;
    
    private int redR = 198;
    private int greenR = 57;
    private int blueR = 61;
    
    private  Color bgColorG = new Color(redG, greenG, blueG);
    private  Color bgColorR = new Color(redR, greenR, blueR);

	private int pageBistIndexes = 1;
	private JButton firstButtonIndexes;
	private JButton oneLeftButtonIndexes;
	private JButton oneRightButtonIndexes;

	private int resultBistIndexes;
	private JLabel pageInfoIndexesLbl;
	private JButton lastButtonIndexes;
	private JLabel pageInfoLblMyStocks;
	private JButton firstButtonMyStocks;
	private JButton oneLeftButtonMyStocks;
	private JButton oneRightButtonMyStocks;
	private JButton lastButtonMyStocks;

	private int resultMyStocks;
	private JButton sellBtn;
	private JButton buyBtn;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockGUI frame = new StockGUI();
					
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StockGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(-10, -5, 1991, 1600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new CompoundBorder());
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		for(int i = 0;i<WebSiteSys.getOnePage();i++) {
			for(int k = 0;k<7;k++) {
				bist100data[i][k] = new JLabel();
				bist100data[i][k].setOpaque(true);
				watchListData[i][k] = new JLabel();
				watchListData[i][k].setOpaque(true);
			}
			
		}
		
		for(int i = 0;i<WebSiteSys.getOnePageForIndexes();i++) {
			for(int k = 0;k<4;k++) {
				bistIndexesData[i][k] = new JLabel();
				bistIndexesData[i][k].setOpaque(true);				
			}
			
		}
		
		String columnNamesForBistIndexes[] = {"INDEX", "LAST","DAILY CHANGE(%)", "WEEKLY CHANGE(%)"};
		for(int i = 0;i<4;i++) {
			JLabel columnNames = new JLabel();
			columnNames.setBounds(1100 + (i * 200) ,80, 220, 20);
			columnNames.setText(columnNamesForBistIndexes[i]);
			columnNames.setHorizontalAlignment(SwingConstants.CENTER);
	        contentPane.add(columnNames);
			
		}
		
		//{"STOCK TICKER", "OPENING PRICE","CURRENT PRICE", "PERCENTAGE","DATE","CEILING PRICE", "FLOOR PRICE"};
		String[] columnNamesForBist100 = {"STOCK TICKER", "OPENING PRICE","CURRENT PRICE", "PERCENTAGE","DATE","CEILING PRICE", "FLOOR PRICE"};
		for(int i = 0;i<7;i++) {
			JLabel columnNames = new JLabel();
			columnNames.setBounds(-50 + (i * 140) ,80, 200, 20);
			columnNames.setText(columnNamesForBist100[i]);
			columnNames.setHorizontalAlignment(SwingConstants.CENTER);
	        contentPane.add(columnNames);
			
		}
		
		
		for(int i = 0;i<7;i++) {
			JLabel columnNames = new JLabel();
			columnNames.setBounds(900 + (i * 140) ,510, 200, 20);
			columnNames.setText(columnNamesForBist100[i]);
			columnNames.setHorizontalAlignment(SwingConstants.CENTER);
	        contentPane.add(columnNames);
			
		}
		
		//watchListData[i][k].setBounds(900 + (k * 140) ,530, 200, 20);
		setTitle("Stock Market");
		JLabel lblNewLabel = new JLabel("Enter Company's Ticker: ");
		lblNewLabel.setBounds(10, 521, 143, 14);
		contentPane.add(lblNewLabel);
		
		
		companyTickerTextField = new JTextField();
		companyTickerTextField.addKeyListener(new KeyAdapter() {
		
			@Override
			public void keyReleased(KeyEvent e) {
				
					companyTickerTextField.setText(companyTickerTextField.getText().toUpperCase());
					pageBist100 = 1;
					createTable();
				setBist100PageButtons();
				
			}
		});
		companyTickerTextField.setBounds(184, 518, 86, 20);
		contentPane.add(companyTickerTextField);
		companyTickerTextField.setColumns(10);
		
		
		
		
		
		
		//scrollPane.setViewportView(table);
		hourLbl = new JLabel("");
		hourLbl.setFont(new Font("Tahoma", Font.PLAIN, 26));
		hourLbl.setBounds(1755, 16, 135, 73);
		contentPane.add(hourLbl);
		formatter = new SimpleDateFormat("HH:mm:ss");  
		date = new Date();  
		hourLbl.setText(formatter.format(date));
		
		JLabel lblNewLabel_1 = new JLabel("Add to Watch List:");
		lblNewLabel_1.setBounds(892, 950, 135, 14);
		contentPane.add(lblNewLabel_1);
		
		
		
		JLabel cannotBeFoundLbl = new JLabel("Ticker cannot be found!");
		cannotBeFoundLbl.setBounds(1139, 950, 164, 14);
		contentPane.add(cannotBeFoundLbl);
		cannotBeFoundLbl.setVisible(false);
		
		addWatchListTextField = new JTextField();
		addWatchListTextField.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				addWatchListTextField.setText(addWatchListTextField.getText().toUpperCase());
				if(e.getKeyCode()  == KeyEvent.VK_ENTER) {
					boolean isAdded = CompanySys.addToWatchList(addWatchListTextField.getText());
					if(!isAdded) {
						if(CompanySys.searchFromWatchListWatchList(addWatchListTextField.getText())) {
							cannotBeFoundLbl.setText(addWatchListTextField.getText()+" is already in watch list!");
						}
						else {
							cannotBeFoundLbl.setText(addWatchListTextField.getText()+" cannot be found!");
							cannotBeFoundLbl.setVisible(true);
						}
						
					}
					if(isAdded) {
						cannotBeFoundLbl.setVisible(false);
						addWatchListTextField.setText("");
						createWatchListTable();
						
						
						displayDeleteWatchListPanel();
					}					
				}
				
			}
		});
		addWatchListTextField.setBounds(1032, 947, 86, 20);
		contentPane.add(addWatchListTextField);
		addWatchListTextField.setColumns(10);
		
		panelDeleteWatchList = new JPanel();
		panelDeleteWatchList.setBounds(879, 970, 411, 51);
		contentPane.add(panelDeleteWatchList);
		panelDeleteWatchList.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Delete From Watch List:");
		lblNewLabel_2.setBounds(10, 22, 135, 14);
		panelDeleteWatchList.add(lblNewLabel_2);
		
		JLabel isDeletedLabel = new JLabel("Ticker cannot be found!");
		isDeletedLabel.setBounds(251, 22, 140, 14);
		panelDeleteWatchList.add(isDeletedLabel);
		isDeletedLabel.setVisible(false);
		
		deleteWatchListTextField = new JTextField();
		deleteWatchListTextField.setBounds(155, 19, 86, 20);
		panelDeleteWatchList.add(deleteWatchListTextField);
		deleteWatchListTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				deleteWatchListTextField.setText(deleteWatchListTextField.getText().toUpperCase());
				pagewatchList = 1;
				createWatchListTable();
				
				
				if(e.getKeyCode()  == KeyEvent.VK_ENTER) {
					boolean isDeleted = CompanySys.deleteFromWatchList(deleteWatchListTextField.getText());
					if(!isDeleted) {
						isDeletedLabel.setText(deleteWatchListTextField.getText()+" cannot be found!");
						isDeletedLabel.setVisible(true);
					}
					else {
						isDeletedLabel.setVisible(false);
						deleteWatchListTextField.setText("");
						createWatchListTable();
						
						displayDeleteWatchListPanel();
					}
				}
				setWatchListPageButtons();
			}
		});
		deleteWatchListTextField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Watch List");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel_3.setBounds(1372, 439, 143, 56);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("STOCKS");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel_4.setBounds(428, 16, 155, 58);
		contentPane.add(lblNewLabel_4);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel.setBounds(10, 552, 684, 409);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("Follow Your Stocks Here");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(223, 11, 235, 34);
		panel.add(lblNewLabel_5);
		
		balanceLabel = new JLabel("Balance: ");
		balanceLabel.setBounds(427, 39, 247, 14);
		panel.add(balanceLabel);
		
		JLabel lblNewLabel_6 = new JLabel("Ticker:");
		lblNewLabel_6.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_6.setBounds(10, 70, 55, 14);
		panel.add(lblNewLabel_6);
		
		stockTickerTf = new JTextField();
		stockTickerTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				stockTickerTf.setText(stockTickerTf.getText().toUpperCase());
			}
		});
		stockTickerTf.setBounds(131, 70, 86, 20);
		panel.add(stockTickerTf);
		stockTickerTf.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Cost:");
		lblNewLabel_7.setBounds(10, 111, 55, 14);
		panel.add(lblNewLabel_7);
		
		costTf = new JTextField();
		costTf.setBounds(131, 111, 86, 20);
		panel.add(costTf);
		costTf.setColumns(10);
		
		
		
		myStocksTextArea = new JTextArea();
		myStocksTextArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		myStocksTextArea.setBackground(new Color(240, 240, 240));
		myStocksTextArea.setBounds(239, 91, 291, 220);
		panel.add(myStocksTextArea);
		displayDeleteWatchListPanel();
		myStocksTextArea.setEditable(false);
		
		JLabel lblNewLabel_8 = new JLabel("Amount of Stock:");
		lblNewLabel_8.setBounds(10, 150, 107, 14);
		panel.add(lblNewLabel_8);
		
		amountTf = new JTextField();
		amountTf.setBounds(131, 147, 86, 20);
		panel.add(amountTf);
		amountTf.setColumns(10);
		
		
		buyBtn = new JButton("BUY");
		buyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					MyStocksSys.addToMyStocksTable(stockTickerTf.getText(), Math.abs(Double.parseDouble(costTf.getText())) ,Math.abs(Integer.parseInt(amountTf.getText())) );
					disableMyStocksButtons();
					setBalanceAndMyStocks();
					setMyStocksButton();
				
			}
		});
		buyBtn.setBounds(10, 193, 89, 23);
		panel.add(buyBtn);
		
		
		
		sellBtn = new JButton("SELL");
		sellBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] result = setBalanceAndMyStocks();
				if(Math.abs(Integer.parseInt(amountTf.getText())) > Integer.parseInt(result[2]));
				else {
					
						MyStocksSys.addToMyStocksTable(stockTickerTf.getText(), Math.abs(Double.parseDouble(costTf.getText())) , -Math.abs(Integer.parseInt(amountTf.getText())));
						disableMyStocksButtons();
						setBalanceAndMyStocks();
						setMyStocksButton();
					
				}
				
				
			}
		});
		sellBtn.setBounds(131, 193, 89, 23);
		panel.add(sellBtn);
		
		totalPLLbl = new JLabel("Total Profit/Loss: ");
		totalPLLbl.setBounds(427, 64, 247, 14);
		panel.add(totalPLLbl);
		
		firstButtonMyStocks = new JButton("<<");
		firstButtonMyStocks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pageMyStocks = 1;
				disableMyStocksButtons();
				setBalanceAndMyStocks();
				setMyStocksButton();
			}
		});
		firstButtonMyStocks.setBounds(239, 322, 49, 23);
		panel.add(firstButtonMyStocks);
		
		oneLeftButtonMyStocks = new JButton("<");
		oneLeftButtonMyStocks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pageMyStocks--;
				disableMyStocksButtons();
				setBalanceAndMyStocks();
				setMyStocksButton();
			}
		});
		oneLeftButtonMyStocks.setBounds(298, 322, 49, 23);
		panel.add(oneLeftButtonMyStocks);
		
		pageInfoLblMyStocks = new JLabel("1 / 0");
		pageInfoLblMyStocks.setBounds(369, 326, 65, 14);
		panel.add(pageInfoLblMyStocks);
		
		oneRightButtonMyStocks = new JButton(">");
		oneRightButtonMyStocks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pageMyStocks++;
				disableMyStocksButtons();
				setBalanceAndMyStocks();
				setMyStocksButton();
			}
		});
		oneRightButtonMyStocks.setBounds(422, 322, 49, 23);
		panel.add(oneRightButtonMyStocks);
		
		lastButtonMyStocks = new JButton(">>");
		lastButtonMyStocks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pageMyStocks = resultMyStocks;
				disableMyStocksButtons();
				setBalanceAndMyStocks();
				setMyStocksButton();
			}
		});
		lastButtonMyStocks.setBounds(481, 322, 49, 23);
		panel.add(lastButtonMyStocks);
		disableMyStocksButtons();
		setBalanceAndMyStocks();
		setMyStocksButton();
		
		
		
		oneLeftButton = new JButton("<");
		oneLeftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pageBist100--;
				createTable();
				setBist100PageButtons();
				
			}
		});
		oneLeftButton.setBounds(375, 518, 49, 23);
		contentPane.add(oneLeftButton);
		
		oneRightButton = new JButton(">");
		oneRightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pageBist100++;
				createTable();
				setBist100PageButtons();
				
			}
		});
		oneRightButton.setBounds(499, 518, 49, 23);
		contentPane.add(oneRightButton);
		
		lastButton = new JButton(">>");
		lastButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pageBist100 = result;
				createTable();
				setBist100PageButtons();
				
			}
		});
		lastButton.setBounds(558, 518, 49, 23);
		contentPane.add(lastButton);
		
		firstButton = new JButton("<<");
		firstButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pageBist100 = 1;
				createTable();
				setBist100PageButtons();
			}
		});
		firstButton.setBounds(316, 518, 49, 23);
		contentPane.add(firstButton);
		
		pageInfoLbl = new JLabel("");
		pageInfoLbl.setBounds(446, 522, 65, 14);
		contentPane.add(pageInfoLbl);
		
		firstPageWLButton = new JButton("<<");
		firstPageWLButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pagewatchList = 1;
				createWatchListTable();
				setWatchListPageButtons();
			}
		});
		firstPageWLButton.setBounds(1313, 943, 49, 23);
		contentPane.add(firstPageWLButton);
		
		oneLeftPageWLButton = new JButton("<");
		oneLeftPageWLButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pagewatchList--;
				setWatchListPageButtons();
				createWatchListTable();
			}
		});
		oneLeftPageWLButton.setBounds(1372, 943, 49, 23);
		contentPane.add(oneLeftPageWLButton);
		
		pageInfoLblWL = new JLabel("1 / 0");
		pageInfoLblWL.setBounds(1446, 947, 40, 14);
		contentPane.add(pageInfoLblWL);
		
		
		
		oneRightWLButton = new JButton(">");
		oneRightWLButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pagewatchList++;
				setWatchListPageButtons();
				createWatchListTable();
			}
		});
		oneRightWLButton.setBounds(1496, 943, 49, 23);
		contentPane.add(oneRightWLButton);
		
		lastWLButton = new JButton(">>");
		lastWLButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pagewatchList = resultWatchList;
				setWatchListPageButtons();
				createWatchListTable();
			}
		});
		lastWLButton.setBounds(1555, 943, 49, 23);
		contentPane.add(lastWLButton);
		
		firstButtonIndexes = new JButton("<<");
		firstButtonIndexes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pageBistIndexes = 1;
				createBist100IndexesTable();
				setBistIndexesPageButton();
			}
		});
		firstButtonIndexes.setBounds(1287, 405, 49, 23);
		contentPane.add(firstButtonIndexes);
		
		oneLeftButtonIndexes = new JButton("<");
		oneLeftButtonIndexes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pageBistIndexes--;
				createBist100IndexesTable();
				setBistIndexesPageButton();
			}
		});
		oneLeftButtonIndexes.setBounds(1346, 405, 49, 23);
		contentPane.add(oneLeftButtonIndexes);
		
		pageInfoIndexesLbl = new JLabel("1 / 0");
		pageInfoIndexesLbl.setBounds(1417, 409, 49, 14);
		contentPane.add(pageInfoIndexesLbl);
		
		oneRightButtonIndexes = new JButton(">");
		oneRightButtonIndexes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pageBistIndexes++;
				createBist100IndexesTable();
				setBistIndexesPageButton();
			}
		});
		oneRightButtonIndexes.setBounds(1470, 405, 49, 23);
		contentPane.add(oneRightButtonIndexes);
		
		lastButtonIndexes = new JButton(">>");
		lastButtonIndexes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pageBistIndexes = resultBistIndexes;
				createBist100IndexesTable();
				setBistIndexesPageButton();
			}
		});
		lastButtonIndexes.setBounds(1529, 405, 49, 23);
		contentPane.add(lastButtonIndexes);
		
		JLabel lblNewLabel_9 = new JLabel("BIST INDEXES");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel_9.setBounds(1351, 28, 164, 51);
		contentPane.add(lblNewLabel_9);
		createTable();
		setBist100PageButtons();
		createWatchListTable();
		setWatchListPageButtons();
		
		createBist100IndexesTable();
		setBistIndexesPageButton();
		TimerTask setTime = new setDate();
		Timer timer = new Timer();
		timer.schedule(setTime, 0,1000);
		
		ThreadForStocksAndWatchList th1 = new ThreadForStocksAndWatchList();
		Thread thread1 = new Thread(th1);
		thread1.start();
		
		ThreadForBistIndexes th2 = new ThreadForBistIndexes();
		Thread thread2 = new Thread(th2);
		thread2.start();
		
		ThreadForMyStocks th3 = new ThreadForMyStocks();
		Thread thread3 = new Thread(th3);
		thread3.start();
		
	}
	public void createTable() {
		WebSiteSys.stockDataOutput(companyTickerTextField.getText(),pageBist100);
		companies = CompanySys.getCompanies();
		
		for(int i = 0;i<WebSiteSys.getOnePage();i++) {
			for(int k = 0;k<7;k++) {
				bist100data[i][k].setText("");
				bist100data[i][k].setBackground(Color.white);
			}
		}
		int y = 100;//  companies.size();
		for(int i = 0;i<companies.size();i++) {
			for(int k = 0;k<7;k++) {				
				bist100data[i][k].setBounds(-50 + (k * 140) ,y, 200, 20);
				switch(k) {
				case 0:bist100data[i][k].setText(companies.get(i).getCompanyName()); break;
				case 1:bist100data[i][k].setText(turkishFormat.format(companies.get(i).getDayStartPrice())); break;
				case 2:bist100data[i][k].setText(turkishFormat.format(companies.get(i).getCompanyPrice())); break;
				case 3:bist100data[i][k].setText(String.valueOf(companies.get(i).getCompanyPercentage())); break;
				case 4:bist100data[i][k].setText(companies.get(i).getPriceDate()); break;
				case 5:bist100data[i][k].setText(turkishFormat.format(companies.get(i).getMaxPriceDay())); break;
				case 6:bist100data[i][k].setText(turkishFormat.format(companies.get(i).getMinPriceDay())); break;
				}
				bist100data[i][k].setHorizontalAlignment(SwingConstants.CENTER);
				
				if(companies.get(i).getCompanyPercentage() > 0) {
					
					bist100data[i][k].setBackground(bgColorG);
				}
				else if(companies.get(i).getCompanyPercentage() == 0) {
					bist100data[i][k].setBackground(Color.LIGHT_GRAY);
				}
				else {
					bist100data[i][k].setBackground(bgColorR);
				}
				
		        contentPane.add(bist100data[i][k]);
			}
			y += 20;
		}
		double first, second;
		first = WebSiteSys.getOnePage();
		second = CompanySys.getAllCompanies().size();
		
		
		result = (int) Math.ceil(second/first);		
		//System.out.println(first+"       "+second+"         "+result);
			pageInfoLbl.setText(pageBist100+" / "+(int)(result));
	}
	
	public void createWatchListTable() {
		CompanySys.displayWatchListTable(deleteWatchListTextField.getText(), pagewatchList);
		watchListCompanies= CompanySys.getWatchListCompanies();
		
		for(int i = 0;i<WebSiteSys.getOnePage();i++) {
			for(int k = 0;k<7;k++) {
				watchListData[i][k].setText("");
				watchListData[i][k].setBackground(Color.white);
			}
		}
		
		int y = 530;
		for(int i = 0;i<watchListCompanies.size();i++) {
			for(int k = 0;k<7;k++) {
				
				watchListData[i][k].setBounds(900 + (k * 140) ,y, 200, 20);
				switch(k) {
				case 0:watchListData[i][k].setText(watchListCompanies.get(i).getCompanyName()); break;
				case 1:watchListData[i][k].setText(turkishFormat.format(watchListCompanies.get(i).getDayStartPrice())); break;
				case 2:watchListData[i][k].setText(turkishFormat.format(watchListCompanies.get(i).getCompanyPrice())); break;
				case 3:watchListData[i][k].setText(String.valueOf(watchListCompanies.get(i).getCompanyPercentage())); break;
				case 4:watchListData[i][k].setText(watchListCompanies.get(i).getPriceDate()); break;
				case 5:watchListData[i][k].setText(turkishFormat.format(watchListCompanies.get(i).getMaxPriceDay())); break;
				case 6:watchListData[i][k].setText(turkishFormat.format(watchListCompanies.get(i).getMinPriceDay())); break;
				}
				watchListData[i][k].setHorizontalAlignment(SwingConstants.CENTER);
		        contentPane.add(watchListData[i][k]);
		        if(watchListCompanies.get(i).getCompanyPercentage() > 0) {
					
		        	watchListData[i][k].setBackground(bgColorG);
				}
				else if(watchListCompanies.get(i).getCompanyPercentage() == 0) {
					watchListData[i][k].setBackground(Color.LIGHT_GRAY);
				}
				else {
					watchListData[i][k].setBackground(bgColorR);
				}
		        
			}
			y += 20;
			
			
		}
		double first, second;
		first = WebSiteSys.getOnePage();
		second = CompanySys.getWatchListAllCompanies().size();
		resultWatchList =(int) Math.ceil(second/first);
		pageInfoLblWL.setText(pagewatchList+" / "+resultWatchList);
	}
	
	public void displayDeleteWatchListPanel() {
		if(CompanySys.isWatchListTableEmpty()) {
			panelDeleteWatchList.setVisible(false);
		}
		else {
			panelDeleteWatchList.setVisible(true);
		}
	}
	
	public String[] setBalanceAndMyStocks() {
		String[] result = MyStocksSys.displayStocks(stockTickerTf.getText().toUpperCase(), pageMyStocks);
		myStocksTextArea.setText(result[0]);
		balanceLabel.setText("Balance: "+result[1]);
		totalPLLbl.setText("Total Profit/Loss: "+result[3]);
		resultMyStocks = (int) Math.ceil(Double.valueOf(result[4])/MyStocksSys.getOnePage());
		pageInfoLblMyStocks.setText(pageMyStocks+" / "+resultMyStocks);
		return result;
	}
	
	public void createBist100IndexesTable() {
		WebSiteSys.bistDataOutput(pageBistIndexes);
		bistIndexes = WebSiteSys.getBistIndexes();
		for(int i = 0;i<WebSiteSys.getOnePageForIndexes();i++) {
			for(int k = 0;k<4;k++) {
				bistIndexesData[i][k].setText("");
				bistIndexesData[i][k].setBackground(Color.white);
			}
		}
		
		int y = 100;
		for(int i = 0;i<bistIndexes.size();i++) {
			for(int k = 0;k<4;k++) {
				
				bistIndexesData[i][k].setBounds(1100 + (k * 200) ,y, 220, 20);
				
				bistIndexesData[i][k].setText(bistIndexes.get(i)[k].toString()); 
			
				
				
				bistIndexesData[i][k].setHorizontalAlignment(SwingConstants.CENTER);
		        contentPane.add(bistIndexesData[i][k]);
		        
		        if(Double.parseDouble(bistIndexes.get(i)[2].toString()) > 0) {
					
		        	bistIndexesData[i][k].setBackground(bgColorG);
				}
				else if(Double.parseDouble(bistIndexes.get(i)[2].toString()) == 0) {
					bistIndexesData[i][k].setBackground(Color.LIGHT_GRAY);
				}
				else {
					bistIndexesData[i][k].setBackground(bgColorR);
				}
		        
			}
			y += 20;
			
			
		}
		double first, second;
		first = WebSiteSys.getOnePageForIndexes();
		second = WebSiteSys.getBistAllIndexes().size();
		resultBistIndexes =(int) Math.ceil(second/first);
		pageInfoIndexesLbl.setText(pageBistIndexes+" / "+(int)resultBistIndexes);
	}
	
	public  void setBist100PageButtons() {
		if(pageBist100 == 1) {
			oneLeftButton.setEnabled(false);
			firstButton.setEnabled(false);
		}
		else {
			oneLeftButton.setEnabled(true);	
			firstButton.setEnabled(true);
		}
		
		if(pageBist100 == result) {
			oneRightButton.setEnabled(false);
			lastButton.setEnabled(false);
		}
		else {
			oneRightButton.setEnabled(true);
			lastButton.setEnabled(true);
		}
	}
	
	public void setWatchListPageButtons() {
		if(pagewatchList == 1) {
			oneLeftPageWLButton.setEnabled(false);
			firstPageWLButton.setEnabled(false);
		}
		else {
			oneLeftPageWLButton.setEnabled(true);	
			firstPageWLButton.setEnabled(true);
		}
		
		if(pagewatchList == resultWatchList) {
			oneRightWLButton.setEnabled(false);
			lastWLButton.setEnabled(false);
		}
		else {
			oneRightWLButton.setEnabled(true);
			lastWLButton.setEnabled(true);
		}
	}
	
	public void setBistIndexesPageButton() {
		if(pageBistIndexes == 1) {
			oneLeftButtonIndexes.setEnabled(false);
			firstButtonIndexes.setEnabled(false);
		}
		else {
			oneLeftButtonIndexes.setEnabled(true);	
			firstButtonIndexes.setEnabled(true);
		}
		
		if(pageBistIndexes == resultBistIndexes) {
			oneRightButtonIndexes.setEnabled(false);
			lastButtonIndexes.setEnabled(false);
		}
		else {
			oneRightButtonIndexes.setEnabled(true);
			lastButtonIndexes.setEnabled(true);
		}
	}
	
	public void setMyStocksButton() {
		if( pageMyStocks== 1) {
			oneLeftButtonMyStocks.setEnabled(false);
			firstButtonMyStocks.setEnabled(false);
		}
		else {
			oneLeftButtonMyStocks.setEnabled(true);	
			firstButtonMyStocks.setEnabled(true);
		}
		
		if(pageMyStocks == resultMyStocks) {
			oneRightButtonMyStocks.setEnabled(false);
			lastButtonMyStocks.setEnabled(false);
		}
		else {
			oneRightButtonMyStocks.setEnabled(true);
			lastButtonMyStocks.setEnabled(true);
		}
	}
	
	public void disableBist100AndWatchListButtons() {
		
		
		oneRightButton.setEnabled(false);
		oneLeftButton.setEnabled(false);
		lastButton.setEnabled(false);
		firstButton.setEnabled(false);
		
		oneLeftPageWLButton.setEnabled(false);
		oneRightWLButton.setEnabled(false);
		lastWLButton.setEnabled(false);
		firstPageWLButton.setEnabled(false);
	}
	
	public void disableMyStocksButtons() {
		oneRightButtonMyStocks.setEnabled(false);
		oneLeftButtonMyStocks.setEnabled(false);
		lastButtonMyStocks.setEnabled(false);
		firstButtonMyStocks.setEnabled(false);		
	}
	
	public void disableBistIndexesButtons() {
		oneRightButtonIndexes.setEnabled(false);
		oneLeftButtonIndexes.setEnabled(false);
		lastButtonIndexes.setEnabled(false);
		firstButtonIndexes.setEnabled(false);
		
		
	}
	
	public class setDate extends TimerTask{

		@Override
		public void run() {
			
			date = new Date();  
			hourLbl.setText(formatter.format(date));
			
		}
		
	}
	
	public class ThreadForStocksAndWatchList implements Runnable{
		@Override
		public void run() {
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(true) {
				disableBist100AndWatchListButtons();
				buyBtn.setEnabled(false);
				sellBtn.setEnabled(false);
				createTable();
				buyBtn.setEnabled(true);
				sellBtn.setEnabled(true);
				createWatchListTable();
				setBist100PageButtons();
				setWatchListPageButtons();
				setMyStocksButton();
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public class ThreadForBistIndexes implements Runnable{

		@Override
		public void run() {
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(true) {
				disableBistIndexesButtons();
				createBist100IndexesTable();
				setBistIndexesPageButton();
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	public class ThreadForMyStocks implements Runnable{

		@Override
		public void run() {
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(true) {
				disableMyStocksButtons();
				setBalanceAndMyStocks();
				setMyStocksButton();
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
		}
		
	}
}
