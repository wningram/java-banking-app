/**
 * 
 */
package com.github.wningram.financeapp.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * 
 */
@Entity
@Data
public class Seller {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String description;
}
