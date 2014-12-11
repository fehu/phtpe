import sbt._
import scala.util.matching.Regex

object FileUtils{
  type FileMapEntry = (File, File)
  private type FileMap = Seq[(File, File)]

  implicit class MapRenameWrapper(map: => FileMap){
    def rename(extract: Regex, build: String): FileMap = map map {
      case (from, to) => from -> FileUtils.rename(extract, build)(to)
    }
  }

  def rename(extract: Regex, build: String): File => File = file =>
    file.getParentFile / extract.replaceAllIn(file.name, build)

}

object TaskUtils{
  def runTasksForAllSubProjects(proj: ResolvedProject, state: Types.Id[State], tasks: TaskKey[_]*) = {
    val extracted = Project.extract(state)
    def projectMap(sp: ProjectRef) = Map(sp.build -> sp.project)

    for { subproj <- proj.aggregate} yield {
      val newSession = extracted.session.copy(currentProject = projectMap(subproj))
      val newState = Project.setProject(newSession, extracted.structure, state)
      (true /: tasks) {
        case (true, task) =>
          Project.runTask(task, newState) match {
            case Some((_, Value(_))) => true
            case Some((_, Inc(cause))) => throw new Exception("task failed", cause)
            case None => false
          }
        case _ => false
      }
    }
  }

}