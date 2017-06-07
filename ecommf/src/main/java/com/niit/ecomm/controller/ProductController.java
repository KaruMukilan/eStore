package com.niit.ecomm.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.niit.ecomm.domain.Product;
import com.niit.ecomm.hiber.domain.MProduct;
import com.niit.ecomm.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	 private com.niit.ecomm.hiber.impl.MProductImpl pro;
	


	@RequestMapping
	public String list(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}
	
	@RequestMapping("/all")
	public ModelAndView allProducts() {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("products", productService.getAllProducts());
		modelAndView.setViewName("products");
		return modelAndView;
	}
	
	@RequestMapping("/{category}")
	public String getProductsByCategory(Model model, @PathVariable("category") String category) {
		List<Product> products = productService.getProductsByCategory(category);

		if (products == null || products.isEmpty()) {
			return null;
		}

		model.addAttribute("products", products);
		return "products";
	}

	
	@RequestMapping("/filter/{ByCriteria}")
	public String getProductsByFilter(@MatrixVariable(pathVar="ByCriteria") Map<String,List<String>> filterParams, Model model) {
		model.addAttribute("products", productService.getProductsByFilter(filterParams));
		return "products";
	}
	
	@RequestMapping("/product")
	public String getProductById(Model model, @RequestParam("id") String productId) {
		Product product = productService.getProductById(productId);
		model.addAttribute("product", product);
		return "product";
	}

	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddNewProductForm(@ModelAttribute("newProduct") Product newProduct) {
	   return "addProduct";
	}
	   
	/**
	 * @param productToBeAdded
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddNewProductForm(@ModelAttribute("newProduct") @Valid Product productToBeAdded, BindingResult result, HttpServletRequest request) {
		if(result.hasErrors()) {
			return "addProduct";
		}

		String[] suppressedFields = result.getSuppressedFields();
		
		if (suppressedFields.length > 0) {
			throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
		}
		
		MultipartFile productImage = productToBeAdded.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		
		MProduct entity=new MProduct();
		entity.setProductId(productToBeAdded.getProductId());
		entity.setName(productToBeAdded.getName());
		entity.setUnitPrice(productToBeAdded.getUnitPrice());
		entity.setDescription(productToBeAdded.getDescription());
		entity.setManufacturer(productToBeAdded.getManufacturer());
		entity.setCategory(productToBeAdded.getCategory());
		entity.setUnitsInStock(productToBeAdded.getUnitsInStock());
		entity.setUnitsInOrder(entity.getUnitsInStock());
		entity.setCondition(productToBeAdded.getCondition());
		String image=""+productToBeAdded.getProductId()+productToBeAdded.getProductImage().getOriginalFilename();
		entity.setProductImage(image);
	
		
			if (productImage!=null && !productImage.isEmpty()) {
		       try {
		      	 //productImage.transferTo(new File(rootDirectory+"resources\\images\\"+productToBeAdded.getProductId() + ".png"));
		    	 productImage.transferTo(new File(rootDirectory+"resources\\images\\"+image));
		       } catch (Exception e) {
				throw new RuntimeException("Product Image saving failed", e);
		   }
		   }		
			
	   	productService.addProduct(productToBeAdded);
	   	pro.persist(entity);
		return "redirect:/products";
	}

}
