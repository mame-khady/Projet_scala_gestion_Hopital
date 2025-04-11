package controllers

import javax.inject._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import dao.GardeDao
import models.Garde
import play.api.libs.json._

@Singleton
class GardeController @Inject()(cc: ControllerComponents, gardeDao: GardeDao)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def addGarde = Action.async(parse.json) { request =>
    request.body.validate[Garde].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> "Format invalide", "errors" -> JsError.toJson(errors)))),
      garde => gardeDao.add(garde).map(saved => Ok(Json.toJson(saved)))
    )
  }

  def getGardesByPersonnel(personnelId: Long) = Action.async {
    gardeDao.getByPersonnel(personnelId).map { gardes =>
      Ok(Json.toJson(gardes))
    }
  }

  def deleteGarde(id: Long) = Action.async {
    gardeDao.delete(id).map {
      case 0 => NotFound(Json.obj("message" -> s"Aucun garde avec id $id"))
      case _ => Ok(Json.obj("message" -> s"Garde $id supprimé"))
    }
  }

  def listGardes = Action.async {
    gardeDao.list().map { gardes =>
      Ok(Json.toJson(gardes))
    }
  }
}
