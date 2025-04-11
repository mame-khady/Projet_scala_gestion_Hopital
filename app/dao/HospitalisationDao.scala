package dao

import javax.inject.{Inject, Singleton}
import models.Hospitalisation
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import java.sql.Date

@Singleton
class HospitalisationDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  class Hospitalisations(tag: Tag) extends Table[Hospitalisation](tag, "hospitalisation") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def patientId = column[Long]("patientId")
    def chambreId = column[Long]("chambreId")
    def motif = column[Option[String]]("motif")
    def dateEntree = column[Date]("dateEntree")
    def dateSortie = column[Option[Date]]("dateSortie")

    def * = (id.?, patientId, chambreId, motif, dateEntree, dateSortie) <> ((Hospitalisation.apply _).tupled, Hospitalisation.unapply)
  }

  val hospitalisations = TableQuery[Hospitalisations]

  def add(h: Hospitalisation): Future[Hospitalisation] =
    db.run((hospitalisations returning hospitalisations.map(_.id) into ((hosp, id) => hosp.copy(id = Some(id)))) += h)

  def list(): Future[Seq[Hospitalisation]] =
    db.run(hospitalisations.result)

  def update(id: Long, h: Hospitalisation): Future[Int] = {
    val updated = h.copy(id = Some(id))
    db.run(hospitalisations.filter(_.id === id).update(updated))
  }

  def delete(id: Long): Future[Int] =
    db.run(hospitalisations.filter(_.id === id).delete)
}
