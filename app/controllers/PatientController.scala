package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}

import dao.PatientDao
import models.Patient

@Singleton
class PatientController @Inject()(val controllerComponents: ControllerComponents, patientDAO: PatientDao)(implicit ec: ExecutionContext) extends BaseController {

  implicit val patientFormat: OFormat[Patient] = Json.format[Patient]

  def addPatient: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Patient].fold(
      errors => {
        val errorJson = JsError.toJson(errors)
        Future.successful(BadRequest(Json.obj("message" -> "Invalid JSON", "errors" -> errorJson)))
      },
      patient => patientDAO.addPatient(patient).map { savedPatient =>
        Created(Json.toJson(savedPatient))
      }
    )
  }

  def updatePatient(id: Long): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Patient].fold(
      errors => {
        val errorJson = JsError.toJson(errors)
        Future.successful(BadRequest(Json.obj("message" -> "Invalid JSON", "errors" -> errorJson)))
      },
      patient => patientDAO.updatePatient(id, patient).map {
        case 0 => NotFound(Json.obj("message" -> "Patient non trouvé"))
        case _ => Ok(Json.obj("message" -> "Patient modifié avec succès"))
      }
    )
  }

  def deletePatient(id: Long): Action[AnyContent] = Action.async {
    patientDAO.deletePatient(id).map {
      case 0 => NotFound(Json.obj("message" -> "Patient non trouvé"))
      case _ => Ok(Json.obj("message" -> "Patient supprimé"))
    }
  }

  def listPatients: Action[AnyContent] = Action.async {
    patientDAO.listPatients().map { patients =>
      Ok(Json.toJson(patients))
    }
  }

  def search(query: String): Action[AnyContent] = Action.async {
    patientDAO.search(query).map { results =>
      Ok(Json.toJson(results))
    }
  }

  def updateHospitalisations(id: Long): Action[JsValue] = Action.async(parse.json) { request =>
    (request.body \ "hospitalisations").asOpt[String] match {
      case Some(hosp) =>
        patientDAO.updateHospitalisations(id, hosp).map {
          case 0 => NotFound(Json.obj("message" -> "Patient non trouvé"))
          case _ => Ok(Json.obj("message" -> "Hospitalisations mises à jour"))
        }
      case None => Future.successful(BadRequest(Json.obj("message" -> "Champ 'hospitalisations' manquant")))
    }
  }

  def updateTraitements(id: Long): Action[JsValue] = Action.async(parse.json) { request =>
    (request.body \ "traitements").asOpt[String] match {
      case Some(traitements) =>
        patientDAO.updateTraitements(id, traitements).map {
          case 0 => NotFound(Json.obj("message" -> "Patient non trouvé"))
          case _ => Ok(Json.obj("message" -> "Traitements mis à jour"))
        }
      case None => Future.successful(BadRequest(Json.obj("message" -> "Champ 'traitements' manquant")))
    }
  }


  def updateNotesMedicales(id: Long): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Map[String, String]].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> "Invalid data format"))),
      data => {
        data.get("notesMedicales") match {
          case Some(notes) =>
            patientDAO.updateNotesMedicales(id, notes).map {
              case Some(updatedPatient) =>
                Ok(Json.toJson(updatedPatient))
              case None =>
                NotFound(Json.obj("message" -> "Patient non trouvé"))
            }
          case None =>
            Future.successful(BadRequest(Json.obj("message" -> "Champ 'notesMedicales' manquant")))
        }
      }
    )
  }
}
