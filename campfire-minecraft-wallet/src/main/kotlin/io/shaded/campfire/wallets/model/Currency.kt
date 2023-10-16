package io.shaded.campfire.wallets.model

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.util.*

class Currency(
  @ColumnName("id") val id: UUID,
  @ColumnName("name") val name: String,
  @ColumnName("symbol") val symbol: String,
  @ColumnName("description") val description: String? = null
)
