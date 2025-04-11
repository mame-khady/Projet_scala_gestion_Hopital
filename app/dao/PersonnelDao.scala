package dao

import javax.inject._
import play.api.db.slick._
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}
import models.Personnel

@Singleton
class PersonnelDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._

  class PersonnelTable(tag: Tag) extends Table[Personnel](tag, "personnel") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def nom = column[String]("nom")
    def prenom = column[String]("prenom")
    def role = column[String]("role")
    def specialite = column[Option[String]]("specialite")
    def telephone = column[String]("telephone")

    def * = (id.?, nom, prenom, role, specialite, telephone) <> ((Personnel.apply _).tupled, Personnel.unapply)
  }

  val personnels = TableQuery[PersonnelTable]

  def add(p: Personnel): Future[Personnel] = {
    val insert = (personnels returning personnels.map(_.id) into ((perso, id) => perso.copy(id = Some(id)))) += p
    db.run(insert)
  }

  def update(id: Long, p: Personnel): Future[Int] = {
    db.run(personnels.filter(_.id === id).update(p.copy(id = Some(id))))
  }

  def delete(id: Long): Future[Int] = {
    db.run(personnels.filter(_.id === id).delete)
  }

  def getAll(): Future[Seq[Personnel]] = db.run(personnels.result)

  def searchByRole(role: String): Future[Seq[Personnel]] = {
    db.run(personnels.filter(_.role === role).result)
  }

  def findById(id: Long): Future[Option[Personnel]] = {
    db.run(personnels.filter(_.id === id).result.headOption)
  }
}
