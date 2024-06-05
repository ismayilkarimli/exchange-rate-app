package com.demo.ExchangeApp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public record FxRate(
        @JacksonXmlProperty(localName = "Tp")
        String tp,

        @JacksonXmlProperty(localName = "Dt")
        String dt,

        @JacksonXmlProperty(localName = "CcyAmt")
        @JacksonXmlElementWrapper(useWrapping = false)
        List<CcyAmt> ccyAmts
) {}
