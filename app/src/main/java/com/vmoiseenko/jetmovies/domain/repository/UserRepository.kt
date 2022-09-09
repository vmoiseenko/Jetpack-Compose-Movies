package com.vmoiseenko.jetmovies.domain.repository

import android.content.SharedPreferences
import com.vmoiseenko.jetmovies.domain.network.model.Account
import com.vmoiseenko.jetmovies.domain.network.model.CredentialsRequestBody
import com.vmoiseenko.jetmovies.domain.network.model.RequestTokenRequestBody
import com.vmoiseenko.jetmovies.domain.network.model.Session
import com.vmoiseenko.jetmovies.domain.network.proxy.MoviesClient
import javax.inject.Inject
import javax.inject.Named

interface UserRepository {
    suspend fun signIn(username: String, password: String): Account
    suspend fun getSession(): Pair<Account, String>?
    suspend fun logout()
}

class UserRepositoryImpl @Inject constructor(
    private val moviesClient: MoviesClient,
    @Named("userPreference") private val sharedPreferences: SharedPreferences
) : UserRepository {

    override suspend fun signIn(username: String, password: String): Account {
        val requestToken = moviesClient.getRequestToken().getOrThrow().requestToken
        val validateToken = moviesClient.validateToken(
            CredentialsRequestBody(
                username,
                password,
                requestToken
            )
        ).getOrThrow().requestToken
        val sessionId = moviesClient.createSession(
            RequestTokenRequestBody(
                validateToken
            )
        ).getOrThrow().sessionId
        val account = moviesClient.getAccount(sessionId).getOrThrow()
        saveAccount(sessionId, account.id, account.name)

        return account
    }

    override suspend fun getSession(): Pair<Account, String>? {
        val id = sharedPreferences.getInt(ACCOUNT_ID, 0)
        val username = sharedPreferences.getString(USERNAME, null) ?: ""
        val session = sharedPreferences.getString(SESSION, null)
        return if (id != 0 && session != null) {
            Account(id, username) to session
        } else null
    }

    override suspend fun logout() {
        val session = sharedPreferences.getString(SESSION, null) ?: ""
        moviesClient.deleteSession(Session(true, session)).fold(
            { clear() },
            { clear() }
        )
    }

    private fun saveAccount(session: String, id: Int, username: String) {
        sharedPreferences
            .edit()
            .putString(SESSION, session)
            .putInt(ACCOUNT_ID, id)
            .putString(USERNAME, username)
            .apply()
    }

    private fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        const val ACCOUNT_ID = "accountId"
        const val USERNAME = "username"
        const val SESSION = "session"
    }
}