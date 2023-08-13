package com.xingyuv.captcha.aot;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

public class AJCaptchaRuntimeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.proxies()
                .registerJdkProxy(TypeReference.of("jakarta.servlet.http.HttpServletRequest"))
        ;

        hints.resources()
                .registerPattern("defaultImages/jigsaw/original/*.png")
                .registerPattern("defaultImages/jigsaw/slidingBlock/*.png")
                .registerPattern("defaultImages/pic-click/*.png")
                .registerPattern("fonts/*.ttf")
        ;
    }
}
