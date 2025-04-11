package controllers

import javax.inject._
import play.api.mvc._
import dao.ChambreDao
import models.Chambre
import play.api.libs.json._
import scala.concurrent.ExecutionContext
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ChambreController @Inject()(cc: ControllerComponents, chambreDao: ChambreDao)(implicit ec: ExecutionContext) extends AbstractController(cc) {
  def getAll = Action.async {
    chambreDao.all().map(res => Ok(Json.toJson(res)))
  }

  def get(id: Long) = Action.async {
    chambreDao.get(id).map {
      case Some(c) => Ok(Json.toJson(c))
      case None => NotFound
    }
  }

  def create = Action.async(parse.json) { implicit request =>
    request.body.validate[Chambre].fold(
      _ => Future.successful(BadRequest("Invalid JSON")),
      chambre => chambreDao.insert(chambre).map(c => Created(Json.toJson(c)))
    )
  }

  def update(id: Long) = Action.async(parse.json) { implicit request =>
    request.body.validate[Chambre].fold(
      _ => Future.successful(BadRequest("Invalid JSON")),
      chambre => chambreDao.update(id, chambre).map(_ => NoContent)
    )
  }

  def delete(id: Long) = Action.async {
    chambreDao.delete(id).map(_ => NoContent)
  }
}