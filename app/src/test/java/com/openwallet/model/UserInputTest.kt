package com.openwallet.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserInputTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_UserInput() {
        val defaultUserInput = UserInput()

        var userInput = UserInput("email","name","password","confirmedPassword","15911111111","86")
        userInput.confirmedPassword = "1111111"
        userInput.countryCode = "86"
        userInput.email = "test@com"
        userInput.name = ""
        userInput.password = "168"
        userInput.phone = "911"
        userInput.loginName = "loginName"
        userInput.loginPassword = "loginPassword"
        System.out.println(userInput.email+userInput.name+userInput.password+userInput.confirmedPassword+userInput.phone
                +userInput.phone+userInput.countryCode+userInput.loginPassword+userInput.loginName)

        userInput.reset()
    }
}