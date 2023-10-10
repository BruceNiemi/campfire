package io.shaded.campfire.wallets.model

import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.*

class HistoryRecord(
  val wallet: UUID,
  val type: Type,
  val amount: BigInteger,
  val time: OffsetDateTime,
  val description: String? = null
) {
  enum class Type {
    DEPOSIT, WITHDRAWAL, TRANSFER
  }
}
