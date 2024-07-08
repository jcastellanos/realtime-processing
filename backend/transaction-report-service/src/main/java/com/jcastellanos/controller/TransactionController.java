package com.jcastellanos.controller;

import com.jcastellanos.dto.TransactionRecord;
import com.jcastellanos.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/report")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @CrossOrigin("*")
    @GetMapping("/transactions")
    public List<TransactionRecord> transactions(@RequestParam(required = false, defaultValue = "") String filter,
                                                @RequestParam(required = false, defaultValue = "") String value) {
        return this.transactionService.findAll(filter, value);

    }
}
