package com.openwallet.model.token

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TokenInfoEntityTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_TokenInfoEntity() {
        val tokenInfoEntity = TokenInfoEntity("tokenOwner",System.currentTimeMillis(),3600L)
        tokenInfoEntity.tokenOwner
        tokenInfoEntity.createTime
        tokenInfoEntity.expirationTime
    }
}