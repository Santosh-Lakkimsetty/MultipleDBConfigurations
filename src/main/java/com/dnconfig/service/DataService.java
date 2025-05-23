package com.dnconfig.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dnconfig.mysql.model.Employee;
import com.dnconfig.mysql.repository.MysqlRepository;
import com.dnconfig.postgresql.repository.PostgresqlRepository;

@Service
public class DataService {
	
	@Autowired
	private MysqlRepository mysqlRepo;
	
	@Autowired
	private PostgresqlRepository postgresqlRepo;

	public List<Employee> getAllEmployee() {
		List<Employee> list = mysqlRepo.findAll();
		return list;
	}
	
	
	public List<com.dnconfig.postgresql.model.Employee> saveAllEmployees(){
		List<Employee> list = mysqlRepo.findAll();
		
		List<com.dnconfig.postgresql.model.Employee> postgresqlEmpList = list.stream().map(mysqlEmployee -> {
			com.dnconfig.postgresql.model.Employee employee = new com.dnconfig.postgresql.model.Employee();
			employee.setId(mysqlEmployee.getId());
			employee.setFirstname(mysqlEmployee.getFirstname());
			employee.setLastname(mysqlEmployee.getLastname());
			employee.setSalary(mysqlEmployee.getSalary());
			employee.setDomain(mysqlEmployee.getDomain());
			
			return employee;
		}).collect(Collectors.toList());
		
		return postgresqlRepo.saveAll(postgresqlEmpList);
		
	}
	
	

}
