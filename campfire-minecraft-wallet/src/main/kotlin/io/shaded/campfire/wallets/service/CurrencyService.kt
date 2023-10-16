package io.shaded.campfire.wallets.service

import io.shaded.campfire.wallets.model.Currency
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.inTransactionUnchecked
import org.jdbi.v3.core.kotlin.mapTo
import org.jdbi.v3.core.kotlin.withHandleUnchecked
import java.util.*

class CurrencyService(val jdbi: Jdbi) {

  /**
   * Creates currency if it does not exist or returns.
   */
  fun createCurrency(
    name: String,
    symbol: String,
    description: String? = null
  ): Currency {
    return this.jdbi.inTransactionUnchecked { handle: Handle ->
      var currency: Currency?

      currency = handle.createQuery(
        """
        SELECT * FROM currencies WHERE name =:name
        """.trimIndent()
      )
        .bind("name", name)
        .mapTo(Currency::class)
        .firstOrNull()
      println("is currency null ${currency == null}")
      if (currency == null) {
        val id = handle.createQuery(
          """
           INSERT INTO currencies(name, symbol, description) VALUES
           (:name, :symbol, :description)
           RETURNING id
          """.trimIndent()
        ).bind("name", name)
          .bind("symbol", symbol)
          .bind("description", description)
          .mapTo(UUID::class)
          .first()!!

        currency = Currency(id, name, symbol, description)
      }

      currency
    }
  }

  fun getCurrency(name: String): Currency? = this.jdbi.withHandleUnchecked {
    it.createQuery(
      """
        SELECT * FROM currencies WHERE name =:name
        """.trimIndent()
    )
      .bind("name", name)
      .mapTo(Currency::class)
      .firstOrNull()
  }
}
