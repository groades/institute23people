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
import com.test.institute.model.Student;
import com.test.institute.service.api.StudentServiceAPI;

@RestController
@RequestMapping(value = "api/v1", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin("*")
@Validated
public class StudentController {

	@Autowired
	private StudentServiceAPI studentServiceAPI;
	
	@GetMapping(value = "students")
	public List<Object> getAllPaged(@RequestParam Map<String, Object> params) {
		int page  = params.get(Constant.PAGE) != null ? (Integer.valueOf(params.get(Constant.PAGE).toString())-1):0;
		int size = params.get(Constant.SIZE) !=null ? (Integer.valueOf(params.get(Constant.SIZE).toString())):Integer.valueOf(Constant.STANDAR_SIZE);
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Student> pageObject = studentServiceAPI.getAllPaged(pageRequest);
		int totalPage = pageObject.getTotalPages();
		List<Object> list = new ArrayList<Object>();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			list.add(pages);
		}
		list.add(pageObject.getContent());
		return list;
	}	
	
	@GetMapping(value = "students/all")
	public List<Student> getAll() {
		return studentServiceAPI.getAll();
	}
	
	@GetMapping(value = "students/{id}")
	public ResponseEntity<Student> get(@PathVariable String id) {
		Student student = studentServiceAPI.get(id);
		return student != null ? 	
				new ResponseEntity<Student>(student, HttpStatus.OK): 
				new ResponseEntity<Student>(student, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value = "students")
	public ResponseEntity<Student> save(@Valid @RequestBody Student student){
		Student result = studentServiceAPI.save(student);
		return new ResponseEntity<Student>(result, HttpStatus.CREATED);
	}
	
	@PutMapping("/students/{id}")
	public Student update(@Valid @RequestBody Student newStudent, @PathVariable String id) {
		
		Student student = studentServiceAPI.get(id);
		if(student!=null) {
			student.setRut(newStudent.getRut());
			student.setName(newStudent.getName());
			student.setLastName(newStudent.getLastName());
			student.setCourse(newStudent.getCourse());
			student.setAge(newStudent.getAge());
			return studentServiceAPI.save(student);
		}else {
			newStudent.setId(id);
			return studentServiceAPI.save(newStudent);
		}
	}
	
	@DeleteMapping("/students/{id}")
	public ResponseEntity<Student> delete(@PathVariable String id){
		Student student = studentServiceAPI.get(id);
		if(student != null) {
			studentServiceAPI.delete(id); 
		}else {
			return new ResponseEntity<Student>(student, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Student>(student, HttpStatus.OK); 
	}
	
}

