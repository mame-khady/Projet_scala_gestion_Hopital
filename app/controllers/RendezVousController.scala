package controllers

import javax.inject._
import play.api.mvc._
import dao.RendezVousDao
import models.RendezVous
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class RendezVousController @Inject()(cc: ControllerComponents, rendezVousDao: RendezVousDao)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  // Création d'un rendez-vous
  def createRendezVous() = Action.async(parse.json) { implicit request =>
    request.body.validate[RendezVous].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> "Données invalides"))),
      rdv => {
        rendezVousDao.addRendezVous(rdv).map { createdRdv =>
          Created(Json.toJson(createdRdv))
        }
      }
    )
  }

  // Modification d'un rendez-vous
  def updateRendezVous(id: Long) = Action.async(parse.json) { implicit request =>
    request.body.validate[RendezVous].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> "Données invalides"))),
      rdv => {
        rendezVousDao.updateRendezVous(id, rdv).map {
          case 0 => NotFound(Json.obj("message" -> "Rendez-vous non trouvé"))
          case _ => NoContent
        }
      }
    )
  }

  // Suppression d'un rendez-vous
  def deleteRendezVous(id: Long) = Action.async { implicit request =>
    rendezVousDao.deleteRendezVous(id).map {
      case 0 => NotFound(Json.obj("message" -> "Rendez-vous non trouvé"))
      case _ => NoContent
    }
  }

  // Consultation du planning des médecins
  def getPlanningByPersonnel(personnelId: Long) = Action.async { implicit request =>
    rendezVousDao.getPlanningByPersonnel(personnelId).map { rendezVous =>
      Ok(Json.toJson(rendezVous))
    }
  }


  // Consultation des rendez-vous d'un patient
  def getRendezVousByPatient(patientId: Long) = Action.async { implicit request =>
    rendezVousDao.getRendezVousByPatient(patientId).map { rendezVous =>
      Ok(Json.toJson(rendezVous))
    }
  }
}
