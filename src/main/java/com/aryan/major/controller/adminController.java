package com.aryan.major.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aryan.major.dto.ProductDTO;
import com.aryan.major.model.Category;
import com.aryan.major.model.Product;
import com.aryan.major.service.CategoryService;
import com.aryan.major.service.ProductService;

@Controller
public class adminController {
	
	public static String uploadDir= System.getProperty("user.dir")+ "/src/main/resources/static/productImages";
	
	
	@Autowired
	CategoryService categoryservice;
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/admin")
	public String adminhome() {
		return "adminHome";
	}
	
	@GetMapping("/admin/categories")
	public String getCat(Model model) {
		model.addAttribute("categories",categoryservice.gettAllCategory());
		return "categories";
	}
	@GetMapping("/admin/categories/add")
	public String getCatAdd(Model model) {
		model.addAttribute("category",new Category());
		return "categoriesAdd";
	}
	
	@PostMapping("/admin/categories/add")
	public String postCatAdd(@ModelAttribute("category") Category category) {
		categoryservice.addCategory(category);
		return "redirect:/admin/categories";
		
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String delete(@PathVariable int id) {
		categoryservice.removeCategoryById(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCat(@PathVariable int id,Model model) {
		Optional<Category> category = categoryservice.getCategoryById(id);
		if(category.isPresent()) {
			model.addAttribute("category", category.get());
			return "categoriesAdd";
		} else
			return "404";
	}
	
	
	//Products
	
	
	
	@GetMapping("/admin/products")
	public String products(Model model) {
		model.addAttribute("products", productService.getAllProduct());
		return "products";
	}
	
	
	@GetMapping("/admin/products/add")
	public String productAddGet(Model model) {
	    model.addAttribute("productDTO", new ProductDTO());
	    model.addAttribute("categories", categoryservice.gettAllCategory());
	    return "productsAdd";
	}
	
	
	
	
	@PostMapping("/admin/products/add")
	public String productAddPost(@ModelAttribute("productDTO")ProductDTO productDTO,
								 @RequestParam("productImage")MultipartFile file,
								 @RequestParam("imgName")String imgName) throws IOException{
		
		
		
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryservice.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		String imageUUID;
		if (!file.isEmpty()) {
		    imageUUID = file.getOriginalFilename();
		    Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
		    Files.write(fileNameAndPath, file.getBytes());
		} else {
			imageUUID = imgName;
		}
		product.setImageName(imageUUID);
		productService.addProduct(product);
		return "redirect:/admin/products";
	}
	

	
	
}















