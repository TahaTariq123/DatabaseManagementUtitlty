package com.dao;

import java.util.List;

import com.model.Employee;

public interface EmployeeDAO {
	
	Employee findById(int id); 
	
	void deleteEmployeeBySsn(String ssn);
	
	void saveEmployee(Employee employee);
	
	List<Employee> findAllEmployees();
	
	Employee findBySsn(String ssn);
}
