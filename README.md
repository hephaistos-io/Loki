# Loki

Old repository: https://github.com/RicardoMonteiroSimoes/TransferGrid

### This is on GitHub Packages now!

As off now I will start using GitHub Packages to distribute this library as a Maven Dependency. Want the newest Version? Here you go:

```xml
<dependency>
  <groupId>ch.hephaistos.utilities</groupId>
  <artifactId>loki</artifactId>
  <version>1.9.9</version>
</dependency>
```

Loki is a powerful, simple to use library that uses annotations to help you edit them in a GUI.

The intention of this Library is to remove a lot of work that it usually takes to create a GUI Element, in which you can edit values of an object.

## Table of content

- [Releases](#Releases)
- [Current Goals](#Goals)
- [HowTo](#HowTo)



## Releases

You can find all releases over [here](https://github.com/hephaistos-io/Loki/packages)

You can use this library with maven and the following dependency:

```xml
<dependency>
  <groupId>ch.hephaistos.utilities</groupId>
  <artifactId>loki</artifactId>
  <version>1.9.9</version>
</dependency>
```

Work is usually on hold for this library, unless there are breaking bugs or lack of an obvious feature.

## Goals

Current released version is stable and there are no features in the pipelines.
If you have any suggestions/bugs, feel free to share them over [here](https://github.com/HephaistosCorp/Loki/issues/new)!



## HowTo

There is a folder containing an example project where all possible features are implemented. You can check it out over [here](https://github.com/HephaistosCorp/Loki/tree/master/test/Example). You will find every use case for every feature inside of those classes.

For more information as well as the documentation, follow [this](https://hephaistos-io.github.io/Loki/javaDoc/index.html) link.

## Usage Examples

As far as my knowledge goes, this library is being used in a project from user [triggerbiggo](https://github.com/tiggerbiggo).
He uses it in [Prima](https://github.com/tiggerbiggo/Prima) to allow the user to change values of the nodes in the image generation:
![Prima screenshot](https://media.discordapp.net/attachments/401528956702162956/544517318663995403/unknown.png)


