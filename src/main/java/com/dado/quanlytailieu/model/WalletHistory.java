package com.dado.quanlytailieu.model;

import com.dado.quanlytailieu.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class WalletHistory {
    private final List<Amount> amounts;
    private int bonus;
    private int money;
    private int afterBonus;
    private int afterMoney;

    public WalletHistory(String json) {
        amounts = new ArrayList<>();
        if (StringUtils.hasText(json)) {
            amounts.addAll(JsonUtils.toObject(json, new TypeReference<>() {}));
        }
    }

    public String getJson() {
        return JsonUtils.toString(amounts);
    }

    private Amount lastAmount() {
        if (amounts.isEmpty()) {
            var amount = new Amount();
            amounts.add(amount);
        }
        return amounts.get(amounts.size() - 1);
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Amount {
        private int money;
        private int bonus;
    }
}
