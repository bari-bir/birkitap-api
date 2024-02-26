package kz.baribir.birkitap.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {
    private String username;
    private String type;
    private String uuid;
}
