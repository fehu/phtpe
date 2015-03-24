package feh.phtpe

import feh.util._

import scala.reflect.macros.whitebox

object PhysTypeMacros {
  object Equality{
    def atCompile[Tpe <: PhysType: c.WeakTypeTag, Expected <: PhysType: c.WeakTypeTag](c: whitebox.Context): c.Expr[Unit] = {
      import c.universe._

      val m = new PhysTypeMacros[c.type](c)
      val (equal, powTpe, powExpected) = m.equal[Tpe, Expected]

      if(!equal) c.abort(c.enclosingPosition, s"PhysType $powTpe is NOT equal to $powExpected")
      c.Expr(q"{}")
    }
    
    def evidence[L <: PhysType: c.WeakTypeTag, R <: PhysType: c.WeakTypeTag](c: whitebox.Context): c.Expr[PhysTypeEqualEvidence[L, R]] = {
      val m = new PhysTypeMacros[c.type](c)
      val (equal, powTpe, powExpected) = m.equal[L, R]

      if(!equal)c.abort(c.enclosingPosition, s"PhysType $powTpe is NOT equal to $powExpected")

      import c.universe._
      c.Expr(q"new PhysTypeEqualEvidence[${c.weakTypeOf[L]}, ${c.weakTypeOf[R]}]")
    }

    def weakEvidence[L <: PhysType: c.WeakTypeTag, R <: PhysType: c.WeakTypeTag](c: whitebox.Context): c.Expr[WeakPhysTypeEqualEvidence[L, R]] = {
      val m = new PhysTypeMacros[c.type](c)
      val (equal, _, _) = m.equal[L, R]

      import c.universe._
      c.Expr(q"new WeakPhysTypeEqualEvidence[${c.weakTypeOf[L]}, ${c.weakTypeOf[R]}]($equal)")
    } 
  }

  def equal[Tpe <: PhysType: c.WeakTypeTag, Expected <: PhysType: c.WeakTypeTag](c: whitebox.Context): c.Expr[Boolean] = {
    import c.universe._

    val m = new PhysTypeMacros[c.type](c)
    val (equal, _, _) = m.equal[Tpe, Expected]

    c.Expr(q"$equal")
  }

  def decomposition[T <: PhysType: c.WeakTypeTag](c: whitebox.Context): c.Expr[PhysTypeDecomposition[T]] = {
    import c.universe._

    val m = new PhysTypeMacros[c.type](c)
    val entries = m.atomPowers[T].map{ case (name, count) => q"${name.name.decodedName.toString} -> $count" }.toList
    c.Expr[PhysTypeDecomposition[T]](q"new PhysTypeDecomposition[${weakTypeOf[T]}](Map(..$entries))")
  }
}

class PhysTypeMacros[C <: whitebox.Context](val c: C){
  import c.universe._

  var DEBUG = false

  def equal[Tpe <: PhysType: c.WeakTypeTag, Expected <: PhysType: c.WeakTypeTag] = {
    val powTpe = atomPowers[Tpe]
    if(DEBUG) c.info(NoPosition, "="*20, true)
    val powExpected = atomPowers[Expected]

    def sameKeys = powTpe.keySet == powExpected.keySet
    def keysAreEqual = {
      val v = powTpe.zipByKey(powExpected).values
      if(DEBUG) c.info(NoPosition, s"keysAreEqual: $v", true)
      v.forall(p => p._1 == p._2)
    }

    (sameKeys && keysAreEqual, powTpe, powExpected)
  }
  
  def atomPowers[Tpe <: PhysType](implicit Tpe: WeakTypeTag[Tpe]): Map[Symbol, Int] = {

    def debugging(what: String, f: => Map[Symbol, Int]) = {
      if(DEBUG) c.info(NoPosition, what, true)
      val res = f
      if(DEBUG) c.info(NoPosition, what + ": " + res, true)
      res
    }

    def rec(tpe: Type, inverse: Boolean): Map[Symbol, Int] = tpe match {
      case t@TypeRef(_, op, args) if t <:< c.typeOf[PhysType.Composite] && !(t <:< typeOf[PhysType.Unit]) =>
        op.asType.name.decodedName.toString -> args match {
          case ("/" | "Division" ,  l :: r :: Nil) =>
            debugging("/", mergeMaps(rec(l, inverse), rec(r, !inverse))(_ + _))
          case ("**" | "Multiplication", l :: r :: Nil) =>
            debugging("*", mergeMaps(rec(l, inverse), rec(r,  inverse))(_ + _))
          case (o@("^" | "^-" | "Power"),  t :: p :: Nil) =>
            def const = constantTypeToInt(p) * (if(inverse) -1 else 1) * (if(o == "^") 1 else -1)
            debugging("^",
              if(t <:< typeOf[PhysType.Atom]) Map(t.typeSymbol -> const) //.name.decodedName.toString
              else rec(t, inverse = false).mapValues(_ * const)
            )
          case _ /* alias */ => rec(t.dealias, inverse)

      }
      case atom if atom <:< typeOf[PhysType.Neutral] => Map()
      case atom if atom <:< typeOf[PhysType.Atom] =>
        debugging("atom", Map(atom.typeSymbol -> (if(inverse) -1 else 1))) //.name.decodedName.toString
      case composite if composite <:< typeOf[PhysType.Unit] =>
        val declaration = composite.baseClasses.map(_.typeSignature)
          .collect {
            case info@ClassInfoType(parents, _, _) if parents.exists(_ <:< typeOf[feh.phtpe.PhysType.Unit]) => info
          }
          .ensuring(_.size == 1).head
          .parents.filter(_ <:< typeOf[PhysType.Composite])
          .ensuring(_.size == 1).head

        debugging("composite", rec(declaration, inverse))
    }

    debugging("res", rec(Tpe.tpe, false).filter(_._2 != 0))
  }

  def mergeMaps[K, V](mp: Map[K, V], mp2: Map[K, V])(f: (V, V) => V): Map[K, V] = (mp.keySet ++ mp2.keySet).map{
    k =>
      k -> mp.get(k).map(v => mp2.get(k).map(v2 => f(v, v2)) getOrElse v).getOrElse(mp2(k))
  }.toMap

  def constantTypeToInt: Type => Int = {
    case tpe if tpe <:< typeOf[PhysType.NegativeIntegerConstant[_]] => -constantTypeToInt(tpe.typeArgs.head)
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