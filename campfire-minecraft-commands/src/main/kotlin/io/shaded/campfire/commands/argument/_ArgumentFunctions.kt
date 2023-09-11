package io.shaded.campfire.commands.argument

import io.shaded.campfire.commands.Command
import io.shaded.campfire.commands.argument.parser.*
import java.util.*

fun <S> Command<S>.boolean(): CommandArgument<Boolean> {
  val booleanArg = BooleanArgument()
  this.arguments.add(booleanArg)
  return booleanArg
}

fun <S> Command<S>.double(
  min: Double = Double.NEGATIVE_INFINITY,
  max: Double = Double.POSITIVE_INFINITY,
  truncationLimit: Int = -1
): CommandArgument<Double> {
  val doubleArg = DoubleArgument(min, max, truncationLimit)
  this.arguments.add(doubleArg)
  return doubleArg
}

fun <S> Command<S>.float(
  min: Float = Float.NEGATIVE_INFINITY,
  max: Float = Float.POSITIVE_INFINITY,
  truncationLimit: Int = -1
): CommandArgument<Float> {
  val floatArg = FloatArgument(min, max, truncationLimit)
  this.arguments.add(floatArg)
  return floatArg
}

fun <S> Command<S>.integer(
  min: Int = Int.MIN_VALUE,
  max: Int = Int.MAX_VALUE
): CommandArgument<Int> {
  val integerArg = IntegerArgument(min, max)
  this.arguments.add(integerArg)
  return integerArg
}

fun <S> Command<S>.long(
  min: Long = Long.MIN_VALUE,
  max: Long = Long.MAX_VALUE
): CommandArgument<Long> {
  val longArg = LongArgument(min, max)
  this.arguments.add(longArg)
  return longArg
}

fun <S> Command<S>.singleString(): CommandArgument<String> {
  val stringArg = StringArgument(StringArgument.StringType.SINGLE_WORD)
  this.arguments.add(stringArg)
  return stringArg
}

fun <S> Command<S>.greedyString(): CommandArgument<String> {
  val stringArg = StringArgument(StringArgument.StringType.GREEDY_PHRASE)
  this.arguments.add(stringArg)
  return stringArg
}

fun <S> Command<S>.uuid(): CommandArgument<UUID> {
  val uuidArg = UUIDArgument()
  this.arguments.add(uuidArg)
  return uuidArg
}
