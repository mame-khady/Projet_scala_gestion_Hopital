package models

import play.api.libs.json._

case class Assurance(
                      id: Option[Long],
                      nom: String,
                      typeAssurance: String, // "Maladie", "Accident", etc.
                      couverture: String // "Complet", "Partiel", etc.
                    )

object Assurance {
  implicit val assuranceFormat: OFormat[Assurance] = Json.format[Assurance]
}
