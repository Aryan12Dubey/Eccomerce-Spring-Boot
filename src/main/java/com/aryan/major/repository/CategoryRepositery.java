package com.aryan.major.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aryan.major.model.Category;

@Repository
public interface CategoryRepositery extends JpaRepository<Category, Integer> {

}
