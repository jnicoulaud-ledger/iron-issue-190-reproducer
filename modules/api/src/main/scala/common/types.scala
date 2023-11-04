package common

import cats.syntax.all.*
import cats.Order
import cats.Show
import common.circe.codecs.given
import common.tapir.syntax.*
import io.circe.Codec as CirceCodec
import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.all.*
import sttp.tapir
import sttp.tapir.codec.iron.TapirCodecIron
import sttp.tapir.Codec.PlainCodec as TapirPlainCodec
import sttp.tapir.Codec as TapirCodec
import sttp.tapir.Schema as TapirSchema

private type VersionConstraints = DescribedAs[GreaterEqual[0], "Version must be positive"]
opaque type Version = Int :| VersionConstraints
object Version extends RefinedTypeOps[Int, VersionConstraints, Version], TapirCodecIron:
  given CirceCodec[Version] = summon[CirceCodec[Int]].iemap(either(_))(_.value)
  given TapirPlainCodec[Version] = summon[TapirPlainCodec[Int]].iemap(either(_))(_.value)
  given TapirSchema[Version] = ironTypeSchema[Int, VersionConstraints]
  given Order[Version] = Order.fromOrdering
  given Show[Version] = summon[Show[Int]].contramap(_.value)
