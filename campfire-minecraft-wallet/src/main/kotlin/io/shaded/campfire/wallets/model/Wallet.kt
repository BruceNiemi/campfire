package io.shaded.campfire.wallets.model

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.math.BigInteger
import java.util.*

class Wallet(
  @ColumnName("id") val id: UUID,
  @ColumnName("player_id") val playerId: UUID,
  @ColumnName("currency_id") val currency: UUID,
  @ColumnName("balance") val balance: Long
)
