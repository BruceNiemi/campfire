package io.shaded.campfire.ui.menu

@JvmInline
value class MenuSize(val size: Int) {
  init {
    require(size % 9 == 0) {
      "Menu size must be dividable by nine"
    }
  }
}
