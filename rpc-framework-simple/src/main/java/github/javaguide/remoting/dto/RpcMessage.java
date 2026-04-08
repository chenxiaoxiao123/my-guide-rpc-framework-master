package github.javaguide.remoting.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcMessage {

    /**
     * rpc message type
     */
    private byte messageType;

    /**
     * Serialization type
     */
    private byte codec;

    /**
     * compress type
     */
    private byte compress;

    /**
     * request Id
     */
    private int requestId;

    /**
     * request data
     */
    private Object data;
}
