package idv.david.additional;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.WebRowSet;

public class TestRead_UpdateXML {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("Output/Emp.xml"));
			WebRowSet rowset = RowSetProvider.newFactory().createWebRowSet();
			rowset.readXml(br);
			br.close();

			ResultSetMetaData rsmd = rowset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			for (int i = 1; i <= numberOfColumns; i++)
				System.out.print(rsmd.getColumnName(i) + " ");

			System.out.println();

			// ↓ update 之前
			while (rowset.next()) {
				for (int i = 1; i <= numberOfColumns; i++)
					System.out.print(rowset.getString(i) + " ");
				System.out.println();
			}

			// ↓ update rowset物件最後一筆資料的"SAL"欄位的值
			if (rowset.last()) {
				rowset.updateDouble("SAL", rowset.getDouble("SAL") * 2);
				rowset.updateRow();
			}

			System.out.println("===============================");
			// ↓ update 之後
			rowset.beforeFirst();
			while (rowset.next()) {
				for (int i = 1; i <= numberOfColumns; i++)
					System.out.print(rowset.getString(i) + " ");
				System.out.println();
			}
			
			// 同樣可以倒退
			/*
			System.out.println("===============================");
			while (rowset.previous()) {
				for (int i = 1; i <= numberOfColumns; i++)
					System.out.print(rowset.getString(i) + " ");
				System.out.println();
			}
			*/
			
			BufferedWriter bw = new BufferedWriter(new FileWriter("Output/Emp_Updated.xml"));
			rowset.writeXml(bw); // 或是System.out
			bw.flush();
			bw.close();

			System.out.println("寫出成功！");

		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} 
	}

}
