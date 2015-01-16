import feh.util.file._

val subProject = args(0)
val projectDir = sys.props("user.dir")
val reportsDir = projectDir / subProject / "test-reports"
val files = reportsDir.file.listFiles().toList
println("files: " + files.map(_.name).mkString(", "))

val regexps =
  """\s+(__.+__)"""                         -> "\n```\n\n[+] $1\n```scala" ::
  """[\s&&[^\n]]*(.+)\(\+\)[\s&&[^\n]]*"""  -> "\t[+] $1" ::
  """ \n\|"""                               -> "\n```\n\n|" ::
  """\|(.*, 0 error )\|"""                  -> "|$1|\n\n" ::
  """\|\n\|"""                              -> "|" ::
  """\n \|"""                               -> " |" ::
  """##(.+)\n```"""                         -> "##$1" ::
  Nil

def refactor(file: File) = {
  val src = io.Source.fromFile(file).mkString
  val result = (src /: regexps){
    case (acc, (reg, replacement)) => acc.replaceAll(reg, replacement)
  }
  println(result)
  file.withOutputStream{
    s =>
      s.write(result.getBytes("UTF-8"))
      s.flush()
  }
  println(s"${file.name} refactored")
}

files.foreach(refactor)