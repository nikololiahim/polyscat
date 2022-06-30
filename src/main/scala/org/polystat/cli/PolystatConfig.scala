package org.polystat.cli

import cats.data.NonEmptyList
import cats.effect.IO
import cats.syntax.apply.*
import com.monovore.decline.Opts
import fs2.Stream
import fs2.io.file.Path
import org.polystat.cli.EOAnalyzer
import org.polystat.odin.analysis.ASTAnalyzer
import org.polystat.cli.util.FileTypes.*

object PolystatConfig:

  final case class AnalyzerConfig(
      inex: Option[IncludeExclude],
      input: Input,
      tmp: Option[Path],
      outputFormats: List[OutputFormat],
      output: Output,
  )

  enum ValidationError:
    case NoAnalyzers(inex: IncludeExclude)
    case MissingAnalyzersKeys(keys: NonEmptyList[String])

  final case class ProcessedConfig(
      filteredAnalyzers: NonEmptyList[EOAnalyzer],
      tempDir: Directory,
      input: Directory,
      fmts: List[OutputFormat],
      output: Output,
  )

  enum SupportedLanguage:
    case EO, Python
    case Java(j2eo: Option[File], j2eoVersion: Option[String])
  end SupportedLanguage

  enum PolystatUsage:
    case Analyze(language: SupportedLanguage, config: AnalyzerConfig)
    case List(config: Boolean)
    case Misc(version: Boolean, configPath: Option[File])
  end PolystatUsage

  enum IncludeExclude:
    case Include(rules: NonEmptyList[String])
    case Exclude(rules: NonEmptyList[String])
  end IncludeExclude

  enum Input:
    case FromDirectory(path: Directory)
    case FromFile(path: File)
    case FromStdin
  end Input

  enum OutputFormat:
    case Sarif

  final case class Output(
      dirs: List[Path],
      files: List[Path],
      console: Boolean,
  )

end PolystatConfig
