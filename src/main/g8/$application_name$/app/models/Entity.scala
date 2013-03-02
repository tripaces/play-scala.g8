package main.g8.$application_name$.app.models

import play.api.db.DB

import play.api.Play.current

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.session.Database
import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.sql.Timestamp
import org.joda.time.DateTime

// Use the implicit threadLocalSession

import scala.slick.session.Database.threadLocalSession

case class Entity(id: Option[Long], name: String, description: Option[String])

// define tables
object Entitys extends Table[Entity]("entitys") {

  def id = column[Long]("id_entity", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def description = column[String]("description")

  def * = id.? ~ name ~ description.? <>(Entity, Entity.unapply _)

  def autoInc = id.? ~ name ~ description.? <>(Entity, Entity.unapply _) returning id

  def stories = StoryEntitys.filter(_.idEntity === id).flatMap(_.story)

  implicit val dateTime: TypeMapper[DateTime]
  = MappedTypeMapper.base[DateTime, Timestamp](dt => new
      Timestamp(dt.getMillis), ts => new DateTime(ts.getTime))

  val byId = createFinderBy(_.id)
  val byName = createFinderBy(_.name)

  lazy val database = Database.forDataSource(DB.getDataSource())

  lazy val pageSize = 10

  def findAll: Seq[Entity] = {
    database.withSession {
      (for (c <- Entitys.sortBy(_.name)) yield c).list
    }
  }

  def count: Int = {
    database.withSession {
      (for (c <- Entitys) yield c.id).list.size
    }
  }

  def findPage(page: Int = 0, orderField: Int): Page[Entity] = {

    val offset = pageSize * page
    database.withSession {
      val entitys = (
        for {c <- Entitys
          .sortBy(entity => orderField match {
          case 1 => entity.name.asc
          case -1 => entity.name.desc
        })
          .drop(offset)
          .take(pageSize)
        } yield c).list

      val totalRows = count
      Page(entitys, page, offset, totalRows)
    }
  }

  def findById(id: Long): Option[Entity] = database withSession {
    Entitys.byId(id).firstOption
  }

  def insert(entity: Entity): Long = database withSession {
    //val c = Entity(None, entity.name, entity.description, new DateTime())
    Entitys.autoInc.insert(entity)
  }

  def update(id:Long,entity: Entity) = database withSession {
    val entity2update = entity.copy(Some(id))
    Entitys.where(_.id === id).update(entity2update)
  }

  def delete(entityId: Long) = database withSession {
    Entitys.where(_.id === entityId).delete
  }


  //JSON
  implicit val entityFormat = Json.format[Entity]

}

