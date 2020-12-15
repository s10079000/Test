package idv.david.additional;

import java.sql.SQLException;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class TestJdbcRowSet {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "david";
	private static final String PASSWORD = "123456";
	private static final String SQL = "SELECT * FROM EMPLOYEE";

	public static void main(String[] args) {
		JdbcRowSet rowset = null;
		try {
			Class.forName(DRIVER);
			rowset = RowSetProvider.newFactory().createJdbcRowSet();
			rowset.setUrl(URL);
			rowset.setUsername(USER);
			rowset.setPassword(PASSWORD);
			rowset.setCommand(SQL);
			// 執行Query，會自動連線，不會斷線
			rowset.execute();

			rowset.beforeFirst();  // 預設ResultSet游標列在Oracle不支援beforeFirst, Rowset已實作了！
			while (rowset.next()) {
				System.out.println("EMPNO = " + rowset.getInt(1));
				System.out.println("ENAME = " + rowset.getString(2));
				System.out.println("==============");
			}

			System.out.println("================Query again!!!==================");

			while (rowset.previous()) {
				System.out.println("EMPNO = " + rowset.getInt(1));
				System.out.println("ENAME = " + rowset.getString(2));
				System.out.println("==============");
			}

		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rowset != null) {
				try {
					rowset.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
	}

}
