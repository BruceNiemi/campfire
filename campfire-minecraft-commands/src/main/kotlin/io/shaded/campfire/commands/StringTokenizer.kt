package io.shaded.campfire.commands

import java.util.*

private val SPLIT_REGEX = "\\s+".toRegex()

/**
 * A utility class for breaking a string into tokens using whitespace as a delimiter.
 *
 * @param input the input string to tokenize
 */
class StringTokenizer(private val input: String) {
  /**
   * A queue to hold the tokens.
   */
  private val tokenQueue: Queue<String> =
    if (input.isBlank()) LinkedList() else LinkedList(input.trim().split(SPLIT_REGEX))

  /**
   * Gets the next token in the queue and removes it from the queue.
   *
   * @return the next token, or null if there are no more tokens
   */
  fun next(): String? = this.tokenQueue.poll()

  /**
   * Returns true if there are more tokens in the queue.
   *
   * @return true if there are more tokens in the queue, false otherwise
   */
  fun hasNext(): Boolean = this.tokenQueue.isNotEmpty()

  /**
   * Gets the current size of the tokenizer.
   *
   * @return the current size.
   */
  fun size(): Int = this.tokenQueue.size

  override fun toString(): String {
    return "StringTokenizer(input='$input', tokenQueue=$tokenQueue)"
  }
}
