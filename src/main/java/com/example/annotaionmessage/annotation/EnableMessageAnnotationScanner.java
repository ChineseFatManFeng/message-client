package com.example.annotaionmessage.annotation;


import com.example.annotaionmessage.launch.AnnotationScannerSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(AnnotationScannerSelector.class)
public @interface EnableMessageAnnotationScanner {


}
