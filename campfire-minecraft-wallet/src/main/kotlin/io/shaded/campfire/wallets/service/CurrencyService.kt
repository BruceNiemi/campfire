package io.shaded.campfire.wallets.service

import io.shaded.campfire.wallets.model.Currency

class CurrencyService {

  /**
   * Creates currency if it does not exist or returns.
   */
  fun createCurrency(
    name: String,
    symbol: String,
    description: String? = null
  ): Currency {
    return Currency(name, symbol, description)
  }
}
