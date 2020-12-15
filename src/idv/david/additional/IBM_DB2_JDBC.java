package idv.david.additional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IBM_DB2_JDBC {
	
	public static void main(String[] args) {
		try {
            Class.forName("COM.ibm.db2.jdbc.app.DB2Driver");    //驅動程式-第二類
            Class.forName("COM.ibm.db2.jdbc.net.DB2Driver");    //驅動程式-第三類 -- 放資料庫的電腦上需執行 [db2jstrt.exe portNumber] , 如 db2jstrt.exe 8888
            Class.forName("com.ibm.db2.jcc.DB2Driver");         //驅動程式-第四類
        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }

        try {
            Connection con =  DriverManager.getConnection("jdbc:db2:sample", "administrator", "678");  // Type 2
            //Connection con =  DriverManager.getConnection("jdbc:db2://localhost:8888/sample", "administrator", "678");  // Type 3, 4  	

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from EMPLOYEE");

            while (rs.next()) {
                String str1 = rs.getString(1);
                String str2 = rs.getString(2);

                System.out.print(" empno = " + str1);
                System.out.print(" ename = " + str2);
                System.out.print("\n");
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
	}
}
