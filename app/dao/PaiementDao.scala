package dao

import javax.inject._
import models.Paiement
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PaiementDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.profile.api._
  val db = dbConfig.db

  class PaiementTable(tag: Tag) extends Table[Paiement](tag, "paiement") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def patientId = column[Long]("patientId")
    def chambreId = column[Long]("chambreId")
    def montant = column[Double]("montant")
    def datePaiement = column[String]("datePaiement")
    def statut = column[String]("statut")

    def * = (id.?, patientId, chambreId, montant, datePaiement, statut) <> ((Paiement.apply _).tupled, Paiement.unapply)
  }

  val paiements = TableQuery[PaiementTable]

  def all(): Future[Seq[Paiement]] = db.run(paiements.result)
  def insert(paiement: Paiement): Future[Paiement] = db.run((paiements returning paiements.map(_.id) into ((p, id) => p.copy(id = Some(id)))) += paiement)
  def update(id: Long, paiement: Paiement): Future[Int] = db.run(paiements.filter(_.id === id).update(paiement.copy(id = Some(id))))
  def get(id: Long): Future[Option[Paiement]] = db.run(paiements.filter(_.id === id).result.headOption)
  def delete(id: Long): Future[Int] = db.run(paiements.filter(_.id === id).delete)
}
