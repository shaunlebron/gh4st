# GH4ST

_post compo version of [ld33-gh4st](https://github.com/shaunlebron/ld33-gh4st)_

![screen](http://i.imgur.com/pNLEb3e.png)

__[PLAY HERE](http://shaunlebron.github.io/gh4st)__

Coordinate the four ghosts, which adhere to the actual AI from the original
Pac-Man arcade. Your only power to catch Pac-Man is to choose which ghost is
allowed to move per turn.

In this game, you must learn to exploit the deterministic behaviors of your
ghosts and Pac-Man in order to catch Pac-Man before he gets his fruit.

## Setup

- Install [leiningen](http://leiningen.org/)
- Run `lein figwheel`
- Open <http://localhost:3449>

If you want to update the CSS styling, install Ruby and run this in a separate
terminal window:

```
gem install sass
cd resources/public/css
sass --watch style.scss:style.css
```

Sprites are animated using a spritesheet, indexed and animated with generated
CSS:

```
lein garden auto
```
