package controllers

import javax.inject._
import play.api.mvc._
import dao.PaiementDao
import models.Paiement
import play.api.libs.json._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

@Singleton
class PaiementController @Inject()(cc: ControllerComponents, paiementDao: PaiementDao)(implicit ec: ExecutionContext) extends AbstractController(cc) {
  def getAll = Action.async {
    paiementDao.all().map(res => Ok(Json.toJson(res)))
  }

  def get(id: Long) = Action.async {
    paiementDao.get(id).map {
      case Some(p) => Ok(Json.toJson(p))
      case None => NotFound
    }
  }

  def create = Action.async(parse.json) { implicit request =>
    request.body.validate[Paiement].fold(
      _ => Future.successful(BadRequest("Invalid JSON")),
      paiement => paiementDao.insert(paiement).map(p => Created(Json.toJson(p)))
    )
  }

  def update(id: Long) = Action.async(parse.json) { implicit request =>
    request.body.validate[Paiement].fold(
      _ => Future.successful(BadRequest("Invalid JSON")),
      paiement => paiementDao.update(id, paiement).map(_ => NoContent)
    )
  }

  def delete(id: Long) = Action.async {
    paiementDao.delete(id).map(_ => NoContent)
  }
}
