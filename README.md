# GH4ST

 <table><tr>
<td>__[PLAY HERE]__</td>
<td>[Blog Post]</td>
<td>[48 hour version]</td>
</tr></table>

[PLAY HERE]:http://shaunlebron.github.io/gh4st
[Blog Post]:http://ludumdare.com/compo/2015/08/30/pac-man-ghost-a-i-puzzle/
[48 hour version]:https://github.com/shaunlebron/ld33-gh4st

![screen](http://i.imgur.com/pNLEb3e.png)

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
