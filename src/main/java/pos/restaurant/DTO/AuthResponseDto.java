package pos.restaurant.DTO;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String accesToken;
    private String tokenType = "Bearer ";
    public AuthResponseDto(String accesToken) {
        this.accesToken = accesToken;
    }
}
