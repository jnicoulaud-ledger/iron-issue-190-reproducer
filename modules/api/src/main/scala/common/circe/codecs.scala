package common.circe

import io.circe.Codec
import io.circe.Decoder
import io.circe.Encoder
import sttp.model.Uri

object codecs:
  given Codec[Int]    = Codec.from(Decoder.decodeInt, Encoder.encodeInt)
  given Codec[String] = Codec.from(Decoder.decodeString, Encoder.encodeString)
  given Codec[Uri]    = Codec.from(Decoder[String].emap(Uri.parse), Encoder[String].contramap(_.toString))
