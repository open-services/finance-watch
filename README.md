# finance-watch

Gathers metrics about finances and exposes it as prometheus metrics


## Usage

Run the application with `lein run` or compile to a jar with `lein uberjar` and then run:

```
$ java -jar target/uberjar/finance-watch-0.1.0-SNAPSHOT-standalone.jar
```

## Options

You can set the `address` and `port` by setting the env vars `ADDRESS` and `PORT`.

## Examples


```
$ java -jar target/uberjar/finance-watch-0.1.0-SNAPSHOT-standalone.jar
Now watching collective open-registry
Starting server at 127.0.0.1:8080
```

Now you can make a GET request to `127.0.0.1:8080/metrics` to get the metrics.

```
$ curl localhost:8080/metrics
# HELP open_registry_contributors_count a gauge metric.
# TYPE open_registry_contributors_count gauge
open_registry_contributors_count 0.0
# HELP open_registry_backers_count a gauge metric.
# TYPE open_registry_backers_count gauge
open_registry_backers_count 6.0
# HELP open_registry_yearly_income a gauge metric.
# TYPE open_registry_yearly_income gauge
open_registry_yearly_income 43583.0
# HELP open_registry_balance a gauge metric.
# TYPE open_registry_balance gauge
open_registry_balance 21322.0
```

## License

Copyright 2019 Open-Registry

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
