package com.openwallet.playerframe.bean

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PlayerStateTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_PlayerState() {
        PlayerState.COMPLETED
        PlayerState.IDLE
        PlayerState.PAUSED
        PlayerState.PLAYING
        PlayerState.PREPARED
        PlayerState.PREPARING
        PlayerState.RELEASED
        PlayerState.STOPPED
    }
}