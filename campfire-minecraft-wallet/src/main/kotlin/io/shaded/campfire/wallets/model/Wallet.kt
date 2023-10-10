package io.shaded.campfire.wallets.model

import java.math.BigInteger
import java.util.*

class Wallet(
  val playerId: UUID,
  val currency: String,
  val balance: BigInteger = BigInteger.ZERO
)
