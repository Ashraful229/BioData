package helper.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponse {
    private Boolean status;
    private Integer code;
    private String message;
    private Long totalItems;
    private Integer totalPages;
    private String reverseSortDir;
    private Integer currentPage;
    private List<Object> data;
}
