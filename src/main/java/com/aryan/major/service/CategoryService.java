package com.aryan.major.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aryan.major.model.Category;
import com.aryan.major.repository.CategoryRepositery;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepositery repositery;
	
	public List<Category> gettAllCategory(){
		return repositery.findAll();
	}
	
	public void addCategory(Category category) {
		 repositery.save(category);
	}
	
	public void removeCategoryById(int id) {
		repositery.deleteById(id);
	}
	
	public Optional<Category> getCategoryById(int id) {
		return repositery.findById(id);
		
	}

}
