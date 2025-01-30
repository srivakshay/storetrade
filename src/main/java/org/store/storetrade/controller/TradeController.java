package org.store.storetrade.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.store.storetrade.enitites.Trade;
import org.store.storetrade.services.TradeService;

@RestController
@CrossOrigin("*")
public class TradeController {
    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping("/{tradeId}")
    public ResponseEntity<Trade> getTrade(@PathVariable int tradeId) {
        return ResponseEntity.ok(tradeService.getTrade(tradeId));
    }

    @PostMapping()
    public ResponseEntity<Trade> createTrade(@RequestBody Trade trade) {
        return ResponseEntity.ok(tradeService.saveTrade(trade));
    }

    @PutMapping()
    public ResponseEntity<Trade> updateTrade(@RequestBody Trade trade) {
        return ResponseEntity.ok(tradeService.updateTrade(trade));
    }

    @DeleteMapping("/{tradeId}")
    public void deleteTrade(@PathVariable int tradeId) {
        tradeService.deleteTrade(tradeId);
    }
}
