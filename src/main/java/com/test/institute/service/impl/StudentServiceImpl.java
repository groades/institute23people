package com.test.institute.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.test.institute.common.CommonServiceImpl;
import com.test.institute.dao.api.StudentDaoAPI;
import com.test.institute.model.Student;
import com.test.institute.service.api.StudentServiceAPI;

@Service
public class StudentServiceImpl extends CommonServiceImpl<Student, String> implements StudentServiceAPI{

	@Autowired
	StudentDaoAPI studentDaoApi;
	
	@Override
	public JpaRepository<Student, String> getDao() {
		return studentDaoApi;
	}

}
