package com.hellspawn287.customer.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

abstract class AbstractRestControllerTest {
    static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
