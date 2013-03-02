package main.g8.$application_name$.app.controllers


object FragmentAssets extends Controller {

  val FRAGMENTS_PATH_PREFIX = List("public", "fragments")

  def at(file: String): Action[AnyContent] = {
    println("/" + FRAGMENTS_PATH_PREFIX.mkString("/")+file)
    Assets.at("/" + FRAGMENTS_PATH_PREFIX.mkString("/"), file)
  }
}