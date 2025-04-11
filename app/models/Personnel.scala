package models

import play.api.libs.json._

case class Personnel(
                      id: Option[Long],
                      nom: String,
                      prenom: String,
                      role: String,
                      specialite: Option[String],
                      telephone: String
                    )

object Personnel {
  implicit val personnelFormat: OFormat[Personnel] = Json.format[Personnel]
}
