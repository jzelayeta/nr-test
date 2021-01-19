// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/julianzelayeta/Downloads/play-samples-play-scala-starter-example/conf/routes
// @DATE:Tue Jan 19 00:10:01 ART 2021


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
