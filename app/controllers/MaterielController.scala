package controllers

import javax.inject._
import play.api.mvc._
import dao.MaterielDao
import models.Materiel
import play.api.libs.json._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

@Singleton
class MaterielController @Inject()(cc: ControllerComponents, materielDao: MaterielDao)(implicit ec: ExecutionContext) extends AbstractController(cc) {
  def getAll = Action.async {
    materielDao.all().map(res => Ok(Json.toJson(res)))
  }

  def get(id: Long) = Action.async {
    materielDao.get(id).map {
      case Some(m) => Ok(Json.toJson(m))
      case None => NotFound
    }
  }

  def create = Action.async(parse.json) { implicit request =>
    request.body.validate[Materiel].fold(
      _ => Future.successful(BadRequest("Invalid JSON")),
      m => materielDao.insert(m).map(res => Created(Json.toJson(res)))
    )
  }

  def update(id: Long) = Action.async(parse.json) { implicit request =>
    request.body.validate[Materiel].fold(
      _ => Future.successful(BadRequest("Invalid JSON")),
      m => materielDao.update(id, m).map(_ => NoContent)
    )
  }

  def delete(id: Long) = Action.async {
    materielDao.delete(id).map(_ => NoContent)
  }
}
