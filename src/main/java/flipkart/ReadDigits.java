package flipkart;

import java.io.IOException;

public class ReadDigits {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DetectText dt = new DetectText();
		String filePath = System.getProperty("user.dir")+"/screenshots/captcha.png";
		dt.detectText(filePath);
		String s = dt.myDigits;
	}

}
