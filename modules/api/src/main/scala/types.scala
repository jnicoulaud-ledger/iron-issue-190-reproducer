import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.all.*
import sttp.tapir.Codec.PlainCodec
import sttp.tapir.DecodeResult
import sttp.tapir.codec.iron.TapirCodecIron

opaque type Version = Int :| GreaterEqual[0]

object Version extends RefinedTypeOps[Int, GreaterEqual[0], Version] with TapirCodecIron:
  given PlainCodec[Version] = summon[PlainCodec[Int]].mapDecode(s => DecodeResult.fromEitherString(s.toString, either(s)))(_.value)
