package pe.chalk.bukkit

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin(), Listener {
    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player && command.name == "nickname") {
            config.set("nickname.${sender.name}", args.joinToString(" "))
            saveConfig()

            applyNickname(sender)
            sender.sendMessage("닉네임이 변경되었습니다: ${getNickname(sender)}")
            return true
        }

        return false
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        val name = player.name
        val nickname = getNickname(player)

        applyNickname(player)

        val joinMessage = event.joinMessage
        if (joinMessage != null) {
            event.joinMessage = joinMessage.replace(name, nickname)
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player

        val name = player.name
        val nickname = getNickname(player)

        val quitMessage = event.quitMessage
        if (quitMessage != null) {
            event.quitMessage = quitMessage.replace(name, nickname)
        }
    }

    private fun getNickname(player: Player): String {
        val originalName = player.name
        val name = config.get("nickname.${originalName}")

        if (name is String) {
            return name
        }
        return originalName
    }

    private fun applyNickname(player: Player) {
        val nickname = this.getNickname(player)

        player.setDisplayName(nickname)
        player.setPlayerListName(nickname)
    }
}