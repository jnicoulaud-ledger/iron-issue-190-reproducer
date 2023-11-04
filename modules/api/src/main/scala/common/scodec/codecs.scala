package common.scodec

import cats.Show
import common.circe.codecs.given
import common.tapir.syntax.*
import io.circe.Codec as CirceCodec
import io.circe.KeyDecoder as CirceKeyDecoder
import io.circe.KeyEncoder as CirceKeyEncoder
import scala.language.adhocExtensions
import scodec.bits.ByteVector
import sttp.tapir
import sttp.tapir.Codec.PlainCodec as TapirPlainCodec
import sttp.tapir.Codec as TapirCodec
import sttp.tapir.Schema as TapirSchema

object codecs:

  trait ByteVectorCodecs:
    protected val decode: String => Either[String, ByteVector]
    protected val encode: ByteVector => String

    given CirceKeyDecoder[ByteVector] = decode(_).toOption
    given CirceKeyEncoder[ByteVector] = encode(_)
    given CirceCodec[ByteVector]      = CirceCodec[String].iemap(decode)(encode)
    given TapirPlainCodec[ByteVector] = TapirCodec.string.iemap(decode)(encode)
    given TapirSchema[ByteVector]     = TapirSchema.string
    given Show[ByteVector]            = encode(_)

  object ByteVectorCodecs:
    trait Hex extends ByteVectorCodecs:
      override final val decode: String => Either[String, ByteVector] = ByteVector.fromHexDescriptive(_)
      override final val encode: ByteVector => String                 = _.toHex

    trait Base64 extends ByteVectorCodecs:
      override final val decode: String => Either[String, ByteVector] = ByteVector.fromBase64Descriptive(_)
      override final val encode: ByteVector => String                 = _.toBase64
