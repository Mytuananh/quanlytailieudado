package com.dado.quanlytailieu.util;

import io.jsonwebtoken.lang.Maps;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@UtilityClass
public class AppUtils {

    public static boolean isAdmin(Authentication auth) {
        var securityExpression = new SecurityExpressionRoot(auth) {};
        return securityExpression.hasRole("ADMIN");
    }

    public static boolean run(BindingResult bindingResult, RedirectAttributes redirectAttributes, Supplier<String> supplier) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", DisplayUtils.getErrorMessage(bindingResult));
            return false;
        }
        return run(redirectAttributes, supplier);
    }

    public static boolean run(RedirectAttributes redirectAttributes, Supplier<String> supplier) {
        try {
            redirectAttributes.addFlashAttribute("success", supplier.get());
            return true;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", DisplayUtils.getErrorMessage(e));
            return false;
        }
    }

    public static Object runApi(BindingResult bindingResult, Runnable runnable) {
        return runApi(bindingResult, () -> {
            runnable.run();
            return Collections.emptyMap();
        });
    }

    public static Object runApi(Runnable runnable) {
        return runApi(() -> {
            runnable.run();
            return Collections.emptyMap();
        });
    }

    public static Object runApi(BindingResult bindingResult, Supplier<?> supplier) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Maps.of("error", DisplayUtils.getErrorMessage(bindingResult)));
        }
        return runApi(supplier);
    }

    public static Object runApi(Supplier<?> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Maps.of("error", DisplayUtils.getErrorMessage(e)));
        }
    }

    @Nullable
    public static <T> T getOrNull(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return null;
        }
    }
}
