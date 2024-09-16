package pos.restaurant.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pos.restaurant.models.EmployeeAccount;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeAccountDto {
    private Long id;
    private String name;
    private String role;

    public static EmployeeAccountDto toDto(EmployeeAccount employeeAccount) {
        EmployeeAccountDto employeeAccountDto = new EmployeeAccountDto();
        employeeAccountDto.setId(employeeAccount.getId());
        employeeAccountDto.setName(employeeAccount.getName());
        employeeAccountDto.setRole(employeeAccount.getRole().name());
        return employeeAccountDto;
    }
}
