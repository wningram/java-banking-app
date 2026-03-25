package com.github.wningram.financeapp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@Getter
//	@Column(name = "category_id")
	private int id;
	
//	@Getter
//	@Column(unique = true)
	private String name;
}
