package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Entity
import models.Entitys._
import com.yammer.metrics.Metrics
import com.yammer.metrics.scala.Timer
import play.api.libs.json.Json

object Entitys extends Controller {

  val metric = Metrics.defaultRegistry().newTimer(classOf[Entity], "Page")
  val timer = new Timer(metric)

  /**
   * Describe the entity form (used in both edit and create screens).
   */
  val entityForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "name" -> nonEmptyText,
      "description" -> optional(text)
    )
      (Entity.apply)(Entity.unapply)
  )

  val Home = Redirect(routes.Entitys.list(0, 0))

  // -- Actions
  def index = Action {
    Home
  }

  def list(page: Int, orderBy: Int) = Action {
    implicit request =>
      val entitys = timer.time(models.Entitys.findPage(page, orderBy))
      val html = views.html.entitys.list("List entitys", entitys, orderBy)
      Ok(html)
  }

  def view(id: Long) = Action {
    implicit request =>

      models.Entitys.findById(id).map {
        entity => {
          val html = views.html.entitys.view("View Entity", entity)
          Ok(html)
        }
      } getOrElse (NotFound)
  }

  def edit(id: Long) = Action {
    implicit request =>
      models.Entitys.findById(id).map {
        entity => {
          val html = views.html.entitys.edit("Edit Entity", id, entityForm.fill(entity))
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
      entityForm.bindFromRequest.fold(
        formWithErrors => BadRequest(views.html.entitys.edit("Edit Entity - errors", id, formWithErrors)),
        entity => {
          models.Entitys.update(id, entity)
          Redirect(routes.Entitys.edit(id)).flashing("success" -> "Entity %s has been updated".format(entity.name))
          //          Redirect(routes.Entitys.view(entity.id))
        }
      )
  }

  /**
   * Display the 'new computer form'.
   */
  def create = Action {
    implicit request =>
      Ok(views.html.entitys.create("New Entity", entityForm))
  }

  /**
   * Handle the 'new computer form' submission.
   */
  def save = Action(parse.json) {
    implicit request =>
      val json = request.body
      //      println(json)
      val entity = json.as[Entity]
      val id = models.Entitys.insert(entity)
      Ok(Json.toJson(id))
    //        Home.flashing("success" -> "Entity %s has been created".format(entity.name))
  }

  /**
   * Handle computer deletion.
   */
  def delete(id: Long) = Action {
    implicit request =>
      models.Entitys.delete(id)
      Home.flashing("success" -> "Entity has been deleted")
  }

}