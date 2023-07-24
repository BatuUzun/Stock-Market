package Companies;



public class Company {
	private String 		companyName;
	private double		companyPrice;
	private double 		companyPercentage;
	private String		priceDate;
	private double		minPriceDay;
	private double		maxPriceDay;
	private double 		dayStartPrice;
	
	public String getCompanyName() {
		return companyName;
	}
	
	public double getCompanyPrice() {
		return companyPrice;
	}
	
	public double getCompanyPercentage() {
		return companyPercentage;
	}
	
	public String getPriceDate() {
		return priceDate;
	}
	
	public double getMinPriceDay() {
		return minPriceDay;
	}
	
	public double getMaxPriceDay() {
		return maxPriceDay;
	}
	
	public double getDayStartPrice() {
		return dayStartPrice;
	}
	
	public void setDayStartPrice() {
		dayStartPrice = (companyPrice) / (1+companyPercentage/100);		
	}	
		
	public void setCompanyPrice(double companyPrice) {
		this.companyPrice = companyPrice;
	}
	
	public void setCompanyPercentage(double companyPercentage) {
		this.companyPercentage = companyPercentage;
	}
	
	public void setPriceDate(String priceDate) {
		this.priceDate = priceDate;
	}
	
	public void setMinPriceDay() {
		minPriceDay = dayStartPrice * 90/100;
	}
	
	public void setMaxPriceDay() {
		maxPriceDay = dayStartPrice * 110/100;
	}
	
	public Company(String companyName, double companyPrice, double companyPercentage, String priceDate) {
		this.companyName = companyName;
		this.companyPrice = companyPrice;
		this.companyPercentage = companyPercentage;
		this.priceDate = priceDate;
		setDayStartPrice();
		setMaxPriceDay();
		setMinPriceDay();
	}
	
	public boolean isThisCompany(String companyName) {
		if(this.companyName.equalsIgnoreCase(companyName))
			return true;
		return false;
	}
	
	
	
	
}
