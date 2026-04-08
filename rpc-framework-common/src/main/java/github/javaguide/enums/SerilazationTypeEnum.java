package github.javaguide.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum SerilazationTypeEnum {
    KYRO((byte) 0x01, "kyro"),
    PROTOSTUFF((byte) 0x02, "protostuff"),
    HESSIAN((byte) 0x03, "hessian");

    private final byte code;
    private final String name;

    private static String getName(byte code){
        for (SerilazationTypeEnum s: SerilazationTypeEnum.values()){
            if (s.getCode() == code){
                return s.name;
            }
        }
        return null;
    }

}
