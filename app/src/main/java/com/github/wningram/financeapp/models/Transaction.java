package com.github.wningram.financeapp.models;

import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class Transaction {
	@Id
	private String transactionId;
	
	/**
	 * The name of the seller who initiated the transaction.
	 */
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
	private Category category;
	
	/**
	 * Any user-defined tags for the transaction (for filtering).
	 */
	@ElementCollection
	private Set<String> tags;
}
