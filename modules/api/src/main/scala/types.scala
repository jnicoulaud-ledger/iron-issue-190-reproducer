import cats.Show
import cats.syntax.all.*
import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.all.*
import sttp.tapir
import sttp.tapir.Codec.PlainCodec
import sttp.tapir.codec.iron.TapirCodecIron
import sttp.tapir.{CodecFormat, DecodeResult, Codec as TapirCodec, Schema as TapirSchema}

private type VersionConstraints = DescribedAs[GreaterEqual[0], "Version must be positive"]
opaque type Version = Int :| VersionConstraints

object Version extends RefinedTypeOps[Int, VersionConstraints, Version] with TapirCodecIron:
  given PlainCodec[Version] = summon[PlainCodec[Int]].mapDecode(s => DecodeResult.fromEitherString(s.show, either(s)))(_.value)
