package models

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

case class $entity;format="word"$(id: Option[Long], name: String, description: Option[String])

// define tables
object $entity;format="word"$s extends Table[$entity;format="word"$]("$entity;format="lower"$s") {

  def id = column[Long]("id_$entity;format="lower"$", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def description = column[String]("description")

  def * = id.? ~ name ~ description.? <>($entity;format="word"$, $entity;format="word"$.unapply _)

  def autoInc = id.? ~ name ~ description.? <>($entity;format="word"$, $entity;format="word"$.unapply _) returning id

  implicit val dateTime: TypeMapper[DateTime]
  = MappedTypeMapper.base[DateTime, Timestamp](dt => new
      Timestamp(dt.getMillis), ts => new DateTime(ts.getTime))

  val byId = createFinderBy(_.id)
  val byName = createFinderBy(_.name)

  lazy val database = Database.forDataSource(DB.getDataSource())

  lazy val pageSize = 10

  def findAll: Seq[$entity;format="word"$] = {
    database.withSession {
      (for (c <- $entity;format="word"$s.sortBy(_.name)) yield c).list
    }
  }

  def count: Int = {
    database.withSession {
      (for (c <- $entity;format="word"$s) yield c.id).list.size
    }
  }

  def findPage(page: Int = 0, orderField: Int): Page[$entity;format="word"$] = {

    val offset = pageSize * page
    database.withSession {
      val $entity;format="lower"$s = (
        for {c <- $entity;format="word"$s
          .sortBy($entity;format="lower"$ => orderField match {
          case 1 => $entity;format="lower"$.name.asc
          case -1 => $entity;format="lower"$.name.desc
        })
          .drop(offset)
          .take(pageSize)
        } yield c).list

      val totalRows = count
      Page($entity;format="lower"$s, page, offset, totalRows)
    }
  }

  def findById(id: Long): Option[$entity;format="word"$] = database withSession {
    $entity;format="word"$s.byId(id).firstOption
  }

  def insert($entity;format="lower"$: $entity;format="word"$): Long = database withSession {
    //val c = $entity;format="word"$(None, $entity;format="lower"$.name, $entity;format="lower"$.description, new DateTime())
    $entity;format="word"$s.autoInc.insert($entity;format="lower"$)
  }

  def update(id:Long,$entity;format="lower"$: $entity;format="word"$) = database withSession {
    val $entity;format="lower"$2update = $entity;format="lower"$.copy(Some(id))
    $entity;format="word"$s.where(_.id === id).update($entity;format="lower"$2update)
  }

  def delete($entity;format="lower"$Id: Long) = database withSession {
    $entity;format="word"$s.where(_.id === $entity;format="lower"$Id).delete
  }


  //JSON
  implicit val $entity;format="lower"$Format = Json.format[$entity;format="word"$]

}

