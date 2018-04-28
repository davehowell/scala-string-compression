package com.davoscollective.utils

import org.scalatest._


class StringUtilsSpec extends FlatSpec with Matchers {
  "The StringUtils object" should "evaluate a naive run-length string compression algorithm" in {

  StringUtils.compressStr("ABBCDD", 2) shouldEqual "A2BC2D"
  StringUtils.compressStr("ABBBCCDDD", 3) shouldEqual "A3BCC3D"
  StringUtils.compressStr("ABBBCCDDD", 4) shouldEqual "ABBBCCDDD"

  StringUtils.compressStrFP("ABBCDD", 2) shouldEqual "A2BC2D"
  StringUtils.compressStrFP("ABBBCCDDD", 3) shouldEqual "A3BCC3D"
  StringUtils.compressStrFP("ABBBCCDDD", 4) shouldEqual "ABBBCCDDD"

  StringUtils.compressStrFPShorter("ABBCDD", 2) shouldEqual "A2BC2D"
  StringUtils.compressStrFPShorter("ABBBCCDDD", 3) shouldEqual "A3BCC3D"
  StringUtils.compressStrFPShorter("ABBBCCDDD", 4) shouldEqual "ABBBCCDDD"

  StringUtils.compressStrProcedural("ABBCDD", 2) shouldEqual "A2BC2D"
  StringUtils.compressStrProcedural("ABBBCCDDD", 3) shouldEqual "A3BCC3D"
  StringUtils.compressStrProcedural("ABBBCCDDD", 4) shouldEqual "ABBBCCDDD"

  StringUtils.regexCompress("ABBCDD", 2) shouldEqual "A2BC2D"
  StringUtils.regexCompress("ABBBCCDDD", 3) shouldEqual "A3BCC3D"
  StringUtils.regexCompress("ABBBCCDDD", 4) shouldEqual "ABBBCCDDD"
  }
}
