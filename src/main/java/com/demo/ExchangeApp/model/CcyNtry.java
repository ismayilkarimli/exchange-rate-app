package com.demo.ExchangeApp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public record CcyNtry(
        @JacksonXmlProperty(localName = "Ccy")
        String ccy
) { }
