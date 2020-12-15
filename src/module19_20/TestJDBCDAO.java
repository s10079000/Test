package module19_20;

import java.util.List;

public class TestJDBCDAO {

	public static void main(String[] args) {
		EmployeeDAO dao = new EmployeeDAOImpl();

		// 新增
		Employee emp1 = new Employee();
		emp1.setEmpno(7016);
		emp1.setEname("David");
		emp1.setJob("MANAGER");
		emp1.setHiredate(java.sql.Date.valueOf("2016-01-01"));
		emp1.setSal(new Double(50000));
		emp1.setComm(new Double(500));
		emp1.setDeptno(10);
		dao.add(emp1);

		// 修改
		Employee emp2 = new Employee();
		emp2.setEmpno(7087);
		emp2.setEname("David Jr.");
		emp2.setJob("MANAGER2");
		emp2.setHiredate(java.sql.Date.valueOf("2016-08-07"));
		emp2.setSal(new Double(20000));
		emp2.setComm(new Double(200));
		emp2.setDeptno(20);
		dao.update(emp2);

		// 刪除
		dao.delete(7087);

		// 查詢
		Employee emp3 = dao.findByPK(7001);
		System.out.print(emp3.getEmpno() + ",");
		System.out.print(emp3.getEname() + ",");
		System.out.print(emp3.getJob() + ",");
		System.out.print(emp3.getHiredate() + ",");
		System.out.print(emp3.getSal() + ",");
		System.out.print(emp3.getComm() + ",");
		System.out.println(emp3.getDeptno());
		System.out.println("---------------------");

		// 查詢
		List<Employee> list = dao.getAll();
		for (Employee emp : list) {
			System.out.print(emp.getEmpno() + ",");
			System.out.print(emp.getEname() + ",");
			System.out.print(emp.getJob() + ",");
			System.out.print(emp.getHiredate() + ",");
			System.out.print(emp.getSal() + ",");
			System.out.print(emp.getComm() + ",");
			System.out.print(emp.getDeptno());
			System.out.println();
		}
	}

}
