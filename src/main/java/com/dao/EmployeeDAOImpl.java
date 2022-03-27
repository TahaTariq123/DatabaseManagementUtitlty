package com.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.model.Employee;

@Repository("employeeDao")
public class EmployeeDAOImpl extends AbstractDAO<Integer, Employee> implements EmployeeDAO {

	@Override
	public void deleteEmployeeBySsn(String ssn) {
//		Query query = getSession().createSQLQuery("delete from Employee e where e.ssn = :ssn");
//        query.setString("ssn", ssn);
//        query.executeUpdate();
				Criteria criteria =  createEntityCriteria();
                Employee emp=(Employee)criteria.add(Restrictions.eq("ssn", ssn)).uniqueResult();
                delete(emp);
	}

	@Override
	public Employee findById(int id) {
		return getByKey(id);
	}

	@Override
	public void saveEmployee(Employee employee) {
		
		persist(employee);
	}

	@Override
	public List<Employee> findAllEmployees() {
		Criteria criteria = createEntityCriteria();
        return (List<Employee>) criteria.list();
	}

	@Override
	public Employee findBySsn(String ssn) {
		Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("ssn", ssn));
        
        return (Employee) criteria.uniqueResult();
	}


}
