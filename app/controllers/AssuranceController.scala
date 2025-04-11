package controllers

import javax.inject._
import play.api.mvc._
import dao.AssuranceDao
import models.Assurance
import play.api.libs.json._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

@Singleton
class AssuranceController @Inject()(cc: ControllerComponents, assuranceDao: AssuranceDao)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def getAll = Action.async {
    assuranceDao.all().map(res => Ok(Json.toJson(res)))
  }

  def get(id: Long) = Action.async {
    assuranceDao.get(id).map {
      case Some(a) => Ok(Json.toJson(a))
      case None => NotFound
    }
  }

  def create = Action.async(parse.json) { implicit request =>
    request.body.validate[Assurance].fold(
      _ => Future.successful(BadRequest("Invalid JSON")),
      assurance => assuranceDao.insert(assurance).map(a => Created(Json.toJson(a)))
    )
  }

  def update(id: Long) = Action.async(parse.json) { implicit request =>
    request.body.validate[Assurance].fold(
      _ => Future.successful(BadRequest("Invalid JSON")),
      assurance => assuranceDao.update(id, assurance).map(_ => NoContent)
    )
  }

  def delete(id: Long) = Action.async {
    assuranceDao.delete(id).map(_ => NoContent)
  }
}
