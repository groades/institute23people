package com.test.institute.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class CommonServiceImpl<T, ID extends Serializable> implements CommonServiceAPI<T, ID> {

	@Override
	public T save(T entity) {
		return getDao().save(entity);
	}

	@Override
	public T get(ID id) {
		Optional<T> obj = getDao().findById(id);
		if (obj.isPresent()) {
			return obj.get();
		}
		return null;
	}

	@Override
	public List<T> getAll() {
		List<T> returnList = new ArrayList<>();
		getDao().findAll().forEach(obj -> returnList.add(obj));
		return returnList;
	}
	
	@Override
	public void delete(ID id) {
		getDao().deleteById(id);
	}
	
	@Override
	public Page<T> getAllPaged(Pageable pageable){
		return getDao().findAll(pageable);
	}

	public abstract JpaRepository<T, ID> getDao();

}
