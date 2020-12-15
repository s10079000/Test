package module10_15;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Util;

public class Test_SELECT_DBMetaData {

	public static void main(String[] args) {
		Connection con = null;
		ResultSet rs = null, rs2 = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			DatabaseMetaData dbmd = con.getMetaData();
			String dbmsName = dbmd.getDatabaseProductName();
			rs = dbmd.getTableTypes();
			System.out.print("The following types of tables are ");
			System.out.println("available in " + dbmsName + ":  ");

			while (rs.next()) {
				String tableType = rs.getString("TABLE_TYPE");
				System.out.println("    " + tableType);
			}
			
			System.out.println("=======================");
			
			rs2 = dbmd.getTypeInfo();
			while (rs2.next()) {
				int codeNumber = rs2.getInt("DATA_TYPE");
				String typeName = rs2.getString("TYPE_NAME");
				String createParams = rs2.getString("CREATE_PARAMS");

				System.out.println("DATA_TYPE = " + codeNumber);
				System.out.println("TYPE_NAME = " + typeName);
				System.out.println("CREATE_PARAMS = " + createParams);
				System.out.println("==================");
			}

		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs2 != null) {
				try {
					rs2.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
	}

}
