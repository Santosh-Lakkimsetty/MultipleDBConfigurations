package com.dnconfig.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnconfig.mysql.model.Employee;

@Repository
public interface MysqlRepository extends JpaRepository<Employee, Integer>{

}
