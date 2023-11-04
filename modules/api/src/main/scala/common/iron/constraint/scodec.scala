package common.iron.constraint

import _root_.scodec.bits.ByteVector
import io.github.iltotore.iron.*
import io.github.iltotore.iron.compileTime.*
import io.github.iltotore.iron.constraint.all.*
import scala.compiletime.summonInline

object scodec:

  type MinLengthL[V <: Long]   = Length[GreaterEqual[V]] `DescribedAs` "Should have a minimum length of " + V
  type MaxLengthL[V <: Long]   = Length[LessEqual[V]] `DescribedAs` "Should have a maximum length of " + V
  type FixedLengthL[V <: Long] = Length[StrictEqual[V]] `DescribedAs` "Should have an exact length of " + V

  class LengthByteVector[C, Impl <: Constraint[Long, C]](using Impl) extends Constraint[ByteVector, Length[C]]:
    override inline def test(value: ByteVector): Boolean = summonInline[Impl].test(value.length)
    override inline def message: String                  = "Length: (" + summonInline[Impl].message + ")"

  inline given lengthByteVector[C, Impl <: Constraint[Long, C]](using inline impl: Impl): LengthByteVector[C, Impl] =
    new LengthByteVector
