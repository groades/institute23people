package com.test.institute.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.institute.common.Constant;
import com.test.institute.model.Course;
import com.test.institute.service.api.CourseServiceAPI;


@RestController
@RequestMapping(value = "api/v1", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin("*")
@Validated
public class CourseController {

	@Autowired
	private CourseServiceAPI courseServiceApi;
	
	@GetMapping(value = "courses")
	public List<Object> getAllPaged(@RequestParam Map<String, Object> params) {
		int page  = params.get(Constant.PAGE) != null ? (Integer.valueOf(params.get(Constant.PAGE).toString())-1):0;
		int size = params.get(Constant.SIZE) !=null ? (Integer.valueOf(params.get(Constant.SIZE).toString())):Integer.valueOf(Constant.STANDAR_SIZE);
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Course> pageObject = courseServiceApi.getAllPaged(pageRequest);
		int totalPage = pageObject.getTotalPages();
		List<Object> list = new ArrayList<Object>();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			list.add(pages);
		}
		list.add(pageObject.getContent());
		return list;
	}	
	
	@GetMapping(value = "courses/all")
	public List<Course> getAll() {
		return courseServiceApi.getAll();
	}
	
	@GetMapping(value = "courses/{id}")
	public ResponseEntity<Course> get(@PathVariable String id) {
		Course course = courseServiceApi.get(id);
		return course != null ? 	
				new ResponseEntity<Course>(course, HttpStatus.OK): 
				new ResponseEntity<Course>(course, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value = "courses")
	public ResponseEntity<Course> save(@Valid @RequestBody Course course){
		Course result = courseServiceApi.save(course);
		return new ResponseEntity<Course>(result, HttpStatus.CREATED);
	}
	
	@PutMapping("/courses/{id}")
	public Course update(@Valid @RequestBody Course newCourse, @PathVariable String id) {
		
		Course course = courseServiceApi.get(id);
		if(course!=null) {
			course.setCode(newCourse.getCode());
			course.setName(newCourse.getName());
			return courseServiceApi.save(course);
		}else {
			newCourse.setId(id);
			return courseServiceApi.save(newCourse);
		}
	}
	
	@DeleteMapping("/courses/{id}")
	public ResponseEntity<Course> delete(@PathVariable String id){
		Course course = courseServiceApi.get(id);
		if(course != null) {
			courseServiceApi.delete(id); 
		}else {
			return new ResponseEntity<Course>(course, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Course>(course, HttpStatus.OK); 
	}
	
}
