package com.openwallet.ui.activity.fragment.home;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ImageGetterTest {

    @Mock
    private TextView mockText;
    @Mock
    private Context mockC;

    private ImageGetter imageGetterUnderTest;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        imageGetterUnderTest = new ImageGetter(mockText, mockC);
    }

    @Test
    public void testGetDrawable() {
        // Setup
        when(mockText.getContext()).thenReturn(null);
        when(mockText.getResources()).thenReturn(Resources.getSystem());

        // Run the test
        final Drawable result = imageGetterUnderTest.getDrawable("source");

        // Verify the results
        //verify(imageGetterUnderTest, Mockito.times(1)).getDrawable("source");

    }
}
