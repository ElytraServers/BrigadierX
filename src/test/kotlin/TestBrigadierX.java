import cn.elytra.code.brigadier.BrigadierX;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.util.Scanner;

public class TestBrigadierX {

	public static void register(CommandDispatcher<String> dispatcher) {

		BrigadierX.register(dispatcher, "test", root -> {
			BrigadierX.argument(root, "sub2", BoolArgumentType.bool(), sub2 -> {
				BrigadierX.executesX(sub2, ctx -> {
					System.out.println(BoolArgumentType.getBool(ctx, "sub2"));
				});
			});
			BrigadierX.argument(root, "sub", StringArgumentType.string(), sub -> {
				BrigadierX.executesX(sub, ctx -> {
					System.out.println(ctx.getSource());
				});
			});
		});

	}

	public static void main(String[] args) {
		var dispatcher = new CommandDispatcher<String>();
		register(dispatcher);
		while(true) {
			var s = new Scanner(System.in);
			if(s.hasNextLine()) {
				var c = s.nextLine();
				if(c.contentEquals("q")) {
					return;
				}

				try {
					dispatcher.execute(c, "Test Source");
				} catch(CommandSyntaxException e) {
					System.out.println(e.getMessage());
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}
