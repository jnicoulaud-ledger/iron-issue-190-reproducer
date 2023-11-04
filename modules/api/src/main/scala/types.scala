import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.all.*
import sttp.tapir.Codec.PlainCodec
import sttp.tapir.DecodeResult
import sttp.tapir.codec.iron.TapirCodecIron

private type VersionConstraints = DescribedAs[GreaterEqual[0], "Version must be positive"]
opaque type Version = Int :| VersionConstraints

object Version extends RefinedTypeOps[Int, VersionConstraints, Version] with TapirCodecIron:
  given PlainCodec[Version] = summon[PlainCodec[Int]].mapDecode(s => DecodeResult.fromEitherString(s.toString, either(s)))(_.value)
