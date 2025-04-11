package models

import play.api.libs.json._

case class Garde(id: Option[Long], personnelId: Long, dateDebut: String, dateFin: String)

object Garde {
  implicit val gardeFormat: OFormat[Garde] = Json.format[Garde]
}
