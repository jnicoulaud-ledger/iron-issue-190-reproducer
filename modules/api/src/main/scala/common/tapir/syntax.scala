package common.tapir

import _root_.sttp.tapir.*
import cats.syntax.all.*
import cats.Show

object syntax:

  extension [L, H: Show, CF <: CodecFormat](codec: Codec[L, H, CF])
    def iemap[HH](f: H => Either[String, HH])(g: HH => H): Codec[L, HH, CF] =
      codec.mapDecode(s => DecodeResult.fromEitherString(s.show, f(s)))(g)
