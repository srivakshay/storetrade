package org.store.storetrade.services;

import org.store.storetrade.enitites.Trade;

public interface TradeService {
    Trade getTrade(int id);

    Trade updateTrade(Trade trade);

    Trade saveTrade(Trade trade);

    void deleteTrade(int id);
}
