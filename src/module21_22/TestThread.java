package module21_22;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import util.Util;

public class TestThread extends SQLAgent implements Runnable {
	private String sqlStatements;
	private ConnPool connPool;

	public TestThread(String sqlStatements, ConnPool connPool) {
		this.sqlStatements = sqlStatements;
		this.connPool = connPool;
	}

	public static void main(String argv[]) {
		ConnPool connPool = new ConnPool();
		connPool.setDriverName(Util.DRIVER);
		connPool.setJdbcURL(Util.URL);
		connPool.setUserName(Util.USER);
		connPool.setPassword(Util.PASSWORD);
		try {
			connPool.setConnectionSwitch("ON"); // 主執行緒: 從資料庫取出連線,建立連線池
		} catch (SQLException ex) {
			System.out.println(" ConnPool 連結失敗:" + ex.getMessage());
		}

		TestThread sr1 = new TestThread("SELECT * FROM EMPLOYEE", connPool);
		TestThread sr2 = new TestThread("SELECT * FROM DEPARTMENT", connPool);
		TestThread sr3 = new TestThread("SELECT * FROM EMPLOYEE", connPool);
		TestThread sr4 = new TestThread("SELECT * FROM DEPARTMENT", connPool);

		Thread t1 = new Thread(sr1, "執行緒1");
		Thread t2 = new Thread(sr2, "執行緒2");
		Thread t3 = new Thread(sr3, "執行緒3");
		Thread t4 = new Thread(sr4, "執行緒4");
		t1.start();
		t2.start();
		t3.start();
		t4.start();

		// 主執行緒: 等候所有執行緒結束執行
		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
		} catch (InterruptedException e) {
			System.out.println("Main thread Interrupted");
		}
		// 主執行緒: 關閉連線池, 並將連線歸還資料庫
		try {
			connPool.setConnectionSwitch("OFF");
			System.out.println(" [主執行緒: " + Thread.currentThread().getName() + "] 等候所有執行緒結束執行之後, 才關閉連線池");
		} catch (SQLException ex) {
			System.out.println(" ConnPool 連結失敗:" + ex.getMessage());
		}
	}

	public void run() {
		setConnPool(connPool); // 通知父類別,將要使用連線池

		System.out.println("執行SQL指令: " + sqlStatements + "......");

		try {
			// 執行緒:從連線池中取出連線
			setConnectionSwitch("on");
			Connection con = getConnection();

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlStatements);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			for (int i = 1; i <= numberOfColumns; i++)
				System.out.print(rsmd.getColumnName(i) + " ");

			System.out.println();
			while (rs.next()) {
				for (int i = 1; i <= numberOfColumns; i++)
					System.out.print(rs.getString(i) + " ");
				System.out.println();
			}
			System.out.println(Thread.currentThread().getName() + ".....執行成功!\n");

			rs.close();
			stmt.close();
			// 執行緒:將連線歸還連線池
			setConnectionSwitch("off");

		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	}

}
