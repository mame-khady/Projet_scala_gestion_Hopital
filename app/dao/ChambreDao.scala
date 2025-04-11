package dao

import javax.inject._
import models.Chambre
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ChambreDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.profile.api._
  val db = dbConfig.db

  class ChambreTable(tag: Tag) extends Table[Chambre](tag, "chambre") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def numero = column[String]("numero")
    def capacite = column[Int]("capacite")
    def litsOccupes = column[Int]("lits_occupes")
    def * = (id.?, numero, capacite, litsOccupes) <> ((Chambre.apply _).tupled, Chambre.unapply)
  }

  val chambres = TableQuery[ChambreTable]

  def all(): Future[Seq[Chambre]] = db.run(chambres.result)
  def insert(chambre: Chambre): Future[Chambre] = db.run((chambres returning chambres.map(_.id) into ((c, id) => c.copy(id = Some(id)))) += chambre)
  def update(id: Long, chambre: Chambre): Future[Int] = db.run(chambres.filter(_.id === id).update(chambre.copy(id = Some(id))))
  def get(id: Long): Future[Option[Chambre]] = db.run(chambres.filter(_.id === id).result.headOption)
  def delete(id: Long): Future[Int] = db.run(chambres.filter(_.id === id).delete)
}