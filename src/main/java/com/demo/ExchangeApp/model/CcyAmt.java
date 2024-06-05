package com.demo.ExchangeApp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public record CcyAmt(
        @JacksonXmlProperty(localName = "Ccy")
        String ccy,

        @JacksonXmlProperty(localName = "Amt")
        double amt
) {}

