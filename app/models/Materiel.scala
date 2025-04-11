package models

import play.api.libs.json._

case class Materiel(
                     id: Option[Long],
                     nom: String,
                     quantite: Int,
                     fournisseur: String,
                     dateEntree: String,
                     dateSortie: Option[String]
                   )

object Materiel {
  implicit val materielFormat: OFormat[Materiel] = Json.format[Materiel]
}