package com.openwallet.playerframe.bean

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PlayerTypeTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_PlayerType() {
        PlayerType.EXO_PLAYER
        PlayerType.IJK_PLAYER
        ScaleType.CENTER
    }
}