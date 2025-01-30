package org.store.storetrade.services.impl;

import org.springframework.stereotype.Service;
import org.store.storetrade.enitites.Trade;
import org.store.storetrade.respository.TradeRepository;
import org.store.storetrade.services.TradeService;

import java.time.Instant;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public Trade getTrade(int id) {
        Optional<Trade> trade = tradeRepository.findById(id);
        return trade.orElse(null);
    }

    @Override
    public Trade updateTrade(Trade trade) {
        Optional<Trade> latestTradeOpt = tradeRepository.findTopBySymbolAndTradeTypeAndPriceAndQuantityOrderByVersionDesc(
                trade.getSymbol(), trade.getTradeType(), trade.getPrice(), trade.getQuantity()
        );

        if (latestTradeOpt.isPresent()) {
            Trade latestTrade = latestTradeOpt.get();
            if (trade.getVersion() == null || !trade.getVersion().equals(latestTrade.getVersion())) {
                throw new RuntimeException("Version conflict! The trade has been updated.");
            }
        } else {
            Optional<Trade> findSimilarTradeOpt = tradeRepository.findTopBySymbolAndTradeTypeAndPriceOrderByVersionDesc(trade.getSymbol(), trade.getTradeType(), trade.getPrice());
            if (findSimilarTradeOpt.isPresent()) {
                return setTrade(trade, findSimilarTradeOpt);
            } else {
                throw new RuntimeException("Trade not found!");
            }
        }
        return null;
    }

    @Override
    public Trade saveTrade(Trade trade) {
        Optional<Trade> latestTradeOpt = tradeRepository.findTopBySymbolAndTradeTypeAndPriceAndQuantityOrderByVersionDesc(
                trade.getSymbol(), trade.getTradeType(), trade.getPrice(), trade.getQuantity()
        );

        if (latestTradeOpt.isPresent()) {
            throw new RuntimeException("Version conflict! The trade already exists.");

        } else {
            Optional<Trade> findSimilarTradeOpt = tradeRepository.findTopBySymbolAndTradeTypeAndPriceOrderByVersionDesc(trade.getSymbol(), trade.getTradeType(), trade.getPrice());
            if (findSimilarTradeOpt.isPresent()) {
                return setTrade(trade, findSimilarTradeOpt);
            } else {
                if (trade.getTradeTime() == null) {
                    trade.setTradeTime(Instant.now());
                }
                trade.setVersion(1);
                return tradeRepository.save(trade);
            }
        }
    }

    private Trade setTrade(Trade trade, Optional<Trade> findSimilarTradeOpt) {
        findSimilarTradeOpt.get().setPrice(trade.getPrice());
        findSimilarTradeOpt.get().setTradeType(trade.getTradeType());
        findSimilarTradeOpt.get().setVersion(findSimilarTradeOpt.get().getVersion() + 1);
        findSimilarTradeOpt.get().setSymbol(trade.getSymbol());
        findSimilarTradeOpt.get().setQuantity(trade.getQuantity());
        if (trade.getTradeTime() != null) {
            findSimilarTradeOpt.get().setTradeTime(trade.getTradeTime());
        } else {
            findSimilarTradeOpt.get().setTradeTime(Instant.now());
        }
        return tradeRepository.save(findSimilarTradeOpt.get());
    }

    @Override
    public void deleteTrade(int id) {
        tradeRepository.deleteById(id);
    }
}
