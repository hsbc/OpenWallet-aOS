package com.openwallet.playerframe.bean

enum class PlayerState {

    IDLE,

    /**
     * If the prepare method is called, the player will load the video header
     */
    PREPARING,

    /**
     * This state means that the video has been loaded, and the user can call the play method to play the video
     */
    PREPARED,

    PLAYING,

    /**
     * You can call the play method to resume play
     */
    PAUSED,

    /**
     *  You can cal the play method to replay
     */
    STOPPED,

    /**
     *  This state means that the video is completed or occur something wrong
     */
    COMPLETED,

    /**
     * After this state is called,you can do nothing
     */
    RELEASED
}