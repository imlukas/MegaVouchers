# <p align="center">Mega Vouchers</p>

<div align="center">

![GitHub Repo stars](https://img.shields.io/github/stars/imlukas/MegaVouchers?style=for-the-badge) 
![GitHub watchers](https://img.shields.io/github/watchers/imlukas/MegaVouchers?style=for-the-badge) 
![GitHub issues](https://img.shields.io/github/issues/imlukas/MegaVouchers?style=for-the-badge)

</div>
<h1>WIP - This project is not finished.</h1>
  
## Features - some planned, other implemented.
  
* Create custom vouchers with a wide range of elements to choose from such as
  * Conditions -> Permission and Withdrawbles
  * Actions -> Messages, Sounds and Command (Chance, Random and Normal)
  * Rewards -> Money, HP, EXP, Item.
* Create virtual and physical vouchers for your players.
  * Physical vouchers -> Consist of an interactable item.
  * Virtual vouchers -> Consist of a voucher that is associated with the player but is not an item, they redeem it by running a command or through a gui. (PLANNED)
* More features are still being though of and ironed out :)


## Example Config - as of now

```yaml
voucher:
  # Check https://docs.advntr.dev/minimessage/format.html for how to format messages, item name etc.
  item: # The item to be displayed and/or given to the player
    type: PAPER # The type of the item
    name: "Example Voucher" # The name of the item
    lore: # The lore of the item
      - "This is an example voucher."
      - "Right click to redeem."
    glow: true # Whether the item should glow or not
    unbreakable: true # Whether the item should be unbreakable or not
    model-data: 1 # The model data of the item
    enchants:
      unbreaking: 1 # The enchantments of the item
  elements: # Check the wiki for all existing elements
    element-id: # This should be either the element id like [message, commnand], or a random identifier
      type: message # Assumes the type by this value.
      message: "You have redeemed the example voucher!"
    sound: # Assumes the type by the section's name -> sound
      sound1: # represents a sound
        sound: "ENTITY_PLAYER_LEVELUP" # Check https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html for all sounds
        volume: 1 # The volume of the sound
        pitch: 1 # The pitch of the sound
      sound2:
        sound: "ENTITY_EXPERIENCE_ORB_PICKUP"
        volume: 1
        pitch: 1
    list-permission:
      type: permission
      message: "<red>You do not have permissions to redeem this voucher." # Sends this message to the player
      permission: # The permission(s) to check for, in this case
        - "example.voucher.list"
    claim-permission:
      type: permission
      message: # Sends a custom message, check wiki/messages for all types and how to configure them.
        type: actionbar
        message: "<red>You have already redeemed this voucher."
      permission: "example.voucher"

    commands: # TODO: add random/chance commands.
      type: command
      commands: # The commands to be executed, they will all be executed in the order they are listed
        - "give %player% diamond 1"
        - "give %player% emerald 1"
    item:
      chance: 80 # The chance of the item being given to the player, in this case 80%
      drop: false # Whether the item should be dropped or not, even if the player's inventory is not full
      type: item
      item: # The item to be given to the player
        type: DIAMOND
        amount: 1
        name: "Example Item"
        lore:
          - "This is an example item."
        glow: true
        unbreakable: true
        model-data: 1
        enchants:
          unbreaking: 1
      item2:
        drop: true
        chance: 10 # The chance of the item being given to the player, in this case 10%
        type: EMERALD
        amount: 1
        name: "Example Item 2"
        lore:
          - "This is an example item."
        glow: true
        unbreakable: true
        model-data: 1
        enchants:
          unbreaking: 1
```
