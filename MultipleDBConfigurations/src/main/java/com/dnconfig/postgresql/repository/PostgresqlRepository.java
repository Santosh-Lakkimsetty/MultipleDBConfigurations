package com.dnconfig.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnconfig.postgresql.model.Employee;

@Repository
public interface PostgresqlRepository extends JpaRepository<Employee, Integer>{

}
