package dao

import javax.inject._
import play.api.db.slick._
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}
import models.Patient

@Singleton
class PatientDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._

  class Patients(tag: Tag) extends Table[Patient](tag, "patients") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def codePatient = column[String]("code_patient")
    def nom = column[String]("nom")
    def prenom = column[String]("prenom")
    def tel = column[String]("tel")
    def numeroAssurance = column[Option[String]]("numero_assurance")
    def notesMedicales = column[Option[String]]("notes_medicales")
    def traitements = column[Option[String]]("traitements")
    def hospitalisations = column[Option[String]]("hospitalisations")

    def * = (id.?, codePatient, nom, prenom, tel, numeroAssurance, notesMedicales, traitements, hospitalisations) <> ((Patient.apply _).tupled, Patient.unapply)
  }

  val patients = TableQuery[Patients]

  def addPatient(patient: Patient): Future[Patient] = {
    val insert = (patients returning patients.map(_.id) into ((p, id) => p.copy(id = Some(id)))) += patient
    db.run(insert)
  }

  def updatePatient(id: Long, updated: Patient): Future[Int] = {
    db.run(patients.filter(_.id === id).update(updated.copy(id = Some(id))))
  }

  def deletePatient(id: Long): Future[Int] = {
    db.run(patients.filter(_.id === id).delete)
  }

  def getPatientById(id: Long): Future[Option[Patient]] = {
    db.run(patients.filter(_.id === id).result.headOption)
  }


  def updateNotesMedicales(id: Long, notes: String): Future[Option[Patient]] = {
    val query = patients.filter(_.id === id).map(_.notesMedicales).update(Some(notes))
    db.run(query).flatMap {
      case 0 => Future.successful(None)
      case _ => getPatientById(id)
    }
  }

  def listPatients(): Future[Seq[Patient]] = {
    db.run(patients.result)
  }

  def search(query: String): Future[Seq[Patient]] = {
    db.run(patients.filter(
      p => p.nom.like(s"%$query%") ||
        p.codePatient.like(s"%$query%") ||
        p.numeroAssurance.like(s"%$query%") ||
        p.tel.like(s"%$query%")
    ).result)
  }

  def updateTraitements(id: Long, traitements: String): Future[Int] = {
    db.run(patients.filter(_.id === id).map(_.traitements).update(Some(traitements)))
  }

  def updateHospitalisations(id: Long, hospitalisations: String): Future[Int] = {
    db.run(patients.filter(_.id === id).map(_.hospitalisations).update(Some(hospitalisations)))
  }
}
