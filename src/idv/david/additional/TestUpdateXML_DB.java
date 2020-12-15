package idv.david.additional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.WebRowSet;

public class TestUpdateXML_DB {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "david";
	private static final String PASSWORD = "123456";

	public static void main(String[] args) {
		Connection con = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader("Output/Emp_Updated.xml"));
			WebRowSet rowset = RowSetProvider.newFactory().createWebRowSet();
			rowset.readXml(br);
			br.close();

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			// 需先將Auto Commit關閉
			con.setAutoCommit(false);
			// 將rowset物件送進資料庫
			rowset.acceptChanges(con);
			// 設定成功 commit
			con.commit();

			ResultSetMetaData rsmd = rowset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			for (int i = 1; i <= numberOfColumns; i++)
				System.out.print(rsmd.getColumnName(i) + " ");

			System.out.println();
			// 順便查看rowset
			while (rowset.next()) {
				for (int i = 1; i <= numberOfColumns; i++)
					System.out.print(rowset.getString(i) + " ");
				System.out.println();
			}

		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (SQLException se) {
			try {
				// 發生例外即進行rollback動作
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			se.printStackTrace();
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
	}

}
