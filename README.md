Proton Server
==============

RESTful HTTP server ready template with batteries included :battery: :computer:

Build
--------

```bash
$ sbt
> scalafix
> scalafmt
> compile
> run
```

or

```bash
sbt run
```

Deployment
----------

```bash
sbt universal:packageBin
```

Generated build available in:

```bash
target/universal/<package>
```
