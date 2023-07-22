package ru.edosgolt.j2j.agent.model.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paged<T> {
    private Page<T> page;
    private Paging paging;

}
