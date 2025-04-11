package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import dao.PersonnelDao
import models.Personnel

@Singleton
class PersonnelController @Inject()(val controllerComponents: ControllerComponents, personnelDAO: PersonnelDao)(implicit ec: ExecutionContext) extends BaseController {

  implicit val personnelFormat = Json.format[Personnel]

  def create: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Personnel].fold(
      errors => Future.successful(BadRequest(JsError.toJson(errors))),
      personnel => personnelDAO.add(personnel).map(p => Created(Json.toJson(p)))
    )
  }

  def update(id: Long): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Personnel].fold(
      errors => Future.successful(BadRequest(JsError.toJson(errors))),
      personnel => personnelDAO.update(id, personnel).map {
        case 0 => NotFound(Json.obj("message" -> "Personnel non trouvé"))
        case _ => Ok(Json.obj("message" -> "Mise à jour réussie"))
      }
    )
  }

  def delete(id: Long): Action[AnyContent] = Action.async {
    personnelDAO.delete(id).map {
      case 0 => NotFound(Json.obj("message" -> "Personnel non trouvé"))
      case _ => Ok(Json.obj("message" -> "Supprimé avec succès"))
    }
  }

  def listAll: Action[AnyContent] = Action.async {
    personnelDAO.getAll().map(p => Ok(Json.toJson(p)))
  }

  def searchByRole(role: String): Action[AnyContent] = Action.async {
    personnelDAO.searchByRole(role).map(p => Ok(Json.toJson(p)))
  }
}
