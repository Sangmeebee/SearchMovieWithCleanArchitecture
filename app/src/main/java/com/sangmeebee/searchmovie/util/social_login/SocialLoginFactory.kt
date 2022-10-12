package com.sangmeebee.searchmovie.util.social_login

import com.sangmeebee.searchmovie.util.social_login.google.GoogleLoginUtil
import com.sangmeebee.searchmovie.util.social_login.kakao.KakaoLoginUtil
import javax.inject.Inject

class SocialLoginFactory @Inject constructor() {
    fun create(type: SocialType): SocialLoginUtil =
        when (type) {
            SocialType.KAKAO -> KakaoLoginUtil()
            else -> GoogleLoginUtil()
        }
}