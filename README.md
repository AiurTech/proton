Proton Server
==============

Build
--------

```bash
$ sbt
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
