package flipkart.utility;

import java.util.ArrayList;

public class TestUtility {
	
	static Xls_Reader reader;
	
	public static ArrayList<Object[]> getDataFromExcel() {
		
		ArrayList<Object[]> myData = new ArrayList<Object[]>();
		
		try {
			String filepath = System.getProperty("user.dir")+"/src/main/java/flipkart/logindata/flipkartLoginData.xlsx";
			reader = new Xls_Reader(filepath);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		for(int rowNum = 2; rowNum <= reader.getRowCount("Data"); rowNum++) {	
			String emailOrMobile = reader.getCellData("Data", "emailOrMobile", rowNum);
			emailOrMobile = emailOrMobile.replace(".", "").split("E")[0];
			
			String password = reader.getCellData("Data", "password", rowNum);
			password = password.replace(".", "").split("E")[0];
			
			Object ob[] = {emailOrMobile, password};
			myData.add(ob);
		}
		return myData;
		
	}
	

}
