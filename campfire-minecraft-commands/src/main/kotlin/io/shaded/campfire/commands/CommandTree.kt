package io.shaded.campfire.commands

class CommandTree {
  private var root: CommandNode<*>? = null

  fun insert(labels: List<String>, command: Command<*>) {
    labels.forEach { label ->
      if (this.root == null) {
        this.root = CommandNode(label, command)
      } else {
        root = insert(this.root, label, command)
      }
    }
  }

  private fun insert(node: CommandNode<*>?, label: String, command: Command<*>): CommandNode<*> {
    if (node == null) {
      return CommandNode(label, command)
    }

    if (label < node.label) {
      node.left = insert(node.left, label, command)
    } else if (label > node.label) {
      node.right = insert(node.right, label, command)
    } else {
      throw IllegalArgumentException("Duplicate value: $label")
    }

    return node
  }

  fun getCommand(label: String): Command<*>? = getCommandRecursive(this.root, label)?.command

  private fun getCommandRecursive(node: CommandNode<*>?, label: String): CommandNode<*>? {
    return when {
      node == null -> null
      label < node.label -> getCommandRecursive(node.left, label)
      label > node.label -> getCommandRecursive(node.right, label)
      else -> node
    }
  }

  fun getLabels(): List<String> {
    val labels = arrayListOf<String>()
    this.getLabelsRecursive(this.root, labels)
    return labels
  }

  private fun getLabelsRecursive(node: CommandNode<*>?, labels: ArrayList<String>) {
    if(node != null) {
      this.getLabelsRecursive(node.left, labels)
      labels.add(node.label)
      this.getLabelsRecursive(node.right, labels)
    }
  }

  private data class CommandNode<T>(
    val label: String,
    val command: Command<T>,
    var left: CommandNode<*>? = null,
    var right: CommandNode<*>? = null
  )

  override fun toString(): String {
    val stringBuilder = StringBuilder()
    buildStringRepresentation(root, stringBuilder)
    return stringBuilder.toString()
  }

  private fun buildStringRepresentation(node: CommandNode<*>?, stringBuilder: StringBuilder) {
    if (node != null) {
      buildStringRepresentation(node.left, stringBuilder)
      stringBuilder.append(node.label).append(", ")
      buildStringRepresentation(node.right, stringBuilder)
    }
  }
}
