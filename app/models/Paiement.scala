package models

import play.api.libs.json._

case class Paiement(
                     id: Option[Long],
                     patientId: Long,
                     chambreId: Long,
                     montant: Double,
                     datePaiement: String,
                     statut: String
                   )

object Paiement {
  implicit val paiementFormat: OFormat[Paiement] = Json.format[Paiement]
}
