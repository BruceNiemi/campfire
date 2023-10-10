package io.shaded.campfire.wallets.service

import io.shaded.campfire.wallets.model.HistoryRecord
import java.math.BigInteger
import java.util.*

class WalletService {

  sealed class AddFundsResult {
    class Added(val newBalance: BigInteger) : AddFundsResult()
    object Error : AddFundsResult()
  }

  fun addFunds(
    playerId: UUID,
    currency: Currency,
    amount: BigInteger, reason:
    String? = null
  ): AddFundsResult {
    require(amount.signum() != -1) {
      "Amount must not be null"
    }
    return AddFundsResult.Error
  }

  sealed class WithdrawFundsResult {
    class Withdrawn(val newBalance: BigInteger) : WithdrawFundsResult()
    object Negative : WithdrawFundsResult()
    object Error : WithdrawFundsResult()
  }

  fun withdrawFunds(
    playerId: UUID,
    currency: Currency,
    amount: BigInteger,
    reason: String? = null
  ): WithdrawFundsResult {
    require(amount.signum() != -1) {
      "Amount must not be null"
    }

    return WithdrawFundsResult.Error
  }

  sealed class GetFundsResult {
    class Balance(val balance: BigInteger) : GetFundsResult()
    object Error : GetFundsResult()
  }

  fun getFunds(playerId: UUID, currency: Currency): BigInteger {
    return BigInteger.ZERO
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
    amount: BigInteger,
    reason: String? = null
  ): TransferFundsResult {
    return TransferFundsResult.Error
  }

  fun getTransactionHistory(playerId: UUID): List<HistoryRecord> {
    // add some stuff for filtering and pagination
    return emptyList()
  }

}
