import cats.Show
import cats.syntax.all.*
import io.circe.{Decoder, Encoder, Codec as CirceCodec}
import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.all.*
import sttp.tapir
import sttp.tapir.Codec.PlainCodec as TapirPlainCodec
import sttp.tapir.codec.iron.TapirCodecIron
import sttp.tapir.{CodecFormat, DecodeResult, Codec as TapirCodec, Schema as TapirSchema}

given CirceCodec[Int] = CirceCodec.from(Decoder.decodeInt, Encoder.encodeInt)

extension[L, H: Show, CF <: CodecFormat] (codec: TapirCodec[L, H, CF])
  def iemap[HH](f: H => Either[String, HH])(g: HH => H): TapirCodec[L, HH, CF] =
    codec.mapDecode(s => DecodeResult.fromEitherString(s.show, f(s)))(g)

private type VersionConstraints = DescribedAs[GreaterEqual[0], "Version must be positive"]
opaque type Version = Int :| VersionConstraints

object Version extends RefinedTypeOps[Int, VersionConstraints, Version], TapirCodecIron:
  given CirceCodec[Version] = summon[CirceCodec[Int]].iemap(either(_))(_.value)

  given TapirPlainCodec[Version] = summon[TapirPlainCodec[Int]].iemap(either(_))(_.value)

  given TapirSchema[Version] = ironTypeSchema[Int, VersionConstraints]
