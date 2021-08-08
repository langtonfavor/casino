package com.example.casino.controller;

import com.example.casino.dto.PlayerRepo;
import com.example.casino.dto.TransactionRepo;
import com.example.casino.models.Player;
import com.example.casino.models.Transaction;
import com.example.casino.models.TransactionType;
import com.example.casino.service.CasinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class CasinoController {

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    PlayerRepo playerRepo;

    @Autowired
    CasinoService casinoService;

    @GetMapping
    public BigDecimal c() {
        return null;

    }

    @PostMapping
    public BigDecimal deductMoney(){
        return null;
    }

    @PostMapping("/add-player")
    public ResponseEntity<BigDecimal> addPlayer(@RequestBody Player player){
        ResponseEntity ret;
        if(player.getBalance() == null){
            player.setBalance(new BigDecimal(0));
        }
        Player save = playerRepo.save(player);
        ret = ResponseEntity.ok().body(save);
        return ret;
    }

    @PostMapping("/win")
    public ResponseEntity<BigDecimal> depositMoney(@RequestBody Transaction transaction){
        transaction.setTransactionType(TransactionType.WIN);
        ResponseEntity ret;

        if(!playerRepo.existsById(transaction.getPlayerId())){
            ret = ResponseEntity.badRequest().body("player not found");
        }
        else if(transactionRepo.existsById(transaction.getTransactionId())){
            ret = ResponseEntity.ok().body("nothing changed");
        }
        else {
            casinoService.depositeService(transaction);
            ret = ResponseEntity.ok().body("successfully updated");
        }
        return ret;
    }

    @PostMapping("/deduct")
    public ResponseEntity deduct(@RequestBody Transaction transaction){
        transaction.setTransactionType(TransactionType.WAGGER);
        ResponseEntity ret;
        Player player = null;
        var playerOption = playerRepo.findById(transaction.getPlayerId());

        if(playerOption.isPresent()){
            player = playerOption.get();
        }
        if(!playerRepo.existsById(transaction.getPlayerId())){
            ret = ResponseEntity.badRequest().body("player not found");
        }
        else if(player.getBalance().compareTo( transaction.getAmount()) == -1 ) {
            ret = ResponseEntity.status(418).build();
        }
        else if(transactionRepo.existsById(transaction.getTransactionId())){
            ret = ResponseEntity.ok().body("nothing changed");
        }
        else {
            Boolean depo = casinoService.withdrawal(transaction);
            ret = ResponseEntity.ok().body("successfully updated");
        }
        return ret;
    }
    @GetMapping("/balance/{playerId}")
    public ResponseEntity balance(@PathVariable Long playerId){

        ResponseEntity ret;
        Player player = playerRepo.getById(playerId);

        if(player == null){
            ret = ResponseEntity.badRequest().body("player not found");
        }
        else {
            BigDecimal depo = player.getBalance();
            ret = ResponseEntity.ok().body(depo);
        }
        return ret;
    }
}

