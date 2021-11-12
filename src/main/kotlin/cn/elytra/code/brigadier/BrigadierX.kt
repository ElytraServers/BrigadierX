@file:Suppress("unused")
@file:JvmName("BrigadierX")

package cn.elytra.code.brigadier

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.*
import com.mojang.brigadier.context.CommandContext
import java.util.function.Consumer

/**
 * LiteralArgumentBuilder 构造方法语法糖
 */
fun <S> newLiteralArgBuilder(name: String): LiteralArgumentBuilder<S> = LiteralArgumentBuilder.literal(name)

/**
 * LiteralArgumentBuilder 构造方法语法糖
 */
fun <S> newLiteralArgBuilder(name: String, func: LiteralArgumentBuilder<S>.() -> Unit): LiteralArgumentBuilder<S> =
	LiteralArgumentBuilder.literal<S>(name).apply { func.invoke(this) }

/**
 * 注册指令
 *
 * @param name 根命令名称
 * @param func 传入根命令，进行命令注册
 * @return Dispatcher
 */
fun <S> CommandDispatcher<S>.register(name: String, func: LiteralArgumentBuilder<S>.() -> Unit): CommandDispatcher<S> {
	val rootCommand = newLiteralArgBuilder<S>(name).apply(func)
	this.register(rootCommand)
	return this
}

fun <S, T : ArgumentBuilder<S, T>> ArgumentBuilder<S, T>.literal(
	name: String,
	func: LiteralArgumentBuilder<S>.() -> Unit = {}
): LiteralArgumentBuilder<S> {
	val subcommand = LiteralArgumentBuilder.literal<S>(name).apply(func)
	this.then(subcommand)
	return subcommand
}

fun <S, T1 : ArgumentBuilder<S, T1>, T2> ArgumentBuilder<S, T1>.argument(
	name: String,
	type: ArgumentType<T2>,
	func: RequiredArgumentBuilder<S, T2>.() -> Unit = {}
): RequiredArgumentBuilder<S, T2> {
	val rab = RequiredArgumentBuilder.argument<S, T2>(name, type).apply(func)
	this.then(rab)
	return rab
}

fun <S, T : ArgumentBuilder<S, T>> ArgumentBuilder<S, T>.executesX(func: (CommandContext<S>) -> Any?) {
	this.executes {
		func.invoke(it)
		Command.SINGLE_SUCCESS
	}
}

// Java Support

fun <S> CommandDispatcher<S>.register(name: String, func: Consumer<LiteralArgumentBuilder<S>>) =
	register(name) { func.accept(this) }

fun <S, T : ArgumentBuilder<S, T>> ArgumentBuilder<S, T>.literal(
	name: String,
	func: Consumer<LiteralArgumentBuilder<S>>
) = literal(name) { func.accept(this) }

fun <S, T1 : ArgumentBuilder<S, T1>, T2> ArgumentBuilder<S, T1>.argument(
	name: String,
	type: ArgumentType<T2>,
	func: Consumer<RequiredArgumentBuilder<S, T2>>
) = argument(name, type) { func.accept(this) }

fun <S, T : ArgumentBuilder<S, T>> ArgumentBuilder<S, T>.executesX(func: Consumer<CommandContext<S>>) =
	executesX { func.accept(it) }