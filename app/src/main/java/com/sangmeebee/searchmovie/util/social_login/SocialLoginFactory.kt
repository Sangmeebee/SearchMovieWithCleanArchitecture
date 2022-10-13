package com.sangmeebee.searchmovie.util.social_login

import com.sangmeebee.searchmovie.util.social_login.google.GoogleLoginUtil
import com.sangmeebee.searchmovie.util.social_login.kakao.KakaoLoginUtil
import javax.inject.Inject

class SocialLoginFactory @Inject constructor(
    private val kakaoLoginUtil: KakaoLoginUtil,
    private val googleLoginUtil: GoogleLoginUtil,
) {
    operator fun invoke(type: SocialType): SocialLoginUtil =
        when (type) {
            SocialType.KAKAO -> kakaoLoginUtil
            else -> googleLoginUtil
        }
}