package com.test.institute.dao.api;

import org.springframework.data.jpa.repository.JpaRepository;
import com.test.institute.model.Student;

public interface StudentDaoAPI extends JpaRepository<Student, String>{

}
