package com.said.B30.config;

import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

@ControllerAdvice
public class WebConfig {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        Locale locale = new Locale("pt", "BR");
        DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        format.setParseBigDecimal(true);

        binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, format, true));
    }
}
