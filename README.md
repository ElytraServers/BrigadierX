
# BrigadierX

BrigadierX is a library for Brigadier of Mojang. It adds a Kotlin-friendly extended functions.

## Installation

To install BrigadierX, you need have [Brigadier](https://github.com/Mojang/brigadier#installation) installed first.

### Gradle

1. First include Jitpack's repository:

```groovy
maven {
    url "https://jitpack.io"
}
```

2. And then use BrigadierX:

```groovy
implementation "com.github.taskeren:brigadier-x:<the-latest-version>"
```

### Maven

1. First include Jitpack's repository:

```xml

<repository>
    <id>jitpack</id>
    <url>https://jitpack.io</url>
</repository>
```

2. And then use BrigadierX:

```xml

<dependency>
    <groupId>com.github.taskeren</groupId>
    <artifactId>brigadier-x</artifactId>
    <version>(the latest version)</version>
</dependency>
```

## Usage

You are supposed to read [Brigadier's Usage](https://github.com/Mojang/brigadier#usage) first, if you are new using
Brigadier.

Few methods in ArgumentBuilder(LiteralArgumentBuilder, RequiredArgumentBuilder, etc.) are extended like following:

```kotlin
val dispatcher = CommandDispatcher<String>()

dispatcher.register("root") { // this: LiteralArgumentBuilder<String>

	// The executor without returning
	executesX { // it: CommandContext<String>
		println(it.source)
	}

	// Creating the subcommand(child node)
	literal("list") { // this: LiteralArgumentBuilder<String>

		executesX { // it: CommandContext<String>
			println("@Sub ${it.source}")
		}
	}

	literal("add") { // this: LiteralArgumentBuilder<String>

		executesX { // it: CommandContext<String>
			println("Usage: /root add <value>")
		}

		// import com.mojang.brigadier.arguments.StringArgumentType
		// Adding argument node
		argument("value", StringArgumentType.string()) {

			executesX { // it: CommandContext<String>
				val theValue = StringArgumentType.getString(it, "value")
				println("The value is $theValue")
			}
		}
	}
}
```