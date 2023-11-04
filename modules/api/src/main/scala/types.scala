import cats.Show
import cats.syntax.all.*
import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.all.*
import sttp.tapir
import sttp.tapir.Codec.PlainCodec as TapirPlainCodec
import sttp.tapir.codec.iron.TapirCodecIron
import sttp.tapir.{CodecFormat, DecodeResult, Codec as TapirCodec, Schema as TapirSchema}

extension[L, H: Show, CF <: CodecFormat] (codec: TapirCodec[L, H, CF])
  def iemap[HH](f: H => Either[String, HH])(g: HH => H): TapirCodec[L, HH, CF] =
    codec.mapDecode(s => DecodeResult.fromEitherString(s.show, f(s)))(g)

private type VersionConstraints = DescribedAs[GreaterEqual[0], "Version must be positive"]
opaque type Version = Int :| VersionConstraints

object Version extends RefinedTypeOps[Int, VersionConstraints, Version], TapirCodecIron:
  given TapirPlainCodec[Version] = summon[TapirPlainCodec[Int]].iemap(either(_))(_.value)

  given TapirSchema[Version] = ironTypeSchema[Int, VersionConstraints]
