package io.shaded.campfire.commands.arguments

import io.shaded.campfire.commands.CampfireCommand
import io.shaded.campfire.commands.arguments.parser.*
import java.util.*

fun <S, T : Any> RequiredArgument<S, T>.optional(): OptionalAdapter<S, T> =
  OptionalAdapter(this)

fun <S, T : Any> RequiredArgument<S, T>.default(
  supplier: DefaultArgumentSupplier<S, T>
): DefaultArgumentAdapter<S, T> =
  DefaultArgumentAdapter(supplier, this)

// Boolean argument
fun <S> CampfireCommand<S>.boolean(name: String): RequiredArgument<S, Boolean> {
  val argument = BooleanArgument<S>(name)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.boolean(
  name: String,
  supplier: DefaultArgumentSupplier<S, Boolean>
): DefaultArgumentAdapter<S, Boolean> {
  val argument = BooleanArgument<S>(name).default(supplier)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.optionalBoolean(
  name: String
): OptionalAdapter<S, Boolean> {
  val argument = BooleanArgument<S>(name).optional()
  this.arguments.add(argument)
  return argument
}

// Double Arguments

fun <S> CampfireCommand<S>.double(
  name: String,
  min: Double = Double.NEGATIVE_INFINITY,
  max: Double = Double.POSITIVE_INFINITY
): RequiredArgument<S, Double> {
  val argument = DoubleArgument<S>(name, min, max)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.double(
  name: String,
  min: Double = Double.NEGATIVE_INFINITY,
  max: Double = Double.POSITIVE_INFINITY,
  supplier: DefaultArgumentSupplier<S, Double>
): DefaultArgumentAdapter<S, Double> {
  val argument = DoubleArgument<S>(name, min, max).default(supplier)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.optionalDouble(
  name: String,
  min: Double = Double.NEGATIVE_INFINITY,
  max: Double = Double.POSITIVE_INFINITY
): OptionalAdapter<S, Double> {
  val argument = DoubleArgument<S>(name, min, max).optional()
  this.arguments.add(argument)
  return argument
}

// Float arguments

fun <S> CampfireCommand<S>.float(
  name: String,
  min: Float = Float.NEGATIVE_INFINITY,
  max: Float = Float.POSITIVE_INFINITY
): RequiredArgument<S, Float> {
  val argument = FloatArgument<S>(name, min, max)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.float(
  name: String,
  min: Float = Float.NEGATIVE_INFINITY,
  max: Float = Float.POSITIVE_INFINITY,
  supplier: DefaultArgumentSupplier<S, Float>
): DefaultArgumentAdapter<S, Float> {
  val argument = FloatArgument<S>(name, min, max).default(supplier)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.optionalFloat(
  name: String,
  min: Float = Float.NEGATIVE_INFINITY,
  max: Float = Float.POSITIVE_INFINITY
): OptionalAdapter<S, Float> {
  val argument = FloatArgument<S>(name, min, max).optional()
  this.arguments.add(argument)
  return argument
}

// Int arguments
fun <S> CampfireCommand<S>.integer(
  name: String,
  min: Int = Int.MIN_VALUE,
  max: Int = Int.MAX_VALUE
): RequiredArgument<S, Int> {
  val argument = IntegerArgument<S>(name, min, max)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.integer(
  name: String,
  min: Int = Int.MIN_VALUE,
  max: Int = Int.MAX_VALUE,
  supplier: DefaultArgumentSupplier<S, Int>
): DefaultArgumentAdapter<S, Int> {
  val argument = IntegerArgument<S>(name, min, max).default(supplier)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.optionalInteger(
  name: String,
  min: Int = Int.MIN_VALUE,
  max: Int = Int.MAX_VALUE
): OptionalAdapter<S, Int> {
  val argument = IntegerArgument<S>(name, min, max).optional()
  this.arguments.add(argument)
  return argument
}

// Long argument

fun <S> CampfireCommand<S>.long(
  name: String,
  min: Long = Long.MIN_VALUE,
  max: Long = Long.MAX_VALUE
): RequiredArgument<S, Long> {
  val argument = LongArgument<S>(name, min, max)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.long(
  name: String,
  min: Long = Long.MIN_VALUE,
  max: Long = Long.MAX_VALUE,
  supplier: DefaultArgumentSupplier<S, Long>
): DefaultArgumentAdapter<S, Long> {
  val argument = LongArgument<S>(name, min, max).default(supplier)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.optionalLong(
  name: String,
  min: Long = Long.MIN_VALUE,
  max: Long = Long.MAX_VALUE
): OptionalAdapter<S, Long> {
  val argument = LongArgument<S>(name, min, max).optional()
  this.arguments.add(argument)
  return argument
}

// String argument

fun <S> CampfireCommand<S>.singleString(
  name: String
): StringArgument<S> {

  val argument = StringArgument<S>(name, StringArgument.StringType.SINGLE_WORD)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.defaultSingleString(
  name: String,
  supplier: DefaultArgumentSupplier<S, String>
): DefaultArgumentAdapter<S, String> {
  val argument = StringArgument<S>(
    name, StringArgument.StringType.SINGLE_WORD
  ).default(supplier)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.optionalSingleString(
  name: String
): OptionalAdapter<S, String> {
  val argument = StringArgument<S>(
    name, StringArgument.StringType.SINGLE_WORD
  ).optional()
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.greedyString(
  name: String
): StringArgument<S> {
  val argument =
    StringArgument<S>(name, StringArgument.StringType.GREEDY_PHRASE)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.defaultGreedyString(
  name: String,
  supplier: DefaultArgumentSupplier<S, String>
): DefaultArgumentAdapter<S, String> {
  val argument = StringArgument<S>(
    name, StringArgument.StringType.GREEDY_PHRASE
  ).default(supplier)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.optionalGreedyString(
  name: String
): OptionalAdapter<S, String> {
  val argument = StringArgument<S>(
    name, StringArgument.StringType.GREEDY_PHRASE
  ).optional()
  this.arguments.add(argument)
  return argument
}

// UUID argument

fun <S> CampfireCommand<S>.uuid(
  name: String
): UUIDArgument<S> {
  val argument = UUIDArgument<S>(name)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.defaultUuid(
  name: String,
  supplier: DefaultArgumentSupplier<S, UUID>
): DefaultArgumentAdapter<S, UUID> {
  val argument = UUIDArgument<S>(name).default(supplier)
  this.arguments.add(argument)
  return argument
}

fun <S> CampfireCommand<S>.optionalUuid(
  name: String
): OptionalAdapter<S, UUID> {
  val argument = UUIDArgument<S>(name).optional()
  this.arguments.add(argument)
  return argument
}
