package com.openwallet.playerframe.impl

import com.openwallet.playerframe.bean.PlayerState
import io.mockk.MockKAnnotations
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SimplePlayerListenerTest : TestCase() {
    val simplePlayerListener = SimplePlayerListener()
    @Before
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
    }
    @Test
    fun testOnError() {
        simplePlayerListener.onError(400,"error")
    }
    @Test
    fun testOnStateChanged() {

        simplePlayerListener.onStateChanged(PlayerState.STOPPED)
    }
    @Test
    fun testOnVideoSizeChanged() {
        simplePlayerListener.onVideoSizeChanged(400,800)
    }
    @Test
    fun testOnLoadingStart() {
        simplePlayerListener.onLoadingStart()
    }
    @Test
    fun testOnLoadingEnd() {
        simplePlayerListener.onLoadingEnd()
    }
}