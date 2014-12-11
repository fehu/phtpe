package feh.phtpe

import scala.reflect.macros.whitebox
import feh.util._

object PhysTypeEqualProves {
  def atCompile[Tpe <: PhysType: c.WeakTypeTag, Expected <: PhysType: c.WeakTypeTag](c: whitebox.Context): c.Expr[Unit] = {
    import c.universe._

    val m = new PhysTypeEqualProveMacros[c.type](c)
    val (equal, powTpe, powExpected) = m.equal[Tpe, Expected]

    if(!equal) c.abort(c.enclosingPosition, s"PhysType $powTpe is NOT equal to $powExpected")
    c.Expr(q"{}")
  }

  def equal[Tpe <: PhysType: c.WeakTypeTag, Expected <: PhysType: c.WeakTypeTag](c: whitebox.Context): c.Expr[Boolean] = {
    import c.universe._

    val m = new PhysTypeEqualProveMacros[c.type](c)
    val (equal, _, _) = m.equal[Tpe, Expected]

    c.Expr(q"$equal")
  }

  def evidence[L <: PhysType: c.WeakTypeTag, R <: PhysType: c.WeakTypeTag](c: whitebox.Context): c.Expr[PhysTypeEqualEvidence[L, R]] = {
    val m = new PhysTypeEqualProveMacros[c.type](c)
    val (equal, powTpe, powExpected) = m.equal[L, R]

    if(!equal)c.abort(c.enclosingPosition, s"PhysType $powTpe is NOT equal to $powExpected")

    import c.universe._
    c.Expr(q"new PhysTypeEqualEvidence[${c.weakTypeOf[L]}, ${c.weakTypeOf[R]}]")
  }

  def weakEvidence[L <: PhysType: c.WeakTypeTag, R <: PhysType: c.WeakTypeTag](c: whitebox.Context): c.Expr[WeakPhysTypeEqualEvidence[L, R]] = {
    val m = new PhysTypeEqualProveMacros[c.type](c)
    val (equal, _, _) = m.equal[L, R]

    import c.universe._
    c.Expr(q"new WeakPhysTypeEqualEvidence[${c.weakTypeOf[L]}, ${c.weakTypeOf[R]}]($equal)")
  }
}

class PhysTypeEqualProveMacros[C <: whitebox.Context](val c: C){
  import c.universe._

  var DEBUG = false

  def equal[Tpe <: PhysType: c.WeakTypeTag, Expected <: PhysType: c.WeakTypeTag] = {
    val powTpe = atomPowers[Tpe]
    if(DEBUG) c.info(NoPosition, "="*20, true)
    val powExpected = atomPowers[Expected]

    def sameKeys = powTpe.keySet== powExpected.keySet
    def keysAreEqual = {
      val v = powTpe.zipByKey(powExpected).values
      if(DEBUG) c.info(NoPosition, s"keysAreEqual: $v", true)
      v.forall(p => p._1 == p._2)
    }

    (sameKeys && keysAreEqual, powTpe, powExpected)
  }
  
  def atomPowers[Tpe <: PhysType](implicit Tpe: WeakTypeTag[Tpe]): Map[String, Int] = {
    def aliasTpe = c.typeOf[PhysType.Composite.Alias]

    def debuging(what: String, f: => Map[String, Int]) = {
      if(DEBUG) c.info(NoPosition, what, true)
      val res = f
      if(DEBUG) c.info(NoPosition, what + ": " + res, true)
      res
    }

    def rec(tpe: Type, inverse: Boolean): Map[String, Int] = tpe match {
      case TypeRef(a, op, args) if a <:< aliasTpe && !(op.typeSignature <:< typeOf[PhysType.Unit]) =>
        op.asType.name.decodedName.toString -> args match {
          case ("/",  l :: r :: Nil) => debuging("/", mergeMaps(rec(l, inverse), rec(r, !inverse))(_ + _))
          case ("**", l :: r :: Nil) => debuging("**", mergeMaps(rec(l, inverse), rec(r,  inverse))(_ + _))
          case ("^",  t :: p :: Nil) =>
            def const = if(inverse) -constantTypeToInt(p) else constantTypeToInt(p)
            debuging("^",
              if(t <:< typeOf[PhysType.Unit]) Map(t.typeSymbol.name.decodedName.toString -> const)
              else rec(t, inverse).mapValues(_ * const)
            )
      }
      case atom if atom <:< typeOf[PhysType.Atom] =>
        debuging("atom", Map(atom.typeSymbol.name.decodedName.toString -> (if(inverse) -1 else 1)))
      case composite if composite <:< typeOf[PhysType.Unit] =>
        val declaration = composite.baseClasses.map(_.typeSignature)
          .collect {
            case info@ClassInfoType(parents, _, _) if parents.exists(_ <:< typeOf[feh.phtpe.PhysType.Unit]) => info
          }
          .ensuring(_.size == 1).head
          .parents.filter(_ <:< typeOf[PhysType.Composite])
          .ensuring(_.size == 1).head

        debuging("composite", rec(declaration, inverse))
    }

    rec(Tpe.tpe, false)
  }

  def mergeMaps[K, V](mp: Map[K, V], mp2: Map[K, V])(f: (V, V) => V): Map[K, V] = (mp.keySet ++ mp2.keySet).map{
    k =>
      k -> mp.get(k).map(v => mp2.get(k).map(v2 => f(v, v2)) getOrElse v).getOrElse(mp2(k))
  }.toMap

  def constantTypeToInt: Type => Int = {
    case tpe if tpe <:< typeOf[PhysType._1] => 1
    case tpe if tpe <:< typeOf[PhysType._2] => 2
    case tpe if tpe <:< typeOf[PhysType._3] => 3
    case tpe if tpe <:< typeOf[PhysType._4] => 4
    case tpe if tpe <:< typeOf[PhysType._5] => 5
    case tpe if tpe <:< typeOf[PhysType._6] => 6
    case tpe if tpe <:< typeOf[PhysType._7] => 7
    case tpe if tpe <:< typeOf[PhysType._8] => 8
  }
}