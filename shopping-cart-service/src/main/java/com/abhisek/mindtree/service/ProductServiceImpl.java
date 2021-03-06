package com.abhisek.mindtree.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abhisek.mindtree.entity.Apparel;
import com.abhisek.mindtree.entity.Book;
import com.abhisek.mindtree.entity.Product;
import com.abhisek.mindtree.exception.ProductSavingException;
import com.abhisek.mindtree.model.ProductRequest;
import com.abhisek.mindtree.model.ProductResponseDto;
import com.abhisek.mindtree.repository.ApparelRepository;
import com.abhisek.mindtree.repository.BookRepository;
import com.abhisek.mindtree.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ApparelRepository apparelRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> saveProduct(List<ProductRequest> productRequests) {

		ObjectMapper mapper = new ObjectMapper();

		List<ProductRequest> booksList = productRequests.stream().filter(b -> b.getGenre() != null)
				.collect(Collectors.toList());
		List<ProductRequest> apparelsList = productRequests.stream().filter(b -> b.getBrand() != null)
				.collect(Collectors.toList());

		String jsonBooks = null;
		String jsonApparels = null;
		try {
			jsonBooks = mapper.writeValueAsString(booksList);
			jsonApparels = mapper.writeValueAsString(apparelsList);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Book> booksObject = null;
		List<Apparel> apparelsObject = null;
		try {
			booksObject = mapper.readValue(jsonBooks, new TypeReference<List<Book>>() {
			});
			apparelsObject = mapper.readValue(jsonApparels, new TypeReference<List<Apparel>>() {
			});
		} catch (JsonMappingException e) {
			throw new ProductSavingException();
		} catch (JsonProcessingException e) {
			throw new ProductSavingException();
		}
		booksObject = bookRepository.saveAll(booksObject);
		apparelsObject = apparelRepository.saveAll(apparelsObject);
		List<Product> result = Stream.concat(booksObject.stream(), apparelsObject.stream()).distinct()
				.collect(Collectors.toList());
		return result;

	}

	@Override
	public String deleteProduct(int id) {
		productRepository.deleteById(id);
		return "Success";

	}

	@Override
	public ProductResponseDto getProductsByCategory(String category, int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Page<Product> contentPage = productRepository.findByDtype(category, pageable);
		List<Product> productsToReturn = contentPage.getContent();
		return new ProductResponseDto(productsToReturn, contentPage.getTotalPages(), contentPage.getTotalElements());

	}

	@Override
	public ProductResponseDto getProductsByName(String name) {
		int pageNumber = 1;
		int pageSize = 10;
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Page<Product> contentPage = productRepository.findByProductName(name, pageable);
		List<Product> products = contentPage.getContent();
		return new ProductResponseDto(products, contentPage.getTotalPages(), contentPage.getTotalElements());
	}

}
