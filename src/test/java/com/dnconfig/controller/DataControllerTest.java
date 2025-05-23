package com.dnconfig.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dnconfig.mysql.model.Employee;
import com.dnconfig.service.DataService;

class DataControllerTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private DataController dataController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Employee emp1 = new Employee(1, "John", "Dev");
        Employee emp2 = new Employee(2, "Jane", "QA");

        List<Employee> mockEmployees = Arrays.asList(emp1, emp2);
        when(dataService.getAllEmployee()).thenReturn(mockEmployees);

        List<Employee> result = dataController.getAll();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstname());
        verify(dataService, times(1)).getAllEmployee();
    }

    @Test
    void testSaveAll() {
        com.dnconfig.postgresql.model.Employee pgEmp1 = new com.dnconfig.postgresql.model.Employee(1, "Alice", "Manager");
        com.dnconfig.postgresql.model.Employee pgEmp2 = new com.dnconfig.postgresql.model.Employee(2, "Bob", "HR");

        List<com.dnconfig.postgresql.model.Employee> mockSaved = Arrays.asList(pgEmp1, pgEmp2);
        when(dataService.saveAllEmployees()).thenReturn(mockSaved);

        List<com.dnconfig.postgresql.model.Employee> result = dataController.saveAll();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getFirstname());
        verify(dataService, times(1)).saveAllEmployees();
    }
    
//    @Test
//    void testGetAll_throwsException() {
//        Exception exception = assertThrows(NullPointerException.class, () -> {
//            dataController.getAll();
//        });
//
//        System.out.println("Caught Exception: " + exception);
//    }
}
