package models

import play.api.libs.json.{Json, OFormat}

case class Patient(
                    id: Option[Long] = None,
                    codePatient: String,
                    nom: String,
                    prenom: String,
                    tel: String,
                    numeroAssurance: Option[String],
                    notesMedicales: Option[String],
                    traitements: Option[String],
                    hospitalisations: Option[String]
                  )

object Patient {
  implicit val format: OFormat[Patient] = Json.format[Patient]
}