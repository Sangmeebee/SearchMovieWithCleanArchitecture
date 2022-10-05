package com.sangmeebee.searchmovie.util

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException


/**
 * 카카오톡 설치 여부에 따라서 설치 되어있으면 카카오톡 로그인을 시도한다.
 * 미설치 시 카카오 계정 로그인을 시도한다.
 *
 * 카카오톡 로그인에 실패하면 사용자가 의도적으로 로그인 취소한 경우를 제외하고는 카카오 계정 로그인을 서브로 실행한다.
 */
suspend fun UserApiClient.Companion.login(context: Context): Result<OAuthToken> = runCatching {
    if (instance.isKakaoTalkLoginAvailable(context)) {
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
@OptIn(ExperimentalCoroutinesApi::class)
private suspend fun UserApiClient.Companion.loginWithKakaoTalk(context: Context): OAuthToken =
    suspendCancellableCoroutine { continuation ->
        instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                continuation.resumeWithException(error)
            } else if (token != null) {
                continuation.resume(token) {
                    Log.e("SearchMovie", "카카오톡으로 로그인 실패", it)
                }
            } else {
                continuation.resumeWithException(RuntimeException("Can't Receive Kaokao Access Token"))
            }
        }
    }

// 카카오 계정으로 로그인 시도
@OptIn(ExperimentalCoroutinesApi::class)
private suspend fun UserApiClient.Companion.loginWithKakaoAccount(context: Context): OAuthToken =
    suspendCancellableCoroutine { continuation ->
        instance.loginWithKakaoAccount(context,
            callback = { token, error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else if (token != null) {
                    continuation.resume(token) {
                        Log.e("SearchMovie", "카카오계정으로 로그인 실패", it)
                    }
                }
            })
    }

private suspend fun UserApiClient.Companion.hasToken(): Boolean =
    if (AuthApiClient.instance.hasToken()) {
        getAccessTokenInfo()
    } else {
        false
    }

@OptIn(ExperimentalCoroutinesApi::class)
private suspend fun UserApiClient.Companion.getAccessTokenInfo(): Boolean =
    suspendCancellableCoroutine { continuation ->
        instance.accessTokenInfo { _, error ->
            if (error != null) {
                continuation.resume(false) {
                    Log.e("SearchMovie", "토큰 가져오기 실패", it)
                }
            } else {
                continuation.resume(true) {
                    Log.e("SearchMovie", "토큰 가져오기 실패", it)
                }
            }
        }
    }