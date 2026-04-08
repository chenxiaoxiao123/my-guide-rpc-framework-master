package github.javaguide.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoadBalanceEnum {
    
    LOADBALANCE("loadBalance"),
    LOADBALANCENEW("loadBalanceNew");
    
    private final String name;
    
}
