package dao

import javax.inject._
import models.Materiel
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MaterielDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.profile.api._
  val db = dbConfig.db

  class MaterielTable(tag: Tag) extends Table[Materiel](tag, "materiel") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def nom = column[String]("nom")
    def quantite = column[Int]("quantite")
    def fournisseur = column[String]("fournisseur")
    def dateEntree = column[String]("date_entree")
    def dateSortie = column[Option[String]]("date_sortie")
    def * = (id.?, nom, quantite, fournisseur, dateEntree, dateSortie) <> ((Materiel.apply _).tupled, Materiel.unapply)
  }

  val materiels = TableQuery[MaterielTable]

  def all(): Future[Seq[Materiel]] = db.run(materiels.result)
  def insert(m: Materiel): Future[Materiel] = db.run((materiels returning materiels.map(_.id) into ((m, id) => m.copy(id = Some(id)))) += m)
  def update(id: Long, m: Materiel): Future[Int] = db.run(materiels.filter(_.id === id).update(m.copy(id = Some(id))))
  def get(id: Long): Future[Option[Materiel]] = db.run(materiels.filter(_.id === id).result.headOption)
  def delete(id: Long): Future[Int] = db.run(materiels.filter(_.id === id).delete)
}
