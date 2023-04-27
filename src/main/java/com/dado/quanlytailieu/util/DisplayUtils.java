package com.dado.quanlytailieu.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component("display") // for Thymeleaf
@Slf4j
@RequiredArgsConstructor
public class DisplayUtils {
    public static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("MM-uuuu");
    private static final MessageSource MESSAGE_SOURCE;
    private static final DecimalFormat THOUSAND_FORMAT = new DecimalFormat("#,###");
    private static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM");
    private static final DateTimeFormatter SHORT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-uuuu");
    private static final List<Integer> LIMITS = List.of(20, 50, 100, 1000);
    private final Environment environment;
    private final WebProperties webProperties;

    static {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        MESSAGE_SOURCE = messageSource;
    }

    public static String formatThousand(Integer amount) {
        return THOUSAND_FORMAT.format(amount);
    }

    public static String formatVnd(Integer amount) {
        return amount == null ? null : THOUSAND_FORMAT.format(amount) + "đ";
    }

//    public static String format(ItemEntity item) {
//        return String.format("%s (%s/%s)", item.getName(), formatVnd(item.getPrice()), format(item.getUnit()));
//    }
//
//    public static String format(ItemUnit unit) {
//        return switch (unit) {
//            case CAI -> "cái";
//            case HOP -> "hộp";
//            case GOI -> "gói";
//            case LON -> "lon";
//            case TUI -> "túi";
//            case CHAI -> "chai";
//            case LO -> "lọ";
//        };
//    }
//
//    public static String format(OrderStatus status) {
//        return switch (status) {
//            case NEW -> "Chờ thanh toán";
//            case PAID -> "Đã thanh toán";
//            case CANCEL -> "Đã hủy";
//            case EXPIRED -> "Tự hủy quá hạn thanh toán";
//        };
//    }
//
//    public static String format(OrderFilterStatus status) {
//        return switch (status) {
//            case NEW -> "Chờ thanh toán";
//            case PAID -> "Đã thanh toán";
//            case CANCEL -> "Đã hủy";
//        };
//    }
//
//    public static String formatOption(PaymentMethod paymentMethod, Integer balance) {
//        if (paymentMethod == PaymentMethod.WALLET) {
//            return "Ví Item (" + formatVnd(balance) + ")";
//        }
//        return formatOption(paymentMethod);
//    }
//
//    public static String formatOption(PaymentMethod paymentMethod) {
//        return switch (paymentMethod) {
//            case BANK -> "Thanh toán luôn: Chuyển khoản";
//            case MOMO -> "Thanh toán luôn: MoMo";
//            default -> formatPay(paymentMethod);
//        };
//    }
//
//    public static String formatType(TransactionEntity entity) {
//        if (entity.getType() == TransactionType.PAY_ORDER) {
//            return String.format("Thanh toán <a href=\"#\" onclick=\"openOrder(%d)\">đơn hàng %d</a>",
//                    entity.getOrderId(), entity.getOrderId());
//        }
//        return format(entity.getType());
//    }
//
//    public static String adminFormatType(TransactionEntity entity) {
//        if (entity.getType() == TransactionType.PAY_ORDER) {
//            return String.format("Thanh toán <a href=\"#\" onclick=\"openAppView('/orders/%d','%s');return false;\">đơn hàng %d</a>",
//                    entity.getOrderId(), entity.getUserId(), entity.getOrderId());
//        }
//        return format(entity.getType());
//    }
//
//    public static String amountClass(TransactionEntity entity) {
//        return switch (entity.getType()) {
//            case TOPUP_WALLET -> "text-success";
//            case PAY_ORDER -> entity.getPaymentMethod() == PaymentMethod.WALLET ? null : "text-danger";
//            default -> null;
//        };
//    }
//
//    public static String formatPay(PaymentMethod paymentMethod) {
//        if (paymentMethod == null) {
//            return null;
//        }
//        return switch (paymentMethod) {
//            case WALLET -> "Trừ ví";
//            case BANK -> "Ngân hàng";
//            case MOMO -> "MoMo";
//            case ADMIN -> "Admin";
//        };
//    }
//
//    public static String format(ItemCategory itemCategory) {
//        if (itemCategory == null) {
//            return null;
//        }
//        return switch (itemCategory) {
//            case BREAD -> "Ăn nhẹ";
//            case SNACK -> "Ăn vặt";
//            case DRINK -> "Đồ uống";
//        };
//    }

    public static String getErrorMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream().map(e -> {
                    if (e instanceof FieldError fe) {
                        return fe.getField() + " " + fe.getDefaultMessage();
                    }
                    return e.getDefaultMessage();
                })
                .collect(Collectors.joining(". "));
    }

    public static String getErrorMessage(Exception e) {
        if (e instanceof AppException ae) {
            return ae.getMessage();
        }
        log.error(e.getMessage(), e);
        return MESSAGE_SOURCE.getMessage("errorDefault", null, Locale.getDefault());
    }

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }

    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static String formatLastCheck(LocalDate date, LocalDate now) {
        if (date == null) {
            return null;
        }
        if (date.equals(now)) {
            return "Hôm nay";
        }
        if (date.equals(now.minusDays(1))) {
            return "Hôm qua";
        }
        if (date.equals(now.minusDays(2))) {
            return "Hôm kia";
        }
        if (date.isAfter(now.minusMonths(1))) {
            return String.format("%d ngày trước", ChronoUnit.DAYS.between(date, now));
        }
        if (date.isAfter(now.minusYears(1))) {
            return String.format("%d tháng trước", ChronoUnit.MONTHS.between(date, now));
        }
        return date.format(DATE_FORMATTER);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    public static String formatShortDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(SHORT_DATE_TIME_FORMATTER);
    }

    public static String formatShortDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(SHORT_DATE_FORMATTER);
    }

    public String getLogo() {
        String env = environment.getActiveProfiles()[0];
        return env.equalsIgnoreCase("prod") ? "/image/logo.png" : "/image/logo_test.png";
    }

//    public static String formatT(TransactionStatus status, Map<TransactionStatus, Integer> countMap) {
//        if (status == null) {
//            return "Tất cả (" + getCount(null, countMap) + ")";
//        }
//        return format(status) + " (" + getCount(status, countMap) + ")";
//    }
//
//    public static String format(OrderFilterStatus status, Map<OrderFilterStatus, Integer> countMap) {
//        return format(status) + " (" + getCount(status, countMap) + ")";
//    }
//
//    public static <K> int getCount(K key, Map<K, Integer> countMap) {
//        if (key == null) {
//            return countMap.values().stream().mapToInt(c -> c).sum();
//        }
//        return countMap.getOrDefault(key, 0);
//    }
//
//    public static String format(TransactionStatus status) {
//        return switch (status) {
//            case NEW -> "Chờ duyệt";
//            case WAIT -> "Chưa chuyển tiền";
//            case DONE -> "Hoàn thành";
//            case CANCEL -> "Hủy";
//        };
//    }
//
//    public static String formatAmount(TransactionEntity transaction) {
//        var text = formatVnd(transaction.getAmount());
//        return transaction.getBonus() == null ? text
//                : text + " + " + formatVnd(transaction.getBonus().getBonus());
//    }
//
//    public static String formatPayment(TransactionEntity transaction) {
//        String text = null;
//        if (transaction.getPaymentMethod() == PaymentMethod.ADMIN) {
//            if (UserId.SALARY.equals(transaction.getUpdatedBy())) {
//                // Trả công cho user
//                // https://docs.google.com/spreadsheets/d/1ZwOxwXYFaa00iB7e4S58cPWpKu0aSiSoSW-GzDIYK6o/edit#gid=575558450
//                text = "Salary";
//            }
//        }
//        if (text == null) {
//            text = formatPay(transaction.getPaymentMethod());
//        }
//        if (transaction.getBonus() == null) {
//            return text;
//        }
//        return text + switch (transaction.getBonus().getBonusType()) {
//            case FIRST_TOPUP -> ", bonus lần đầu";
//            case MONTHLY -> ", bonus hàng tháng";
//        };
//    }
//
//    public static String formatStatus(OrderEntity order) {
//        var text = format(order.getStatus());
//        if (order.getStatus() == OrderStatus.CANCEL && !Objects.equals(order.getUserId(), order.getUpdatedBy())) {
//            text = text + " bởi Admin";
//        }
//        return text;
//    }
//
//    public static String format(TransactionType type) {
//        return switch (type) {
//            case TOPUP_WALLET -> "Nạp tiền vào ví";
//            case PAY_ORDER -> "Mua hàng";
//            case DONATE -> "Donate";
//        };
//    }
//
//    public static int getCurrent(Page<?> page) {
//        return Math.min(page.getNumber(), page.getTotalPages()) + 1;
//    }
//
//    public static int getFrom(Page<?> page) {
//        return Math.max(getCurrent(page) - 2, 1);
//    }
//
//    public static int getTo(Page<?> page) {
//        return Math.min(getCurrent(page) + 2, page.getTotalPages());
//    }
//
//    public static List<Integer> getLimits(Page<?> page) {
//        if (LIMITS.contains(page.getSize())) {
//            return LIMITS;
//        }
//        var limits = new ArrayList<>(LIMITS);
//        limits.add(page.getSize());
//        limits.sort(Comparator.comparing(Function.identity()));
//        return limits;
//    }
//
//    public static String cancelNote(TransactionEntity transaction) {
//        if (transaction.getStatus() == TransactionStatus.CANCEL) {
//            String note = "Đã hủy bởi ";
//            note += transaction.getUserId().equals(transaction.getUpdatedBy()) ? "bạn" : "Admin";
//            return note + " vào " + formatDateTime(transaction.getUpdatedAt());
//        }
//        return null;
//    }
//
//    public static String format(List<AdminPermissionEntity> adminPermissionList) {
//        return adminPermissionList.stream()
//                .map(e -> e.getPermission().name())
//                .collect(Collectors.joining(", "));
//    }
//
//    public static boolean isPathAdmin() {
//        String path = getCurrentHttpRequest().getRequestURI();
//        return path.startsWith("/admin");
//    }
//
//    private static HttpServletRequest getCurrentHttpRequest() {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (requestAttributes instanceof ServletRequestAttributes) {
//            return ((ServletRequestAttributes) requestAttributes).getRequest();
//        }
//        throw new AppException("Not called in the context of an HTTP request");
//    }
//
//    public static String formatSearchForm(Object form) {
//        Map<String, Object> map = JsonUtils.toMap(form);
//        Map<String, Object> newMap = new HashMap<>();
//        map.forEach((k, v) -> {
//            if (v instanceof LocalDate d) {
//                newMap.put(k, formatDate(d));
//            } else if (!ObjectUtils.isEmpty(v) && !"ALL".equals(v.toString())) {
//                newMap.put(k, v);
//            }
//        });
//        if (newMap.isEmpty()) {
//            return null;
//        }
//        return newMap.toString();
//    }
//
//    public static String format(TransAccount type) {
//        return switch (type) {
//            case VCB_HIENDT -> "Vietcombank HienDT";
//            case VCB_TAMND -> "Vietcombank TamND";
//            case MOMO_HANN -> "MoMo HaNN";
//            case MOMO_TAMND -> "MoMo TamND";
//        };
//    }
//
//    public String getAdminUrl() {
//        String url = webProperties.adminUrl();
//        return url == null ? "" : url;
//    }
//
//    public String getAppUrl() {
//        String url = webProperties.appUrl();
//        return url == null ? "" : url;
//    }
//
//    public String getAdminHome() {
//        return getAdminUrl() + Constants.ADMIN_HOME;
//    }
//
//    public String getAppHome() {
//       return getAppUrl() + Constants.APP_HOME;
//    }
//
//    public static String format(ExtTransactionStatus status) {
//        return switch (status) {
//            case NOT_LINK -> "Chờ duyệt";
//            case LINK_INVALID -> "Đã duyệt (số tiền không khớp)";
//            case LINK_VALID -> "Đã duyệt";
//            case UNKNOWN -> "Không có giao dịch tương ứng";
//            case DELETED -> "Đã xóa";
//        };
//    }
//
//    public static String getWeekendCls(LocalDate date) {
//        return DatetimeUtils.isWeekend(date) ? "weekend" : "";
//    }
//
//    public List<String> format(ImportOrderEntity entity) {
//        List<String> lines = new ArrayList<>();
//        for (var oi : entity.getOrderItems()) {
//            var i = oi.getItem();
//            var line = String.format("%s %s * %s = %s", oi.getCount(), format(i.getUnit()), i.getName(),
//                    formatVnd(oi.getAmount()));
//            if (StringUtils.hasText(oi.getNote())) {
//                line += " (" + oi.getNote() + ")";
//            }
//            lines.add(line);
//        }
//        if (entity.getCostsIncurred() != null && entity.getCostsIncurred() != 0) {
//            lines.add("Chi phí phát sinh " + DisplayUtils.formatVnd(entity.getCostsIncurred()));
//        }
//        if (entity.getDiscount() != null && entity.getDiscount() != 0) {
//            lines.add("Chiết khấu " + DisplayUtils.formatVnd(entity.getDiscount()));
//        }
//        return lines;
//    }
//
//    public static String paySpecialUserIds() {
//        return PaymentMethod.ADMINS.toString();
//    }
//
//    public static List<String> getSearchMonths() {
//        var now = LocalDate.now().withDayOfMonth(1);
//        var date = now.minusMonths(12);
//        if (date.isBefore(Constants.FIRST_RELEASE)) {
//            date = Constants.FIRST_RELEASE;
//        }
//        List<String> yearMonths = new ArrayList<>();
//        while (!date.isAfter(now)) {
//            yearMonths.add(MONTH_FORMATTER.format(date));
//            date = date.plusMonths(1);
//        }
//        Collections.reverse(yearMonths);
//        return yearMonths;
//    }
}
