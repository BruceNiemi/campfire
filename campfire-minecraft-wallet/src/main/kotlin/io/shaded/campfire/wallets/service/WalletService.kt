package io.shaded.campfire.wallets.service

import io.shaded.campfire.wallets.model.Currency
import io.shaded.campfire.wallets.model.Wallet
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.inTransactionUnchecked
import org.jdbi.v3.core.kotlin.mapTo
import org.jdbi.v3.core.kotlin.withHandleUnchecked
import org.jdbi.v3.core.transaction.TransactionIsolationLevel
import java.util.*

class WalletService(val jdbi: Jdbi) {

  sealed class AddFundsResult {
    class Added(val newBalance: Long) : AddFundsResult()
    object Error : AddFundsResult()
  }

  fun addFunds(
    playerId: UUID,
    currency: Currency,
    amount: Long
  ): AddFundsResult {
    require(amount > 0) {
      "Amount must be greater than zero"
    }

    return jdbi.inTransactionUnchecked(TransactionIsolationLevel.SERIALIZABLE) {
      val wallet = it.createQuery(
        """
        SELECT * FROM wallets
        WHERE player_id = :player_id AND currency_id = :currency_id
        FOR UPDATE
        """.trimIndent()
      )
        .bind("player_id", playerId)
        .bind("currency_id", currency.id)
        .mapTo(Wallet::class)
        .findFirst()
        .orElse(null)

      if (wallet != null) {
        // Use returned value from the JDBC rather than wallet even though
        // it's locked....
        val newBalance = it.createQuery(
          """
          UPDATE wallets
          SET balance = balance + :amount
          WHERE id = :wallet_id
          RETURNING balance
        """.trimIndent()
        )
          .bind("amount", amount)
          .bind("wallet_id", wallet.id)
          .mapTo(Long::class)
          .first()

        if (newBalance == null || newBalance != wallet.balance + amount) {
          it.rollback()
          AddFundsResult.Error
        } else {
          AddFundsResult.Added(newBalance)
        }
      } else {
        val updatedRows = it.createUpdate(
          """
          INSERT INTO wallets(id, player_id, currency_id, balance)
          VALUES (:wallet_id, :player_id, :currency_id, :balance)
          """.trimIndent()
        )
          .bind("wallet_id", UUID.randomUUID())
          .bind("player_id", playerId)
          .bind("currency_id", currency.id)
          .bind("balance", amount)
          .execute()

        if (updatedRows != 1) {
          it.rollback()
          AddFundsResult.Error
        } else {
          AddFundsResult.Added(amount)
        }
      }
      AddFundsResult.Error
    }
  }

  sealed class WithdrawFundsResult {
    class Withdrawn(val newBalance: Long) : WithdrawFundsResult()
    object InsignificantFunds : WithdrawFundsResult()
    object Error : WithdrawFundsResult()
  }

  fun withdrawFunds(
    playerId: UUID,
    currency: Currency,
    amount: Long
  ): WithdrawFundsResult {
    require(amount > 0) {
      "Amount must be greater than zero"
    }

    return jdbi.inTransactionUnchecked(TransactionIsolationLevel.SERIALIZABLE) {
      val wallet = it.createQuery(
        """
        SELECT * FROM wallets
        WHERE player_id = :player_id AND currency_id = :currency_id
        FOR UPDATE
        """.trimIndent()
      )
        .bind("player_id", playerId)
        .bind("currency_id", currency.id)
        .mapTo(Wallet::class)
        .findFirst()
        .orElse(null)

      if (wallet != null) {
        if (wallet.balance - amount < 0) {
          it.rollback()
          return@inTransactionUnchecked WithdrawFundsResult.InsignificantFunds
        } else {
          val newBalance = it.createQuery(
            """
            UPDATE wallets
            SET balance = balance - :amount
            WHERE id = :wallet_id
            RETURNING balance
           """.trimIndent()
          )
            .bind("amount", amount)
            .bind("wallet_id", wallet.id)
            .mapTo(Long::class)
            .first()

          if (newBalance == null || newBalance != wallet.balance - amount) {
            it.rollback()
            return@inTransactionUnchecked WithdrawFundsResult.Error
          }

          return@inTransactionUnchecked WithdrawFundsResult.Withdrawn(newBalance)
        }
      }
      WithdrawFundsResult.InsignificantFunds
    }
  }

  sealed class GetFundsResult {
    class Balance(val balance: Long) : GetFundsResult()
    object Error : GetFundsResult()
  }

  sealed class GetAllFunds {
    class Balances(val balances: Map<Currency, Long>) : GetAllFunds()
    object Error : GetAllFunds()
  }

  fun getFunds(playerId: UUID) : GetAllFunds {
    return GetAllFunds.Error
  }

  fun getFunds(playerId: UUID, currency: Currency): GetFundsResult {
    return this.jdbi.withHandleUnchecked {
      val amount = it.createQuery(
        """
        SELECT balance FROM wallets
        WHERE player_id = :player_id
      """.trimIndent()
      )
        .bind("player_id", playerId)
        .bind("currency_id", currency.id)
        .mapTo(Long::class)
        .findFirst()
        .orElse(null)
      if (amount == null) {
        GetFundsResult.Error
      } else {
        GetFundsResult.Balance(amount)
      }
    }
  }

  sealed class TransferFundsResult {
    object Transferred : TransferFundsResult()
    object InsignificantFunds : TransferFundsResult()
    object Error : TransferFundsResult()
  }

  fun transferFunds(
    currency: Currency,
    from: UUID,
    to: UUID,
    amount: Long
  ): TransferFundsResult {
    require(amount > 0) {
      "Amount must be greater than zero"
    }

    require(from != to) {
      "Cannot transfer to the same as sender."
    }

    return jdbi.inTransactionUnchecked(TransactionIsolationLevel.SERIALIZABLE) {
      val walletFrom = it.createQuery(
        """
        SELECT * FROM wallets
        WHERE player_id = :player_id AND currency_id = :currency_id
        FOR UPDATE
        """.trimIndent()
      )
        .bind("player_id", from)
        .bind("currency_id", currency.id)
        .mapTo(Wallet::class)
        .findFirst()
        .orElse(null)

      if (walletFrom == null || walletFrom.balance - amount < 0) {
        it.rollback()
        return@inTransactionUnchecked TransferFundsResult.InsignificantFunds
      }

      val walletTo = it.createQuery(
        """
        SELECT * FROM wallets
        WHERE player_id = :player_id AND currency_id = :currency_id
        FOR UPDATE
        """.trimIndent()
      )
        .bind("player_id", to)
        .bind("currency_id", currency.id)
        .mapTo(Wallet::class)
        .findFirst()
        .orElse(null)

      if (walletTo == null) {
        it.rollback()
        return@inTransactionUnchecked TransferFundsResult.InsignificantFunds
      }

      val resultWithdraw = it.createUpdate(
        """
            UPDATE wallets
            SET balance = balance - :amount
            WHERE id = :wallet_id
           """.trimIndent()
      )
        .bind("amount", amount)
        .bind("wallet_id", walletFrom.id)
        .execute()
      println("resultWithdrawn $resultWithdraw")
      if (resultWithdraw != 1) {
        it.rollback()
        return@inTransactionUnchecked TransferFundsResult.Error
      }

      val resultDeposit = it.createUpdate(
        """
            UPDATE wallets
            SET balance = balance + :amount
            WHERE id = :wallet_id
           """.trimIndent()
      )
        .bind("amount", amount)
        .bind("wallet_id", walletTo.id)
        .execute()

      println("resultDeposit $resultDeposit")
      if (resultDeposit != 1) {
        it.rollback()
        return@inTransactionUnchecked TransferFundsResult.Error
      }

      return@inTransactionUnchecked TransferFundsResult.Transferred
    }
  }
}
