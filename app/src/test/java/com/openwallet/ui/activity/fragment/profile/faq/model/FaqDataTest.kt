package com.openwallet.ui.activity.fragment.profile.faq.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FaqDataTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_FaqData() {
        val expectedData = FaqData("answer",1,"question","1",true)
        val faqData = FaqData("answer",1,"question","1",false)
        faqData.isExpandedQuestion = true
        faqData.answer
        faqData.question
        faqData.category
        faqData.isExpandedQuestion
        faqData.id
        assertEquals(expectedData.isExpandedQuestion,faqData.isExpandedQuestion)
    }
}