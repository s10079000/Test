package module19_20;

import java.util.List;

public interface EmployeeDAO {
	// 此介面定義對資料庫的相關存取抽象方法
	void add(Employee employee);
	void update(Employee employee);
	void delete(int empno);
	Employee findByPK(int empno);
	List<Employee> getAll();
}