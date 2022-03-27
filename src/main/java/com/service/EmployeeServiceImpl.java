package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.EmployeeDAO;
import com.model.Employee;

@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDAO employeeDao;
	
	@Override
	public Employee findById(int id) {
		return employeeDao.findById(id);
	}
	
	@Override
	public List<Employee> findAllEmployees() {
		return employeeDao.findAllEmployees();
	}
	
	@Override
	public void deleteEmployeeBySsn(String ssn) {
		employeeDao.deleteEmployeeBySsn(ssn);
	}
	
	@Override
	public Employee findEmployeeBySsn(String ssn) {
		return employeeDao.findBySsn(ssn);
	}
	
	@Override
	public boolean isEmployeeSsnUnique(Integer id, String ssn) {
		Employee employee = findEmployeeBySsn(ssn);
        return ( employee == null || ((id != null) && (employee.getId() == id)));
	}
	
	@Override
	public void saveEmployee(Employee employee) {
		employeeDao.saveEmployee(employee);
	}
	
	@Override
	public void updateEmployee(Employee employee) {
		System.out.println(".........................."+employee.getId());
		Employee entity=employeeDao.findById(employee.getId());
		System.out.println(".........................."+entity);
		if(entity!=null) {
			entity.setName(employee.getName());
            entity.setJoiningDate(employee.getJoiningDate());
            entity.setSalary(employee.getSalary());
            entity.setSsn(employee.getSsn());
		}
		
	}
}
