# Ludum Dare 33 - GH4ST

A puzzle game for Ludum Dare 33.  The theme is "you are the monster".

Control the four ghosts.  They use the actual AI from the original Pac-Man
arcade.  Pac-Man is given a newly derived AI, since you'll be controlling the
ghosts.

How can you control the ghosts if they already have AI?  Well, your only power
is to choose which ghost is allowed to move per turn.  Learn and coordinate the
deterministic behaviors of your ghosts to catch Pac-Man before he gets his
fruit.

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

