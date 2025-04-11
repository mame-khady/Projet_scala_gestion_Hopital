package models

import java.sql.Date

case class Hospitalisation(
                            id: Option[Long],
                            patientId: Long,
                            chambreId: Long,
                            motif: Option[String],
                            dateEntree: Date,
                            dateSortie: Option[Date]
                          )


object Hospitalisation {
  // Formats pour la sérialisation/désérialisation JSON
  import play.api.libs.json._
  implicit val hospitalisationFormat: OFormat[Hospitalisation] = Json.format[Hospitalisation]
}
