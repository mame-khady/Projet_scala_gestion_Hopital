package models

import play.api.libs.json._

case class Chambre(
                    id: Option[Long],
                    numero: String,
                    capacite: Int,
                    litsOccupes: Int
                  )

object Chambre {
  implicit val chambreFormat: OFormat[Chambre] = Json.format[Chambre]
}