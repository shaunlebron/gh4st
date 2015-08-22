# Ludum Dare 33 - GH4ST

A puzzle game for Ludum Dare 33.  The theme is "you are the monster".

Knowing the four ghost behaviors of the original Pac-Man arcade, configure
the initial state of the ghosts such that they reach Pac-Man before he
reaches his target.  Pre-arranged levels.

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

