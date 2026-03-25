package com.github.wningram.financeapp.models;

import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Entity
@Data
public class Transaction {
	@Id
	private String transactionId;
	
	/**
	 * The name of the seller who initiated the transaction.
	 */
	@ManyToOne
	private Seller seller;
	
	/**
	 * A description of the transaction.
	 */
	private String description;
	
	/**
	 * The amount of the transaction.
	 */
	private double amount;
	
	/**
	 * Any user notes regarding the transaction.
	 */
	private String notes;
	
	/**
	 * The category the transaction is associated with.
	 */
	@ManyToOne
	private Category category;
	
	/**
	 * Any user-defined tags for the transaction (for filtering).
	 */
	@ElementCollection
	private Set<String> tags;
}
