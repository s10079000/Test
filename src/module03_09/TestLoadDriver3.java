package module03_09;

/*
java -Djdbc.drivers=oracle.jdbc.driver.OracleDriver TestLoadDriver3
*/

public class TestLoadDriver3 {

	public static void main(String[] args) {
		System.setProperty("jdbc.drivers", "oracle.jdbc.driver.OracleDriver");
		System.out.println("Loading driver successfully! (載入成功！)");
		
	}

}
