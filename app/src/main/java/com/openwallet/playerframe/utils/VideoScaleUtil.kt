package com.openwallet.playerframe.utils

import com.openwallet.playerframe.bean.ScaleInfo
import com.openwallet.playerframe.bean.ScaleType

object VideoScaleUtil {

    fun calculateScaleInfo(videoWidth : Int
                           ,videoHeight : Int
                           ,containerWidth : Int
                           , containerHeight : Int
                           ,scaleType: ScaleType
    ) : ScaleInfo {
        return when(scaleType){
            ScaleType.CENTER -> getCenterScaleInfo(videoWidth,videoHeight,containerWidth,containerHeight)
            ScaleType.FULL_SCREEN -> getFullScreenScaleInfo(videoWidth,videoHeight,containerWidth,containerHeight)
            ScaleType.FIT_SHORT_SIDE -> getFitShortSideScaleInfo(videoWidth,videoHeight,containerWidth,containerHeight)
            ScaleType.FIT_LONG_SIDE -> getFitLongSideScaleInfo(videoWidth,videoHeight,containerWidth,containerHeight)
        }
    }

    private fun getFitLongSideScaleInfo(videoWidth : Int
                               ,videoHeight : Int
                               ,containerWidth : Int
                               , containerHeight : Int) : ScaleInfo {
        var resultWidth = 0
        var resultHeight = 0
        var marginLeft = 0
        var marginTop = 0

        if(containerWidth > containerHeight) {
            val scale = containerWidth * 1.0 / videoWidth
            resultWidth = containerWidth
            resultHeight = (videoHeight * scale).toInt()
            marginTop = (containerHeight - resultHeight) / 2
        } else {
            val scale = containerHeight * 1.0 / videoHeight
            resultHeight = containerHeight
            resultWidth = (videoWidth * scale).toInt()
            marginLeft = (containerWidth - resultWidth) / 2
        }
        return ScaleInfo(resultWidth,resultHeight,marginLeft,marginTop)
    }

    private fun getFitShortSideScaleInfo(videoWidth : Int
                               ,videoHeight : Int
                               ,containerWidth : Int
                               , containerHeight : Int) : ScaleInfo {
        var resultWidth = 0
        var resultHeight = 0
        var marginLeft = 0
        var marginTop = 0

        if(containerWidth > containerHeight) {
            val scale = containerHeight * 1.0 / videoHeight
            resultHeight = containerHeight
            resultWidth = (videoWidth * scale).toInt()
            marginLeft = (containerWidth - resultWidth) / 2
        } else {
            val scale = containerWidth * 1.0 / videoWidth
            resultWidth = containerWidth
            resultHeight = (videoHeight * scale).toInt()
            marginTop = (containerHeight - resultHeight) / 2
        }
        return ScaleInfo(resultWidth,resultHeight,marginLeft,marginTop)
    }

    private fun getFullScreenScaleInfo(videoWidth : Int
                               ,videoHeight : Int
                               ,containerWidth : Int
                               , containerHeight : Int) : ScaleInfo {
        return ScaleInfo(containerWidth,containerHeight,0,0)
    }

    private fun getCenterScaleInfo(videoWidth : Int
                           ,videoHeight : Int
                           ,containerWidth : Int
                           , containerHeight : Int) : ScaleInfo {
        val  marginLeft = (containerWidth - videoWidth) / 2
        val  marginTop = (containerHeight - videoHeight) / 2
        return ScaleInfo(videoWidth, videoHeight, marginLeft, marginTop)
    }

}