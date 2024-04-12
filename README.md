# GameObject.mod

PaperMC plugin (for 1.20.4) for implementing "Game Objects", i. e. tokens that serve a specific purpose in an in-game game/event and therefore need/profit  from special formatting or protection.

## Features

This plugin supports the following features:

### Locking of Item Stacks

Item Stacks (i. e. slots in your inventory) can be "locked" and "unlocked" with a secret key.

Locking an item adds an NBT tag, so that other items (that do not have this NBT tag set) cannot stack with the locked items. This prevents stacking of items even if they have the same item type and the same other properties. This allows, for example, setting up an redstone item filter only selecting items locked with a specific secret key. 

The purpose of such locking is to prevent other players from forging game objects, unless they have been specifically authorized to do so by being given the secret key.

You can **lock** the items of the item stack you are currently holding in your main hand ("currently held item stack") using the following command, where `<key>` is replaced with your secret key:

```
/go-lock <key>
```

You can **remove the lock** of the currently held item stack, if it is locked, using the following command, where `<key>` is again replaced with your secret key:

```
/go-lock <key>
```

### Item Name Formatting

You can add/clear formatting for the displayed item name of the currently held item stack.

This will work just fine if you have previously renamed the item in an anvil. If you have not renamed the item in an anvil before, the plugin cannot know the correct item name for technical reasons and will attempt to "guess" it from the item id. In most cases, this produces a good-enough result to not be a problem, in all other cases, the item must be renamed after executing this command.

The following command will **change the item name formatting** of the currently held item stack:

```
/go-format <option> <option> ...
```

You can provide an unlimited amount of formatting options separated by a space. They are applied in the order in which they are entered into the command.

Formatting options are:

1. `clear` which will remove all previously applied formatting

2. `bold` and `italic` which will style the name bold or italic, respectively

3. the following colors:

    - `yellow`,
    - `red`,
    - `pink`,
    - `purple`,
    - `blue`,
    - `aqua`,
    - `green`,
    - `darkgreen`,
    - `white`,
    - `gray` or `grey`,
    - `black`

4. `magic` for a special "magic"-like hidden effect

Executing this command does not clear previous formatting by default (unless `clear` is passed), so that passing two options is equivalent to executing the command with the first option and then executing it again with the second option.

### Item Lore

You can add or update lore (=explanation) to an item, which will be displayed when hovering the item and may contain information about its origin, use or functionality.

To **set the lore** for the currently held item stack, use the following command:

```
/go-lore <text>
```

To add a line break in the text, use two forward slashes (`//`).


### Custom Model Data

It is also possible to set a (numeric) custom model data, which can be reacted to in a template. This allows overriding the default texture for an item limited to certain instances of it.

Set the **custom model data** for the currently held item stack with the following command:

```
/go-custommodel <id>
```

Please note that the ID has to be a positive integral number.

### Save/Apply

In order to ease batch operations, you can save the aforementioned game object details of an item stack and apply them to other item stacks.

Each saved set of data will have a name under which it can be accessed later and it will be saved only for your user profile, i. e. other people cannot access it.

To **save** the game object data of the currently held item stack, use the following command:

```
/go-save <name> [<key>]
```

You must provide a name, which may only contain lowercase letters (`a`-`z`), numbers (`0`-`9`), underscores (`_`) and points (`.`).

If the currently held item stack has been locked, you must provide the secret key, to prevent circumvention of the forgery protection.

To **apply** saved game object data to the currently held item stack, use the following command:

```
/go-apply <name>
```

Please note, that this command will fail if the name of the currently held item is not equal to the name saved with the game object data. This is to separate this functionality from the vanilla behavior of an anvil (which costs levels).


## Installation

To install this plugin, just copy the `GameObjectMod-<version>.jar` to the `plugins/` folder of your PaperMC installation.

Then restart or reload the server.

## Attribution and License

Developed with <3 by luap42.

This project is licensed under the terms of the AGPL version 3 license. See LICENSE.txt for more details.