package com.openwallet.view

enum class State {
    None,
    Loading, // when loading data
    Content, // show data
    Empty, // no data
    Error // load data fail
}