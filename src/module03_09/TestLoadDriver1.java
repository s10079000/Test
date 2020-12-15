package module03_09;

public class TestLoadDriver1 {

	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Loading driver successfully! (載入成功！)");
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}

			
	}

}
