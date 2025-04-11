package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}
import dao.HospitalisationDao
import models.Hospitalisation

@Singleton
class HospitalisationController @Inject()(val controllerComponents: ControllerComponents, dao: HospitalisationDao)(implicit ec: ExecutionContext) extends BaseController {

  implicit val hospitalisationFormat: OFormat[Hospitalisation] = Json.format[Hospitalisation]

  def create: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Hospitalisation].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> "JSON invalide", "errors" -> JsError.toJson(errors)))),
      h => dao.add(h).map(res => Created(Json.toJson(res)))
    )
  }

  def list: Action[AnyContent] = Action.async {
    dao.list().map(h => Ok(Json.toJson(h)))
  }

  def update(id: Long): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Hospitalisation].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> "JSON invalide", "errors" -> JsError.toJson(errors)))),
      h => dao.update(id, h).map {
        case 0 => NotFound(Json.obj("message" -> "Hospitalisation non trouvée"))
        case _ => Ok(Json.obj("message" -> "Hospitalisation mise à jour"))
      }
    )
  }

  def delete(id: Long): Action[AnyContent] = Action.async {
    dao.delete(id).map {
      case 0 => NotFound(Json.obj("message" -> "Hospitalisation non trouvée"))
      case _ => Ok(Json.obj("message" -> "Hospitalisation supprimée"))
    }
  }
}
