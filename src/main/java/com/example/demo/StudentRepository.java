package com.example.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {
	public List<Student> findByEmail(String email);
	public List<Student> findAll();

}