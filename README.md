# ![logo](core/assets/textures/logo.png)

A multiplayer territory conquest game.

The server is over at [conquest-server](https://github.com/conquest/conquest-server).
The level designer is over at [conquest-ld](https://github.com/conquest/conquest-ld).

## How to Play

* The purpose of the game is to conquer the entire map with the starting tile and troops.
* Every tile has a set production. (Player owned tiles produce more than unowned tiles)
* Cities are spread around the map, that is, if the map loaded has cities anywhere.
* Cities produce significantly more than tiles without cities.
  * Gold-tier cities are better than silver-tier cities.
* Troops can be transferred from tile to tile.
  * Paths can only be created from what is player owned.
* Troops can attack foreign tiles.
* Every ten seconds, all tiles produce new troops.

## Controls

* WASD or the Arrow Keys to move across the screen accordingly.
* Scroll to zoom in or out
* Tile selection:
  * To select just one tile, click on it
  * To select multiple tiles, click and drag on a point to create a selection rectangle.
* After tiles are selected, just click on any visible target tile and the tile manager will pop up.
* Manager controls:
  * The left field is for partial troop transfers. This sends the requested number of troops, or the maximum possible if incapable.
    * `Q` is the macro defined.
    * The number field can be edited directly, or can be doubled/halved via the mini buttons on the side.
  * The right field is for maximum troop transfers. This sends all of the troops that the tiles selected own.
    * `E` and `Enter` are the macro defined.

## Specifications

* Tile base production is `2` troops every refresh.
* Every ten seconds, a:
  * Gold-tier city produces `5` extra troops.
  * Silver-tier city produces `2` extra troops.
  * Player owned tile produces `1` extra troop.
* To conquer a tile with `x` troops, it is required to have at least `x + 1` troops to seize control of it.
* The larger the troop, the more time is required for a troop to sucessfully travel.
  * The time required has a minimum of `1` second, and a maximum of `5` seconds.
