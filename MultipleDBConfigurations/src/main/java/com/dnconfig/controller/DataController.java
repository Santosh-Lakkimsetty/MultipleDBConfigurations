package com.dnconfig.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnconfig.mysql.model.Employee;
import com.dnconfig.service.DataService;

@RestController
public class DataController {

	@Autowired
	private DataService dataService;
	
	@GetMapping("getAll")
	public List<Employee> getAll(){
		return dataService.getAllEmployee();
	}
	
	@PostMapping("saveAll")
	public List<com.dnconfig.postgresql.model.Employee> saveAll(){
		return dataService.saveAllEmployees();
	}
}
