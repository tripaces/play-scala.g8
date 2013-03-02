package models

import play.api.Play.current

import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB

import java.sql.Timestamp
import org.joda.time.DateTime

import play.api.libs.json._
import play.api.libs.functional.syntax._


case class $entity;format="cap"$(id: Option[Long], name: String, description: Option[String])

// define tables
object $entity;format="cap"$s extends Table[$entity;format="cap"$]("$entity;format="lower"$s") {

  def id = column[Long]("id_$entity;format="lower"$", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def description = column[String]("description")

  def * = id.? ~ name ~ description.? <>($entity;format="cap"$, $entity;format="cap"$.unapply _)

  def autoInc = id.? ~ name ~ description.? <>($entity;format="cap"$, $entity;format="cap"$.unapply _) returning id

  implicit val dateTime: TypeMapper[DateTime]
  = MappedTypeMapper.base[DateTime, Timestamp](dt => new
      Timestamp(dt.getMillis), ts => new DateTime(ts.getTime))

  val byId = createFinderBy(_.id)
  val byName = createFinderBy(_.name)

  lazy val pageSize = 10

  def findAll: Seq[$entity;format="cap"$] = {
    DB.withSession{ implicit session =>
      (for (c <- $entity;format="cap"$s.sortBy(_.name)) yield c).list
    }
  }

  def count: Int = {
    DB.withSession{ implicit session =>
      (for (c <- $entity;format="cap"$s) yield c.id).list.size
    }
  }

  def findPage(page: Int = 0, orderField: Int): Page[$entity;format="cap"$] = {

    val offset = pageSize * page
    DB.withSession{ implicit session =>
      val $entity;format="lower"$s = (
        for {c <- $entity;format="cap"$s
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

  def findById(id: Long): Option[$entity;format="cap"$] = {
    DB.withSession{ implicit session =>
      $entity;format="cap"$s.byId(id).firstOption
    }
  }

  def insert($entity;format="lower"$: $entity;format="cap"$): Long = {
    DB.withSession{ implicit session =>
      $entity;format="cap"$s.autoInc.insert($entity;format="lower"$)
    }
  }

  def update(id:Long,$entity;format="lower"$: $entity;format="cap"$) = {
    DB.withSession{ implicit session => {
        val $entity;format="lower"$2update = $entity;format="lower"$.copy(Some(id))
        $entity;format="cap"$s.where(_.id === id).update($entity;format="lower"$2update)
      }
    }
  }

  def delete($entity;format="lower"$Id: Long) = {
    DB.withSession{ implicit session =>
      $entity;format="cap"$s.where(_.id === $entity;format="lower"$Id).delete
    }
  }


  //JSON
  implicit val $entity;format="lower"$Format = Json.format[$entity;format="cap"$]

}

