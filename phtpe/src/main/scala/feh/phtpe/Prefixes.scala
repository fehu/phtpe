package feh.phtpe

sealed trait Prefix
sealed trait PrefixNumeric[Pref <: Prefix, N]{
  def modifier: N
}

sealed trait PrefixBundle{
  type Prefix <: feh.phtpe.Prefix
  type Tpe <: PhysType
}

object PrefixBundle{
  class Evidence[N: Numeric, Bundle <: PrefixBundle](
    val prefixNumeric: PrefixNumeric[Bundle#Prefix, N],
    val physTypeStringDecomposition: PhysTypeStringDecomposition[Bundle#Tpe]
  )
}

object Prefixes {
  /** e1 */
  sealed trait Deca extends Prefix
  /** e2 */
  sealed trait Hecto extends Prefix
  /** e3 */
  sealed trait Kilo extends Prefix
  /** e6 */
  sealed trait Mega extends Prefix
  /** e9 */
  sealed trait Giga extends Prefix
  /** e12 */
  sealed trait Tera extends Prefix

  /** e-1 */
  sealed trait Deci extends Prefix
  /** e-2 */
  sealed trait Centi extends Prefix
  /** e-3 */
  sealed trait Milli extends Prefix
  /** e-6 */
  sealed trait Micro extends Prefix
  /** e-9 */
  sealed trait Nano extends Prefix
  /** e-12 */
  sealed trait Pico	extends Prefix

  type Prefixed[Pref <: Prefix, Type <: PhysType] = PrefixBundle{ type Prefix = Pref; type Tpe = Type }
  type @@[Pref <: Prefix, Tpe <: PhysType] = Prefixed[Pref, Tpe]

  implicit def prefixedBundleEvidence[N, Bundle <: PrefixBundle](implicit num: Numeric[N],
                                                                          prefNum: PrefixNumeric[Bundle#Prefix, N],
                                                                          decompose: PhysTypeStringDecomposition[Bundle#Tpe]): PrefixBundle.Evidence[N, Bundle] =
    new PrefixBundle.Evidence[N, Bundle](prefNum, decompose)

  private class PrefixInt[Pref <: Prefix, N: Numeric](i: Int) extends PrefixNumeric[Pref, N] {
    def modifier: N = implicitly[Numeric[N]].fromInt(i)
  }
  private class PrefixReverseInt[Pref <: Prefix, N](i: Int)(implicit num: Fractional[N]) extends PrefixNumeric[Pref, N]{
    def modifier: N = num.div(num.one, num.fromInt(i))
  }

  implicit def decaIsNumeric[N: Numeric]: PrefixNumeric[Deca, N]   = new PrefixInt[Deca, N]  (10)
  implicit def hectoIsNumeric[N: Numeric]: PrefixNumeric[Hecto, N] = new PrefixInt[Hecto, N] (100)
  implicit def kiloIsNumeric[N: Numeric]: PrefixNumeric[Kilo, N]   = new PrefixInt[Kilo, N]  (1000)
  implicit def megaIsNumeric[N: Numeric]: PrefixNumeric[Mega, N]   = new PrefixInt[Mega, N]  (1e6.toInt)
  implicit def gigaIsNumeric[N: Numeric]: PrefixNumeric[Giga, N]   = new PrefixInt[Giga, N]  (1e9.toInt)

  implicit def teraIsLong: PrefixNumeric[Tera, Long] = new PrefixNumeric[Tera, Long] { def modifier = 1e12.toLong }
  implicit def teraIsBigInt: PrefixNumeric[Tera, BigInt] = new PrefixNumeric[Tera, BigInt] { def modifier: BigInt = 1e12.toLong }
  implicit def teraIsFractional[N](implicit num: Fractional[N]): PrefixNumeric[Tera, N] = new PrefixNumeric[Tera, N] {
    private def e6 = num.fromInt(1e6.toInt)
    def modifier: N = num.times(e6, e6)
  }

  implicit def deciIsFractional[N: Fractional]: PrefixNumeric[Deci, N]   = new PrefixReverseInt[Deci, N]  (10)
  implicit def centiIsFractional[N: Fractional]: PrefixNumeric[Centi, N] = new PrefixReverseInt[Centi, N] (100)
  implicit def milliIsFractional[N: Fractional]: PrefixNumeric[Milli, N] = new PrefixReverseInt[Milli, N] (1000)
  implicit def microIsFractional[N: Fractional]: PrefixNumeric[Micro, N] = new PrefixReverseInt[Micro, N] (1e6.toInt)
  implicit def nanoIsFractional[N: Fractional]: PrefixNumeric[Nano, N]   = new PrefixReverseInt[Nano, N]  (1e9.toInt)
  implicit def picoIsFractional[N](implicit num: Fractional[N]): PrefixNumeric[Pico, N] = new PrefixNumeric[Pico, N]{
    private def e6 = num.fromInt(1e6.toInt)
    def modifier: N = num.div(num.one, num.times(e6, e6))
  }

}
