package idv.david.additional;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class Test_SELECT_CachedRowSet {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "david";
	private static final String PASSWORD = "123456";
	private static final String SQL = "SELECT * FROM MEMBER";

	public static void main(String[] args) {
		try {
			Class.forName(DRIVER);
			CachedRowSet rowset = RowSetProvider.newFactory().createCachedRowSet();
			rowset.setUrl(URL);
			rowset.setUsername(USER);
			rowset.setPassword(PASSWORD);
			rowset.setCommand(SQL);
			// 執行Query，會自動連線，之後再斷線
			rowset.execute();

			ResultSetMetaData rsmd = rowset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			for (int i = 1; i <= numberOfColumns; i++)
				System.out.print(rsmd.getColumnName(i) + " ");

			System.out.println();
			while (rowset.next()) {
				for (int i = 1; i <= numberOfColumns; i++)
					System.out.print(rowset.getString(i) + " ");
				System.out.println();
			}

			System.out.println("========================");

			while (rowset.previous()) {
				for (int i = 1; i <= numberOfColumns; i++)
					System.out.print(rowset.getString(i) + " ");
				System.out.println();
			}
			
			// 額外測試 Serializable
			ObjectOutputStream out = 
					new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Output/USER.ser")));
			out.writeObject(rowset);
			out.flush();
			out.close();

		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

}
