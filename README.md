<div align="center" style="text-align: center;">

# Create: Nerfed Drills

</div>

## Introduction:
Nerfs Mechanical Drills on Contraptions in various ways so mining doesn't become trivial with [Create](https://modrinth.com/mod/create) installed.  
  
Note: By default, this mod will use Create defaults and not do anything until configured;  
**To set custom multipliers**:
- After loading into a Singleplayer world, use the "Mods" option in the pause menu, then the "Config" button after selecting "Create: Nerfed Drills" to configure the mod.
- After starting your Multiplayer server, **edit the file `config/create_nerfed_drills-server.toml`**  
  
You can set global breaking speed and drop chance multipliers, specify whether to only affect Drills on contraptions, or also any other breaking block like Saws, and even define per-dimension overrides, which could be used to undo the applied nerf (or go the other way and apply a bonus) in specific mining dimensions and give different dimensions different multiplier values.  
  
The mod applies a few mixins to Create's `BlockBreakingMovementBehaviour` to modify the mining speed and drop chances, so any addon mod using the same class will also be affected by the configured multipliers.

<br>

## Installation:
You can visit the [releases page](https://github.com/the-drunken-cod/Create-Nerfed-Drills/releases), the [Modrinth page](https://modrinth.com/mod/create-nerfed-drills), or the [CurseForge page](https://www.curseforge.com/minecraft/mc-mods/create-nerfed-drills) to download the latest version of the mod.  
Then simply place the downloaded JAR file into your Minecraft `mods` folder and launch the game with NeoForge.  

> [!NOTE]  
> You will need to have the [NeoForge](https://neoforge.dev/) mod loader and the [Create mod](https://modrinth.com/mod/create) installed to run the mod.  
> An easy way of doing this is by creating a modded game instance in a launcher like [ATLauncher](https://atlauncher.com/) or [MultiMC.](https://multimc.org/)

<br>

## Modpack Policy:
You are free to use Create: Nerfed Drills in any modpacks; public or private :)  
Just make sure you abide by [our license](#license), common sense, and [the Minecraft EULA.](https://minecraft.net/en-us/eula)  
We would also appreciate a mention in the credits section of your modpack and a quick shout on our [discussion board](https://github.com/the-drunken-cod/Create-Nerfed-Drills/discussions) about your modpack (so we can check it out and play it ourselves!)  
Please also consider [reporting any issues or suggestions](https://github.com/the-drunken-cod/Create-Nerfed-Drills/issues), so we can improve the mod for you and other players and have better compatibility with other mods.

<br>

## Attribution:
- Created from the template [jaredlll08/MultiLoader-Template.](https://github.com/jaredlll08/MultiLoader-Template)  
  Changes:
  - Added build and release GitHub Actions workflows.
  - Added VS Code settings and debug launch configurations.
  - Added base config services for Neo and Fabric.
  - Added a base registry helper for Neo and Fabric.
  - Added base datagen services for Neo and Fabric.

<br>

## License:
This project is licensed under the LGPL-3.0-or-later License.  
See the [`LICENSE.txt` file](https://github.com/the-drunken-cod/blob/develop/LICENSE.txt) for details.

<br>

## Disclaimers:
- NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.  
- THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES PROVIDE THE PROGRAM “AS IS” WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE PROGRAM IS WITH YOU. SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL NECESSARY SERVICING, REPAIR OR CORRECTION.
- IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN WRITING WILL ANY COPYRIGHT HOLDER, OR ANY OTHER PARTY WHO MODIFIES AND/OR CONVEYS THE PROGRAM AS PERMITTED ABOVE, BE LIABLE TO YOU FOR DAMAGES, INCLUDING ANY GENERAL, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OR INABILITY TO USE THE PROGRAM (INCLUDING BUT NOT LIMITED TO LOSS OF DATA OR DATA BEING RENDERED INACCURATE OR LOSSES SUSTAINED BY YOU OR THIRD PARTIES OR A FAILURE OF THE PROGRAM TO OPERATE WITH ANY OTHER PROGRAMS), EVEN IF SUCH HOLDER OR OTHER PARTY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
