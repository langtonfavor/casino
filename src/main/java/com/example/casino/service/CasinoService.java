package com.example.casino.service;

import com.example.casino.dto.PlayerRepo;
import com.example.casino.dto.TransactionRepo;
import com.example.casino.models.Player;
import com.example.casino.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CasinoService {

    @Autowired
    PlayerRepo playerRepo;

    @Autowired
    TransactionRepo transactionRepo;

    public Boolean depositeService(Transaction transaction){
        Player player = playerRepo.getById(transaction.getPlayerId());
        player.setBalance(player.getBalance().add(transaction.getAmount()));
        playerRepo.save(player);
        transactionRepo.save(transaction);
        return true;
    }

    public Boolean withdrawal(Transaction transaction){
        Player player = playerRepo.getById(transaction.getPlayerId());
        player.setBalance(player.getBalance().subtract(transaction.getAmount()));
        playerRepo.save(player);
        transactionRepo.save(transaction);
        return true;
    }
}
