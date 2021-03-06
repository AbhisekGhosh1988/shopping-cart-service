package com.abhisek.mindtree.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Apparel extends Product {

	@Column(name = "type")
	private String type;
	@Column(name = "brand")
	private String brand;
	@Column(name = "design")
	private String design;
	
	
}
