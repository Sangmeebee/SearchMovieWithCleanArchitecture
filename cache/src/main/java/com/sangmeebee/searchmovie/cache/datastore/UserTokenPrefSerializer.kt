package com.sangmeebee.searchmovie.cache.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.sangmeebee.searchmovie.cache.UserTokenPref
import java.io.InputStream
import java.io.OutputStream

object UserTokenPrefSerializer : Serializer<UserTokenPref> {
    override val defaultValue: UserTokenPref = UserTokenPref.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): UserTokenPref {
        try {
            return UserTokenPref.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: UserTokenPref, output: OutputStream) = t.writeTo(output)
}
