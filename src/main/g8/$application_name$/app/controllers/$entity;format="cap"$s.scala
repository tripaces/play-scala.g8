package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.$entity;format="cap"$
import models.$entity;format="cap"$s._
import com.yammer.metrics.Metrics
import com.yammer.metrics.scala.Timer
import play.api.libs.json.Json

object $entity;format="cap"$s extends Controller {

  val metric = Metrics.defaultRegistry().newTimer(classOf[$entity;format="cap"$], "Page")
  val timer = new Timer(metric)

  /**
   * Describe the $entity;format="lower"$ form (used in both edit and create screens).
   */
  val $entity;format="lower"$Form = Form(
    mapping(
      "id" -> optional(longNumber),
      "name" -> nonEmptyText,
      "description" -> optional(text)
    )
      ($entity;format="cap"$.apply)($entity;format="cap"$.unapply)
  )

  val Home = Redirect(routes.$entity;format="cap"$s.list(0, 0))

  // -- Actions
  def index = Action {
    Home
  }

  def list(page: Int, orderBy: Int) = Action {
    implicit request =>
      val $entity;format="lower"$s = timer.time(models.$entity;format="cap"$s.findPage(page, orderBy))
      val html = views.html.$entity;format="lower"$s.list("List $entity;format="lower"$s", $entity;format="lower"$s, orderBy)
      Ok(html)
  }

  def view(id: Long) = Action {
    implicit request =>

      models.$entity;format="cap"$s.findById(id).map {
        $entity;format="lower"$ => {
          val html = views.html.$entity;format="lower"$s.view("View $entity;format="cap"$", $entity;format="lower"$)
          Ok(html)
        }
      } getOrElse (NotFound)
  }

  def edit(id: Long) = Action {
    implicit request =>
      models.$entity;format="cap"$s.findById(id).map {
        $entity;format="lower"$ => {
          val html = views.html.$entity;format="lower"$s.edit("Edit $entity;format="cap"$", id, $entity;format="lower"$Form.fill($entity;format="lower"$))
          Ok(html)
        }
      } getOrElse (NotFound)
  }

  /**
   * Handle the 'edit form' submission
   *
   * @param id Id of the computer to edit
   */
  def update(id: Long) = Action {
    implicit request =>
      $entity;format="lower"$Form.bindFromRequest.fold(
        formWithErrors => BadRequest(views.html.$entity;format="lower"$s.edit("Edit $entity;format="cap"$ - errors", id, formWithErrors)),
        $entity;format="lower"$ => {
          models.$entity;format="cap"$s.update(id, $entity;format="lower"$)
          Redirect(routes.$entity;format="cap"$s.edit(id)).flashing("success" -> "$entity;format="cap"$ %s has been updated".format($entity;format="lower"$.name))
          //          Redirect(routes.$entity;format="cap"$s.view($entity;format="lower"$.id))
        }
      )
  }

  /**
   * Display the 'new computer form'.
   */
  def create = Action {
    implicit request =>
      Ok(views.html.$entity;format="lower"$s.create("New $entity;format="cap"$", $entity;format="lower"$Form))
  }

  /**
   * Handle the 'new computer form' submission.
   */
  def save = Action(parse.json) {
    implicit request =>
      val json = request.body
      //      println(json)
      val $entity;format="lower"$ = json.as[$entity;format="cap"$]
      val id = models.$entity;format="cap"$s.insert($entity;format="lower"$)
      Ok(Json.toJson(id))
    //        Home.flashing("success" -> "$entity;format="cap"$ %s has been created".format($entity;format="lower"$.name))
  }

  /**
   * Handle computer deletion.
   */
  def delete(id: Long) = Action {
    implicit request =>
      models.$entity;format="cap"$s.delete(id)
      Home.flashing("success" -> "$entity;format="cap"$ has been deleted")
  }

}