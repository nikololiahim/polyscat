package org.polystat.sarif

import fs2.io.file.Path
import io.circe.Json
import io.circe.syntax.*
import org.polystat.odin.analysis.EOOdinAnalyzer.OdinAnalysisResult
import org.polystat.cli.BuildInfo

import Sarif.*

case class AggregatedSarifOutput(
    analyzed: Seq[(Path, List[OdinAnalysisResult])]
):
  val sarif: SarifLog = SarifLog(
    runs = analyzed.map { case (path, errors) =>
      SarifOutput.sarifRun(path, errors)
    },
    version = SARIF_VERSION,
    $schema = SARIF_SCHEMA,
  )
  val json: Json = sarif.asJson.deepDropNullValues
end AggregatedSarifOutput
