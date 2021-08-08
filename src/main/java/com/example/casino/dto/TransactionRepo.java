package com.example.casino.dto;

import com.example.casino.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
//    public Long findbyId(Long id);
}
