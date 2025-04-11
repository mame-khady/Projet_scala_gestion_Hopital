package dao

import javax.inject._
import models.Assurance
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AssuranceDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.profile.api._
  val db = dbConfig.db

  class AssuranceTable(tag: Tag) extends Table[Assurance](tag, "assurance") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def nom = column[String]("nom")
    def typeAssurance = column[String]("type_assurance")
    def couverture = column[String]("couverture")
    def * = (id.?, nom, typeAssurance, couverture) <> ((Assurance.apply _).tupled, Assurance.unapply)
  }

  val assurances = TableQuery[AssuranceTable]

  def all(): Future[Seq[Assurance]] = db.run(assurances.result)
  def insert(assurance: Assurance): Future[Assurance] = db.run((assurances returning assurances.map(_.id) into ((a, id) => a.copy(id = Some(id)))) += assurance)
  def update(id: Long, assurance: Assurance): Future[Int] = db.run(assurances.filter(_.id === id).update(assurance.copy(id = Some(id))))
  def get(id: Long): Future[Option[Assurance]] = db.run(assurances.filter(_.id === id).result.headOption)
  def delete(id: Long): Future[Int] = db.run(assurances.filter(_.id === id).delete)
}
