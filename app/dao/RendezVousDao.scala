package dao

import javax.inject._
import play.api.db.slick._
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}
import models.RendezVous

@Singleton
class RendezVousDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._

  class RendezVousTable(tag: Tag) extends Table[RendezVous](tag, "rendez_vous") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def patientId = column[Long]("patient_id")
    def personnelId = column[Long]("personnel_id")
    def dateHeure = column[String]("date_heure") // ou Timestamp si tu préfères
    def motif = column[Option[String]]("motif")

    def * = (id.?, patientId, personnelId, dateHeure, motif) <> ((RendezVous.apply _).tupled, RendezVous.unapply)
  }

  val rendezvous = TableQuery[RendezVousTable]

  def addRendezVous(rdv: RendezVous): Future[RendezVous] = {
    val insert = (rendezvous returning rendezvous.map(_.id) into ((r, id) => r.copy(id = Some(id)))) += rdv
    db.run(insert)
  }

  def updateRendezVous(id: Long, updatedRdv: RendezVous): Future[Int] = {
    db.run(rendezvous.filter(_.id === id).update(updatedRdv.copy(id = Some(id))))
  }

  def deleteRendezVous(id: Long): Future[Int] = {
    db.run(rendezvous.filter(_.id === id).delete)
  }

  def getPlanningByPersonnel(personnelId: Long): Future[Seq[RendezVous]] = {
    db.run(rendezvous.filter(_.personnelId === personnelId).result)
  }

  def getRendezVousByPatient(patientId: Long): Future[Seq[RendezVous]] = {
    db.run(rendezvous.filter(_.patientId === patientId).result)
  }
}
