package pro.fessional.wings.slardar.servlet.resolver;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleTimeZoneAwareLocaleContext;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.web.servlet.i18n.AbstractLocaleContextResolver;
import pro.fessional.mirana.i18n.LocaleResolver;
import pro.fessional.mirana.i18n.ZoneIdResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZoneId;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import static pro.fessional.wings.slardar.servlet.WingsServletConst.ATTR_I18N_CONTEXT;

/**
 * @author trydofor
 * @since 2019-06-30
 */
@Getter
public class WingsLocaleResolver extends AbstractLocaleContextResolver {

    private final Set<String> localeParam = new LinkedHashSet<>();
    private final Set<String> localeCookie = new LinkedHashSet<>();
    private final Set<String> localeHeader = new LinkedHashSet<>();
    private final Set<String> zoneidParam = new LinkedHashSet<>();
    private final Set<String> zoneidCookie = new LinkedHashSet<>();
    private final Set<String> zoneidHeader = new LinkedHashSet<>();

    public void addLocaleCookie(Collection<String> keys) {
        localeCookie.addAll(keys);
    }

    public void addLocaleHeader(Collection<String> keys) {
        localeHeader.addAll(keys);
    }

    public void addLocaleParam(Collection<String> keys) {
        localeParam.addAll(keys);
    }

    public void addZoneidCookie(Collection<String> keys) {
        zoneidCookie.addAll(keys);
    }

    public void addZoneidHeader(Collection<String> keys) {
        zoneidHeader.addAll(keys);
    }

    public void addZoneidParam(Collection<String> keys) {
        zoneidParam.addAll(keys);
    }

    @NotNull
    @Override
    public LocaleContext resolveLocaleContext(@NotNull HttpServletRequest request) {
        return resolveI18nContext(request);
    }

    public TimeZoneAwareLocaleContext resolveI18nContext(HttpServletRequest request) {

        Object obj = request.getAttribute(ATTR_I18N_CONTEXT);
        if (obj instanceof TimeZoneAwareLocaleContext) {
            return (TimeZoneAwareLocaleContext) obj;
        }

        final Locale locale = resolveUserLocale(request);
        final TimeZone timeZone = resolveUserTimeZone(request);

        SimpleTimeZoneAwareLocaleContext context = new SimpleTimeZoneAwareLocaleContext(locale, timeZone);
        request.setAttribute(ATTR_I18N_CONTEXT, context);

        return context;
    }

    @Override
    public void setLocaleContext(@NotNull HttpServletRequest request, HttpServletResponse response, LocaleContext context) {
        if (context instanceof TimeZoneAwareLocaleContext) {
            request.setAttribute(ATTR_I18N_CONTEXT, context);
            return;
        }

        final Locale locale = context.getLocale();
        final TimeZone timeZone = resolveUserTimeZone(request);

        context = new SimpleTimeZoneAwareLocaleContext(locale == null ? Locale.getDefault() : locale, timeZone);
        request.setAttribute(ATTR_I18N_CONTEXT, context);
    }

    // /////////////////
    private TimeZone resolveUserTimeZone(HttpServletRequest request) {

        for (String s : zoneidParam) {
            String q = request.getParameter(s);
            if (q != null && !q.isEmpty()) {
                return ZoneIdResolver.timeZone(q);
            }
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (String s : zoneidCookie) {
                for (Cookie c : cookies) {
                    if (c.getName().equalsIgnoreCase(s)) {
                        return ZoneIdResolver.timeZone(c.getValue());
                    }
                }
            }
        }

        for (String s : zoneidHeader) {
            String h = request.getHeader(s);
            if (h != null && !h.isEmpty()) {
                return ZoneIdResolver.timeZone(h);
            }
        }

        return TimeZone.getDefault();
    }

    private Locale resolveUserLocale(HttpServletRequest request) {

        for (String s : localeParam) {
            String q = request.getParameter(s);
            if (q != null && !q.isEmpty()) {
                return LocaleResolver.locale(q);
            }
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (String s : localeCookie) {
                for (Cookie c : cookies) {
                    if (c.getName().equalsIgnoreCase(s)) {
                        return LocaleResolver.locale(c.getValue());
                    }
                }
            }
        }

        for (String s : localeHeader) {
            String h = request.getHeader(s);
            if (h != null && !h.isEmpty()) {
                return LocaleResolver.locale(h);
            }
        }

        return Locale.getDefault();
    }

    public static class Context implements TimeZoneAwareLocaleContext {

        private final Locale locale;
        private final TimeZone timeZone;
        private final ZoneId zoneId;

        public Context(Locale locale, TimeZone timeZone, ZoneId zoneId) {
            this.locale = locale;
            this.timeZone = timeZone;
            this.zoneId = zoneId;
        }

        @Override
        public TimeZone getTimeZone() {
            return timeZone;
        }

        @Override
        public Locale getLocale() {
            return locale;
        }

        @NotNull
        public ZoneId getZoneId() {
            return zoneId;
        }
    }
}
