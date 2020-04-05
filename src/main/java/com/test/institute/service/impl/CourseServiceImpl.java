package com.test.institute.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.test.institute.common.CommonServiceImpl;
import com.test.institute.dao.api.CourseDaoAPI;
import com.test.institute.model.Course;
import com.test.institute.service.api.CourseServiceAPI;

@Service
public class CourseServiceImpl extends CommonServiceImpl<Course, String> implements CourseServiceAPI {

	@Autowired
	private CourseDaoAPI courseDaoApi;

	@Override
	public JpaRepository<Course, String> getDao() {
		return courseDaoApi;
	}

	
}
