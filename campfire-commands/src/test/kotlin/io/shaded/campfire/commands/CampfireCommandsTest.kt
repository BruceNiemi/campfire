package io.shaded.campfire.commands

import io.shaded.campfire.commands.arguments.integer
import io.shaded.campfire.commands.arguments.optionalInteger
import org.junit.jupiter.api.assertThrows
import kotlin.test.*

/**
 * Tests execution and suggesting commands.
 */
internal class CampfireCommandsTest {

  @Test
  fun `test registering a root level command`() {
    val campfireCommands = DummyCampfireCommands()

    campfireCommands.register("foo") {
      execute {
        // NO-OP
      }
    }

    assertNotNull(
      campfireCommands.commandMap.getCommand("foo"),
      "Failed to find root command 'foo'."
    )
  }

  @Test
  fun `test insert duplicate command`() {
    val campfireCommands = DummyCampfireCommands()

    campfireCommands.register("foo") {
      execute {
        // NO-OP
      }
    }

    val exception = assertThrows<IllegalStateException> {
      campfireCommands.register("foo") {
        execute {
          // NO-OP
        }
      }
    }

    assertEquals(
      "Command with name 'foo' already exists.",
      exception.message,
      "Throws an IllegalStateException for duplicate command named 'foo."
    )
  }

  @Test
  fun `test insert command with children`() {
    val campfireCommands = DummyCampfireCommands()

    campfireCommands.register("foo") {
      command("bar") {

      }
    }

    val command = campfireCommands.commandMap.getCommand("foo")

    assertNotNull(
      command,
      "Failed to find root command 'foo'."
    )

    assertNotNull(
      command.children["bar"],
      "Failed to find child command 'bar'."
    )
  }

  @Test
  fun `test execute root command`() {
    val campfireCommands = DummyCampfireCommands()
    var executed = false
    campfireCommands.register("foo") {
      execute {
        executed = true
      }
    }

    campfireCommands.dispatch(Unit, "foo")

    assertTrue(executed, "Failed to execute root level command 'foo'.")
  }

  @Test
  fun `test execute child command`() {
    val campfireCommands = DummyCampfireCommands()
    var executed = false
    campfireCommands.register("foo") {
      command("bar") {
        execute {
          executed = true
        }
      }
    }

    campfireCommands.dispatch(Unit, "foo bar")

    assertTrue(
      executed,
      "Failed to execute child command 'foo bar'."
    )
  }

  @Test
  fun `test execute root with argument`() {
    val campfireCommands = DummyCampfireCommands()
    var number = -1
    campfireCommands.register("foo") {
      val numberParsed by integer("number")
      execute {
        number = numberParsed
      }
    }

    campfireCommands.dispatch(Unit, "foo 5")

    assertEquals(5, number, "Failed to parse argument.")
  }

  @Test
  fun `test default argument`() {
    val campfireCommands = DummyCampfireCommands()
    var number = -1
    campfireCommands.register("foo") {
      val numberParsed by integer("number") { 5 }
      execute {
        number = numberParsed
      }
    }

    campfireCommands.dispatch(Unit, "foo")
    assertEquals(5, number, "Failed to parse argument.")
  }

  @Test
  fun `test optional argument`() {
    val campfireCommands = DummyCampfireCommands()
    var number: Int? = -5
    campfireCommands.register("foo") {
      val numberParsed by optionalInteger("number")
      execute {
        number = numberParsed
      }
    }

    campfireCommands.dispatch(Unit, "foo")
    assertNull(number, "Shouldn't have value for optional argument.")
  }

  @Test
  fun `test invalid argument`() {
    val campfireCommands = DummyCampfireCommands()

    campfireCommands.register("foo") {
      integer("number")

      execute {
        // For this test there must be a body since there is a fail fast.
      }
    }

    val result = campfireCommands.dispatch(Unit, "foo bar")
    assertTrue(result is CampfireCommands.CommandExecutionResult.INVALID_SYNTAX)
  }

  @Test
  fun `test precondition`() {
    val campfireCommands = DummyCampfireCommands()

    var executed = false

    campfireCommands.register("foo") {
      condition { sender, input -> false }

      execute {
        // For this test there must be a body since there is a fail fast.
        executed = true
      }
    }

    val result = campfireCommands.dispatch(Unit, "foo")
    assertTrue(result is CampfireCommands.CommandExecutionResult.PRECONDITION_FAILED && !executed)
  }

  @Test
  fun `test execute child with argument`() {
    val campfireCommands = DummyCampfireCommands()
    var number = -1
    campfireCommands.register("foo") {
      command("bar") {
        val numberParsed by integer("number")
        execute {
          number = numberParsed
        }
      }
    }

    campfireCommands.dispatch(Unit, "foo bar 5")

    assertEquals(5, number, "Failed to parse argument.")
  }

  @Test
  fun `test execute many command`() {
    val campfireCommands = DummyCampfireCommands()

    var command1 = false
    var command2 = false
    var command3 = false

    campfireCommands.register("foo") {
      execute {
        command1 = true
      }
    }

    campfireCommands.register("bar") {
      command("bar") {
        command2 = true
      }
    }

    campfireCommands.register("foobaz") {
      command("foo") {
        execute { }
      }

      command("bar") {
        command("foo") {
          command3 = true
        }
      }
    }

    campfireCommands.dispatch(Unit, "foo")
    campfireCommands.dispatch(Unit, "bar bar")
    campfireCommands.dispatch(Unit, "foobaz bar foo")

    assertTrue(
      command1 && command2 && command3,
      "Failed to execute many complex command."
    )
  }

  @Test
  fun `test suggestions root`() {
    val campfireCommands = DummyCampfireCommands()

    campfireCommands.register("foo") {
      execute {
        // NO-OP
      }
    }

    assertEquals(
      2,
      campfireCommands.suggestions(Unit, "").size,
      "Failed to suggest root level commands"
    )
  }

  @Test
  fun `test suggestions child`() {
    val campfireCommands = DummyCampfireCommands()

    campfireCommands.register("foo") {
      command("bar") {
        execute { }
      }

      command("foo") {
        execute { }
      }
    }

    val suggestions = campfireCommands.suggestions(Unit, "foo")

    assertContains(suggestions, "bar")
    assertContains(suggestions, "foo")
  }

  @Test
  fun `test suggestions argument`() {
    val campfireCommands = DummyCampfireCommands()

    campfireCommands.register("foo") {
      integer("money")
    }

    assertEquals(
      3,
      campfireCommands.suggestions(Unit, "foo").size,
      "Failed to suggest command argument"
    )
  }

  @Test
  fun `test suggestions argument with space`() {
    val campfireCommands = DummyCampfireCommands()

    campfireCommands.register("foo") {
      integer("money")
    }

    assertEquals(
      3,
      campfireCommands.suggestions(Unit, "foo ").size,
      "Failed to suggest command argument"
    )
  }

  @Test
  fun `test suggest argument out of range`() {
    val campfireCommands = DummyCampfireCommands()

    campfireCommands.register("foo") {
      integer("money")
    }

    assertEquals(
      0,
      campfireCommands.suggestions(Unit, "foo 12").size,
      "Failed suggested arguments out of range."
    )
  }
}
