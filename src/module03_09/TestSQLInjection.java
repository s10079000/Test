package module03_09;

/*
 *  userName = 1' OR '1'='1
 * 	userPassword = 1' OR '1'='1;
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import util.Util;

//ingection SQL注入攻擊
public class TestSQLInjection {

	public static void main(String[] args) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;

		String userName = "", userPassword = "";

		Scanner sc = new Scanner(System.in);
		System.out.println("請輸入名稱: ");
		userName = sc.nextLine();
		System.out.println("請輸入密碼: ");
		userPassword = sc.nextLine();

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			System.out.println("Connecting to database successfully! (連線成功！)");

			stat = con.createStatement();
			rs = stat.executeQuery(
					"SELECT * FROM MEMBER WHERE (NAME = '" + userName + "') AND (PASSWORD = '" + userPassword + "')");
			/*
			 * 駭客們所稱呼的"填空遊戲"，將鍵盤輸入資料當做變數所組合成的SQL句子： SELECT * FROM USER WHERE
			 * (NAME = '1' OR '1'='1') and (PASSWORD = '1' OR '1'='1');
			 */

			while (rs.next()) {
				System.out.println("User ID: " + rs.getInt("ID"));
				System.out.println("User name: " + rs.getString("NAME"));
				System.out.println("User password: " + rs.getString("PASSWORD"));
				System.out.println();
			}

		} catch (ClassNotFoundException ce) {
			System.out.println(ce);
		} catch (SQLException se) {
			System.out.println(se);
		} finally {
			// 依建立順序關閉資源 (越晚建立越早關閉)
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}
			sc.close();
		}
	}
}
