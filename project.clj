(defproject reagent-test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj" "src/cljs"]

  :dependencies [[cljsjs/react "0.13.1-0"]
                 [compojure "1.3.3"]
                 [environ "1.0.0"]
                 [jayq "2.5.4"]
                 [joplin.jdbc "0.2.12"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3169" :scope "provided"]
                 [org.clojure/test.check "0.7.0"]
                 [org.postgresql/postgresql "9.4-1200-jdbc41"]
                 [prone "0.8.1"]
                 [reagent "0.5.0"]
                 [reagent-forms "0.4.9"]
                 [reagent-utils "0.1.4"]
                 [ring "1.3.2"]
                 [ring-server "0.4.0"]
                 [ring/ring-defaults "0.1.4"]
                 [secretary "1.2.3"]
                 [selmer "0.8.2"]
                 [yesql "0.4.0"]]

  :plugins [[lein-asset-minifier "0.2.2"]
            [lein-cljsbuild "1.0.4"]
            [lein-environ "1.0.0"]
            [lein-less "1.7.2"]
            [lein-ring "0.9.1"]]

  :ring {:handler reagent-test.handler/app
         :uberwar-name "reagent-test.war"}

  :min-lein-version "2.5.0"

  :uberjar-name "reagent-test.jar"

  :main reagent-test.server

  :clean-targets ^{:protect false} ["resources/public/js"]

  :minify-assets {:assets {"resources/public/css/site.min.css" "resources/public/css/site.css"}}

  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]
                             :compiler {:output-to    "resources/public/js/app.js"
                                        :output-dir   "resources/public/js/out"
                                        :asset-path   "js/out"
                                        :optimizations :none
                                        :pretty-print  true}}}}
  :less {:source-paths ["src/less"]
         :target-path "resources/public/css"}

  :profiles {:dev {:repl-options {:init-ns reagent-test.repl
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

                   :dependencies [[ring-mock "0.1.5"]
                                  [ring/ring-devel "1.3.2"]
                                  [leiningen "2.5.1"]
                                  [figwheel "0.2.5"]
                                  [weasel "0.6.0"]
                                  [com.cemerick/piggieback "0.2.0"]
                                  [org.clojure/tools.nrepl "0.2.10"]
                                  [pjstadig/humane-test-output "0.7.0"]]

                   :source-paths ["env/dev/clj"]
                   :plugins [[lein-figwheel "0.2.5"]]

                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]

                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :css-dirs ["resources/public/css"]
                              :ring-handler reagent-test.handler/app}

                   :env {:dev? true}

                   :cljsbuild {:builds {:app {:source-paths ["env/dev/cljs"]
                                              :compiler {:main "reagent-test.dev"
                                                         :source-map true}}}}}

             :uberjar {:hooks [leiningen.cljsbuild minify-assets.plugin/hooks]
                       :env {:production true}
                       :aot :all
                       :omit-source true
                       :cljsbuild {:jar true
                                   :builds {:app
                                            {:source-paths ["env/prod/cljs"]
                                             :compiler
                                             {:optimizations :advanced
                                              :pretty-print false}}}}}})
