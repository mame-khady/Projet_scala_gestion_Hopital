package models

import play.api.libs.json._

case class RendezVous(
                       id: Option[Long],
                       patientId: Long,
                       personnelId: Long,
                       dateHeure: String,
                       motif: Option[String]
                     )

object RendezVous {
  implicit val rendezVousFormat: OFormat[RendezVous] = Json.format[RendezVous]
}
