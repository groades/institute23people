package com.test.institute.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.institute.model.Course;

public interface CommonServiceAPI<T, ID extends Serializable> {
	
	T save(T entity);
	
	T get(ID id);
	
	List<T> getAll();
	
	void delete(ID id);
	
	Page<T> getAllPaged(Pageable pageable);
	
}
