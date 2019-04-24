package com.statflo.challenge.rest_api.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Locale;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class MessageHelperServiceTest {
    @InjectMocks
    private MessageHelperService messageHelperService;
    @Mock
    private MessageSource messageSource;
    private Locale locale = Locale.getDefault();

    @Test
    public void testGetMessage() {
        String messageCode = "ABC";
        String message = UUID.randomUUID().toString();
        when(messageSource.getMessage(eq(messageCode), any(), eq(locale))).thenReturn(message);
        String result = messageHelperService.getMessage(messageCode);
        assertEquals(message, result);
    }
}