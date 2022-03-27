package com.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.exporter.EmployeePdfExporter;
import com.lowagie.text.DocumentException;
import com.model.Employee;
import com.service.EmployeeService;

@Controller
@RequestMapping("/")
@ComponentScan("com")
public class AppController {
	@Autowired
	EmployeeService service;
	
	@Autowired
	MessageSource messageSource;
	
	@RequestMapping(value= {"/","/list"},method = RequestMethod.GET)
	public String ListEmployees(ModelMap model) {
		List<Employee> employees=service.findAllEmployees();
		System.out.println(employees);
		model.addAttribute("employees",employees);
		return "AllEmployees";
	}
	
	@RequestMapping(value="/new",method=RequestMethod.GET)
	public String newEmployee(ModelMap model) {
		Employee employee = new Employee();
        model.addAttribute("employee", employee);
        model.addAttribute("edit", false);
        
		return "registration";
	}
	@RequestMapping(value="/new",method=RequestMethod.POST)
	public String saveEmployee(@Valid Employee employee,BindingResult result,ModelMap model) {
		if(result.hasErrors())
		{
			return "registration";
		}
		
		if(!service.isEmployeeSsnUnique(employee.getId(),employee.getSsn()))
		{
			FieldError ssnError=new FieldError("employee","ssn",messageSource.getMessage("non.unique.ssn", new String[]{employee.getSsn()}, Locale.getDefault()));
			result.addError(ssnError);
			return "registration";
		}
		
		service.saveEmployee(employee);
		
		model.addAttribute("success","Employee " + employee.getName() + " registered successfully");
		return "success";
	}
	
	@RequestMapping(value = { "/edit-{ssn}-employee" },method=RequestMethod.GET )
	public String updateEmployee1(ModelMap model,@PathVariable String ssn) {
		Employee employee =service.findEmployeeBySsn(ssn);
        model.addAttribute("employee", employee);
        model.addAttribute("edit", true);
        
		return "registration";
	}
	
	
	@RequestMapping(value = { "/edit-{ssn}-employee" } , method=RequestMethod.POST)
	public String updateEmployee(@Valid Employee employee, BindingResult result,
            ModelMap model, @PathVariable String ssn) {
		 
		if(result.hasErrors())
		{
			return "registration";
		}
		
		if(!service.isEmployeeSsnUnique(employee.getId(),employee.getSsn())) {
			FieldError ssnError=new FieldError("employee","ssn",messageSource.getMessage("non.unique.ssn",new String[]{employee.getSsn()},Locale.getDefault()));
			result.addError(ssnError);
			return "registration";
		}
		
		service.updateEmployee(employee);
		
		model.addAttribute("success","Employee " + employee.getName() + " registered successfully");
		return "success";
	}
	@RequestMapping(value = { "/delete-{ssn}-employee" },method=RequestMethod.GET)
	public String deleteEmployee(@PathVariable String ssn) {
        service.deleteEmployeeBySsn(ssn);
        return "redirect:/list";
    }
	
	@RequestMapping(value="/employee/export/pdf",method=RequestMethod.GET)
	public void exportToPdf(HttpServletResponse response)throws IOException,DocumentException{
		response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=employees_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        
        List<Employee> listEmp=service.findAllEmployees();
        EmployeePdfExporter exporter=new EmployeePdfExporter(listEmp);
        exporter.export(response);
	}
}
