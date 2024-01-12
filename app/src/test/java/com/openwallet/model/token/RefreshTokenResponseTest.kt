package com.openwallet.model.token

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RefreshTokenResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_RefreshTokenResponse() {
        val refreshTokenResponse = RefreshTokenResponse("token")
        refreshTokenResponse.token
        val refreshTokenRequestBody = RefreshTokenRequestBody("refreshToken")
        refreshTokenRequestBody.refreshToken
    }
}