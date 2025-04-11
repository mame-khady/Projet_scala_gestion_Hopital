package dao

import javax.inject._
import models.Garde
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GardeDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  // Récupération de la configuration de la base de données
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.profile.api._

  // Définition de la table des gardes
  class GardeTable(tag: Tag) extends Table[Garde](tag, "garde") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def personnelId = column[Long]("personnel_id")
    def dateDebut = column[String]("date_debut")
    def dateFin = column[String]("date_fin")

    def * = (id.?, personnelId, dateDebut, dateFin) <> ((Garde.apply _).tupled, Garde.unapply)
  }

  // Représentation de la table des gardes dans la base de données
  val gardes = TableQuery[GardeTable]

  // Ajouter une nouvelle garde
  def add(garde: Garde): Future[Garde] = {
    val insertQuery = (gardes returning gardes.map(_.id) into ((g, id) => g.copy(id = Some(id)))) += garde
    dbConfig.db.run(insertQuery)
  }

  // Récupérer les gardes pour un personnel donné
  def getByPersonnel(personnelId: Long): Future[Seq[Garde]] = {
    dbConfig.db.run(gardes.filter(_.personnelId === personnelId).result)
  }

  // Supprimer une garde par ID
  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(gardes.filter(_.id === id).delete)
  }

  // Récupérer la liste de toutes les gardes
  def list(): Future[Seq[Garde]] = {
    dbConfig.db.run(gardes.result)
  }
}
