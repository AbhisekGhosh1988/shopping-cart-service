package com.abhisek.mindtree.entity;

import java.util.Objects;

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
public class Book extends Product {
	@Column(name = "genre")
	private String genre;
	@Column(name = "author")
	private String author;
	@Column(name = "publication")
	private String publication;


}
