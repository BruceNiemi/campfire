package io.shaded.campfire.homes.command

import com.google.inject.Inject
import io.shaded.campfire.homes.service.HomeService
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetHomeCommand @Inject constructor(private val homeService: HomeService) :
  CommandExecutor {

  override fun onCommand(
    sender: CommandSender,
    command: Command,
    label: String,
    args: Array<String>
  ): Boolean {
    if (sender !is Player) return false

    if (args.isEmpty()) {
      sender.sendMessage("Command usage: /sethome <name>")
      return true
    }

    val homeName = args[0]

    if (homeName.isBlank()) {
      sender.sendMessage("Oops, your home name can't be blank!")
      return true
    }

    when (val result = this.homeService.createHome(sender, homeName, sender.location)) {
      HomeService.CreateHomeResult.Created -> sender.sendMessage(
        "You have created a new home, $homeName!"
      )
      HomeService.CreateHomeResult.DuplicateName -> sender.sendMessage(
        "Oops, there's already a home with the name '$homeName'!"
      )
      HomeService.CreateHomeResult.WorldNotAllowed -> sender.sendMessage(
        "Oops, it seems you're not allowed to create a home here!"
      )
      is HomeService.CreateHomeResult.MaximumHomes -> sender.sendMessage(
        "Oops, you've already reached the maximum number of homes (${result.current}) allowed!"
      )
      HomeService.CreateHomeResult.Error -> sender.sendMessage(
        "Oops, there was a hiccup while creating your home. Please try again later!"
      )
    }

    return true
  }

}
