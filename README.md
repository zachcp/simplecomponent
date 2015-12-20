# simplecomponent

A simple compoenent to use Reagent/Re-frame with d3.js

see http://zachcp.github.io/simplecomponent/

## Overview

If you want to use reagent with D3.js you need to use lifecycle methods.
These can be very non-intuitive to use, somewhat defeating the otherwise
easy-to-use [Reagent](https://holmsand.github.io/reagent/).

This project follows the state-management pattern of [re-frame](https://github.com/Day8/re-frame).
Its worth reading the documentationon that project because

1. it will get you up and running
2. it has great sections for more advanced topics.

I also ended up using these following as references:

1. jszakmeister's [clojurescript, D3, and Reagent](http://www.szakmeister.net/blog/2015/nov/26/clojurescript-d3-and-reagent)
2. nils blum-oests [post on the same topic](http://nils-blum-oeste.net/clojurescripts-reagent-using-props-in-lifecycle-hooks/)
3. the discussion in [this thread](https://groups.google.com/forum/#!searchin/reagent-project/component-did-update/reagent-project/bDIiKdeDqj8/FdiaKRDJFcsJ)


## Setup

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL.

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
