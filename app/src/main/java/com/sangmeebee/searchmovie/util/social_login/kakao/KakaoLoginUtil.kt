package com.sangmeebee.searchmovie.util.social_login.kakao

import android.content.Context
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.sangmeebee.searchmovie.model.UserModel
import com.sangmeebee.searchmovie.util.social_login.SocialLoginUtil
import com.sangmeebee.searchmovie.util.social_login.SocialType
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
 * 카카오톡 설치 여부에 따라서 설치 되어있으면 카카오톡 로그인을 시도한다.
 * 미설치 시 카카오 계정 로그인을 시도한다.
 *
 * 카카오톡 로그인에 실패하면 사용자가 의도적으로 로그인 취소한 경우를 제외하고는 카카오 계정 로그인을 서브로 실행한다.
 */
internal class KakaoLoginUtil : SocialLoginUtil {
    override suspend fun login(context: Context): Result<String> = runCatching {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            try {
                UserApiClient.loginWithKakaoTalk(context)
            } catch (error: Throwable) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    throw error
                } else {
                    UserApiClient.loginWithKakaoAccount(context)
                }
            }
        } else {
            UserApiClient.loginWithKakaoAccount(context)
        }
    }

    // 카카오톡으로 로그인 시도
    private suspend fun UserApiClient.Companion.loginWithKakaoTalk(context: Context): String =
        suspendCoroutine { continuation ->
            instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else if (token != null) {
                    continuation.resume(token.accessToken)
                } else {
                    continuation.resumeWithException(RuntimeException("Can't Receive Kaokao Access Token"))
                }
            }
        }

    // 카카오 계정으로 로그인 시도
    private suspend fun UserApiClient.Companion.loginWithKakaoAccount(context: Context): String =
        suspendCoroutine { continuation ->
            instance.loginWithKakaoAccount(context) { token, error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else if (token != null) {
                    continuation.resume(token.accessToken)
                } else {
                    continuation.resumeWithException(RuntimeException("Can't Receive Kaokao Access Token"))
                }
            }
        }

    override suspend fun isLogin(): Boolean = if (AuthApiClient.instance.hasToken()) {
        suspendCoroutine { continuation ->
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    continuation.resume(false)
                } else {
                    continuation.resume(true)
                }
            }
        }
    } else {
        false
    }

    override suspend fun logout(): Result<Boolean> = runCatching {
        suspendCoroutine { continuation ->
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else {
                    continuation.resume(true)
                }
            }
        }
    }

    override suspend fun getUserInfo(): Result<UserModel> = runCatching {
        suspendCoroutine { continuation ->
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else if (user != null) {
                    continuation.resume(UserModel(
                        userId = "kakao${user.id}",
                        nickname = user.kakaoAccount?.profile?.nickname,
                        profileImageUrl = user.kakaoAccount?.profile?.thumbnailImageUrl,
                        email = user.kakaoAccount?.email,
                        gender = user.kakaoAccount?.gender,
                        age = user.kakaoAccount?.ageRange,
                        loginType = SocialType.KAKAO
                    ))
                }
            }
        }
    }
}

