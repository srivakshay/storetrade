package org.store.storetrade.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.store.storetrade.enitites.Trade;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
    Optional<Trade> findTopBySymbolAndTradeTypeAndPriceAndQuantityOrderByVersionDesc(
            String symbol, String tradeType, BigDecimal price, int quantity
    );

    Optional<Trade> findTopBySymbolAndTradeTypeAndPriceOrderByVersionDesc(String symbol, String tradeType, BigDecimal price);
}
