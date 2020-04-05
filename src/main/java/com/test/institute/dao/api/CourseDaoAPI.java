package com.test.institute.dao.api;

import org.springframework.data.jpa.repository.JpaRepository;
import com.test.institute.model.Course;

public interface CourseDaoAPI extends JpaRepository<Course, String> {

}
