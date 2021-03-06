(defproject v01 "0.1.0-SNAPSHOT"

  :description "The executable digital music album"

  :url "https://github.com/violetband/v01"

  :license {:name "MIT License"
            :url  "https://github.com/violetband/v01/blob/master/LICENSE"}

  :min-lein-version "2.7.0"

  :dependencies [[org.clojure/clojure "1.9.0-alpha13"]
                 [org.clojure/clojurescript "1.9.229"]
                 [org.clojure/core.async "0.2.391" :exclusions [org.clojure/tools.reader]]
                 [io.replikativ/replikativ "0.2.0-beta2"]
                 [kunstmusik/pink "0.3.0"]
                 [kunstmusik/score "0.3.0"]
                 [org.immutant/web "2.1.5"]
                 [compojure "1.6.0-beta1"]
                 [com.taoensso/sente "1.11.0-alpha5"]
                 [carbon "0.2.0-SNAPSHOT"]]

  :plugins [[lein-ancient "0.6.10"]
            [lein-figwheel "0.5.7"]
            [lein-cljsbuild "1.1.4" :exclusions [org.clojure/clojure]]]

  :source-paths ["src"]

  :aliases {"up" ["ancient" "upgrade" ":all" ":allow-qualified" ":check-clojure"]}

  :main v01.core

  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[binaryage/devtools "0.8.2"]
                                  [figwheel-sidecar "0.5.7"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [ring/ring-devel "1.6.0-beta6"]]
                   ;; need to add dev source path here to get user.clj loaded
                   :source-paths ["src" "dev"]
                   ;; for CIDER
                   ;; :plugins [[cider/cider-nrepl "0.12.0"]]
                   :repl-options {; for nREPL dev you really need to limit output
                                  :init (set! *print-length* 50)
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds
              [{:id           "dev"
                :source-paths ["src"]

                ;; the presence of a :figwheel configuration here
                ;; will cause figwheel to inject the figwheel client
                ;; into your build
                :figwheel     {;:on-jsload "v01.core/on-js-reload"
                               ;; :open-urls will pop open your application
                               ;; in the default browser once Figwheel has
                               ;; started and complied your application.
                               ;; Comment this out once it no longer serves you.
                               ;:open-urls ["http://localhost:3449/index.html"]
                               }

                :compiler     {:main                 v01.core
                               :asset-path           "js/compiled/out"
                               :output-to            "resources/public/js/compiled/v01.js"
                               :output-dir           "resources/public/js/compiled/out"
                               :source-map-timestamp true
                               ;; To console.log CLJS data-structures make sure you enable devtools in Chrome
                               ;; https://github.com/binaryage/cljs-devtools
                               :preloads             [devtools.preload]}}
               ;; This next build is an compressed minified build for
               ;; production. You can build this with:
               ;; lein cljsbuild once min
               {:id           "min"
                :source-paths ["src"]
                :compiler     {:output-to     "resources/public/js/compiled/v01.js"
                               :main          v01.core
                               :optimizations :advanced
                               :pretty-print  false}}]}

  :figwheel {;; :http-server-root "public" ;; default and assumes "resources"
             ;; :server-port 3449 ;; default
             ;; :server-ip "127.0.0.1"

             :css-dirs ["resources/public/css"]             ;; watch and update CSS

             ;; Start an nREPL server into the running figwheel process
             ;; :nrepl-port 7888

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is for simple ring servers, if this

             ;; doesn't work for you just run your own server :) (see lein-ring)

             ;; :ring-handler hello_world.server/handler

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "myfile-opener"

             ;; if you are using emacsclient you can just use
             ;; :open-file-command "emacsclient"

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"
             })
