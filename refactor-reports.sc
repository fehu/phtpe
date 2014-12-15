import feh.util.file._
val projectDir = sys.props("user.home") / "dev/scala/phtpe"
val reportsDir = projectDir / "phtpe" / "test-reports"
//val reportsDir = projectDir / "vectors" / "test-reports"
val f = reportsDir.file
val files = reportsDir.file.listFiles().toList
println("files: " + files.map(_.name))
val regexps =
  """\s+(__.+__)"""       -> "\n```\n\n[+] $1\n```scala" ::
    """ +(.+)\(\+\) +"""  -> "\t[+] $1" ::
    """ +(.+)\(\+\)"""  -> "\t[+] $1" ::
    """ \n\|"""           -> "\n```\n\n|" ::
    """\|\n\|"""          -> "|" ::
    """##(.+)\n```"""     -> "##$1" ::
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