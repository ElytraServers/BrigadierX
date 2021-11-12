import cn.elytra.code.brigadier.*
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.exceptions.CommandSyntaxException
import java.util.*

object TestBrigadierXKt {

	@JvmStatic
	fun main(args: Array<String>) {
		val dispatcher = CommandDispatcher<String>()
		register(dispatcher)
		while(true) {
			val s = Scanner(System.`in`)
			if(s.hasNextLine()) {
				val c = s.nextLine()
				if(c == "q") {
					return
				}
				try {
					dispatcher.execute(c, "Test Source")
				} catch(e: CommandSyntaxException) {
					println(e.message)
				} catch(ex: Exception) {
					ex.printStackTrace()
				}
			}
		}
	}

	private fun register(dispatcher: CommandDispatcher<String>) {
		dispatcher.register("test") {

			literal("sub") {

				literal("bray") {
					executesX {
						println(it.source)
					}
				}

				argument("level", IntegerArgumentType.integer(0, 255)) {
					executesX {
						println(IntegerArgumentType.getInteger(it, "level"))
					}
				}

				executesX {
					println(it.source)
				}
			}

			argument("trust", BoolArgumentType.bool()) {
				executesX {
					println(
						"What if I " + (if(BoolArgumentType.getBool(
								it,
								"trust"
							)
						) "trusted" else "not trusted") + " in him?"
					)
				}
			}

			executesX {
				println(it.source)
			}
		}
	}

}