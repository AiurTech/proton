// Latest SBT throws exceptions when cancelling a running process
// https://github.com/sbt/sbt/pull/4058
// https://github.com/sbt/sbt/issues/3252
// https://github.com/sbt/sbt/pull/4630/files
// https://github.com/sbt/sbt/issues/4822
// https://github.com/playframework/playframework/issues/9418
// https://github.com/playframework/playframework/pull/9420
Global / cancelable := false
